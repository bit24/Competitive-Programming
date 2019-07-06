struct BIT2D_RQ { // 2D BIT with 0 indexing, range queries, point updates

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

        void u(int i, int d) {
            in_u(searchL(yCoor, i), d);
        }

        int rQ(int l, int r) {
            l = searchF(yCoor, l);
            r = searchL(yCoor, r);
            if (l > r) return 0;
            return in_q(r) - in_q(l - 1);
        }
    };

    vector<node> tr;

    void init(vector<pi> &pts) { // pass in future points
        int maxX = 0;
        for (pi cP: pts) {
            maxX = max(maxX, cP.f);
        }
        tr.resize(maxX + 1);

        // coordinate compress on x if necessary
        for (pi cP: pts) {
            int i = cP.f;
            while (i < tr.size()) {
                tr[i].yCoor.pb(cP.s);
                i = i | (i + 1);
            }
        }

        for (node &cN : tr) {
            sort(cN.yCoor.begin(), cN.yCoor.end());
            cN.yCoor.erase(unique(cN.yCoor.begin(), cN.yCoor.end()), cN.yCoor.end());
            cN.tr.resize(cN.yCoor.size(), 0);
        }
    }

    int in_rQ(int i, int l, int r) {
        int s = 0;
        while (i >= 0) {
            s += tr[i].rQ(l, r);
            i = (i & (i + 1)) - 1;
        }
        return s;
    }

    void u(int i1, int i2, int d) {
        while (i1 < tr.size()) {
            tr[i1].u(i2, d);
            i1 = i1 | (i1 + 1);
        }
    }

    int rQ(int l1, int r1, int l2, int r2) {
        return in_rQ(r1, l2, r2) - in_rQ(l1 - 1, l2, r2);
    }
} bit2D;
