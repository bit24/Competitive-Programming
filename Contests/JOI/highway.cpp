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

int N;

struct LSegTr {
    int tr[4 * MAXN];

    void push(int i, int l, int r) {
        if (l == r || tr[i] == -1) return;

        tr[i * 2] = tr[i];
        tr[i * 2 + 1] = tr[i];

        tr[i] = -1;
    }

    int q(int i, int l, int r, int x) {
        push(i, l, r);
        if (l == r) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            return q(i * 2, l, mid, x);
        } else {
            return q(i * 2 + 1, mid + 1, r, x);
        }
    }

    void u(int i, int l, int r, int s, int e, int d) {
        push(i, l, r);
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            tr[i] = d;
            return;
        }
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
    }

    int q(int x) {
        return q(1, 0, N - 1, x);
    }

    void u(int s, int e, int d) {
        return u(1, 0, N - 1, s, e, d);
    }
};

int rt;

vi aL[MAXN];

int p[MAXN], d[MAXN], hC[MAXN], cS[MAXN];

LSegTr tr;
int tI[MAXN];
int tV[MAXN];

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
                tV[nTI] = cV;
                tI[cV] = nTI++;
            }
        }
    }
}

int fSame(int l, int r, int cT) {
    assert(tr.q(r) == cT);
    while (l != r) {
        int mid = (l + r) / 2;
        if (tr.q(mid) == cT) {
            r = mid;
        } else {
            l = mid + 1;
        }
    }
    assert(tr.q(l) == cT);
    return l;
}

vector<pi> path;

void query(int cV) {
    path.clear();

    while (cV != -1) {
        int cCS = cS[cV];
        int cT = tr.q(tI[cV]);

        int cFS = fSame(tI[cCS], tI[cV], cT);

        path.pb({cT, tI[cV] - cFS + 1});

        cV = p[tV[cFS]];
    }
}

void update(int cV, int cT) {
    while (cV != -1) {
        int cCS = cS[cV];

        tr.u(tI[cCS], tI[cV], cT);

        cV = p[cCS];
    }
}

int color[MAXN];

vector<pi> proc;

void processPath() {
    proc.clear();
    reverse(path.begin(), path.end());

    int cC = -1;
    int cCnt = 0;

    for (pi x : path) {
        if (color[x.f] == cC) {
            cCnt += x.s;
        } else {
            if (cCnt > 0) {
                proc.pb({cC, cCnt});
            }

            cC = color[x.f];
            cCnt = x.s;
        }
    }

    if (cCnt > 0) {
        proc.pb({cC, cCnt});
    }
}

int cnt = 0;

vector<pi> cntInversions(vector<pi> &input) {
    if (input.size() == 1) {
        return input;
    }

    int mid = input.size() / 2;
    vector<pi> a = vector<pi>(input.begin(), input.begin() + mid);
    vector<pi> b = vector<pi>(input.begin() + mid, input.end());

    a = cntInversions(a);
    b = cntInversions(b);

    vector<pi> combine;
    int nA = 0;
    int nB = 0;

    int remA = 0;
    for (pi x : a) {
        remA += x.s;
    }

    while (nA < a.size() || nB < b.size()) {
        if (nA < a.size()) {
            if (nB < b.size()) {
                if (b[nB].f < a[nA].f) {
                    cnt += remA * b[nB].s;
                    combine.pb(b[nB++]);
                } else {
                    remA -= a[nA].s;
                    combine.pb(a[nA++]);
                }
            } else {
                remA -= a[nA].s;
                combine.pb(a[nA++]);
            }
        } else {
            assert(nB < b.size());
            combine.pb(b[nB++]);
        }
    }
    return combine;
}

int main() {
    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> color[i];
    }

    vi queries;
    for (int i = 0; i < N - 1; i++) {
        int a, b;
        cin >> a >> b;
        a--, b--;
        aL[a].pb(b);
        queries.pb(b);
    }

    rt = 0;
    init();

    fill(tr.tr, tr.tr + 4 * MAXN, -1);
    update(rt, 0);

    for (int cQ : queries) {
//        ps(cQ);
        query(p[cQ]);
//        ps(path);
        processPath();
//        ps(proc);

        cnt = 0;
        cntInversions(proc);
//        pr("ans: ");
        cout << cnt << endl;

        update(cQ, cQ);
    }
}