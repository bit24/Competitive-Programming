#pragma GCC optimize ("O3")
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

vector<vi> aList;

vector<vi> comp;
vector<bool> vis;

int dfs(int cV) {
    vis[cV] = true;
    comp[comp.size() - 1].pb(cV);
    for (int aV : aList[cV]) {
        if (!vis[aV]) {
            dfs(aV);
        }
    }
}

vector<vector<bool>> dp;
vector<vector<bool>> sel;

int main() {
    freopen("bitstrings.in", "r", stdin);
    freopen("bitstrings.out", "w", stdout);
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        int N, M;
        cin >> N >> M;
        aList.resize(N);
        for (int i = 0; i < N; i++) {
            aList[i].clear();
        }

        for (int i = 0; i < M; i++) {
            int x, y;
            cin >> x >> y;
            x--, y--;

            while (x < y) {
                aList[x].pb(y);
                aList[y].pb(x);
                x++, y--;
            }
        }

        comp.clear();
        vis.resize(N);
        for (int i = 0; i < N; i++) {
            vis[i] = false;
        }

        for (int i = 0; i < N; i++) {
            if (!vis[i]) {
                comp.pb(vi());
                dfs(i);
            }
        }


        vi opt;

        for (int i = 0; i < comp.size(); i++) {
            opt.pb(comp[i].size());
        }

        dp.resize(opt.size() + 1);
        sel.resize(opt.size() + 1);

        for (int i = 0; i <= opt.size(); i++) {
            dp[i].resize(2 * N + 1);
            sel[i].resize(2 * N + 1);
            for (int j = 0; j <= 2 * N; j++) {
                dp[i][j] = false;
            }
        }

        dp[0][N + 0] = true;
        for (int i = 0; i < opt.size(); i++) {
            for (int j = -N; j <= N; j++) {
                if (dp[i][N + j]) {
                    if (-N <= j - opt[i]) {
                        dp[i + 1][N + j - opt[i]] = true;
                        sel[i + 1][N + j - opt[i]] = false;
                    }
                    if (j + opt[i] <= N) {
                        dp[i + 1][N + j + opt[i]] = true;
                        sel[i + 1][N + j + opt[i]] = true;
                    }
                }
            }
        }

        vector<bool> state(opt.size());

        int cAmt = 0;
        for (int i = 0; i <= N; i++) {
            if (dp[opt.size()][N + i]) {
                cAmt = i;
                break;
            }
        }

        for (int i = opt.size(); i > 0; i--) {
            state[i - 1] = sel[i][N + cAmt];
            if (sel[i][N + cAmt]) {
                cAmt -= opt[i - 1];
            } else {
                cAmt += opt[i - 1];
            }
        }

        vector<bool> res(N, false);
        for (int i = 0; i < opt.size(); i++) {
            if (state[i]) {
                for (int j : comp[i]) {
                    res[j] = true;
                }
            }
        }

        cout << "Case #" << cT << ": ";

        for (int i = 0; i < N; i++) {
            if (res[i]) {
                cout << '1';
            } else {
                cout << '0';
            }
        }
        cout << endl;
    }
}