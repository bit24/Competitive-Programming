struct HLDecomp {
    int N, rt;

    vi aL[MAXN];

    int p[MAXN], d[MAXN], hC[MAXN], cS[MAXN];

    LSegTr tr;
    int tI[MAXN];

    int initDFS(int cV) {
        int cCnt = 1, mSCnt = 0;

        for (int aV : aL[cV]) {
            if (aV != p[cV]) {
                p[aV] = cV;
                d[aV] = d[cV] + 1;

                int aCnt = initDFS(aV);
                if (aCnt > mSCnt) {
                    mSCnt = aCnt;
                    hC[cV] = aV;
                }
                cCnt += aCnt;
            }
        }
    }

    void init() {
        fill(hC, hC + MAXN, -1);
        p[rt] = -1;
        initDFS(rt);

        int nTI = 0;
        for (int curS = 0; curS < N; curS++) {
            if (curS == rt || hC[p[curS]] != curS) {
                for (int cV = curS; cV != -1; cV = hC[cV]) {
                    cS[cV] = curS;
                    tI[cV] = nTI++;
                }
            }
        }
    }

    int query(int a, int b) {
        int sum = 0;
        while (cS[a] != cS[b]) {
            if (d[cS[a]] < d[cS[b]]) swap(a, b);

            sum += tr.q(1, 0, N - 1, tI[cS[a]], tI[a]);
            a = p[cS[a]];
        }

        if (tI[a] > tI[b]) swap(a, b);

        sum += tr.q(1, 0, N - 1, tI[a], tI[b]);
        return sum;
    }

    void update(int a, int b, int delta) {
        while (cS[a] != cS[b]) {
            if (d[cS[a]] < d[cS[b]]) swap(a, b);

            tr.u(1, 0, N - 1, tI[cS[a]], tI[a], delta);
            a = p[cS[a]];
        }

        if (tI[a] > tI[b]) swap(a, b);

        tr.u(1, 0, N - 1, tI[a], tI[b], delta);
    }
};
