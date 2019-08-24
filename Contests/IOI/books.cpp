#include <bits/stdc++.h>

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


int N;
vi nxt;

vi id;
vi lM, rM;
int cL, cR;

void includeAll(int nL, int nR) {
    while (nL < cL || cR < nR) {
        while (nL < cL) {
            int cG = id[--cL];
            nL = min(nL, lM[cG]);
            nR = max(nR, rM[cG]);
        }
        while (cR < nR) {
            int cG = id[++cR];
            nL = min(nL, lM[cG]);
            nR = max(nR, rM[cG]);
        }
    }
}

ll calc(int s) {
    int cG = id[s];
    cL = lM[cG], cR = lM[cG];
    includeAll(lM[cG], rM[cG]);

    ll tExt = 0, lExt = 0, rExt = 0;
    int cSide = 0;

    while (cL != 0 || cR != N - 1) {
        cSide = !cSide;
        if (cSide == 0) {
            if (cL == 0) {
                continue;
            }

            lExt++;

            int cGroup = id[cL - 1];

            int nL = lM[cGroup];
            int nR = max(cR, rM[cGroup]);

            for (int i = cL - 1; i >= nL; i--) {
                nL = min(nL, lM[id[i]]);
                nR = max(nR, rM[id[i]]);
            }

            if (nR > cR) {
                // use this one
                tExt += lExt;
                lExt = 0, rExt = 0;
            }
            includeAll(nL, nR);
        } else {
            if (cR == N - 1) {
                continue;
            }

            rExt++;
            int cGroup = id[cR + 1];

            int nL = min(cL, lM[cGroup]);
            int nR = rM[cGroup];

            for (int i = cR + 1; i <= nR; i++) {
                nL = min(nL, lM[id[i]]);
                nR = max(nR, rM[id[i]]);
            }

            if (nL < cL) {
                tExt += rExt;
                lExt = 0, rExt = 0;
            }
            includeAll(nL, nR);
        }
    }

    tExt += lExt + rExt;
    return 2 * tExt;
}


ll minimum_walk(vi p, int s) {
    N = p.size();

    // prune
    int l = 0, r = N - 1;
    while (l < s && p[l] == l) {
        l++;
    }
    while (r > s && p[r] == r) {
        r--;
    }

    for (int i = l; i <= r; i++) {
        nxt.pb(p[i] - l);
    }
    s -= l;
    N = nxt.size();

    //ps("fin prune");

    ll sum = 0;
    for (int i = 0; i < N; i++) {
        sum += abs(nxt[i] - i);
    }

    vector<vi> groups;
    id.resize(N, -1);

    for (int i = 0; i < N; i++) {
        if (id[i] == -1) {
            vi newG;

            int cur = i;
            while (id[cur] == -1) {
                id[cur] = groups.size();
                newG.pb(cur);
                cur = nxt[cur];
            }

            groups.pb(newG);
        }
    }

    //ps("div groups");

    int numG = groups.size();

    for (int i = 0; i < numG; i++) {
        int cLM = N, cRM = 0;
        for (int x : groups[i]) {
            cLM = min(cLM, x);
            cRM = max(cRM, x);
        }
        lM.push_back(cLM);
        rM.push_back(cRM);
    }

    //ps("prep");

    return sum + calc(s);
}