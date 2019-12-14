#include <bits/stdc++.h>
#include "game13.h"

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

// only earns 80 pts.

namespace debug {
    int DEBUG = false;

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

const int MAXN = 22000000;

int R, C;

int lC[MAXN], rC[MAXN];
ll tr[MAXN];
// let tr[0] = 0 to represent all blank ranges
int nN = 2;

inline ll gcd(ll X, ll Y) {
    ll tmp;
    while (X != Y && Y != 0) {
        tmp = X;
        X = Y;
        Y = tmp % Y;
    }
    return X;
}

void update1(int i, int l, int r, int x, ll v) {
//    ps("update1", i, l, r, x, v);
    if (l == r) {
        tr[i] = v;
        return;
    }
    int mid = (l + r) / 2;
    if (x <= mid) {
        if (lC[i] == 0) lC[i] = nN++;
        update1(lC[i], l, mid, x, v);
    } else {
        if (rC[i] == 0) rC[i] = nN++;
        update1(rC[i], mid + 1, r, x, v);
    }
    tr[i] = gcd(tr[lC[i]], tr[rC[i]]);
}

ll query1(int i, int l, int r, int s, int e) {
    if (i == 0) return 0; // range all 0's
    if (e < l || r < s) {
        return 0;
    }

    if (s <= l && r <= e) {
        return tr[i];
    }

    int mid = (l + r) / 2;
    return gcd(query1(lC[i], l, mid, s, e), query1(rC[i], mid + 1, r, s, e));
}

ll update2(int i, int l, int r, int x, int y, ll v) {
    if (tr[i] == 0) tr[i] = nN++;
    int cT = tr[i];
//    ps();

    if (l == r) {
        update1(cT, 0, 1e9, y, v);
        return v;
    }

    int mid = (l + r) / 2;

    ll lRes = 0, rRes = 0;
    if (x <= mid) {
        if (lC[i] == 0) lC[i] = nN++;
        lRes = update2(lC[i], l, mid, x, y, v);
        rRes = query1(tr[rC[i]], 0, 1e9, y, y);
    } else {
        if (rC[i] == 0) rC[i] = nN++;
        lRes = query1(tr[lC[i]], 0, 1e9, y, y);
        rRes = update2(rC[i], mid + 1, r, x, y, v);
    }

    ll nVal = gcd(lRes, rRes);
//    ps("update2", i, l, r, x, y, v);
//    ps(nVal);
    update1(cT, 0, 1e9, y, nVal);
    return nVal;
}


ll query2(int i, int l, int r, int s, int e, int s2, int e2) {
    if (i == 0 || tr[i] == 0) return 0;
    if (e < l || r < s) {
        return 0;
    }

    if (s <= l && r <= e) {
        return query1(tr[i], 0, 1e9, s2, e2);
    }

    int mid = (l + r) / 2;
    return gcd(query2(lC[i], l, mid, s, e, s2, e2), query2(rC[i], mid + 1, r, s, e, s2, e2));
}

void init(int iR, int iC) {
    R = iR, C = iC;
}

void update(int P, int Q, ll K) {
//    ps("updating", P, Q, K);

    update2(1, 0, 1e9, P, Q, K);
//    ps(K);
//    if (K == 12) {
//        ps("request");
//        ps(query2(1, 0, 1e9, 0, 1, 0, 2));
//        ps(query2(1, 0, 1e9, 0, 1, 0, 1));
//        ps(query2(1, 0, 1e9, 0, 1, 1, 2));
//    }
//    DEBUG = true;
}

ll calculate(int P, int Q, int U, int V) {
    ll ans = query2(1, 0, 1e9, P, U, Q, V);
    return ans;
}
