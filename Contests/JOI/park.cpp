#include <bits/stdc++.h>
#include "Joi.h"

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

namespace debug {
    const int DEBUG = true;

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x);

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x);

    template<class T>
    void pr(const vector<T> &x);

    template<class T>
    void pr(const set<T> &x);

    template<class T1, class T2>
    void pr(const map<T1, T2> &x);

    template<class T>
    void pr(const T &x) { if (DEBUG) cout << x; }

    template<class T, class... Ts>
    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }

    template<class T>
    void prIn(const T &x) {
        pr("{");
        bool fst = 1;
        for (auto &a : x) {
            pr(fst ? "" : ", ", a), fst = 0;
        }
        pr("}");
    }

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x) { prIn(x); }

    template<class T>
    void pr(const vector<T> &x) { prIn(x); }

    template<class T>
    void pr(const set<T> &x) { prIn(x); }

    template<class T1, class T2>
    void pr(const map<T1, T2> &x) { prIn(x); }

    void ps() { pr("\n"), cout << flush; }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;


namespace shared {
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
using namespace shared;

void Joi(int N, int M, int A[], int B[], ll X, int T) {
    prep(N, M, A, B);

//    ps(assigned);

    int bits[60];

    for (int i = 0; i < 60; i++) {
        bits[i] = ((1ll << i) & X) != 0;
    }

    for (int i = 0; i < N; i++) {
        if (type[i] != -1) {
            MessageBoard(i, bits[type[i]]);
        } else {
            MessageBoard(i, 0); // default 0
        }
    }
}
