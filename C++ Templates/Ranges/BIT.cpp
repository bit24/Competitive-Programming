struct BIT {
    int tr[MAXN];

    void u(int i, int d) {
        while (i < MAXN) {
            tr[i] += d;
            i += (i & - i);
        }
    }

    int q(int i) {
        int s = 0;
        while (i > 0) {
            s += tr[i];
            i -= (i & -i);
        }
        return s;
    }
};
