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

int N, M;

vi l, r, y, cost;

ll min_distance(vi x, vi h, vi iL, vi iR, vi iY, int s, int g) {
    l = iL;
    r = iR;
    y = iY;

    N = x.size();
    M = l.size();
    ps(M);

    priority_queue<pi, vector<pi>, greater<pi>> start;
    priority_queue<pi, vector<pi>, greater<pi>> end;

    cost.resize(M + 2, 1e9);

    for (int i = 0; i < M; i++) {
        start.push({l[i], i});
        end.push({r[i], i});
    }

    l.pb(0);
    r.pb(0);
    y.pb(0);
    cost[M] = 0;

    end.push({0, M});

    bool sameH = true;

    int h0 = h[0];
    for (int i = 1; i < N; i++) {
        if (h[i] != h0) {
            sameH = false;
            break;
        }
    }

    ps(N, M);

    if (s == 0 && g == N - 1 && sameH) {
        set<pi> active;
        active.insert({0, M});

        for (int i = 0; i < N; i++) {
            while (end.size() && end.top().f < i) {
                pi tR = end.top();
                end.pop();
                active.erase({y[tR.s], tR.s});
            }

            if (active.size() == 0) {
                return -1;
            }

            while (start.size() && start.top().f == i) {
                pi tI = start.top();
                start.pop();
                int cBridge = tI.s;

                pi cP = {y[cBridge], cBridge};
                auto above = active.lower_bound(cP);

                int bestC = 1e9;
                if (above != active.end()) {
                    int pBridge = (*above).s;
                    bestC = min(bestC, cost[pBridge] + x[i] - x[l[pBridge]] + abs(y[pBridge] - y[cBridge]));
//                    if (i == 3) {
//                        ps("special");
//                        ps(x[i]);
//                        ps(pBridge);
//                        ps(cost[pBridge]);
//                        ps(x[i] - l[pBridge]);
//                        ps(abs(y[pBridge] - y[cBridge]));
//                        ps(cBridge);
//                        ps(bestC);
//                    }
                }

                if (above != active.begin()) {
                    auto below = --above;
                    int pBridge = (*below).s;
                    ps(pBridge);
                    bestC = min(bestC, cost[pBridge] + x[i] - x[l[pBridge]] + abs(y[pBridge] - y[cBridge]));
                }

                active.insert(cP);
                cost[tI.s] = bestC;
            }

            ps("after", i);
            ps(active);
        }

        int fBridge = (*active.begin()).s;
        int ans = cost[fBridge] + x[N - 1] - x[l[fBridge]] + y[fBridge];
        ps(cost);
        return ans;
    } else {

        return -1;
    }
}
