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


const int MAXN = 2e5 + 100;

struct BIT {
    int tr[MAXN];

    void u(int i, int d) {
        i++;
        while (i < MAXN) {
            tr[i] += d;
            i += (i & -i);
        }
    }

    int q(int i) {
        i++;
        int s = 0;
        while (i > 0) {
            s += tr[i];
            i -= (i & -i);
        }
        return s;
    }

    int q(int l, int r) {
        if (l > r) return 0;
        return q(r) - q(l - 1);
    }
} bit;

vi loc[2][MAXN];

int match[MAXN];

int used[MAXN];

ll count_swaps(vi a) {
//    ps(a);
    int N = a.size();
    memset(bit.tr, 0, sizeof(bit.tr));

    for (int i = 0; i < N; i++) {
//        ps(i);
        if (a[i] < 0) {
            loc[0][-a[i]].pb(i);
//            ps(loc[0][-a[i]]);
        } else {
            loc[1][a[i]].pb(i);
//            ps(loc[1][a[i]]);
        }
    }
//    ps("init");
//    ps(loc[0][1]);
//    ps("init2");

    for (int sz = 1; sz <= 1e5; sz++) {
//        ps("nat");
//        ps(sz);
//        ps(loc[0][sz]);
//        ps(loc[1][sz]);
        assert(loc[0][sz].size() == loc[1][sz].size());

        for (int i = 0; i < loc[0][sz].size(); i++) {
            int x = loc[0][sz][i];
            int y = loc[1][sz][i];
            match[x] = y;
            match[y] = x;
        }
    }


    for (int i = 0; i < N; i++) {
        bit.u(i, 1);
    }
    fill(used, used + MAXN, 0);

    ll ans = 0;
    for (int i = 0; i < N; i++) {
        if (!used[i]) {
            int cM = match[i];
            assert(i < cM);

            int count = bit.q(i + 1, cM - 1);
            if (a[i] < 0) {
                ans += count;
            } else {
                ans += count + 1;
            }

            bit.u(i, -1);
            bit.u(cM, -1);

            used[i] = true;
            used[cM] = true;
        }
    }
    return ans;
}