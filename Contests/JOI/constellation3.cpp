#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef pair<pi, ll> pt;

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


const int MAXN = 2e5 + 20;

int a[MAXN];
int N, M;

// cartesian tree
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

pt pts[MAXN];
pi pil[MAXN];

bool grY(pt a, pt b) {
    return a.f.s > b.f.s;
}

// dp information
vector<pt> rel[MAXN];

ll maxV[MAXN];

struct BIT {
    ll tr[MAXN];

    void init() {
        memset(tr, 0, sizeof(tr));
    }

    void u(int i, ll d) {
        i++;
        while (i < MAXN) {
            tr[i] += d;
            i += (i & -i);
        }
    }

    ll q(int i) {
        i++;
        ll s = 0;
        while (i > 0) {
            s += tr[i];
            i -= (i & -i);
        }
        return s;
    }

    void u(int l, int r, ll d) {
        u(l, d);
        u(r + 1, -d);
    }
} bit;

int tSt[MAXN], tEnd[MAXN];

int nT = 0;

void number(int i) {
    assert(p[i] == -1 || a[p[i]] >= a[i]);
    tSt[i] = nT++;
    if (l[i] != -1) {
        number(l[i]);
    }
    if (r[i] != -1) {
        number(r[i]);
    }
    tEnd[i] = nT - 1;
}

void compute(int i) {
    ll cMax = 0;
    if (l[i] != -1) {
        compute(l[i]);
        cMax += maxV[l[i]];
    }
    if (r[i] != -1) {
        compute(r[i]);
        cMax += maxV[r[i]];
    }

    if (l[i] != -1 && r[i] != -1) {
        bit.u(tSt[l[i]], tEnd[l[i]], maxV[r[i]]);
        bit.u(tSt[r[i]], tEnd[r[i]], maxV[l[i]]);
    }

    for (pt x : rel[i]) {
//        if (i == 2) {
//            assert(x.f.f == 0);
//            int cI = x.f.f;
//
//            ps(x);
//            ps(bit.q(tSt[cI]));
//            ps((l[cI] != -1 ? maxV[l[cI]] : 0));
//            ps((r[cI] != -1 ? maxV[r[cI]] : 0));
//        }
        int cI = x.f.f;
        ll cV = x.s + bit.q(tSt[cI]) + (l[cI] != -1 ? maxV[l[cI]] : 0) + (r[cI] != -1 ? maxV[r[cI]] : 0);
        cMax = max(cMax, cV);
    }

    maxV[i] = cMax;
//    ps(i, maxV[i]);
}

int main() {
    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> a[i];
    }
    a[N] = 2e5 + 1;
    N++;

    cin >> M;

    for (int i = 0; i < M; i++) {
        cin >> pts[i].f.f >> pts[i].f.s >> pts[i].s;
        pts[i].f.f--;
    }

    buildCartesian();
//
//    for (int i = 0; i < N; i++) {
//        ps(i, l[i], r[i], p[i]);
//    }

    // find relevant points for each interval
    // decreasing y-coordinate
    sort(pts, pts + M, grY);

    for (int i = 0; i < N; i++) {
        pil[i] = {a[i], i};
    }
    sort(pil, pil + N, greater<pi>());

    set<int> act;
    act.insert(-1);
    int nPt = 0;
    for (int cPI = 0; cPI < N; cPI++) {
        int cA = pil[cPI].f;
//        ps(cA);

        while (nPt < M && pts[nPt].f.s > cA) {
//            ps(pts[nPt]);
//            ps(pts[nPt].f.s);
//            ps("act:", act);

            int left = *prev(act.upper_bound({pts[nPt].f.f}));
            int right = *act.upper_bound({pts[nPt].f.f});

//            ps(left, right);

            if (left != -1 && a[left] <= a[right]) {
                rel[r[left]].pb(pts[nPt]);
            } else {
                rel[l[right]].pb(pts[nPt]);
            }
            nPt++;
        }

        act.insert(pil[cPI].s);
    }

    for (int i = 0; i < N; i++) {
//        ps(i, a[i]);
//        ps(rel[i]);

        for (auto x : rel[i]) {
            assert(x.f.s > a[i]);
//            assert(x.f.s <= a[p[i]]);
        }
    }

    // preprocess for queries
    number(rt);

//    for (int i = 0; i < N; i++) {
//        ps(tSt[i], tEnd[i]);
//    }

    bit.init();
    compute(rt);

    ll sub = maxV[rt];
    ll ans = 0;
    for (int i = 0; i < M; i++) {
        ans += pts[i].s;
    }
    ans -= sub;
    cout << ans << endl;
}