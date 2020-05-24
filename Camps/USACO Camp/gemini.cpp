#include <bits/stdc++.h>

typedef long long ll;

using namespace std;

const int MAXN = 310;
ll dp[MAXN][MAXN], nDP[MAXN][MAXN];

ll t[MAXN][MAXN];

const ll INF = 1e15;

int main() {
    int N, M;
    ll K;
    cin >> N >> M >> K;

    for (int i = 1; i <= N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> t[i][j];
        }
    }

    for (int j = 0; j < M; j++) {
        for (int k = 0; k < M; k++) {
            dp[j][k] = INF;
            nDP[j][k] = INF;
        }
    }


    for (int i = 0; i < M; i++) {
        for (int j = 0; j < M; j++) {
            if (i != j) {
                dp[i][j] = 0;
            }
        }
    }

    for (int r = 1; r <= N; r++) {

        for (int j = 0; j < M; j++) {
            ll bCost = INF;
            for (int i = 0; i < M; i++) {
                // change first to character i from character < i
                bCost = min(bCost + K, dp[i][j]);

                if (i == j) continue;
                nDP[i][j] = bCost;
            }


            bCost = INF;
            for (int i = M - 1; i >= 0; i--) {
                bCost = min(bCost + K, dp[i][j]);

                if (i == j) continue;
                nDP[i][j] = min(nDP[i][j], bCost);
            }
        }


        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j] = nDP[i][j];
                nDP[i][j] = INF;
            }
        }

        for (int i = 0; i < M; i++) {
            ll bCost = INF;
            for (int j = 0; j < M; j++) {
                bCost = min(bCost + K, dp[i][j]);

                if (i == j) continue;
                nDP[i][j] = min(nDP[i][j], bCost);
            }
            for (int j = M - 1; j >= 0; j--) {
                bCost = min(bCost + K, dp[i][j]);

                if (i == j) continue;
                nDP[i][j] = min(nDP[i][j], bCost);
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j] = nDP[i][j];
                nDP[i][j] = INF;
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if (i == j) continue;
                nDP[i][j] = dp[i][j] + t[r][i] + t[r][j];
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j] = nDP[i][j];
                nDP[i][j] = INF;
            }
        }
    }

    ll ans = INF;
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < M; j++) {
            ans = min(ans, dp[i][j]);
//            cout << dp[6][i][j] << endl;
        }
    }
    cout << ans << endl;
}