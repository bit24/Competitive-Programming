#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef pair<int, int> pi;

#define pb push_back
#define f first
#define s second

namespace debug {
    template<class T1, class T2>
    void pr(const pair<T1, T2> &x);

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x);

    template<class T>
    void pr(const vector<T> &x);

    template<class T>
    void pr(const set<T> &x);

    template<class T1, class T2>
    void pr(const map<T1, T2> &x);

    template<class T>
    void pr(const T &x) { cout << x; }

    template<class T, class... Ts>
    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }

    template<class T>
    void prIn(const T &x) {
        pr("{");
        bool fst = 1;
        for (auto &a : x) {
            pr(fst ? "" : ", ", a), fst = 0;
        }
        pr("}");
    }

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x) { prIn(x); }

    template<class T>
    void pr(const vector<T> &x) { prIn(x); }

    template<class T>
    void pr(const set<T> &x) { prIn(x); }

    template<class T1, class T2>
    void pr(const map<T1, T2> &x) { prIn(x); }

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

const int MAXN = 3100000;
const int INF = 1e9;

inline pi operator+(pi a, pi b) {
    if (a.f != b.f) {
        if (a.f < b.f) {
            return a;
        } else {
            return b;
        }
    }
    return {a.f, a.s + b.s};
}

int buf[MAXN];

struct LSegTr {
    pi tr[4 * MAXN];
    int lz[4 * MAXN];

    void ps(int i, int l, int r) {
        tr[i].f += lz[i];
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    void b(int i, int l, int r) {
        if (l == r) {
            tr[i] = {buf[l], 1};
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid);
        b(i * 2 + 1, mid + 1, r);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
        // debug::ps(tr[i]);
    }

    pi q(int i, int l, int r, int s, int e) {
        ps(i, l, r);
        if (e < l || r < s) {
            return {INF, 0};
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
} hist;

int nR, nC;

inline int l(int i, int j) {
    return (nC + 2) * i + j;
}

int tim[MAXN];
int loc[MAXN];

int dLoc[4]; // u,l,d,r
int sqr[4];

vi times(4);

void mod(int a, int d) { // takes top left of 2x2
    for (int i = 0; i < 4; i++) {
        times[i] = tim[a + sqr[i]];
    }
    // ps(times);

    sort(times.begin(), times.end());

    // ps("upd:", times[0], times[1] - 1, d);
    if (times[0] != times[1]) {
        hist.u(1, 1, nR * nC, times[0], times[1] - 1, d);
    }
    if (times[2] != times[3]) {
        hist.u(1, 1, nR * nC, times[2], times[3] - 1, d);
    }
}


void modB(int a, int d) { // takes top left of 2x2
    for (int i = 0; i < 4; i++) {
        times[i] = tim[a + sqr[i]];
    }
    // ps(times);

    sort(times.begin(), times.end());

    // ps("upd:", times[0], times[1] - 1, d);
    if (times[0] != times[1]) {
        buf[times[0]] += d;
        if (times[1] <= nR * nC) {
            buf[times[1]] -= d;
        }
    }
    if (times[2] != times[3]) {
        buf[times[2]] += d;
        if (times[3] <= nR * nC) {
            buf[times[3]] -= d;
        }
    }
}

void modSur(int ct, int d) {
    for (int i = 0; i < 4; i++) {
        mod(ct, d);
        ct += dLoc[i];
    }
}

void give_initial_chart(int H, int W, vi R, vi C) {
    // ps("in initial");
    nR = H, nC = W;
    fill_n(tim, MAXN, INF);
    for (int i = 1; i <= nR * nC; i++) {
        loc[i] = l(R[i - 1] + 1, C[i - 1] + 1);
        // ps(R[i - 1], C[i - 1], l(R[i - 1], C[i - 1]));
        tim[loc[i]] = i;
    }

    dLoc[0] = -(nC + 2);
    dLoc[1] = -1;
    dLoc[2] = (nC + 2);
    dLoc[3] = 1;
    // ps(dLoc[0], dLoc[1], dLoc[2], dLoc[3]);

    sqr[0] = 0;
    sqr[1] = dLoc[3]; // r
    sqr[2] = dLoc[3] + dLoc[2]; // r, d
    sqr[3] = dLoc[2]; // d

    // ps(nR * nC);

    /*ps("pre");
    for (int i = 1; i <= 6; i++) {
        ps(hist.q(1, 1, nR * nC, i, i));
    }*/

    memset(buf, 0, sizeof(buf));
    for (int i = 0; i <= nR; i++) {
        for (int j = 0; j <= nC; j++) {
            modB(l(i, j), 1);
        }
    }

    for (int i = 1; i <= nR * nC; i++) {
        buf[i] += buf[i - 1];
    }
    hist.b(1, 1, nR * nC);

    // ps("fin initial", hist.tr[1].s);
    /*
    for (int i = 1; i <= 6; i++) {
        ps(hist.q(1, 1, nR * nC, i, i));
    }*/
}

int swap_seats(int a, int b) {
    a++, b++;
    modSur(loc[a], -1);
    modSur(loc[b], -1);
    swap(loc[a], loc[b]);
    tim[loc[a]] = a;
    tim[loc[b]] = b;
    modSur(loc[a], 1);
    modSur(loc[b], 1);
    // ps("printing", hist.tr[1].s);
    return hist.tr[1].s; // min amt hist will always be 4, find # of times where it is 4
}

/*
int main() {
    // ps("start");
    vi R = {0, 1, 1, 0, 0, 1};
    vi C = {0, 0, 1, 1, 2, 2};
    give_initial_chart(2, 3, R, C);
    ps(swap_seats(0, 5));
    ps(swap_seats(0, 5));
}*/