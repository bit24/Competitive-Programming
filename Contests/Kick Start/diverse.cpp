#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;


const int MAXN = 100005;

struct LSegTr {
    int tr[4 * MAXN], lz[4 * MAXN];

    void ps(int i, int l, int r) {
        tr[i] += lz[i];
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        ps(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }

    void u(int i, int l, int r, int s, int e, int d) {
        ps(i, l, r);
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            lz[i] += d;
            ps(i, l, r);
            return;
        }
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }
} lseg;

int a[MAXN];

vi groups[MAXN];

int main() {
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        int N, S;
        cin >> N >> S;
        for (int i = 1; i <= N; i++) {
            cin >> a[i];
        }

        memset(lseg.tr, 0, sizeof(lseg.tr));
        memset(lseg.lz, 0, sizeof(lseg.lz));

        for (int i = 0; i < MAXN; i++) {
            groups[i].clear();
        }

        int ans = 0;
        for (int i = 1; i <= N; i++) {
            ps("u:", 1, i, 1);
            groups[a[i]].pb(i);
            int cSz = groups[a[i]].size();
            if (cSz > S) {
                if (cSz > S + 1) {
                    ps("u:", groups[a[i]][cSz - (S + 2)] + 1, groups[a[i]][cSz - (S + 1)], -S);
                    lseg.u(1, 1, N, groups[a[i]][cSz - (S + 2)] + 1, groups[a[i]][cSz - (S + 1)], -S);
                    ps("u:", groups[a[i]][cSz - (S + 1)] + 1, i, 1);
                    lseg.u(1, 1, N, groups[a[i]][cSz - (S + 1)] + 1, i, 1);
                } else {
                    ps("u:", 1, groups[a[i]][cSz - (S + 1)], -S);
                    lseg.u(1, 1, N, 1, groups[a[i]][cSz - (S + 1)], -S);
                    ps("u:", groups[a[i]][cSz - (S + 1)] + 1, i, 1);
                    lseg.u(1, 1, N, groups[a[i]][cSz - (S + 1)] + 1, i, 1);
                }
            } else {
                lseg.u(1, 1, N, 1, i, 1);
            }
            if (i <= 8) {
                for (int j = 1; j <= 8; j++) {
                    pr(lseg.q(1, 1, N, j, j), " ");
                }
                ps();
            }

            ps("q:", lseg.q(1, 1, N, 1, i));
            ans = max(ans, lseg.q(1, 1, N, 1, i));
        }
        cout << "Case #" << cT << ": " << ans << '\n';
    }
    cout << flush;
}