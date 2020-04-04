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

const int MAXN = 2e3 + 5;
const ll MOD = 1e9 + 7;

int grid[MAXN][MAXN]; // index: column from left, rows up
int preS[MAXN][MAXN];

ll pow2[MAXN];
ll dp[MAXN][MAXN][2];

int N;

int cntBelow(int x, int h) {
    if (h < 0) return 0;
    return preS[x][h];
}

// returns the number of 1 cells above h
int cntAbove(int x, int h) {
    if (h > N) return 0;

    return preS[x][N] - preS[x][h];
}

int main() {
    freopen("sprinklers2.in", "r", stdin);
    freopen("sprinklers2.out", "w", stdout);

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    pow2[0] = 1;
    for (int i = 1; i < MAXN; i++) {
        pow2[i] = pow2[i - 1] * 2 % MOD;
    }

    cin >> N;

//    ps("read");

    memset(grid, 0, sizeof(grid));

    for (int i = 0; i < N; i++) {
        string line;
        cin >> line;
        for (int j = 0; j < N; j++) {
            if (line[j] == '.') {
                grid[j + 1][N - i] = 1;
            }
        }
    }

//    ps("here?");

    memset(preS, 0, sizeof(preS));

    for (int x = 1; x <= N; x++) {
        for (int y = 1; y <= N; y++) {
            preS[x][y] = preS[x][y - 1] + grid[x][y];
        }
    }

//    ps("hi");

    memset(dp, 0, sizeof(dp));

    dp[0][N][0] = dp[0][N][1] = 1; // virtual column that can corner

    for (int x = 1; x <= N; x++) {

        ll dropPreCnt = 0; // stores the sum of above and can corner

        for (int h = N; h >= 0; h--) {
            // case 1: drop

            if (h != N) {
                dropPreCnt = (dropPreCnt + dp[x - 1][h + 1][1]) % MOD;

                if (grid[x][h + 1] == 1) {

                    // case 1a: can corner
                    if (grid[x][h] || h == 0) {
                        int free = cntAbove(x, h + 1) + cntBelow(x, h - 1);
                        dp[x][h][1] = (dp[x][h][1] + dropPreCnt * pow2[free]) % MOD;
                    }

                    // case 2a: cannot corner
                    int free = cntAbove(x, h + 1) + cntBelow(x, h);
                    dp[x][h][0] = (dp[x][h][0] + dropPreCnt * pow2[free]) % MOD;
                }
            }


            // case 2: no drop

            ll preCnt = dp[x - 1][h][0];

            // case 2a: can corner
            if (grid[x][h] || h == 0) {
                int free = cntAbove(x, h) + cntBelow(x, h - 1);

                dp[x][h][1] = (dp[x][h][1] + preCnt * pow2[free]) % MOD;
            }

            // case 2b: cannot corner
            int free = cntAbove(x, h) + cntBelow(x, h);
            dp[x][h][0] = (dp[x][h][0] + preCnt * pow2[free]) % MOD;
        }
    }

//    ps(dp[1][N][1]);

    ll ans = 0;
    for (int h = 0; h <= N; h++) {
        ans = (ans + dp[N][h][1]) % MOD;
    }
    cout << ans << endl;
}