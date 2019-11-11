namespace PSegTr {
    static const int MAXN = 500000, NODES = 50 * MAXN;
    // different definition of lz to nicely implement persistence
    // lz is only for children, node which it is on already contains updated value
    int lC[NODES], rC[NODES], tr[NODES];
    int nx = 0;

    inline int cpy(int o) {
        int n = nx++;
        lC[n] = lC[o], rC[n] = rC[o], tr[n] = tr[o];
        return n;
    }

    // q does not create any new nodes
    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(lC[i], l, mid, s, e) +
               q(rC[i], mid + 1, r, s, e);
    }

    // returns index of updated node
    int u(int i, int l, int r, int x, int d) {
        i = cpy(i);
        if (l == r) {
            tr[i] += d;
            return i;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            lC[i] = u(lC[i], l, mid, x, d);
        } else {
            rC[i] = u(rC[i], mid + 1, r, x, d);
        }
        tr[i] = tr[lC[i]] + tr[rC[i]];
        return i;
    }
};