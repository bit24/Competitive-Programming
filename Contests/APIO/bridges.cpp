#pragma GCC optimize ("O3")

#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef tuple<int, int, int, int, int> edge; // weight, startTime, endTime, endpts (x2)
typedef tuple<int, int, int> query; // weight, time, pt

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const int BLKSZ = 400;
const int NUMBLKS = 100000 / BLKSZ + 5;

const int MAXN = 50100;

struct DSU {
    int anc[MAXN], sz[MAXN], rank[MAXN];

    void init() {
        for (int i = 0; i < MAXN; i++) {
            anc[i] = i;
            sz[i] = 1;
            rank[i] = 0;
        }
    }

    int fRt(int i) {
        return anc[i] == i ? i : anc[i] = fRt(anc[i]);
    }

    bool merge(int a, int b) { // TODO: implement rank
        a = fRt(a), b = fRt(b);
        if (a == b) {
            return false;
        }
        if (rank[a] < rank[b]) {
            anc[a] = b;
            sz[b] += sz[a];
        } else if (rank[a] > rank[b]) {
            anc[b] = a;
            sz[a] += sz[b];
        }
        else{
            anc[b] = a;
            sz[a] += sz[b];
            rank[a]++;
        }

        return true;
    }
} ds;

vector<vi> aList;
vector<bool> vis;
vector<int> undo;
int cnt = 0;

void dfs(int cV) {
    vis[cV] = true;
    cnt += ds.sz[cV];
    for (int aV : aList[cV]) {
        if (!vis[aV]) {
            dfs(aV);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    // freopen("bridges.in", "r", stdin);

    int V, E, Q;
    cin >> V >> E;

    vector<edge> edges;
    vector<pi> endP(E);
    vi sT(E);
    vi lW(E);

    for (int i = 0; i < E; i++) {
        int u, v, w;
        cin >> u >> v >> w;
        u--, v--;
        endP[i] = {u, v};
        sT[i] = -1;
        lW[i] = w;
    }

    cin >> Q;

    vector<vector<query>> qBlks(NUMBLKS);
    vector<vector<edge>> eBlks(NUMBLKS);

    for (int cT = 0; cT < Q; cT++) {
        int t, x, w;
        cin >> t >> x >> w;
        x--;
        if (t == 1) {
            edge cE = {lW[x], sT[x], cT - 1, endP[x].f, endP[x].s};

            edges.pb(cE);
            eBlks[sT[x] / BLKSZ].pb(cE);
            eBlks[(cT - 1) / BLKSZ].pb(cE);

            sT[x] = cT;
            lW[x] = w;
        } else {
            qBlks[cT / BLKSZ].pb({w, cT, x});
        }
    }

    for (int x = 0; x < E; x++) {
        edge cE = {lW[x], sT[x], 100005, endP[x].f, endP[x].s};
        edges.pb(cE);
        eBlks[sT[x] / BLKSZ].pb(cE);
        eBlks[100005 / BLKSZ].pb(cE);
    }

    edges.pb({-1, -1, -1, -1, -1}); // dummy edge with weight -1 to make sure all queries are processed

    sort(edges.begin(), edges.end());
    reverse(edges.begin(), edges.end());
    // decreasing weight

    aList.resize(V);
    vis.resize(V, false);

    vi qAns(Q, -1);

    for (int cBlk = 0; cBlk < NUMBLKS; cBlk++) {
        vector<query> &cQs = qBlks[cBlk];
        vector<edge> &cEs = eBlks[cBlk];

        sort(cQs.begin(), cQs.end());
        reverse(cQs.begin(), cQs.end());
        // decreasing weight

        int blkL = cBlk * BLKSZ;
        int blkR = (cBlk + 1) * BLKSZ - 1;

        ds.init();

        int nxtQ = 0;
        for (edge oE: edges) {
            int oW = get<0>(oE);
            int oST = get<1>(oE);
            int oET = get<2>(oE);
            int oU = get<3>(oE);
            int oV = get<4>(oE);

            while (nxtQ < cQs.size() && get<0>(cQs[nxtQ]) > oW) {
                int qW = get<0>(cQs[nxtQ]);
                int qT = get<1>(cQs[nxtQ]);
                int qPt = get<2>(cQs[nxtQ]);

                // we have components determined by ds and a set of extra edges

                for (edge iE : cEs) {
                    int iW = get<0>(iE);
                    int iST = get<1>(iE);
                    int iET = get<2>(iE);
                    int iU = ds.fRt(get<3>(iE));
                    int iV = ds.fRt(get<4>(iE));

                    if (iU == iV) { // useless
                        continue;
                    }

                    if (iST <= qT && qT <= iET && iW >= qW) { // usable iff within time zone and qW
                        aList[iU].pb(iV);
                        aList[iV].pb(iU);
                        undo.pb(iU);
                        undo.pb(iV);
                    }
                }

                dfs(ds.fRt(qPt));
                undo.pb(ds.fRt(qPt));

                qAns[qT] = cnt;


                for (int cV : undo) {
                    aList[cV].clear();
                    vis[cV] = false;
                }

                undo.clear();
                cnt = 0;

                nxtQ++;
            }

            // only consider edges that completely surround this block
            if (oST < blkL && blkR < oET) {
                // merge endpoints in disjoint set
                ds.merge(oU, oV);
            }
        }
    }

    for (int i = 0; i < Q; i++) {
        if (qAns[i] != -1) {
            cout << qAns[i] << '\n';
        }
    }
    cout << flush;
}