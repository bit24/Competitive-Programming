#include <bits/stdc++.h>

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
    const int DEBUG = false;

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

const int MAXN = 1e5 + 100;
const int INF = 1e8;

struct node {
    int c[2][2] = {{0,   INF},
                   {INF, 0}}; // empty node can only stay the same type
};

node infNode() {
    node ans;
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
            ans.c[i][j] = INF;
        }
    }
    return ans;
}

node nullNode() {
    node ans;
    ans.c[0][0] = -1;
    return ans;
}

void ps(node x) {
    ps(x.c[0][0], x.c[0][1], x.c[1][0], x.c[1][1]);
}

const node operator+(node a, node b) {
    if (a.c[0][0] == -1) {
        return b;
    }
    if (b.c[0][0] == -1) {
        return a;
    }

    node ans = infNode();
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 2; k++) {
                ans.c[i][j] = min(ans.c[i][j], a.c[i][k] + b.c[k][j]);
                ans.c[i][j] = min(ans.c[i][j], a.c[i][k] + b.c[k ^ 1][j] + 1);
            }
        }
    }
    return ans;
}

struct SegTr {
    node tr[4 * MAXN];

    void b(int i, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid);
        b(i * 2 + 1, mid + 1, r);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }

    node q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return nullNode();
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void u(int i, int l, int r, int x, node d) {
        if (l == r) {
            tr[i] = d;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            u(i * 2, l, mid, x, d);
        } else {
            u(i * 2 + 1, mid + 1, r, x, d);
        }
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }

    node q(int l, int r) {
        return q(1, 0, MAXN - 1, l, r);
    }

    void u(int x, node d) {
        u(1, 0, MAXN - 1, x, d);
    }
};

int N, rt;

vi aL[MAXN];

int p[MAXN], d[MAXN], hC[MAXN], cS[MAXN], cE[MAXN];

SegTr tr;
int tI[MAXN];

int initDFS(int cV) {
    int cCnt = 1, mSCnt = 0;

    for (int aV : aL[cV]) {
        if (aV != p[cV]) {
            p[aV] = cV;
            d[aV] = d[cV] + 1;

            int aCnt = initDFS(aV);
            if (aCnt > mSCnt) {
                mSCnt = aCnt;
                hC[cV] = aV;
            }
            cCnt += aCnt;
        }
    }
    return cCnt;
}

void init() {
    fill(hC, hC + MAXN, -1);
    p[rt] = -1;
    initDFS(rt);

    int nTI = 0;
    for (int curS = 0; curS < N; curS++) {
        if (curS == rt || hC[p[curS]] != curS) {
            for (int cV = curS; cV != -1; cV = hC[cV]) {
                cS[cV] = curS;
                cE[curS] = cV;
                tI[cV] = nTI++;
            }
        }
    }
}

// stores the contributions of all cS to their parents
int contrib[MAXN][2];


// purpose of this function is to edit data structure
void update(int cV, int type, int dCost) {
    ps("updating", cV, type, dCost);
    node edit = tr.q(tI[cV], tI[cV]);
    ps(edit);

    edit.c[type][type] += dCost;

    ps(edit);
    tr.u(tI[cV], edit);

    ps(tr.q(tI[cV], tI[cV]));

    while (cV != -1) {
        int cCS = cS[cV];
        int cCE = cE[cCS];

        node newRes = tr.q(tI[cCS], tI[cCE]);
        ps("start stop", cCS, cCE);

        // end of chain can be anything, only the start is significant
        int startRes[] = {min(newRes.c[0][0], newRes.c[0][1]), min(newRes.c[1][0], newRes.c[1][1])};

        // costs 1 extra to use opposite type
        int newContrib[] = {min(startRes[0], startRes[1] + 1), min(startRes[0] + 1, startRes[1])};

        int cP = p[cCS];
        if (cP != -1) {
            edit = tr.q(tI[cP], tI[cP]);

            ps("inside");
            ps(edit);

            for (int a = 0; a < 2; a++) {
                for (int b = 0; b < 2; b++) {
                    edit.c[a][b] -= contrib[cCS][b];

                    edit.c[a][b] += newContrib[b];
                }
            }

            ps(edit);

            tr.u(tI[cP], edit);
        }

        contrib[cCS][0] = newContrib[0];
        contrib[cCS][1] = newContrib[1];

        cV = cP;
    }
}

int type[MAXN];

//int getAns() {
//    node fRes = tr.q(tI[0], tI[cE[0]]);
//    return min(fRes.c[0][0], fRes.c[0][1]);
//}

int cat(int cV) {
    cV--;
    ps("cat");
    type[cV] = 0;
    update(cV, 1, INF); // force vertex to be cat by making dog cost really large
    return min(contrib[0][0], contrib[0][1]);
}

int dog(int cV) {
    cV--;
    ps("dog");
    type[cV] = 1;
    update(cV, 0, INF);
    return min(contrib[0][0], contrib[0][1]);
}

int neighbor(int cV) {
    cV--;
    ps("unset");
    update(cV, type[cV] ^ 1, -INF); // undo changes to opposite type
    type[cV] = 0;
    return min(contrib[0][0], contrib[0][1]);
}

void initialize(int iN, vi A, vi B) {
    N = iN;
    for (int i = 0; i < N - 1; i++) {
        A[i]--;
        B[i]--;
        aL[A[i]].pb(B[i]);
        aL[B[i]].pb(A[i]);
    }

    rt = 0;
    init();

    fill(type, type + MAXN, -1);

    memset(contrib, 0, sizeof(contrib));

    tr.b(1, 0, MAXN - 1);

//    ps(tr.q(1, 1));
}

void end() {
    node a = tr.q(tI[0], tI[0]);
    node b = tr.q(tI[3], tI[3]);
    node c = tr.q(tI[4], tI[4]);

    ps(a);
    ps(b);
    ps(c);
    ps(b + c);
    ps(a + (b + c));
}