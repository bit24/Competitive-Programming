#pragma GCC optimize ("O3")

#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const ll INF = 1e13;

struct line {
    int x;
    int y1;
    int y2;

    bool operator<(line o) {
        return x < o.x;
    }
};

int N, H;
vector<line> lines;
ll cost[55][55];

int last[100005];

// Edmond Karp
int nV, src, sink;
ll res[55][55];
int pre[55];

bool fpts() {
    for (int i = 0; i < 55; i++) {
        pre[i] = -2;
    }
    pre[src] = -1;

    queue<int> q;
    q.push(src);

    while (q.size()) {
        int cV = q.front();
        q.pop();
        if (cV == sink) {
            return true;
        }

        for (int aV = 0; aV < nV; aV++) {
            if (pre[aV] == -2 && res[cV][aV] > 0) {
                pre[aV] = cV;
                q.push(aV);
            }
        }
    }
    return false;
}

ll computeFlow() {
    nV = N + 2;
    src = N;
    sink = N + 1;

    for (int i = 0; i < N; i++) {
        if (lines[i].y1 == 0) {
            res[src][i] = INF;
        }
        if (lines[i].y2 == H) {
            res[i][sink] = INF;
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            res[i][j] = cost[i][j];
        }
    }

    ll tFlow = 0;
    while (true) {
        if (!fpts()) {
            break;
        }

        int cV = sink;
        ll bNeck = INF;
        while (cV != src) {
            int pV = pre[cV];
            bNeck = min(bNeck, res[pV][cV]);
            cV = pV;
        }

        cV = sink;
        while (cV != src) {
            int pV = pre[cV];
            res[pV][cV] -= bNeck;
            res[cV][pV] += bNeck;
            cV = pV;
        }
        tFlow += bNeck;
    }
    return tFlow;
}

int main() {
    freopen("ladders.in", "r", stdin);
    freopen("ladders.out", "w", stdout);

    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        for (int i = 0; i < 55; i++) {
            for (int j = 0; j < 55; j++) {
                cost[i][j] = 0;
                res[i][j] = 0;
            }
        }

        for (int i = 0; i < 100005; i++) {
            last[i] = -1;
        }
        lines.clear();

        cin >> N >> H;

        for (int i = 0; i < N; i++) {
            int x, a, b;
            cin >> x >> a >> b;
            lines.pb({x, a, b});
        }
        sort(lines.begin(), lines.end());

        for (int i = 0; i < N; i++) {
            for (int j = lines[i].y1; j < lines[i].y2; j++) {
                if (last[j] != -1) {
                    cost[i][last[j]]++;
                    cost[last[j]][i]++;
                }
                last[j] = i;
            }
        }

        ll tFlow = computeFlow();

        cout << "Case #" << cT << ": ";
        if (tFlow >= INF) {
            cout << -1 << endl;
        } else {
            cout << tFlow << endl;
        }
    }
}