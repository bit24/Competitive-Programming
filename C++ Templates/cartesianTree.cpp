const int MAXN = 2e5 + 20;

int a[MAXN];
int N;

// cartesian tree with largest element at root
int rt;
int l[MAXN], r[MAXN], p[MAXN];

void buildCartesian() {
    fill(l, l + N, -1);
    fill(r, r + N, -1);
    fill(p, p + N, -1);

    rt = 0;

    for (int i = 1; i < N; i++) {
        int x = i - 1;
        while (x != -1 && a[x] <= a[i]) {
            x = p[x];
        }
        if (x == -1) {
            l[i] = rt;
            rt = i;
            if (l[i] != -1) p[l[i]] = i;
        } else {
            assert(a[x] > a[i]);
            l[i] = r[x];
            if (l[i] != -1) p[l[i]] = i;

            r[x] = i;
            p[i] = x;
        }
    }
}
