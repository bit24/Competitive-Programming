namespace matmul {
    void mult(ll a[MAXN][MAXN], ll b[MAXN][MAXN], ll res[MAXN][MAXN]) {
        memset(res, 0, 8 * MAXN * MAXN);

        for (int i = 0; i < MAXN; i++) {
            for (int j = 0; j < MAXN; j++) {
                for (int k = 0; k < MAXN; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

    void exp(ll b[MAXN][MAXN], int e, ll c[MAXN][MAXN]) { // caution: modifies b
        memset(c, 0, 8 * MAXN * MAXN);

        for (int i = 0; i < MAXN; i++) {
            c[i][i] = 1;
        }

        ll tmp[MAXN][MAXN];
        while (e > 0) {
            if (e & 1) {
                mult(c, b, tmp);

                memcpy(c, tmp, sizeof(tmp));
            }

            mult(b, b, tmp);
            memcpy(b, tmp, sizeof(tmp));
            e >>= 1;
        }
        return;
    }
}
