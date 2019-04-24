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

const int MAXN = 200005;
const int MAXM = 400005;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

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
} dsu;

struct Tree {
    vi cList[MAXN];
    int in[MAXN];
    int out[MAXN];

    int cT = 0;

    void dfs(int cV) {
        in[cV] = cT++;
        for (int aV : cList[cV]) {
            dfs(aV);
        }
        out[cV] = cT - 1;
    }

} t1, t2;

struct SegTr {
    int tr[4 * MAXN];

    void init() {
        fill(tr, tr + 4 * MAXN, -1e9);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return -1e9;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }

    void u(int i, int l, int r, int x, int d) {
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
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }
} segTr;

struct Query {
    int s, e, l, r, r1, r2, i;
};

bool cmpL(Query a, Query b) {
    return a.l > b.l;
}

bool cmpR(Query a, Query b) {
    return a.r < b.r;
}

bool cmpFR(Query a, Query b) {
    return t1.out[a.r1] < t1.out[b.r1];
}

vi aList[MAXN];

vi check_validity(int N, vi X, vi Y, vi S, vi E, vi L, vi R) {
    int M = X.size();
    int Q = S.size();

    for (int i = 0; i < M; i++) {
        aList[X[i]].pb(Y[i]);
        aList[Y[i]].pb(X[i]);
    }

    vector<Query> qs;

    for (int i = 0; i < Q; i++) {
        qs.pb({S[i], E[i], L[i], R[i], -1, -1, i});
    }

    //tree 1
    dsu.init();
    sort(qs.begin(), qs.end(), cmpL);

    int cV = N - 1;
    for (Query &cQ : qs) {
        while (cV >= cQ.l) {
            for (int aV : aList[cV]) {
                if (aV > cV) {
                    if (dsu.fRt(aV) != cV) {
                        t1.cList[cV].pb(dsu.fRt(aV));
                        dsu.merge(aV, cV);
                    }
                }
            }
            cV--;
        }
        cQ.r1 = dsu.fRt(cQ.s);
    }

    while (cV >= 0) {
        for (int aV : aList[cV]) {
            if (aV > cV) {
                if (dsu.fRt(aV) != cV) {
                    t1.cList[cV].pb(dsu.fRt(aV));
                    dsu.merge(aV, cV);
                }
            }
        }
        cV--;
    }

    t1.dfs(0);

    //tree 2
    dsu.init();
    sort(qs.begin(), qs.end(), cmpR);

    cV = 0;
    for (Query &cQ : qs) {
        while (cV <= cQ.r) {
            for (int aV : aList[cV]) {
                if (aV < cV) {
                    if (dsu.fRt(aV) != cV) {
                        t2.cList[cV].pb(dsu.fRt(aV));
                        dsu.merge(aV, cV);
                    }
                }
            }
            cV++;
        }
        cQ.r2 = dsu.fRt(cQ.e);
    }

    while (cV < N) {
        for (int aV : aList[cV]) {
            if (aV < cV) {
                if (cV == 5) {
                    ps(aV);
                }
                if (dsu.fRt(aV) != cV) {
                    t2.cList[cV].pb(dsu.fRt(aV));
                    dsu.merge(aV, cV);
                }
            }
        }
        cV++;
    }

    t2.dfs(N - 1);

    /*
    ps(cV);

    for(int i = 0; i <= 5; i++){
        ps(t2.cList[i]);
    }

    for (int i = 0; i < N; i++) {
        pr(t2.in[i], " ");
    }
    ps();
    for (int i = 0; i < N; i++) {
        pr(t2.out[i], " ");
    }
    ps();*/

    vector<pi> pts;

    for (int i = 0; i < N; i++) {
        pts.pb({t1.in[i], t2.in[i]});
        ps(pts[i]);
    }

    sort(pts.begin(), pts.end());
    sort(qs.begin(), qs.end(), cmpFR);


    for (Query q : qs) {
        ps(q.r1, q.r2);
        ps(t1.in[q.r1], t1.out[q.r1], t2.in[q.r2], t2.out[q.r2]);
    }

    segTr.init();

    vi ans(Q);

    ps(ans);
    int pI = 0;
    for (Query cQ : qs) {
        ps("qI:", cQ.i);
        int fR = t1.out[cQ.r1];
        while (pI < N && pts[pI].f <= fR) {
            segTr.u(1, 0, N - 1, pts[pI].second, pts[pI].first);
            pI++;
        }

        ps(segTr.q(1, 0, N - 1, t2.in[cQ.r2], t2.out[cQ.r2]));
        ans[cQ.i] = segTr.q(1, 0, N - 1, t2.in[cQ.r2], t2.out[cQ.r2]) >= t1.in[cQ.r1];
    }
    return ans;
}