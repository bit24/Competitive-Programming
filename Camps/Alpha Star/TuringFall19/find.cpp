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

vi need = {0, 1, 2, 2, 3, 1};

ll dp[6][251][251];

int main() {
    int V, E;
    cin >> V >> E;
    vi ch(V);
    for (int i = 0; i < V; i++) {
        char cChar;
        cin >> cChar;
        if (cChar == 'b') {
            ch[i] = 0;
        } else if (cChar == 'e') {
            ch[i] = 1;
        } else if (cChar == 's') {
            ch[i] = 2;
        } else if (cChar == 'i') {
            ch[i] = 3;
        }
    }

    vector<pi> edges;

    for (int i = 0; i < E; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        edges.pb({u, v});
        edges.pb({v, u});
    }

    memset(dp, 0, sizeof(dp));
    for (int i = 0; i < V; i++) {
        if (ch[i] == 0) {
            dp[0][i][0] = 1;
        }
    }

    for (int i = 1; i <= 5; i++) {
        for (pi cE : edges) {
            int u = cE.f;
            int v = cE.s;
            if (ch[v] == need[i]) {
                if (i == 1) {
                    dp[1][v][v] += dp[0][u][0];
                }
                if (i == 2 || i == 3 || i == 4) {
                    for (int j = 0; j < V; j++) {
                        dp[i][v][j] += dp[i - 1][u][j];
                    }
                }
                if (i == 5) {
                    for (int j = 0; j < V; j++) {
                        if (j != v) {
                            dp[5][v][j] += dp[4][u][j];
                        }
                    }
                }
            }
        }
    }
    ll sum = 0;
    for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
            sum += dp[5][i][j];
        }
    }
    cout << sum << endl;
}