struct SegTr {
    int tr[4 * MAXN];

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (l <= s && e <= r) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void u(int i, int l, int r, int x, int d) {
        if (l == r) {
            tr[x] += d;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            u(i * 2, l, mid, x, d);
        } else {
            u(i * 2 + 1, mid + 1, r, x, d);
        }
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }
};
