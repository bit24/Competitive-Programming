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

struct Stone {
    int t;
    int e;
    int l;
};

namespace debug {
    const int DEBUG = false;

    void pr(Stone a);

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

    void pr(Stone a) {
        pr("(", a.t, " ", a.e, " ", a.l, ")");
    }

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

bool cmp(Stone a, Stone b) { //returns stone that should go first
    return (ll) a.l * b.t > (ll) b.l * a.t;
}

const int MAXT = 100 * 100 + 50;
ll dp[MAXT];

int main() {
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        int N;
        cin >> N;

        vector<Stone> stones;
        for (int i = 0; i < N; i++) {
            int t, e, l;
            cin >> t >> e >> l;
            stones.pb({t, e, l});
        }
        sort(stones.begin(), stones.end(), cmp);

        memset(dp, 0, sizeof(dp));

        for (Stone cStone : stones) {
            for (int j = MAXT - 1; j >= 0; j--) {
                if (j + cStone.t < MAXT) {
                    dp[j + cStone.t] = max(dp[j + cStone.t], dp[j] + cStone.e - ((ll) cStone.l * j));
                }
            }
        }
        ll ans = 0;
        for (int i = 0; i < MAXT; i++) {
            ans = max(ans, dp[i]);
        }
        cout << "Case #" << cT << ": " << ans << '\n';
    }

    cout << flush;
}
