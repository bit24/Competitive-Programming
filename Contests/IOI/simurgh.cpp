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

int count_common_roads(const vi &roads);

int N, M;
vi u, v, anc, tr, inTr, spec;

void dsu_init() {
    anc.resize(N);
    for (int i = 0; i < N; i++) {
        anc[i] = i;
    }
}

int fRt(int x) {
    return anc[x] == x ? x : anc[x] = fRt(anc[x]);
}

bool merge(int a, int b) {
    a = fRt(a), b = fRt(b);
    if (a == b) {
        return false;
    }
    anc[a] = b;
    return true;
}

vector<vector<pi>> trA, aL;

int goal;
vi path;

bool dfs(int cV, int pV) {
    if (cV == goal) return true;
    for (pi adj : trA[cV]) {
        if (adj.f == pV) continue;
        if (dfs(adj.f, cV)) {
            path.pb(adj.s);
            return true;
        }
    }
    return false;
}

int query(vi &base) {
    dsu_init();
    vi used;

    for (int x : base) {
        used.pb(x);
        assert(merge(u[x], v[x]));
    }

    for (int x : tr) {
        if (merge(u[x], v[x])) {
            used.pb(x);
        }
    }
    assert(used.size() == N - 1);
    return count_common_roads(used);
}

int queryForest(vi &base) {
    dsu_init();
    vi used;

    int oCnt = 0;
    for (int x : base) {
        used.pb(x);
        assert(merge(u[x], v[x]));
    }

    for (int x : tr) {
        if (merge(u[x], v[x]) && spec[x] != -1) {
            used.pb(x);
            oCnt += spec[x];
        }
    }
    assert(used.size() == N - 1);
    return count_common_roads(used) - oCnt;
}

void calcTree() {
    for (int ext = 0; ext < M; ext++) {
        if (inTr[ext]) continue;

        goal = v[ext];
        path.clear();
        dfs(u[ext], -1);

        vi k, uk;
        for (int x : path) {
            if (spec[x] == -1) {
                uk.pb(x);
            } else {
                k.pb(x);
            }
        }

        if (uk.empty()) continue;

        if (k.size()) { // if any are known
            vi base; // query cycle without a known
            base.pb(ext);
            for (int x : path) {
                if (x != k[0]) {
                    base.pb(x);
                }
            }

            int bCnt = query(base);

            for (int exc : uk) { // query cycle without unknowns
                base.clear();
                base.pb(ext);
                for (int inc : path) {
                    if (inc != exc) {
                        base.pb(inc);
                    }
                }

                int cCnt = query(base);
                if (cCnt < bCnt) {
                    spec[exc] = 1;
                } else if (cCnt > bCnt) {
                    spec[exc] = 0;
                } else {
                    spec[exc] = spec[k[0]];
                }
            }
        } else {
            uk.pb(ext);
            vi cnts;

            for (int exc : uk) {
                vi base;
                for (int inc : uk) {
                    if (inc != exc) {
                        base.pb(inc);
                    }
                }
                cnts.pb(query(base));
            }

            int cMax = *max_element(cnts.begin(), cnts.end());
            int cMin = *min_element(cnts.begin(), cnts.end());

            if (cMax == cMin) {
                for (int x : uk) {
                    spec[x] = 0;
                }
            } else {
                for (int i = 0; i < uk.size(); i++) {
                    if (cnts[i] == cMin) {
                        spec[uk[i]] = 1;
                    } else {
                        assert(cnts[i] == cMax);
                        spec[uk[i]] = 0;
                    }
                }
            }
        }
    }
}

void find(vi &edges, int cnt) {
    if (cnt == 0) {
        for (int x : edges) {
            spec[x] = 0;
        }
        return;
    }

    if (edges.size() == cnt) {
        for (int x : edges) {
            spec[x] = 1;
        }
        return;
    }

    int half = edges.size() / 2;
    vi g1, g2;

    for (int i = 0; i < half; i++) {
        g1.pb(edges[i]);
    }
    for (int i = half; i < edges.size(); i++) {
        g2.pb(edges[i]);
    }

    int c1 = queryForest(g1);
    find(g1, c1);
    find(g2, cnt - c1);
}

void calcRem() {
    for (int i = 0; i < N; i++) {
        vi search;
        for (pi x : aL[i]) {
            if (spec[x.s] == -1) {
                search.pb(x.s);
            }
        }
        if (search.empty()) continue;

        int tCnt = queryForest(search);
        find(search, tCnt);
    }
}

vi find_roads(int i0, vi i1, vi i2) {
    N = i0;
    u = i1;
    v = i2;
    M = i1.size();

    dsu_init();

    inTr.resize(M);
    fill(inTr.begin(), inTr.end(), false);
    trA.resize(N);
    aL.resize(N);

    for (int i = 0; i < M; i++) {
        if (merge(u[i], v[i])) {
            tr.pb(i);
            inTr[i] = true;
            trA[u[i]].pb({v[i], i});
            trA[v[i]].pb({u[i], i});
        }
        aL[u[i]].pb({v[i], i});
        aL[v[i]].pb({u[i], i});
    }

    spec.resize(M);
    fill(spec.begin(), spec.end(), -1);

    calcTree();

    for (int x : tr) {
        if (spec[x] == -1) { // bridges
            spec[x] = 1;
        }
    }

    calcRem();

    vi ans;
    for (int i = 0; i < M; i++) {
        if (spec[i]) {
            ans.pb(i);
        }
    }
    return ans;
}