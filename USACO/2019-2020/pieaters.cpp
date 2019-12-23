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


int N, M;

const int MAXN = 305;

int maxW[MAXN][MAXN][MAXN];

int dp[MAXN][MAXN];

int w[MAXN][MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    freopen("pieaters.in", "r", stdin);
    freopen("pieaters.out", "w", stdout);

    cin >> N >> M;

    memset(maxW, 0, sizeof(maxW));
    memset(dp, 0, sizeof(dp));
    memset(w, 0, sizeof(w));

    for (int i = 0; i < M; i++) {
        int cW, l, r;
        cin >> cW >> l >> r;
        l--, r--;
        w[l][r] = cW;
    }

//    ps("read");

    for (int i = 0; i < N; i++) {
        for (int len = 1; len <= N; len++) {
            for (int l = 0; l + len - 1 < N; l++) {
                int r = l + len - 1;
                if (len > 1) {
                    maxW[i][l][r] = max(maxW[i][l + 1][r], maxW[i][l][r - 1]);
                }
                if (l <= i && i <= r) {
                    maxW[i][l][r] = max(maxW[i][l][r], w[l][r]); // c interval contains i
                }
            }
        }
    }

    for (int len = 1; len <= N; len++) {
        for (int l = 0; l + len - 1 < N; l++) {
            int r = l + len - 1;
            for (int split = l; split <= r; split++) {
                int cSum = maxW[split][l][r];
                if (l < split) {
                    cSum += dp[l][split - 1];
                }
                if (split < r) {
                    cSum += dp[split + 1][r];
                }
//                if (l == 0 && r == 2 && split == 0) {
//                    ps("split", split);
//                    ps(maxW[0][0][2]);
//                    ps(dp[1][2]);
//                    ps(cSum);
//                }
                dp[l][r] = max(dp[l][r], cSum);
            }
        }
    }

//    ps("spec");
//    ps(maxW[])
//
//    for (int i = 0; i < N; i++) {
//        for (int j = 0; j < N; j++) {
//            pr(dp[i][j], " ");
//        }
//        ps();
//    }

//    ps("extra");
//    ps(dp[1][2]);
//    ps(dp[0][2]);
//    ps(maxW[0][0][2]);


    cout << dp[0][N - 1] << endl;
}