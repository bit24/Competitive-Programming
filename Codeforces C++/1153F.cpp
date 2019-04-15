#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

const ll MOD = 998244353;
const int MAXN = 2005;

int N, K;
ll L;

ll dp[2 * MAXN][MAXN][2];


ll bexp(ll b, int e = MOD - 2) {
    ll c = 1;
    while (e) {
        if (e & 1) {
            c = c * b % MOD;
        }
        b = b * b % MOD;
        e >>= 1;
    }
    return c;
}

int main() {
    cin >> N >> K >> L;

    memset(dp, 0, sizeof(dp));
    dp[0][1][0] = N;


    for (int i = 0; i < 2 * N; i++) {
        for (int j = 0; j <= N; j++) {
            for (int x = 0; x <= 1; x++) {
                if (dp[i][j][x]) {
                    //open new interval
                    if (2 * N - (i + 1) > 1 - x + j) {
                        dp[i + 1][j + 1][x] =
                                (dp[i + 1][j + 1][x] + dp[i][j][x] * (N - ((i + 1 - j - x) / 2 + j))) % MOD;
                    }
                    //close interval
                    if (j) {
                        dp[i + 1][j - 1][x] = (dp[i + 1][j - 1][x] + dp[i][j][x] * j) % MOD;
                    }
                    // add point (only if at least K are open)
                    if (j >= K && !x) {
                        dp[i + 1][j][1] = (dp[i + 1][j][1] + dp[i][j][x]) % MOD;
                    }
                }
            }
        }
    }
    ll fact = 1;

    for (int i = 1; i <= 2 * N + 1; i++) {
        fact = fact * i % MOD;
    }

    ll ans = dp[2 * N][0][1] * bexp(2, N) % MOD * bexp(fact) % MOD * L % MOD;
    cout << ans << endl;
}