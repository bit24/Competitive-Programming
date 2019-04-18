#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const int MAXN = 1100000;

struct LSegTr {
    int tr[4 * MAXN], lz[4 * MAXN], sum[4 * MAXN];

    void ps(int i, int l, int r) {
        tr[i] += (r - l + 1) * lz[i];
        if (l != r) {
            int mid = (l + r) / 2;
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    int q(int i, int l, int r, int s, int e) {
        ps(i, l, r);
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void u(int i, int l, int r, int s, int e, int d) {
        ps(i, l, r);
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
} crn;

int nR, nC;

int l(int i, int j) {
    return (nC + 2) * i + j;
}

int tm[MAXN];
int loc[MAXN];

int dLoc[4]; // u,l,d, r
int sqr[4];

void give_initial_chart(int H, int W, int R[], int C[]) {
    nR = H, nC = W;
    fill_n(tm, MAXN, 2 * MAXN);
    for (int i = 1; i <= nR * nC; i++) {
        loc[i] = l(R[i], C[i]);
        tm[l(R[i], C[i])] = i;
    }

    dLoc[0] = -(nC + 2);
    dLoc[1] = -1;
    dLoc[2] = (nC + 2);
    dLoc[3] = 1;

    sqr[0] = 0;
    sqr[1] = dLoc[3]; // r
    sqr[2] = dLoc[3] + dLoc[2]; // r, d
    sqr[3] = dLoc[2]; // d
}

void mod(int a, int d) {
    vi times;

    for (int i = 0; i < 4; i++) {
        times.pb(tm[a + sqr[i]]);
    }

    sort(times.begin(), times.end());

    crn.u(1, 1, MAXN, times[0], times[1] - 1, d);
}

void modAll(int ct, int d) {
    for (int i = 0; i < 4; i++) {
        mod(ct, d);
        ct += dLoc[i];
    }
}

int swap_seats(int a, int b) {
    modAll(loc[a], -1);
    modAll(loc[b], -1);
    swap(loc[a], loc[b]);
    tm[loc[a]] = a;
    tm[loc[b]] = b;
    modAll(loc[a], 1);
    modAll(loc[b], 1);
}