#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

typedef tuple<int, int, int> ti;

#define pb push_back
#define f first
#define s second

const int INF = 1e8;

int dist[55][55];

int main() {
    freopen("graphs.in", "r", stdin);
    freopen("graphs.out", "w", stdout);
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        for (int i = 0; i < 55; i++) {
            for (int j = 0; j < 55; j++) {
                dist[i][j] = INF;
            }
        }

        int N, M;
        cin >> N >> M;

        vector<ti> reqs;
        for (int i = 0; i < M; i++) {
            int a, b, w;
            cin >> a >> b >> w;
            a--, b--;
            reqs.pb({a, b, w});
            dist[a][b] = w;
            dist[b][a] = w;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    dist[j][k] = min(dist[j][k], dist[j][i] + dist[i][k]);
                }
            }
        }

        bool pos = true;
        for (int i = 0; i < M; i++) {
            int a = get<0>(reqs[i]);
            int b = get<1>(reqs[i]);
            int c = get<2>(reqs[i]);

            if (dist[a][b] != c) {
                cout << "Case #" << cT << ": Impossible" << endl;
                pos = false;
                break;
            }
        }
        if (pos) {
            cout << "Case #" << cT << ": " << M << endl;
            for (int i = 0; i < M; i++) {
                int a = get<0>(reqs[i]) + 1;
                int b = get<1>(reqs[i]) + 1;
                int c = get<2>(reqs[i]);
                cout << a << " " << b << " " << c << endl;
            }
        }
    }
}