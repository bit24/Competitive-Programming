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
    const int DEBUG = false;

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


const ll MOD = 1e9;
const int MAXN = 1e5 + 10;

int N;
vector<pi> pts;
map<pi, int> comp;

int nC = 0;

ll ans = 0;

int sz[MAXN];
vi aL[MAXN];

int dX[] = {-1, 0, 1, 0};
int dY[] = {0, -1, 0, 1};

int dfs(int cV, int pV) {
    int cSz = sz[cV];
    for (int aV : aL[cV]) {
        if (aV != pV) {
            cSz += dfs(aV, cV);
        }
    }
//    ps(cV, cSz);
    ans = (ans + (ll) cSz * (N - cSz)) % MOD;
    return cSz;
}

void sumV() {
    comp.clear();
    nC = 0;

    memset(sz, 0, sizeof(sz));
    for (int i = 0; i < N; i++) {
        aL[i].clear();
    }

    sort(pts.begin(), pts.end());
//    ps(pts);

    for (int i = 0; i < N; i++) {
        if (i > 0 && pts[i].f == pts[i - 1].f && pts[i].s == pts[i - 1].s + 1) { // one above previous
            comp[pts[i]] = nC - 1;
            sz[nC - 1]++;
        } else {
            comp[pts[i]] = nC++;
            sz[nC - 1]++;
        }
    }

    for (int i = 0; i < N; i++) {
        pi left = {pts[i].f - 1, pts[i].s};
        if (comp.count(left)) {
            int cC = comp[pts[i]];
            int aC = comp[left];
            aL[cC].pb(aC);
            aL[aC].pb(cC);
        }
    }

    for (int i = 0; i < N; i++) {
        aL[i].erase(unique(aL[i].begin(), aL[i].end()), aL[i].end());
    }

//    ps("debug graph");
//    for (int i = 0; i < N; i++) {
//        ps(pts[i]);
//        ps(comp[pts[i]]);
//    }
//
//    for (int i = 0; i < nC; i++) {
//        ps(sz[i]);
//    }
//
//    for (int i = 0; i < nC; i++) {
//        ps(aL[i]);
//    }
//
//    ps("fin");

    for (int i = 0; i < nC; i++) {
        if (aL[i].size() == 1) {
            dfs(i, -1);
            break;
        }
    }
}

int DistanceSum(int iN, int *X, int *Y) {
    N = iN;
    for (int i = 0; i < N; i++) {
        pts.pb({X[i], Y[i]});
    }

    sumV();
//    ps(ans);

    for (int i = 0; i < N; i++) {
        swap(pts[i].f, pts[i].s);
    }
    sumV();
    return ans;
}