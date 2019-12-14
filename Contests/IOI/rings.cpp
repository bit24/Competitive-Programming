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

const int MAXN = 1e6 + 10;

struct Graph {
    bool pos = false;
    int crit;
    int opp[MAXN];
};

Graph cand[4];

vi aL[MAXN];

int opp[MAXN], sz[MAXN];
int nLoop = 0;
int numCrit = 0;

int N;
int phase = 2;

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


void Init(int iN) {
    N = iN;
    for (int i = 0; i < N; i++) {
        opp[i] = i;
        sz[i] = 1;
    }
    numCrit = N;
}

vector<pi> edges;

inline void addEdge(Graph &cG, int u, int v) {
//    ps("edge:", u, v);
    if (u == cG.crit || v == cG.crit) {
        return;
    }

    if (cG.opp[u] == -1 || cG.opp[v] == -1 || cG.opp[u] == v) {
//        ps(u, v);
//        ps(cG.opp[u], cG.opp[v]);
        cG.pos = false;
        return;
    }

    int e1 = cG.opp[u];
    int e2 = cG.opp[v];

    cG.opp[u] = cG.opp[v] = -1;
    cG.opp[e1] = e2;
    cG.opp[e2] = e1;
}

void build(Graph &cG) {
//    ps("crit:", cG.crit);
    cG.pos = true;
    for (int i = 0; i < N; i++) {
        cG.opp[i] = i;
    }

    for (pi cE : edges) {
        addEdge(cG, cE.f, cE.s);
        if (!cG.pos) {
            return;
        }
    }
//    ps("fin build");
}

void Link(int u, int v) {
    edges.pb({u, v});

    if (phase == 2) {
        aL[u].pb(v);
        aL[v].pb(u);

        if (aL[u].size() == 3) {
            cand[0].crit = u;
            build(cand[0]);

            for (int i = 0; i < aL[u].size(); i++) {
                cand[i + 1].crit = aL[u][i];
                build(cand[i + 1]);
            }
            phase = 3;
            return;
        } else if (aL[v].size() == 3) {
            cand[0].crit = v;
            build(cand[0]);

            for (int i = 0; i < aL[v].size(); i++) {
                cand[i + 1].crit = aL[v][i];
                build(cand[i + 1]);
            }
            phase = 3;
            return;
        }

        assert(opp[u] != -1 && opp[v] != -1);

        if (opp[u] == v) {
            nLoop++;
            if (nLoop == 1) {
                numCrit = sz[u];
            } else {
                numCrit = 0;
            }
            opp[u] = opp[v] = -1;
            return;
        }

        int e1 = opp[u];
        int e2 = opp[v];
        int nS = sz[u] + sz[v];

        opp[u] = opp[v] = -1;
        opp[e1] = e2;
        opp[e2] = e1;
        sz[e1] = nS;
        sz[e2] = nS;
    } else {
        for (Graph &cG : cand) {
            if (!cG.pos) {
                continue;
            }
            addEdge(cG, u, v);
        }
    }
}

int CountCritical() {
    if (phase == 2) {
        return numCrit;
    } else {
        int ans = 0;
        for (Graph &cG : cand) {
            if (cG.pos) {
                ans++;
            }
        }
        return ans;
    }
}
