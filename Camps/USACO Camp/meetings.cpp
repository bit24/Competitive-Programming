#include <bits/stdc++.h>

using namespace std;

typedef vector<int> vi;
#define pb push_back

const int MAXN = 305;
const int INF = 1e9;
int N;

int a[MAXN];

vi aL[MAXN];

int sz[MAXN];

void dfs0(int cV, int pV) {
    sz[cV] = 1;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            dfs0(aV, cV);
            sz[cV] += sz[aV];
        }
    }
}

int sDP[MAXN][MAXN][MAXN][2];

int dp[MAXN][2];
int nDP[MAXN][2];

void dfs(int cV, int pV) {
    for (int aV : aL[cV]) {
        if (aV != pV) {
            dfs(aV, cV);
        }
    }

    for (int nodeD = 0; nodeD < N; nodeD++) { // fix at which distance node will be connected

        for (int i = 0; i <= sz[cV]; i++) {
            dp[i][0] = 0;
            dp[i][1] = INF; // impossible to be connected initially
            nDP[i][0] = nDP[i][1] = INF;
        }

        int pSize = 0;

        for (int aV : aL[cV]) {
            if (aV != pV) {

                for (int chK = sz[aV]; chK >= 0; chK--) {
                    for (int preK = pSize; preK >= 0; preK--) {
                        if (preK + chK > N) continue;

                        // case 1: use child as source for nodeD
                        if (nodeD >= 1) {
                            int oCost = dp[preK][0];
                            int chCost = sDP[aV][chK][nodeD - 1][1];
                            nDP[preK + chK][1] = min(nDP[preK + chK][1], oCost + chCost);
                        }

                        // case 2: child also draws from nodeD
                        int oCost = dp[preK][0]; // 2a: not yet sourced
                        int chCost = sDP[aV][chK][nodeD + 1][0];
                        nDP[preK + chK][0] = min(nDP[preK + chK][0], oCost + chCost);

                        oCost = dp[preK][1];
                        chCost = sDP[aV][chK][nodeD + 1][0];
                        nDP[preK + chK][1] = min(nDP[preK + chK][1], oCost + chCost);
                    }
                }

                for (int i = 0; i <= sz[cV]; i++) {
                    dp[i][0] = nDP[i][0];
                    dp[i][1] = nDP[i][1];
                    nDP[i][0] = nDP[i][1] = INF;
                }

                pSize += sz[aV];
            }
        }


        // account for root connection
        for (int k = 0; k <= sz[cV]; k++) {
            // given already has connection at nodeD
            sDP[cV][k][nodeD][1] = min(sDP[cV][k][nodeD][1], dp[k][1] + a[cV] * nodeD);

            // else cV is marked
            if (k >= 1) {
                sDP[cV][k][nodeD][1] = min(sDP[cV][k][nodeD][1], dp[k - 1][0]);
            }

            // else no connection at nodeD
            sDP[cV][k][nodeD][0] = min(sDP[cV][k][nodeD][0], dp[k][0] + a[cV] * nodeD);
        }

        // if you are providing, doesn't hurt to pretend to be actually closer
        for (int k = 1; k <= sz[cV]; k++) {
            sDP[cV][k][nodeD][1] = min(sDP[cV][k][nodeD][1], sDP[cV][k][nodeD - 1][1]);
        }

        for (int k = 0; k <= sz[cV]; k++) {
            sDP[cV][k][nodeD][0] = min(sDP[cV][k][nodeD][0], sDP[cV][k][nodeD][1]);
        }

        // if you are giving, doesn't hurt to be actually closer
        for (int k = sz[cV] - 1; k >= 0; k--) {
            sDP[cV][k][nodeD][0] = min(sDP[cV][k][nodeD][0], sDP[cV][k][nodeD + 1][1]);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> a[i];
    }
    for (int i = 0; i < N - 1; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
        aL[v].pb(u);
    }
    dfs0(0, -1);

    for (int i = 0; i < MAXN; i++) {
        for (int j = 0; j < MAXN; j++) {
            for (int k = 0; k < MAXN; k++) {
                sDP[i][j][k][0] = sDP[i][j][k][1] = INF;
            }
        }
    }

//    cout << "init" << endl;
    dfs(0, -1);

//    cout << "ans" << endl;
    for (int k = 1; k <= N; k++) {
        int ans = INF;
        for (int nodeD = 0; nodeD <= N; nodeD++) {
            ans = min(ans, sDP[0][k][nodeD][1]);
        }
        assert(ans >= 0);
        assert(ans < INF);
        cout << ans << " ";
    }
    cout << endl;

//    assert(sDP[1][1][1][1] == 2);

//    cout << "debug" << endl;
//    for (int nodeD = 0; nodeD <= N; nodeD++) {
//        cout << sDP[1][1][nodeD][0] << endl;
//    }
}