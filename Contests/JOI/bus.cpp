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


const int MAXV = 205;
const int MAXE = 5e4 + 10;
const ll INF = 1e15;

int V, E;

int u[MAXE], v[MAXE];
ll c[MAXE], r[MAXE];

int src;
int sink;

int aM[MAXV][MAXV];

ll cost[MAXV];
bool vis[MAXV];

int pEdge[MAXV];

vi sEdges;

void dijkstra() {
    for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
            aM[i][j] = -1;
        }
    }

    for (int i = 0; i < E; i++) {
        if (aM[u[i]][v[i]] == -1 || c[aM[u[i]][v[i]]] > c[i]) {
            aM[u[i]][v[i]] = i;
        }
    }

    fill(cost, cost + MAXV, INF);
    fill(vis, vis + MAXV, false);

    fill(pEdge, pEdge + MAXV, -1);
    sEdges.clear();

    cost[src] = 0;

    while (true) {
        int cV = -1;
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                if (cV == -1 || cost[i] < cost[cV]) {
                    cV = i;
                }
            }
        }
        if (cV == -1) {
            break;
        }
        vis[cV] = true;

        ll cC = cost[cV];

        for (int aV = 0; aV < V; aV++) {
            int aE = aM[cV][aV];
            if (aE == -1) continue;

            if (cost[aV] > cC + c[aE]) {
                cost[aV] = cC + c[aE];
                pEdge[aV] = aE;
            }
        }
    }

    int cV = sink;
    while (cV != src) {
        if (pEdge[cV] == -1) break;
        sEdges.pb(pEdge[cV]);
        cV = u[pEdge[cV]];

        assert(sEdges.size() < V);
    }
}

bool specEdge[MAXE];

ll g1Cost[MAXV], g2Cost[MAXV];

ll partAns[MAXE];

// compute cost for all reversed edges from src to sink
void computeCost() {
    dijkstra();

//    ps("fin1");

    fill(specEdge, specEdge + MAXE, false);
    for (int x : sEdges) {
        specEdge[x] = true;
    }
    assert(sEdges.size() < V);

    memcpy(g1Cost, cost, sizeof(cost));

    for (int i = 0; i < E; i++) {
        int temp = u[i];
        u[i] = v[i];
        v[i] = temp;
    }
    swap(src, sink);

    dijkstra();
    memcpy(g2Cost, cost, sizeof(cost));
    assert(g1Cost[sink] == g2Cost[src]);

    for (int i = 0; i < E; i++) {
        int temp = u[i];
        u[i] = v[i];
        v[i] = temp;
    }
    swap(src, sink);

//    ps("fin2");

    fill(partAns, partAns + MAXE, INF);

    for (int i = 0; i < E; i++) {
        if (g1Cost[v[i]] < g1Cost[u[i]]) { // edge is against gradient
            partAns[i] = g1Cost[v[i]] + g2Cost[u[i]] + c[i];
            partAns[i] = min(partAns[i], g1Cost[sink]); // don't use reverse edge
        } else {
            if (specEdge) {
                swap(u[i], v[i]);
                dijkstra();
                swap(u[i], v[i]);

                partAns[i] = cost[sink];
            } else {
                partAns[i] = g1Cost[sink]; // just use the already found shortest path
            }
        }
    }
}

ll ans[MAXE];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> V >> E;

    for (int i = 0; i < E; i++) {
        cin >> u[i] >> v[i] >> c[i] >> r[i];
        u[i]--, v[i]--;
    }

    src = 0;
    sink = V - 1;

    computeCost();
    for (int i = 0; i < E; i++) {
        ans[i] = partAns[i] + r[i];
    }
    ans[E] = g1Cost[sink];

//    ps("half");
//    ps(partAns[1]);

    swap(src, sink);

    computeCost();
    for (int i = 0; i < E; i++) {
        ans[i] += partAns[i];
    }
    ans[E] += g1Cost[sink];
//
//    ps("full");
//    ps(partAns[1]);

    ll fAns = INF;
    for (int i = 0; i <= E; i++) {
        fAns = min(fAns, ans[i]);
    }

    assert(fAns >= 0);

    if (fAns == INF) {
        fAns = -1;
    }
    cout << fAns << endl;
}