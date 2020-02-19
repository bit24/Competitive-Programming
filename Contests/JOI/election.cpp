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

struct SegTr {
    ll tr[4 * MAXN];

    ll q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void u(int i, int l, int r, int x, ll d) {
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
};

int N, rt;

vi aL[MAXN];

int p[MAXN], d[MAXN], hC[MAXN], cS[MAXN];

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
                tI[cV] = nTI++;
            }
        }
    }
}

ll query(int a, int b) {
    ll sum = 0;
    while (cS[a] != cS[b]) {
        if (d[cS[a]] < d[cS[b]]) swap(a, b);

        sum += tr.q(1, 0, N - 1, tI[cS[a]], tI[a]);
        a = p[cS[a]];
    }

    if (tI[a] > tI[b]) swap(a, b);

    sum += tr.q(1, 0, N - 1, tI[a], tI[b]);
    return sum;
}

void update(int cV, ll nV) {
    tr.u(1, 0, N - 1, tI[cV], nV);
}

int lca(int a, int b) {
    while (cS[a] != cS[b]) {
        if (d[cS[a]] < d[cS[b]]) swap(a, b);

        a = p[cS[a]];
    }

    if (tI[a] > tI[b]) swap(a, b);

    return a;
}

vector<pair<pi, int>> ops[MAXN];
ll dp[MAXN];

void compute(int cV) {
    ll chSum = 0;
    for (int aV : aL[cV]) {
        if (aV != p[cV]) {
            compute(aV);
            chSum += dp[aV];
        }
    }

    ll cBest = chSum;
    for (auto x : ops[cV]) {
        ll cVal = query(x.f.f, x.f.s) + x.s + chSum;
        cBest = max(cBest, cVal);
    }

    dp[cV] = cBest;
    update(cV, chSum - cBest);
//    ps("stored", cV, chSum - cBest);
}

int main() {
    cin >> N;
    for (int i = 0; i < N - 1; i++) {
        int x, y;
        cin >> x >> y;
        x--, y--;
        aL[x].pb(y);
        aL[y].pb(x);
    }

    rt = 0;
    init();

    memset(tr.tr, 0, sizeof(tr.tr));

    int M;
    cin >> M;

    for (int i = 0; i < M; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        a--, b--;
        int cLCA = lca(a, b);
        ops[cLCA].pb({{a, b}, c});
    }

    compute(rt);
//
//    for (int i = 0; i < N; i++) {
//        ps(i, dp[i]);
//    }
//

    ll ans = dp[rt];
    cout << ans << endl;
}