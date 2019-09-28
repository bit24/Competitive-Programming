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

ll dp[16][1 << 15];

const ll INF = 1e15;

int main() {
    int N;
    cin >> N;

    vi H(N);
    for (int i = 0; i < N; i++) {
        cin >> H[i];
    }

    for (int i = 0; i < 16; i++) {
        for (int j = 0; j < (1 << 15); j++) {
            dp[i][j] = INF;
        }
    }

    for (int i = 0; i < N; i++) {
        dp[1][1 << i] = 0;
    }

    for (int i = 1; i < N; i++) {
        for (int bS = 0; bS < (1 << N); bS++) {
            if (__builtin_popcount(bS) != i) {
                continue;
            }

            for (int nxt = 0; nxt < N; nxt++) {
                if ((bS & (1 << nxt)) == 0) {

                    ll eC = 1;
                    for (int j = 0; j < N; j++) {
                        if ((bS & (1 << j)) != 0) {
                            eC *= abs(H[j] - H[nxt]);
                        }
                    }

                    dp[i + 1][bS | (1 << nxt)] = min(dp[i + 1][bS | (1 << nxt)], dp[i][bS] + eC);
                }
            }
        }
    }

    cout << dp[N][(1 << N) - 1] << endl;
}