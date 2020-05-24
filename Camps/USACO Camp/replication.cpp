#include <bits/stdc++.h>

using namespace std;

typedef vector<int> vi;
#define pb push_back

const int MAXN = 1e5 + 10;

vi aL[MAXN];

int dp[MAXN];

bool pos(vi costs, int L) {
    if (costs.empty()) return true;

    int c = 1;

    for (int i = 0; i < costs.size(); i++) {
        if (L < costs[i]) return false; // forgotten case?

        while (L > costs[i]) {
            c = min(c * 2, 200000);
            L--;
            if (c == 200000) {
                L = costs[i];
            }
        }

        c--;
        if (c <= 0) return false;
    }
    return true;
}

void dfs(int cV, int pV) {
    vi cCosts;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            dfs(aV, cV);
            cCosts.pb(dp[aV]);
        }
    }

    sort(cCosts.begin(), cCosts.end());
    reverse(cCosts.begin(), cCosts.end());

    int low = 0;
    int hi = 2e5;

    while (low != hi) {
        int mid = (low + hi) / 2;

        if (pos(cCosts, mid)) {
            hi = mid;
        } else {
            low = mid + 1;
        }
    }
    dp[cV] = low;
}


int main() {
    int N;
    cin >> N;

    for (int i = 0; i < N - 1; i++) {
        int a, b;
        cin >> a >> b;
        a--, b--;
        aL[a].pb(b);
        aL[b].pb(a);
    }

    dfs(0, -1);
    int ans = dp[0] + N - 1;
    cout << ans << endl;
}