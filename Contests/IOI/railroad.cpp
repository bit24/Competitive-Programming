#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef tuple<int, int, int> ti;

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

struct DSU {
    vi anc;

    void init(int N) {
        anc.resize(N);
        for (int i = 0; i < N; i++) {
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

ll plan_roller_coaster(vi s, vi e) {
    s.pb(1e9);
    e.pb(1);

    int N = s.size();
    set<int> pts;

    for (int i = 0; i < N; i++) {
        pts.insert(s[i]);
        pts.insert(e[i]);
    }

    int PS = pts.size();

    map<int, int> pMap;
    vi ptl;

    int cP = 0;
    for (int x : pts) {
        pMap[x] = cP++;
        ptl.pb(x);
    }

    for (int i = 0; i < N; i++) {
        s[i] = pMap[s[i]];
        e[i] = pMap[e[i]];
    }
    ps("mapped");

    vi up(PS, 0);
    vi down(PS, 0);

    for (int i = 0; i < N; i++) {
        if (s[i] < e[i]) {
            up[s[i]]++;
            up[e[i]]--;
        } else {
            down[e[i]]++;
            down[s[i]]--;
        }
    }
    int uSum = 0, dSum = 0;

    for (int i = 0; i < PS; i++) {
        uSum += up[i];
        dSum += down[i];
        up[i] = uSum;
        down[i] = dSum;
    }

    ps("segmented");
    ps(ptl);
    ps(s);
    ps(e);

    dsu.init(PS);

    ll cost = 0;

    for (int i = 0; i < N; i++) {
        ps("merge:", s[i], e[i]);
        dsu.merge(s[i], e[i]);
    }

    for (int i = 0; i < PS - 1; i++) {
        if (up[i] > down[i]) {
            cost += (ll) (up[i] - down[i]) * (ptl[i + 1] - ptl[i]);
            ps("merge:", i, i + 1);
            dsu.merge(i, i + 1); // will fall through
        }
        if (up[i] < down[i]) {
            ps("merge:", i, i + 1);
            dsu.merge(i, i + 1); // will jump up
        }
    }

    for (int i = 0; i < PS; i++) {
        pr(dsu.fRt(i), " ");
    }
    ps();

    vector<pi> mstE;
    for (int i = 0; i < PS - 1; i++) {
        mstE.pb({ptl[i + 1] - ptl[i], i});
    }

    sort(mstE.begin(), mstE.end());
    for (pi x : mstE) {
        int c1 = dsu.fRt(x.s);
        int c2 = dsu.fRt(x.s + 1);
        if (c1 != c2) {
            dsu.merge(c1, c2);
            cost += x.f;
        }
    }

    return cost;
}