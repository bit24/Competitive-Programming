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

const int MAXN = 1e6 + 10;
const int INF = 1e8;

int N;
string As, Bs;

int a[MAXN], b[MAXN];

int dp[MAXN][3][2];

int main() {
    cin >> N;
    cin >> As >> Bs;

    As = ' ' + As;
    Bs = ' ' + Bs;

    for (int i = 1; i <= N; i++) {
        a[i] = As[i] - '0';
        b[i] = Bs[i] - '0';
    }
    a[0] = b[0] = 0; // initialize to be the same so no toggle needed

    for (int i = 0; i < MAXN; i++) {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 2; k++) {
                dp[i][j][k] = INF;
            }
        }
    }

    dp[0][0][0] = 0;

    for (int i = 0; i < N; i++) {
        // case 1: nothing
        int tog = a[i] ^b[i];
        int cCost = dp[i][0][0];

        if (cCost != INF) {
            // case 1a: continue nothing
            int nTog = a[i + 1] ^b[i + 1];
            int nCost = cCost + (tog < nTog);

            dp[i + 1][0][0] = min(dp[i + 1][0][0], nCost);

            // case 1b: add a set
            for (int nSet = 0; nSet < 2; nSet++) {
                int nTog = nSet ^b[i + 1];
                int nCost = cCost + 1 + (tog < nTog);

                dp[i + 1][1][nSet] = min(dp[i + 1][1][nSet], nCost);
            }
        }

        // case 2: 1 set
        for (int cSet = 0; cSet < 2; cSet++) {
            int tog = cSet ^b[i];
            int cCost = dp[i][1][cSet];
            if (cCost != INF) {
                // case 2a: continue/change cSet
                for (int nSet = 0; nSet < 2; nSet++) {
                    int nTog = nSet ^b[i + 1];
                    int nCost = cCost + (cSet ^ nSet) + (tog < nTog);
                    dp[i + 1][1][nSet] = min(dp[i + 1][1][nSet], nCost);
                }


                // case 2b: stop set
                int nTog = a[i + 1] ^b[i + 1];
                int nCost = cCost + (tog < nTog);
                dp[i + 1][0][0] = min(dp[i + 1][0][0], nCost);


                // case 2c: add second set that is opposite of cSet
                nTog = (cSet ^ 1) ^ b[i + 1];
                nCost = cCost + 1 + (tog < nTog);
                dp[i + 1][2][cSet] = min(dp[i + 1][2][cSet], nCost);
            }
        }

        // case 3: 2 sets, cSet is the the base set
        for (int cSet = 0; cSet < 2; cSet++) {
            int tog = cSet ^1 ^b[i];
            int cCost = dp[i][2][cSet];
            if (cCost != INF) {
                // case 2a: continue cSet
                int nTog = cSet ^1 ^b[i + 1];
                int nCost = cCost + (tog < nTog);
                dp[i + 1][2][cSet] = min(dp[i + 1][2][cSet], nCost);

                // case 2b: stop second set
                nTog = cSet ^ b[i + 1];
                nCost = cCost + (tog < nTog);
                dp[i + 1][1][cSet] = min(dp[i + 1][1][cSet], nCost);
            }
        }
    }

    int ans = INF;

    assert(dp[N][0][1] == INF);

    // case 1: no set
    ans = min(ans, dp[N][0][0]);

    // case 2: 1 set
    ans = min(ans, dp[N][1][0]);
    ans = min(ans, dp[N][1][1]);

    // case 2: 2 set
    ans = min(ans, dp[N][2][0]);
    ans = min(ans, dp[N][2][1]);


//    cout << dp[7][0][0] << endl;
//    cout << dp[8][0][0] << endl;

    assert(ans >= 0);

    cout << ans << endl;
}