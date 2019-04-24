struct DSU {
    int anc[MAXN];

    void init() {
        for (int i = 0; i < MAXN; i++) {
            anc[i] = i;
        }
    }

    int fRt(int i) {
        return anc[i] == i ? i : anc[i] = fRt(anc[i]);
    }

    bool merge(int a, int b) {
        a = fRt(a), b = fRt(b);
        if (a == b) {
            return false;
        }
        anc[a] = b;
        return true;
    }
};
