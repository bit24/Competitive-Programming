#include <bits/stdc++.h>
#include "Ioi.h"

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

namespace shared2 {
    const int MAXN = 1e4 + 10;

    int N;
    int M;

    vi aL[MAXN];
    vi tL[MAXN];
    int par[MAXN];

    bool vis[MAXN];
    int maxH[MAXN];
    int type[MAXN];

    int mode = -1;

    void dfs(int cV) {
        vis[cV] = true;
        for (int aV : aL[cV]) {
            if (!vis[aV]) {
                dfs(aV);
                tL[cV].pb(aV);
                par[aV] = cV;
            }
        }
    }

    void dfs2(int cV) {
        for (int aV : tL[cV]) {
            dfs2(aV);
            maxH[cV] = max(maxH[cV], maxH[aV] + 1);
        }
    }

    void dfs3(int cV, int cT) {
        type[cV] = cT;
        for (int aV : tL[cV]) {
            dfs3(aV, (cT + 1) % 60);
        }
    }

    vi assigned;
    int nType = 0;

    int dfs4(int cV, int bound, bool lastG) {
        if (bound == 0) return 0;

        type[cV] = nType++;
        assigned.pb(cV);
        bound--;

        if (!lastG) { // if it isn't last, pick randomly
            for (int aV : tL[cV]) {
                bound = dfs4(aV, bound, false);
            }
            assigned.pb(-1);
            return bound;
        }

        assert(lastG);

        int bigC = -1;
        for (int aV : tL[cV]) {
            if (maxH[aV] == maxH[cV] - 1) {
                bigC = aV;
                break;
            }
        }

        if (tL[cV].empty()) {
            assert(bound == 0);
            return 0;
        }
        assert(bigC != -1);

        // we need to reserve at least maxH[bigC] for last
        int avail = bound - maxH[bigC];

        for (int aV : tL[cV]) {
            if (aV != bigC) {
                avail = dfs4(aV, avail, false);
            }
        }

        int fBound = maxH[bigC] + avail; // in case we weren't able to fulfill needs earlier
        int rem = dfs4(bigC, fBound, true);
        assert(rem == 0);
        return 0;
    }

    void prep(int iN, int iM, int A[], int B[]) {
        N = iN, M = iM;

        for (int i = 0; i < M; i++) {
            aL[A[i]].pb(B[i]);
            aL[B[i]].pb(A[i]);
        }

        fill(vis, vis + MAXN, false);
        fill(par, par + MAXN, -1);
        dfs(0);

        fill(maxH, maxH + MAXN, 1);
        dfs2(0);

        fill(type, type + MAXN, -1);

        if (maxH[0] >= 60) {
            mode = 0;
            dfs3(0, 0);
        } else {
            mode = 1;
            dfs4(0, 60, true);
        }
    }
}
using namespace shared2;

ll process0(int cV, ll cVal) {
    ll ans = 0;
    ans |= (cVal << type[cV]);

    while (type[cV] != 0) {
        cV = par[cV];
        cVal = Move(cV);
        ans |= (cVal << type[cV]);
    }

    if (cV != 0) {
        cV = par[cV];
        cVal = Move(cV);
        ans |= (cVal << type[cV]);

        while (type[cV] != 0) {
            cV = par[cV];
            cVal = Move(cV);
            ans |= (cVal << type[cV]);
        }

        return ans;
    }

    for (int i = 1; i < 60; i++) {
        for (int aV : tL[cV]) {
            if (maxH[aV] == maxH[cV] - 1) {
                cV = aV;
                cVal = Move(cV);

//                cout << "cVal: " << cVal << endl;
//                cout << "i: " << i << endl;
                ans |= cVal << i;
//                cout << "intAns: " << ans << endl;
                break;
            }
        }
    }
    return ans;
}

ll process1(int cV, ll cVal) {
    while (cV != 0) {
        cV = par[cV];
        cVal = Move(cV);
    }

    ll ans = 0;

    assert(assigned[0] == 0);

    ans |= cVal;

    int i = 1;

    for (int x : assigned) {
        if (x == 0) continue;

        if (x != -1) {
            cV = x;
            cVal = Move(cV);

            ans |= cVal << i;

            i++;
        } else {
            cV = par[cV];
            cVal = Move(cV);
        }
    }
    return ans;
}

ll Ioi(int N, int M, int A[], int B[], int initV, int firstVal, int T) {
    prep(N, M, A, B);

    ll ans;
    if (mode == 0) {
        ans = process0(initV, firstVal);
    } else {
        ans = process1(initV, firstVal);
    }
//    cout << "mode: " << mode << endl;
//    cout << "think: " << ans << endl;
    return ans;
}
