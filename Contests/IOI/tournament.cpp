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
    const int DEBUG = true;

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


const int MAXN = 1e5 + 10;

struct MaxSegTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r, int o[]) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }
} maxSegTr;

struct SumSegTr {
    int tr[4 * MAXN], lz[4 * MAXN];

    void b(int i, int l, int r) {
        lz[i] = 0;
        if (l == r) {
            tr[i] = 1;
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid);
        b(i * 2 + 1, mid + 1, r);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }

    void push(int i, int l, int r) {
        if (lz[i] == 0) {
            return;
        }
        tr[i] = 0;
        if (l != r) {
            lz[i * 2] = 1;
            lz[i * 2 + 1] = 1;
        }
        lz[i] = 0;
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        push(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void clear(int i, int l, int r, int s, int e) {
        push(i, l, r); // pushed early to use in recalculation of parent
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            lz[i] = 1;
            push(i, l, r);
            return;
        }
        int mid = (l + r) / 2;
        clear(i * 2, l, mid, s, e);
        clear(i * 2 + 1, mid + 1, r, s, e);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }

    int qInd(int i, int l, int r, int nBef) {
        if (l == r) {
            assert(nBef == 0);
            return l;
        }

        int mid = (l + r) / 2;
        if (tr[i * 2] > nBef) {
            return qInd(i * 2, l, mid, nBef);
        } else {
            nBef -= tr[i * 2];
            return qInd(i * 2 + 1, mid + 1, r, nBef);
        }
    }

} sumSegTr;

int N;
int Q;
int R;

vector<pi> intervals;

int dArr[MAXN];

int GetBestPosition(int iN, int iQ, int iR, int *ranks, int *S, int *E) {
    N = iN, Q = iQ, R = iR;

    maxSegTr.b(1, 0, N - 2, ranks);
    sumSegTr.b(1, 0, N);

//    ps("first");
//    for (int i = 0; i < N - 1; i++) {
//        ps(i, sumSegTr.q(1, 0, N - 1, 0, i));
//    }

    for (int cQ = 0; cQ < Q; cQ++) {
        int cS = S[cQ], cE = E[cQ];

        int actS = sumSegTr.qInd(1, 0, N, cS);
        int actE = sumSegTr.qInd(1, 0, N, cE + 1);
        intervals.pb({actS, actE-1});

        sumSegTr.clear(1, 0, N, actS + 1, actE - 1);

//        ps("cQ:", cQ);
//        for (int i = 0; i < N - 1; i++) {
//            ps(i, sumSegTr.q(1, 0, N, 0, i));
//        }
    }

//    ps(intervals);

    memset(dArr, 0, sizeof(dArr));

    for (int cQ = 0; cQ < Q; cQ++) {
        int cL = intervals[cQ].f, cR = intervals[cQ].s;

        int maxR = maxSegTr.q(1, 0, N - 2, cL, cR - 1);

        if (maxR < R) {
            dArr[cL]++;
            dArr[cR + 1]--;
        }
    }

    int bI = 0;
    int bC = -1;

    int cur = 0;
    for (int i = 0; i < N; i++) {
        cur += dArr[i];
//        ps(i, cur);
        if (cur > bC) {
            bC = cur;
            bI = i;
        }
    }
    return bI;
}