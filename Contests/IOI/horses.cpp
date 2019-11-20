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

const int MAXN = 600000;
const ll MOD = 1e9 + 7;

struct ProdTr {
    ll tr[4 * MAXN];

    void b(int i, int l, int r, int o[]) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = tr[i * 2] * tr[i * 2 + 1] % MOD;
    }

    ll q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 1;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) * q(i * 2 + 1, mid + 1, r, s, e) % MOD;
    }

    void u(int i, int l, int r, int x, int d) {
        if (l == r) {
            tr[i] = d;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            u(i * 2, l, mid, x, d);
        } else {
            u(i * 2 + 1, mid + 1, r, x, d);
        }
        tr[i] = tr[i * 2] * tr[i * 2 + 1] % MOD;
    }
} prodTr;

struct MaxTr {
    ll tr[4 * MAXN];

    void b(int i, int l, int r, int o[]) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    ll q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }

    void u(int i, int l, int r, int x, int d) {
        if (l == r) {
            tr[i] = d;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            u(i * 2, l, mid, x, d);
        } else {
            u(i * 2 + 1, mid + 1, r, x, d);
        }
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }
} maxTr;

int N;

vl X, Y;

bool first = true;
set<int> notO;

ll query() {
    if (first) {
//        ps(notO);
        first = false;
    }
    auto it = notO.end(); // at N + 1 mark
    it--;

    int bI = -1;
    ll bY = -1;

    long double bestV = -1;

    ll denom = 1;
    // more than 30 not 1's will produce factor greater than 10^9
    while (denom < 1e9) {
        int last = *it;
        it--;
        int i = *it;
//        ps("i", i);

        ll cY = maxTr.q(1, 1, N, i, last - 1);
        if ((ld) cY / denom > bestV) {
            bestV = (ld) cY / denom;
            bI = i;
            bY = cY;
        }

        denom *= X[i];

        if (it == notO.begin()) {
            break;
        }
    }

//    ps("bI, bY:", bI, bY);

    ll ans = prodTr.q(1, 1, N, 1, bI) * bY % MOD;
    return ans;
}

int init(int iN, int iX[], int iY[]) {
    N = iN;

    fill(prodTr.tr, prodTr.tr + 4 * MAXN, 1);
    fill(maxTr.tr, maxTr.tr + 4 * MAXN, 0);

    //1-indexing
    X.pb(-1);
    Y.pb(-1);

    X.pb(1);
    Y.pb(1);
    for (int i = 0; i < N; i++) {
        X.pb(iX[i]);
        Y.pb(iY[i]);
    }

    N++;

    for (int i = 1; i <= N; i++) {
        prodTr.u(1, 1, N, i, X[i]);
        maxTr.u(1, 1, N, i, Y[i]);
        if (X[i] != 1) {
            notO.insert(i);
        }
    }

    notO.insert(1);
    notO.insert(N + 1);

//    ps(X);
//    ps(Y);
//    ps(notO);
    return query();
}

int updateX(int i, int v) {
    i += 2;
//    ps("changing", i, v);
    X[i] = v;
    prodTr.u(1, 1, N, i, v);
    if (v == 1) {
        notO.erase(i);
    } else {
        notO.insert(i);
    }
    return query();
}

int updateY(int i, int v) {
    i += 2;
    Y[i] = v;
    maxTr.u(1, 1, N, i, v);
    return query();
}