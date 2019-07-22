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

vector<vi> occur;
vector<vl> minC, dp;

ll gV(int l, int r) {
    if (l == r + 1) {
        return 1;
    }
    return dp[l][r];
}

void calc(int l, int r) {
    int cMinC = minC[l][r];
    vi &cOccur = occur[cMinC];
    int fI = cOccur[0];
    int lI = cOccur[cOccur.size() - 1];
    if (fI < l || r < lI) {
        dp[l][r] = 0;
        return;
    }

    ll sumR = 0;
    for (int nR = lI; nR <= r; nR++) {
        sumR = (sumR + gV(lI + 1, nR) * gV(nR + 1, r)) % MOD;
    }

    ll sumL = 0;
    for (int nL = fI; nL >= l; nL--) {
        sumL = (sumL + gV(l, nL - 1) * gV(nL, fI - 1)) % MOD;
    }

    ll inProd = 1;

    for (int i = 0; i + 1 < cOccur.size(); i++) {
        inProd = inProd * gV(cOccur[i] + 1, cOccur[i + 1] - 1) % MOD;
    }

    dp[l][r] = (sumL * sumR % MOD) * inProd % MOD;
}

int main() {
    int N, M;
    cin >> M >> N;

    vi a;
    for (int i = 0; i < N; i++) {
        int x;
        cin >> x;
        x--;
        if (a.empty() || a[a.size() - 1] != x) {
            a.pb(x);
        }
    }

    N = a.size();

    if (N > 2 * M) {
        cout << 0 << endl;
        return 0;
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

    occur.resize(M);
    for (int i = 0; i < N; i++) {
        occur[a[i]].pb(i);
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