struct PLSegTr {
    static const int MAXN = 500000, NODES = 10 * 500000;
    // different definition of lz to nicely implement persistence
    // lz is only for children, node which it is on already contains updated value
    int lC[NODES], rC[NODES], tr[NODES], lz[NODES];
    int nx = 0;

    int cpy(int o) {
        int n = nx++;
        lC[n] = lC[o], rC[n] = rC[o], tr[n] = tr[o], lz[n] = lz[o];
        return n;
    }

    void ps(int i, int l, int r) {
        if (lz[i] == 0) {
            return;
        }
        i = cpy(i);
        if (l != r) {
            lC[i] = cpy(lC[i]);
            rC[i] = cpy(rC[i]);
            int m = (l + r) / 2;
            tr[lC[i]] += lz[i] * (m - l + 1); // aggregate effects
            tr[rC[i]] += lz[i] * (r - m);
            lz[lC[i]] += lz[i];
            lz[rC[i]] += lz[i];
        }
        lz[i] = 0;
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
        return lz[i] * (min(r, e) - max(l, s) + 1) + q(lC[i], l, mid, s, e) +
               q(rC[i], mid + 1, r, s, e); // aggregate effects
    }


    // returns index of updated node
    int u(int i, int l, int r, int s, int e, int d) {
        if (e < l || r < s) {
            return i;
        }
        i = cpy(i);
        if (s <= l && r <= e) {
            tr[i] += d * (r - l + 1); // aggregate effects
            lz[i] += d;
            return i;
        }
        ps(i, l, r);
        int mid = (l + r) / 2;
        lC[i] = u(lC[i], l, mid, s, e, d);
        rC[i] = u(rC[i], mid + 1, r, s, e, d);
        tr[i] = tr[lC[i]] + tr[rC[i]];
        return i;
    }

    int b(vi &a, int l, int r) {
        int i = nx++;
        if (l == r) {
            if (l < a.size()) tr[i] = a[l];
            return i;
        }
        int mid = (l + r) / 2;
        lC[i] = b(a, l, mid);
        rC[i] = b(a, mid + 1, r);
        tr[i] = tr[lC[i]] + tr[rC[i]];
        return i;
    }

    int clr() {
        memset(lC, 0, sizeof(lC)), memset(rC, 0, sizeof(lC)), memset(tr, 0, sizeof(lC)), memset(lz, 0, sizeof(lC));
        nx = 0;
    }

    vi trees;

    void uTrees(int s, int e, int d) {
        trees.pb(u(trees.back(), 1, MAXN, s, e, d));
    }

    int qTrees(int t, int s, int e) {
        return q(trees[t], 1, MAXN, s, e);
    }

    void bTree(vi&a){
        trees.pb(b(a, 1, MAXN));
    }
};
