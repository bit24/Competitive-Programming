struct SegTrBeats {
    int mx[2][4 * MAXN], mxCnt[4 * MAXN];
    ll sum[4 * MAXN];

    void ps(int i, int l, int r) {
        if (l == r) {
            return;
        }
        for (int ch = 0; ch < 2; ch++) {
            if (mx[0][i] < mx[0][i * 2 + ch]) {
                sum[i * 2 + ch] -= (ll) mxCnt[i * 2 + ch] * (mx[0][i * 2 + ch] - mx[0][i]);
                mx[0][i * 2 + ch] = mx[0][i];
            }
        }
    }

    void pl(int i) { // merging is now more complicated
        mx[0][i] = max(mx[0][i * 2], mx[0][i * 2 + 1]);
        mx[1][i] = max(mx[1][i * 2], mx[1][i * 2 + 1]);
        mxCnt[i] = 0;

        for (int ch = 0; ch < 2; ch++) {
            if (mx[0][i] == mx[0][i * 2 + ch]) {
                mxCnt[i] += mxCnt[i * 2 + ch];
            } else {
                mx[1][i] = max(mx[1][i], mx[1][i * 2 + ch]); // both from same subtree
            }
        }
        sum[i] = sum[i * 2] + sum[i * 2 + 1];
    }

    void minU(int i, int l, int r, int s, int e, int bd) {
        if (e < l || r < s || mx[0][i] <= bd) {
            return;
        }
        ps(i, l, r);
        if (s <= l && r <= e && mx[1][i] < bd) {
            sum[i] -= (ll) mxCnt[i] * (mx[0][i] - bd);
            mx[0][i] = bd;
            return;
        }
        if (l == r) {
            return;
        }
        int mid = (l + r) / 2;
        minU(i * 2, l, mid, s, e, bd);
        minU(i * 2 + 1, mid + 1, r, s, e, bd);
        pl(i);
    }

    int qMax(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        ps(i, l, r);
        if (s <= l && r <= e) {
            return mx[0][i];
        }
        int mid = (l + r) / 2;
        return max(qMax(i * 2, l, mid, s, e), qMax(i * 2 + 1, mid + 1, r, s, e));
    }

    ll qSum(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        ps(i, l, r);
        if (s <= l && r <= e) {
            return sum[i];
        }
        int mid = (l + r) / 2;
        return qSum(i * 2, l, mid, s, e) + qSum(i * 2 + 1, mid + 1, r, s, e);
    }
};
