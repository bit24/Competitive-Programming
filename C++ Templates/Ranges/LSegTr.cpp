struct LSegTr {
    int tr[4 * MAXN], lz[4 * MAXN];

    void ps(int i, int l, int r) {
        tr[i] += (r - l + 1) * lz[i];
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        ps(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void u(int i, int l, int r, int s, int e, int d) {
        ps(i, l, r); // pushed early to use in recalculation of parent
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            lz[i] += d;
            ps(i, l, r);
            return;
        }
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }
}
