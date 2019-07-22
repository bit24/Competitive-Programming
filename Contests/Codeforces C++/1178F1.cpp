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

const int INF = 1e7;

const ll MOD = 998244353;

vi cInd;
vector<vl> minC, dp;

ll gV(int l, int r) {
    if (l == r + 1) {
        return 1;
    }
    return dp[l][r];
}

void calc(int l, int r) {
    int cMinC = minC[l][r];
    int cI = cInd[cMinC];

    ll sumR = 0;
    for (int nR = cI; nR <= r; nR++) {
        sumR = (sumR + gV(cI + 1, nR) * gV(nR + 1, r)) % MOD;
    }

    ll sumL = 0;
    for (int nL = cI; nL >= l; nL--) {
        sumL = (sumL + gV(l, nL - 1) * gV(nL, cI - 1)) % MOD;
    }
    dp[l][r] = sumL * sumR % MOD;
}

int main() {
    int N, M;
    cin >> N >> M;
    vi a(N);
    for (int i = 0; i < N; i++) {
        cin >> a[i];
        a[i]--;
    }

    minC.resize(N);
    for (int i = 0; i < N; i++) {
        minC[i].resize(N);

        int cMin = a[i];
        for (int j = i; j < N; j++) {
            cMin = min(cMin, a[j]);
            minC[i][j] = cMin;
        }
    }

    cInd.resize(N);
    for (int i = 0; i < N; i++) {
        cInd[a[i]] = i;
    }

    dp.resize(N);
    for (int i = 0; i < N; i++) {
        dp[i].resize(N);
        fill(dp[i].begin(), dp[i].end(), 0);
    }

    for (int len = 1; len <= N; len++) {
        for (int l = 0; l < N; l++) {
            int r = l + len - 1;
            if (r >= N) {
                continue;
            }

            calc(l, r);
        }
    }

    cout << dp[0][N - 1] << endl;
}