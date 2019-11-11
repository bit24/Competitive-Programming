#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef tuple<int, int, int> ti;

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

bool success = true;
int standardNx;

namespace PSegTr {
    static const int MAXN = 500000, NODES = 50 * MAXN;
    // different definition of lz to nicely implement persistence
    // lz is only for children, node which it is on already contains updated value
    int lC[NODES], rC[NODES], tr[NODES];
    int nx = 0;

    inline int cpy(int o) {
        int n = nx++;
        lC[n] = lC[o], rC[n] = rC[o], tr[n] = tr[o];
        return n;
    }

    // q does not create any new nodes
    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(lC[i], l, mid, s, e) +
               q(rC[i], mid + 1, r, s, e);
    }

    // returns index of updated node
    int u(int i, int l, int r, int x, int d) {
        i = cpy(i);
        if (l == r) {
            tr[i] += d;
            return i;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            lC[i] = u(lC[i], l, mid, x, d);
        } else {
            rC[i] = u(rC[i], mid + 1, r, x, d);
        }
        tr[i] = tr[lC[i]] + tr[rC[i]];
        return i;
    }

    int bEmpty(int l, int r) {
        int i = nx++;
        if (l == r) {
            tr[i] = 0;
            return i;
        }
        int mid = (l + r) / 2;
        lC[i] = bEmpty(l, mid);
        rC[i] = bEmpty(mid + 1, r);
        tr[i] = tr[lC[i]] + tr[rC[i]];
        assert(tr[i] == 0);
        return i;
    }

    int clr() {
        memset(lC, 0, sizeof(lC)), memset(rC, 0, sizeof(rC)), memset(tr, 0, sizeof(tr));
        nx = 0;
    }

    int callsUseK = 0;
    int useK(int n, int u, int l, int r, int k) {
        callsUseK++;
//        ps("l, r, k:", l, r, k);
        if (k == 0) {
            return u;
        }

        int rem = tr[n] - tr[u];
        if (rem < k) {
            success = false;
            return u;
        }

        u = cpy(u);
        tr[u] += k;
        if (l == r) {
            return u;
        }

        int remL = tr[lC[n]] - tr[lC[u]];

        assert(remL >= 0);

        int mid = (l + r) / 2;
        if (remL >= k) {
            lC[u] = useK(lC[n], lC[u], l, mid, k);
        } else {
            lC[u] = lC[n]; // all of it is used, interesting how you can copy subtrees with persistence
            rC[u] = useK(rC[n], rC[u], mid + 1, r, k - remL);
        }
        return u;
    }

    // cut is exclusive
    int callsRemB = 0;
    int remB(int i, int l, int r, int cut) {
        callsRemB++;
        i = cpy(i);
        if (l == r) {
            tr[i] = 0;
            return i;
        }
        int mid = (l + r) / 2;
        if (cut <= mid) {
            lC[i] = remB(lC[i], l, mid, cut);
        } else {
            lC[i] = 0; // don't need to worry about children as we shouldn't ever descend into it again
            rC[i] = remB(rC[i], mid + 1, r, cut);
        }
        tr[i] = tr[lC[i]] + tr[rC[i]];
        return i;
    }

    vector<vi> layers;

    int printTr(int x, int l, int r, int d) {
        layers[d].pb(tr[x]);
        if (l != r) {
            int mid = (l + r) / 2;
            printTr(lC[x], l, mid, d + 1);
            printTr(rC[x], mid + 1, r, d + 1);
        }
    }

    void printTr(int x) {
        layers.clear();
        layers.resize(10);
        printTr(x, 1, MAXN, 0);
        for (int i = 0; i < 10; i++) {
            ps(layers[i]);
        }
    }
}

using namespace PSegTr;

int N;
vector<pi> pts;

vi trees;

int init(int iN, int a[], int b[]) {
//    ps("initstart");
    N = iN;
    for (int i = 0; i < N; i++) {
        pts.pb({a[i], b[i]});
    }
    pts.pb({-1, -1});
    sort(pts.begin(), pts.end());

//    ps("p");

    vector<ti> changes;

    for (int i = 1; i <= N; i++) {
        changes.pb({pts[i].f, pts[i].s, 1});
        changes.pb({pts[i].s + 1, pts[i].s, -1});
    }
    sort(changes.begin(), changes.end());

//    ps("startbuild");

    int cTree = bEmpty(1, MAXN);

//    ps("builtfirst");

    int nC = 0;

    trees.pb(cTree);
    for (int i = 1; i <= N; i++) {
//        ps("fin", i, nx);
        while (nC < changes.size() && get<0>(changes[nC]) <= i) {
            cTree = u(cTree, 1, MAXN, get<1>(changes[nC]), get<2>(changes[nC]));
            nC++;
//            ps(nC, nx);
        }
        trees.pb(cTree);
    }

//    printTr(trees[1]);

    standardNx = nx;
//    ps(standardNx);

//    ps("initfin");
}

int cnt = 0;

int can(int M, int k[]) {
//   ps(cnt++);
    success = true;
    nx = standardNx; // save space by reusing elements
    sort(k, k + M);
//    ps(allK);

    int used = 0;

//    auto start = chrono::high_resolution_clock::now();
    for (int i = 0; i < M; i++) {
        int options = trees[k[i]];

//        ps("options", i);
//        printTr(options);

        used = k[i] - 1 > 0 ? remB(used, 1, MAXN, k[i] - 1) : used;

//        ps("k[i]:", k[i]);
        used = useK(options, used, 1, MAXN, k[i]);

        if (success == false) {
//            ps("failed on:", i);
            return 0;
        }

//        ps("used");
//        printTr(used);
    }
//    auto stop = chrono::high_resolution_clock::now();
//    auto duration = chrono::duration_cast<chrono::milliseconds>(stop - start);
//    ps(duration.count());
    return 1;
}

void fin(){
//    ps("callsUseK", callsUseK);
//    ps("callsRemB", callsRemB);
}

