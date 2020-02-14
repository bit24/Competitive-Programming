#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp> // Common file
#include <ext/pb_ds/tree_policy.hpp> // Including tree_order_statistics_node_update

using namespace __gnu_pbds;

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


// set
typedef tree<
        pi,
        null_type,
        less<pi>,
        rb_tree_tag,
        tree_order_statistics_node_update>
        ordered_set;

const int MAXN = 10e5 + 100;
const int INF = 1e9;

struct LSegTr {
    int tr[4 * MAXN], lz[4 * MAXN];

    void push(int i, int l, int r) {
        tr[i] += lz[i];
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return -INF;
        }
        push(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }

    void u(int i, int l, int r, int s, int e, int d) {
        push(i, l, r); // pushed early to use in recalculation of parent
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            lz[i] += d;
            push(i, l, r);
            return;
        }
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    void set(int i, int l, int r, int x, int v) {
        push(i, l, r);
        if (l == r) {
            tr[i] = v;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            set(i * 2, l, mid, x, v);
            push(i * 2 + 1, mid + 1, r);
        } else {
            set(i * 2 + 1, mid + 1, r, x, v);
            push(i * 2, l, mid);
        }
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    void b(int i, int l, int r, vi &init) {
        lz[i] = 0;
        if (l == r) {
            tr[i] = init[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, init);
        b(i * 2 + 1, mid + 1, r, init);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

} lSegTr;


int N, Q;

ordered_set vals;

vi countScans(vi A, vi X, vi V) {
    vector<pi> allVal;

    vi a(A.begin(), A.end());
    N = a.size();
    Q = X.size();

    for (int i = 0; i < N; i++) {
        allVal.pb({a[i], i});
    }

    for (int i = 0; i < Q; i++) {
        allVal.pb({V[i], X[i]});
    }

    sort(allVal.begin(), allVal.end());
    allVal.erase(unique(allVal.begin(), allVal.end()), allVal.end());

//    ps(allVal);

    for (int i = 0; i < N; i++) {
        vals.insert({a[i], i});
    }

    int M = allVal.size();

    vi init(M, -INF);

    for (int cP = 0; cP < N; cP++) {
        pi cVal = {a[cP], cP};
        int gP = vals.order_of_key(cVal);

        int index = lower_bound(allVal.begin(), allVal.end(), cVal) - allVal.begin();
        init[index] = cP - gP;
    }

//    ps(init);

    lSegTr.b(1, 0, M - 1, init);


//    for (int i = 0; i < M; i++) {
//        pr(lSegTr.q(1, 0, M - 1, i, i), " ");
//    }
//    ps();
//    ps("checkAll: ", lSegTr.q(1, 0, M - 1, 0, 6));


    vi ans(Q);
    for (int cQ = 0; cQ < Q; cQ++) {
        int cP = X[cQ];
        pi oV = {a[cP], cP};
        pi nV = {V[cQ], cP};

        a[cP] = V[cQ];
        vals.erase(oV);
        vals.insert(nV);

        int oIndex = lower_bound(allVal.begin(), allVal.end(), oV) - allVal.begin();
        int nIndex = lower_bound(allVal.begin(), allVal.end(), nV) - allVal.begin();

        if (oIndex + 1 < M) {
//            ps(oIndex + 1, M - 1, +1);
            lSegTr.u(1, 0, M - 1, oIndex + 1, M - 1, +1);
        }

//        for (int i = 0; i < M; i++) {
//            pr(lSegTr.q(1, 0, M - 1, i, i), " ");
//        }
//        ps();
//        ps("checkAll: ", lSegTr.q(1, 0, M - 1, 0, 6));


        if (nIndex + 1 < M) {
//            ps(nIndex + 1, M - 1, -1);
            lSegTr.u(1, 0, M - 1, nIndex + 1, M - 1, -1);
        }

//        for (int i = 0; i < M; i++) {
//            pr(lSegTr.q(1, 0, M - 1, i, i), " ");
//        }
//        ps();
//        ps("checkAll: ", lSegTr.q(1, 0, M - 1, 0, 6));


//        ps(cQ);
//        ps(oV, nV);
//        ps(oIndex, nIndex);

        lSegTr.set(1, 0, M - 1, oIndex, -INF);

        int gP = vals.order_of_key(nV);
        lSegTr.set(1, 0, M - 1, nIndex, cP - gP);

        ans[cQ] = lSegTr.q(1, 0, M - 1, 0, M - 1);

//        ps(a);

//        for (int i = 0; i < M; i++) {
//            pr(lSegTr.q(1, 0, M - 1, i, i), " ");
//        }
//        ps();
//        ps("checkAll: ", lSegTr.q(1, 0, M - 1, 0, M - 1));
//        ps("check: ", lSegTr.q(1, 0, M - 1, 4, 4));
//        ps("check: ", lSegTr.q(1, 0, M - 1, 4, 5));
//        ps("check: ", lSegTr.q(1, 0, M - 1, 4, 6));
//        ps("check: ", lSegTr.q(1, 0, M - 1, 0, 6));
    }
    return ans;
}
