namespace BCC { //unverified
    const int MAXN = 1e5;

    int N;
    vi aL[MAXN];

    int cCnt = 1;
    int disc[MAXN], low[MAXN];

    stack<pi> stk;

    vector<vi> BCCs;
    vi AP;

    void dfs(int cV, int pV) {
        disc[cV] = low[cV] = cCnt++;

        int nCh = 0;
        for (int aV : aL[cV]) {
            if (disc[aV] == -1) {
                nCh++;

                pi cE = {cV, aV};
                stk.push(cE);

                dfs(aV, cV);
                low[cV] = min(low[cV], low[aV]);

                if (pV != -1 ? (low[aV] >= disc[cV]) : (nCh > 1)) {
                    AP.pb(cV);
                    vi cBCC;
                    while (stk.top() != cE) {
                        cBCC.pb(stk.top().f);
                        cBCC.pb(stk.top().s);
                        stk.pop();
                    }
                    cBCC.pb(stk.top().f);
                    cBCC.pb(stk.top().s);
                    stk.pop();
                    BCCs.pb(cBCC);
                }
            } else if (low[cV] > disc[aV]) { // back edge
                low[cV] = disc[aV];
            }
        }
    }

    void fBCCs() {
        fill(disc, disc + MAXN, -1);

        for (int i = 0; i < N; i++) {
            if (disc[i] == -1) {
                dfs(i, -1);

                if (stk.size()) {
                    vi cBCC;
                    while (stk.size()) {
                        cBCC.pb(stk.top().f);
                        cBCC.pb(stk.top().s);
                        stk.pop();
                    }
                    BCCs.pb(cBCC);
                }
            }
        }

        for (vi &x : BCCs) {
            sort(x.begin(), x.end());
            x.erase(unique(x.begin(), x.end()), x.end());
        }

        sort(AP.begin(), AP.end());
        AP.erase(AP.begin(), AP.end());
    }
}
