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

const int MAXN = 1e5 + 10;
const int MAXP = 50;

int N, Q, K;

struct sums {
    ll v[MAXP];

    sums() {
        memset(v, 0, sizeof(v));
    }

    sums const operator+(sums o) {
        sums ret;
        for (int i = 0; i < MAXP; i++) {
            ret.v[i] = v[i] + o.v[i];
        }
        return ret;
    }

    void shift(int x) {
        if (K == 1) return;
        for (int i = 0; i < MAXP; i++) {
            if (i + x < MAXP) {
                v[i] = v[i + x];
            } else {
                v[i] = 0;
            }
        }
    }
};

sums convert(int v) {
    sums ret;
    ret.v[0] = v;
    for (int p = 1; p < MAXP; p++) {
        ret.v[p] = ret.v[p - 1] / K;
    }
    return ret;
}

struct LSegTr {
    sums tr[4 * MAXN];
    int lz[4 * MAXN];

    void b(int i, int l, int r, vi &init) {
        lz[i] = 0;
        if (l == r) {
            tr[i] = convert(init[l]);
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, init);
        b(i * 2 + 1, mid + 1, r, init);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }

    void push(int i, int l, int r) {
        tr[i].shift(lz[i]);
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    sums q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return sums();
        }
        push(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void uShift(int i, int l, int r, int s, int e, int d) {
//        ps(i, l, r, d);
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
        uShift(i * 2, l, mid, s, e, d);
        uShift(i * 2 + 1, mid + 1, r, s, e, d);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }

    void u(int i, int l, int r, int x, int v) {
        push(i, l, r);
        if (l == r) {
            tr[i] = convert(v);
            return;
        }
        int mid = (l + r) / 2;
        push(i * 2, l, mid);
        push(i * 2 + 1, mid + 1, r);

        if (x <= mid) {
            u(i * 2, l, mid, x, v);
        } else {
            u(i * 2 + 1, mid + 1, r, x, v);
        }

        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }
} lSegTr;


//int a[MAXN];
//
//void u(int x, int v) {
//    a[x] = v;
//}
//
//void uShift(int l, int r) {
//    for (int i = l; i <= r; i++) {
//        a[i] /= K;
//    }
//}
//
//ll sum(int l, int r) {
//    ll s = 0;
//    for (int i = l; i <= r; i++) {
//        s += a[i];
//    }
//    return s;
//}

int main() {
    cin >> N >> Q >> K;
//    ps(N);
    vi init(N);
    for (int i = 0; i < N; i++) {
        cin >> init[i];
//        ps(init[i]);
    }
    lSegTr.b(1, 0, N - 1, init);

    for (int i = 0; i < N; i++) {
//        a[i] = init[i];
    }

    for (int i = 0; i < Q; i++) {
        int t, l, r;
        cin >> t >> l >> r;
        if (t == 1) {
            lSegTr.u(1, 0, N - 1, l - 1, r);
//            u(l - 1, r);
        } else if (t == 2) {
            lSegTr.uShift(1, 0, N - 1, l - 1, r - 1, 1);
//            uShift(l - 1, r - 1);
        } else {
            assert(t == 3);
            sums ansSum = lSegTr.q(1, 0, N - 1, l - 1, r - 1);
            ll ans = ansSum.v[0];
//            ll ans2 = sum(l - 1, r - 1);
//            assert(ans == ans2);
            cout << ans << endl;
        }
    }
}