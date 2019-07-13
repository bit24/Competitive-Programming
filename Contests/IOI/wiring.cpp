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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

const ll INF = 1e17;

void mineq(ll &a, ll b) {
    if (b < a) a = b;
}

vl pSum;

ll fSum(int l, int r) {
    return l == 0 ? pSum[r] : pSum[r] - pSum[l - 1];
}


ll min_total_length(vi r, vi b) {
    vector<pi> pts;
    for (int i : r) {
        pts.pb({i, 0});
    }
    for (int i : b) {
        pts.pb({i, 1});
    }
    sort(pts.begin(), pts.end());

    vi gSz;
    vi gSt;

    for (int i = 0; i < pts.size(); i++) {
        if (i == 0 || pts[i].s != pts[i - 1].s) {
            gSz.pb(1);
            gSt.pb(i);
        } else {
            gSz[gSz.size() - 1]++;
        }
    }

    int nG = gSz.size();

    pSum.resize(pts.size());
    pSum[0] = pts[0].f;
    for (int i = 1; i < pts.size(); i++) {
        pSum[i] = pSum[i - 1] + pts[i].f;
    }

    vector<vl> dp(nG);
    for (int i = 0; i < nG; i++) {
        dp[i].resize(gSz[i] + 1, INF);
    }

    dp[0][0] = 0;

    for (int cG = 0; cG < nG - 1; cG++) {

        // match current unmatched with first index of next group
        for (int m = 0; m < gSz[cG]; m++) {
            mineq(dp[cG][m + 1], dp[cG][m] + (pts[gSt[cG + 1]].f - pts[gSt[cG] + m].f));
        }

        // match remaining unmatched with next group
        for (int m = max(0, gSz[cG] - gSz[cG + 1]); m <= gSz[cG]; m++) {
            int nM = gSz[cG] - m;
            mineq(dp[cG + 1][nM],
                  dp[cG][m] + (fSum(gSt[cG + 1], gSt[cG + 1] + nM - 1) - fSum(gSt[cG] + m, gSt[cG + 1] - 1)));
        }

        // match next unmatched with last index of current group
        for (int m = 0; m < gSz[cG + 1]; m++) {
            mineq(dp[cG + 1][m + 1], dp[cG + 1][m] + (pts[gSt[cG + 1] + m].f - pts[gSt[cG + 1] - 1].f));
        }
    }

    return dp[nG - 1][gSz[nG - 1]];
}