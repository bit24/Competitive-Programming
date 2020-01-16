namespace SCC { // verified: Theme Park Planning
    const int MAXN = 2e5 + 100;
    const int INF = 1e9;

    int N, M, K;

    vi aL[MAXN];

    int cT = 0;
    int disc[MAXN], low[MAXN];

    stack<int> stk;

    int nC = 0;
    int cID[MAXN];

    void dfs(int cV) {
        disc[cV] = low[cV] = cT++;
        stk.push(cV);

        for (int aV : aL[cV]) {
            if (disc[aV] == -1) { // tree edge
                dfs(aV);
                low[cV] = min(low[cV], low[aV]);
            } else {
                low[cV] = min(low[cV], disc[aV]); // look for back edges
            }
        }

        if (low[cV] == disc[cV]) {
            while (stk.top() != cV) {
                cID[stk.top()] = nC;
                disc[stk.top()] = INF;
                stk.pop();
            }
            cID[stk.top()] = nC;
            disc[stk.top()] = INF;
            stk.pop();

            nC++;
        }

//    disc[cV] = INF; // to avoid cross edge interference
    } //rev

    vi cAL[MAXN];
    vi topOrd;

    int vis[MAXN];

    void condGraph() {
        for (int cV = 0; cV < N; cV++) {
            int id1 = cID[cV];
            for (int aV : aL[cV]) {
                int id2 = cID[aV];

                if (id1 != id2) {
                    cAL[id1].pb(id2);
                }
            }
        }

        for (int i = 0; i < nC; i++) {
            vi &cL = cAL[i];
            sort(cL.begin(), cL.end());
            cL.erase(unique(cL.begin(), cL.end()), cL.end());
        }
    }

    void dfs2(int cV) {
        vis[cV] = true;
        for (int aV : cAL[cV]) {
            if (!vis[aV]) {
                dfs2(aV);
            }
        }
        topOrd.pb(cV);
    } //rev
}
using namespace SCC;
