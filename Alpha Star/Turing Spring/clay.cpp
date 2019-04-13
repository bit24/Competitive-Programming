#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

const int MAXN = 105;
const int MAXH = 60000;
const int INF = 2e9;

int a[MAXN];
int c[MAXN];
int cnt[MAXN];

int dp[MAXH];
int ind[MAXH];
int pre[MAXH];
int jCnt[MAXH];

int main() {
    int N, H;
    cin >> N >> H;

    for (int i = 0; i < N; i++) {
        cin >> a[i] >> c[i];
    }

    for (int i = 0; i < MAXH; i++) {
        dp[i] = INF;
    }
    dp[0] = 0;

    for (int i = N - 1; i >= 0; i--) {
        for (int m = 1; m <= H; m *= 2) {
            int cA = a[i] * m;
            int cC = c[i] * m;

            for (int j = MAXH - 1; j >= 0; j--) {
                if (j + cA < MAXH && dp[j] + cC < dp[j + cA]) {
                    dp[j + cA] = dp[j] + cC;
                    pre[j + cA] = j;
                    ind[j + cA] = i;
                    jCnt[j + cA] = m;
                }
            }

            for (int j = MAXH - 1; j >= 1; j--) {
                if (dp[j] < dp[j - 1]) {
                    dp[j - 1] = dp[j];
                    pre[j - 1] = pre[j];
                    ind[j - 1] = ind[j];
                    jCnt[j - 1] = jCnt[j];
                }
            }
        }
    }

    int i = H;

    while (i > 0) {
        int cP = pre[i];
        cnt[ind[i]] += jCnt[i];
        i = cP;
    }

    for (int i = 0; i < N; i++) {
        cout << cnt[i] << '\n';
    }
    cout << flush;
}