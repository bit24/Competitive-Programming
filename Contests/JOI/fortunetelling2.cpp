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


static const int MAXN = 600100, NODES = 9 * MAXN;
const int INF = 1e9;

struct SegTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r, vi &o) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = min(tr[i * 2], tr[i * 2 + 1]);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return INF;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return min(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }
} segTr;


int lC[NODES], rC[NODES], tr[NODES];
int nx = 0;

inline int cpy(int o) {
    int n = nx++;
    lC[n] = lC[o], rC[n] = rC[o], tr[n] = tr[o];
    return n;
}

int b(int l, int r) {
    if (l == r) {
        tr[nx] = 0;
        return nx++;
    }
    int mid = (l + r) / 2;
    lC[nx] = b(l, mid);
    rC[nx] = b(mid + 1, r);
    return nx++;
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

int N, K;

pi c[MAXN];

int op[MAXN];

int rt[MAXN];

set<int> vals;
map<int, int> mp;
map<int, int> rMp;

int nV;

int main() {
    cin >> N >> K;

    for (int i = 0; i < N; i++) {
        cin >> c[i].f >> c[i].s;
        vals.insert(c[i].f);
        vals.insert(c[i].s);
    }
    for (int i = 0; i < K; i++) {
        cin >> op[K - i];
        vals.insert(op[K - i]);
    }

    nV = 0;
    for (int x : vals) {
        mp[x] = nV;
        rMp[nV] = x;
        nV++;
    }

    for (int i = 0; i < N; i++) {
        c[i].f = mp[c[i].f];
        c[i].s = mp[c[i].s];
    }

    for (int i = 0; i < K; i++) {
        op[K - i] = mp[op[K - i]];
    }

    assert(nV <= MAXN);

    rt[0] = b(0, nV);
    for (int i = 1; i <= K; i++) {
        rt[i] = u(rt[i - 1], 0, nV, op[i], 1);
    }

    vi o(nV + 1);
    fill(o.begin(), o.end(), INF);
    for (int i = K; i >= 1; i--) {
        o[op[i]] = i;
    }
    segTr.b(1, 0, nV, o);

    ll ans = 0;
    for (int i = 0; i < N; i++) {
//        ps(c[i]);
        int small = min(c[i].f, c[i].s);
        int big = max(c[i].f, c[i].s);

        if (small == big) {
//            ps("add", rMp[small]);
            ans += rMp[small];
            continue;
        }

        int force = segTr.q(1, 0, nV, small, big - 1);
        force = min(force, K + 1);
//        ps(force);

        int flip = q(rt[force - 1], 0, nV, big, nV);

        if (force == K + 1 && c[i].f == small) flip++;

//        ps(flip);
        if (flip & 1) {
//            ps("add", rMp[small]);
            ans += rMp[small];
        } else {
//            ps("add", rMp[big]);
            ans += rMp[big];
        }
    }
    cout << ans << endl;
}