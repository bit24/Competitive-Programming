struct LCA {
    const int LOGN = 18;

    int anc[MAXN][LOGN];
    int depth[MAXN];
    
    void dfs(int cV, int pV) {
       for (int aV : aL[cV]) {
           if (aV != pV) {
               depth[aV] = depth[cV] + 1;
               dfs(aV, cV);
               anc[aV][0] = cV;
           }
       }
    }

    void prep() {
        depth[0] = 0; // if root is different change
        dfs(0, -1);
        anc[0][0] = 0;
        for (int k = 1; k < LOGN; k++) {
            for (int i = 0; i < N; i++) {
                anc[i][k] = anc[anc[i][k - 1]][k - 1];
            }
        }
    }

    int j(int a, int d) {
        for (int i = 0; i < LOGN; i++) {
            if (d & (1 << i)) {
                a = anc[a][i];
            }
        }
        return a;
    }

    int fLCA(int a, int b) {
        if (depth[a] > depth[b]) swap(a, b);

        b = j(b, depth[b] - depth[a]);

        if (a == b) return a;

        for (int i = LOGN - 1; i >= 0; i--) {
            if (anc[a][i] != anc[b][i]) {
                a = anc[a][i];
                b = anc[b][i];
            }
        }
        return anc[a][0];
    }
};
