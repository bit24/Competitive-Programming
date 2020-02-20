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

const int MAXN = 3005;
const ll MOD = 1e9 + 7;
ll dp[MAXN][MAXN];

int main() {
    int N, M;
    cin >> N >> M;

    memset(dp, 0, sizeof(dp));

    for (int i = 0; i < N; i++) {
        dp[i][0] = 1;
    }

    for (int j = 0; j < M; j++) {
        dp[0][j] = 1;
    }

    for (int r = 1; r <= N; r++) {
        for (int c = 1; c <= M; c++) {
            // nothing in top row
            ll sum = dp[r - 1][c];

            // one unpaired in top row, 4 possible orientations, all columns
            sum = (sum + 4 * c * dp[r - 1][c - 1]) % MOD;

            // two in same column
            if (r >= 2) {
                sum = (sum + c * (r - 1) * dp[r - 2][c - 1]) % MOD;
            }

            //two in same row
            if (c >= 2) {
                sum = (sum + c * (c - 1) / 2 * dp[r - 1][c - 2]) % MOD;
            }
            dp[r][c] = sum;
        }
    }
    cout << dp[N][M] << endl;
}