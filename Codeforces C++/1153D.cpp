#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

const int MAXN = 300005;

int o[MAXN];
vi c[MAXN];

int dp[MAXN];

int K = 0;

void dfs(int v) {
    if (c[v].size() == 0) {
        K++;
        dp[v] = -1;
        return;
    }

    for (int cC : c[v]) {
        dfs(cC);
    }

    if (o[v] == 0) {
        int ans = 0;

        for (int cC : c[v]) {
            ans += dp[cC];
        }
        dp[v] = ans;
    } else {
        int ans = -1e9;
        for (int cC : c[v]) {
            ans = max(ans, dp[cC]);
        }
        dp[v] = ans;
    }
}

int main() {
    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> o[i];
    }
    for (int i = 1; i < N; i++) {
        int p;
        cin >> p;
        p--;
        c[p].pb(i);
    }

    dfs(0);
    cout << (K + 1 + dp[0]) << endl;
}
