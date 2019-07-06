struct BIT2D_RUpd { // 2D BIT with 0 indexing; range updates, points queries
    static int searchF(vi &a, int x) { // returns first element ge x
        return lower_bound(a.begin(), a.end(), x) - a.begin();
    }

    static int searchL(vi &a, int x) { // returns last element le x
        return upper_bound(a.begin(), a.end(), x) - a.begin() - 1;
    }

    struct node {
        vi yCoor;
        vi tr;

        void in_u(int i, int d) {
            while (i < tr.size()) {
                tr[i] += d;
                i = i | (i + 1);
            }
        }

        int in_q(int i) {
            int s = 0;
            while (i >= 0) {
                s += tr[i];
                i = (i & (i + 1)) - 1;
            }
            return s;
        }

        void rU(int l, int r, int d) {
            l = searchF(yCoor, l);
            r = searchL(yCoor, r);
            if (l > r) return;
            in_u(l, d);
            in_u(r + 1, -d);
        }

        int q(int i) {
            return in_q(searchL(yCoor, i));
        }
    };

    vector<node> tr;

    void init(vector<pi> &pts) { // pass in future queries
        int maxX = 0;
        for (pi cP: pts) {
            maxX = max(maxX, cP.f);
        }
        tr.resize(maxX + 1);

        // coordinate compress on x if necessary in future
        for (pi cP: pts) {
            int i = cP.f;
            while (i >= 0) {
                tr[i].yCoor.pb(cP.s);
                i = (i & (i + 1)) - 1;
            }
        }

        for (node &cN : tr) {
            sort(cN.yCoor.begin(), cN.yCoor.end());
            cN.yCoor.erase(unique(cN.yCoor.begin(), cN.yCoor.end()), cN.yCoor.end());
            cN.tr.resize(cN.yCoor.size(), 0);
        }
    }

    void in_rU(int i, int l, int r, int d) {
        while (i < tr.size()) {
            tr[i].rU(l, r, d);
            i = i | (i + 1);
        }
    }

    int q(int i1, int i2) {
        int s = 0;
        while (i1 >= 0) {
            s += tr[i1].q(i2);
            i1 = (i1 & (i1 + 1)) - 1;
        }
        return s;
    }

    void rU(int l1, int r1, int l2, int r2, int d) {
        if (l1 > r1) return;
        in_rU(l1, l2, r2, d);
        in_rU(r1 + 1, l2, r2, -d);
    }
} bit2D;
