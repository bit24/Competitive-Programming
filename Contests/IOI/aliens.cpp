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


int N;
int K;
vl diag, fC;

bool maxFMinS(pi a, pi b) {
    return a.f > b.f || (a.f == b.f && a.s < b.s);
}

deque<pair<pl, ll>> hull; // curved downwards

ll eval(pl l, ll x) {
    return l.f * x + l.s;
}

ld xInt(pl a, pl b) {
    return (ld) (b.s - a.s) / (a.f - b.f);
}

bool relevant() {
    if (hull.size() <= 2) {
        return true;
    }
    return xInt(hull[hull.size() - 3].f, hull[hull.size() - 2].f) <
           xInt(hull[hull.size() - 3].f, hull[hull.size() - 1].f);
}

void addData(pair<pl, ll> d) {
    assert(hull.empty() || hull[hull.size() - 1].f.f > d.f.f);
    hull.pb(d);
    while (!relevant()) {
        hull[hull.size() - 2] = hull[hull.size() - 1];
        hull.pop_back();
    }
}

bool secBetter(ll x) {
    if (hull.size() < 2) {
        return false;
    }
    ll e0 = eval(hull[0].f, x);
    ll e1 = eval(hull[1].f, x);
    return e1 < e0 || (e1 == e0 && hull[1].s < hull[0].s);
}

pl qPt(ll x) {
    while (secBetter(x)) {
        hull.pop_front();
    }
    return {eval(hull[0].f, x), hull[0].s};
}


ll sqr(ll x) {
    return x * x;
}

pl calcDP(ll C) {
    hull.clear();
    vector<pl> dp(N + 1);

    addData({{2 * (-fC[1] + 1), sqr(-fC[1] + 1)}, 0});

    for (int i = 1; i <= N; i++) {
        pl qret = qPt(diag[i]);
        qret.f += sqr(diag[i]) + C;
        qret.s++;
        dp[i] = qret;
        if (i < N) {
            addData({{2 * (-fC[i + 1] + 1), dp[i].f + -sqr(max(0ll, diag[i] - fC[i + 1] + 1)) + sqr(-fC[i + 1] + 1)},
                     dp[i].s});
        }
    }
    return dp[N];
}

ll binSearch() {
    ll lo = 0;
    ll hi = 1e12;

    while (lo != hi) {
        ll mid = (lo + hi) / 2;
        pl res = calcDP(mid);

        if (res.s > K) {
            lo = mid + 1;
        } else {
            hi = mid;
        }
    }

    pl ref = calcDP(lo);
    return ref.f - K * lo;
}

ll take_photos(int iN, int M, int iK, vi r, vi c) {
    K = iK;
    vector<pi> pts;
    for (int i = 0; i < iN; i++) {
        if (r[i] < c[i]) {
            pts.pb({c[i], r[i]});
        } else {
            pts.pb({r[i], c[i]});
        }
    }

    sort(pts.begin(), pts.end(), maxFMinS);

    vector<pi> flt;

    for (pi cP : pts) {
        if (flt.empty()) {
            flt.pb(cP);
        } else {
            pi lst = flt[flt.size() - 1];

            if (cP.s < lst.s) {
                flt.pb(cP);
            }
        }
    }
    reverse(flt.begin(), flt.end());

    N = flt.size();
    diag.resize(N + 1, 0);
    fC.resize(N + 1, 0);
    for (int i = 1; i <= N; i++) {
        diag[i] = flt[i - 1].f;
        fC[i] = flt[i - 1].s;
    }

    return binSearch();
}