#include <bits/stdc++.h>
#include "wombats.h"

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

namespace debug {
    int DEBUG = true;

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
    void pr(const T &x) { if (DEBUG) cout << x; }

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

    void ps() { pr("\n"), cout << flush; }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

int R, C;

int h[6000][200], v[6000][200];

const int BSZ = 20;
const int NUMBLK = 256;

int blks[NUMBLK][200][200];
int tr[512][200][200];

int cDist[200]; // temporary workspace

// sR inclusive, eR exclusive
void buildBlock(int sR, int eR, int block[200][200]) {
    // dist[i][j] has destination i

    for (int stC = 0; stC < C; stC++) {
        for (int i = 0; i < C; i++) {
            cDist[i] = 1e6;
        }
        cDist[stC] = 0;

//        ps("stC:", stC);

        for (int cR = sR; cR < eR; cR++) {
            for (int i = 1; i < C; i++) {
                cDist[i] = min(cDist[i], cDist[i - 1] + h[cR][i - 1]);
            }

            for (int i = C - 2; i >= 0; i--) {
                cDist[i] = min(cDist[i], cDist[i + 1] + h[cR][i]);
            }

            for (int i = 0; i < C; i++) {
                cDist[i] += v[cR][i];
            }

//            ps(cR);
//            for (int i = 0; i < C; i++) {
//                pr(cDist[i], " ");
//            }
//            ps();
        }
        for (int i = 0; i < C; i++) {
            block[stC][i] = cDist[i];
        }
    }
//    ps("built");
//    if (sR == 0) {
//        DEBUG = false;
//    }
}

int start;

void merge(int a[200][200], int b[200][200], int ans[200][200], int eL, int eR, int xL, int xR) {
    int midE = (eL + eR) / 2;

    int minC = 1e9;
    int minX = -1;
    for (int x = xL; x <= xR; x++) {
        int cCost = a[start][x] + b[x][midE];
        if (cCost < minC) {
            minC = cCost;
            minX = x;
        }
    }

    ans[start][midE] = minC;

    if (eL < midE) {
        merge(a, b, ans, eL, midE - 1, xL, minX);
    }
    if (midE < eR) {
        merge(a, b, ans, midE + 1, eR, minX, xR);
    }
}

void merge(int a[200][200], int b[200][200], int ans[200][200]) {
    for (start = 0; start < C; start++) {
        merge(a, b, ans, 0, C - 1, 0, C - 1);
    }
}

void build(int i, int l, int r) {
    if (l == r) {
        memcpy(tr[i], blks[l], sizeof(blks[l]));
        return;
    }
    int mid = (l + r) / 2;
    build(i * 2, l, mid);
    build(i * 2 + 1, mid + 1, r);
    merge(tr[i * 2], tr[i * 2 + 1], tr[i]);
}

void recalculate(int i, int l, int r, int x) {
    if (l == r) {
        memcpy(tr[i], blks[l], sizeof(blks[l]));
        return;
    }
    int mid = (l + r) / 2;
    if (x <= mid) {
        recalculate(i * 2, l, mid, x);
    } else {
        recalculate(i * 2 + 1, mid + 1, r, x);
    }
    merge(tr[i * 2], tr[i * 2 + 1], tr[i]);
}

void update(int bI) {
    int bS = bI * BSZ, bE = (bI + 1) * BSZ; // bE is exclusive
    buildBlock(bS, bE, blks[bI]);
    recalculate(1, 0, NUMBLK - 1, bI);
}

void init(int iR, int iC, int iH[5000][200], int iV[5000][200]) {
    R = iR, C = iC;

    for (int i = 0; i < R; i++) {
        for (int j = 0; j < C - 1; j++) {
            h[i][j] = iH[i][j];
        }
    }
    for (int i = 0; i < R - 1; i++) {
        for (int j = 0; j < C; j++) {
            v[i][j] = iV[i][j];
        }
    }

    for (int i = R; i < 6000; i++) {
        for (int j = 0; j < C - 1; j++) {
            h[i][j] = 1e6;
        }
    }

    for (int i = R - 1; i < 6000 - 1; i++) {
        for (int j = 0; j < C; j++) {
            v[i][j] = 0;
        }
    }

//    ps(R, C);
//    for(int i = 0; i < C-1; i++){
//        pr(h[0][i], " ");
//    }
//    ps();
//    ps("fin");

    for (int bI = 0; bI < NUMBLK; bI++) {
        int bS = bI * BSZ, bE = (bI + 1) * BSZ; // bE is exclusive
        buildBlock(bS, bE, blks[bI]);
    }

    build(1, 0, NUMBLK - 1);

//    for (int i = 0; i < C; i++) {
//        for (int j = 0; j < C; j++) {
//            pr(tr[1][i][j], " ");
//        }
//        ps();
//    }
}

void changeH(int P, int Q, int W) {
    h[P][Q] = W;
    update(P / BSZ);
}

void changeV(int P, int Q, int W) {
    v[P][Q] = W;
    update(P / BSZ);
}


int escape(int C1, int C2) {
    int ans = tr[1][C1][C2];
    return ans;
}
