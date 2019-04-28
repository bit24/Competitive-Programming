struct QGSegTr { // extends segment tree to query first greater to left/right of an index
    int tr[4 * MAXN];

    // find first greater to left
    int qLG(int i, int l, int r, int x, int bd) {
        if (tr[i] <= bd) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int mid = (l + r) / 2;
        if (x > mid) {
            int qF = qLG(i * 2 + 1, mid + 1, r, x, bd);
            if (qF != -1) {
                return qF;
            }
        }
        return qLG(i * 2, l, mid, x, bd);
    }

    // find first greater to right
    int qRG(int i, int l, int r, int x, int bd) {
        // ps(i, l, r, x, bd);
        if (tr[i] <= bd) {
            // ps("N:", N);
            return N;
        }
        if (l == r) {
            return l;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            int qF = qRG(i * 2, l, mid, x, bd);
            if (qF != N) {
                return qF;
            }
        }
        return qRG(i * 2 + 1, mid + 1, r, x, bd);
    }
} 
