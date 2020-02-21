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


const int MAXN = 200 + 5;
const ll INF = 1e15;

int N;
ll L;

ll dp[MAXN][MAXN][MAXN][2];

ll x[MAXN], t[MAXN];

ll dist(int l, int r) {
    if (l <= r) {
        return x[r] - x[l];
    } else {
        return L - x[l] + x[r];
    }
}

int main() {
    cin >> N >> L;

    x[0] = 0;
    t[0] = -1;
    for (int i = 1; i <= N; i++) {
        cin >> x[i];
    }

    for (int i = 1; i <= N; i++) {
        cin >> t[i];
    }

    for (int cVal = 0; cVal <= N; cVal++) {
        for (int l = 0; l <= N; l++) {
            for (int r = 0; r <= N; r++) {
                for (int s = 0; s < 2; s++) {
                    dp[cVal][l][r][s] = INF;
                }
            }
        }
    }

    dp[0][0][0][0] = 0;

    // we don't need to transition from state where we already have all N+1 points
    for (int len = 1; len <= N; len++) {
        for (int r = 0; r <= N; r++) {
            int l = r - len + 1;
            if (l > 0) {
                continue;
            }
            l = (l + N + 1) % (N + 1);

            for (int cVal = 0; cVal < N; cVal++) {
                for (int s = 0; s < 2; s++) {
                    ll cCost = dp[cVal][l][r][s];
                    if (cCost == INF) continue;

//                    ps(cVal, l, r, s);
//                    ps(cCost);

                    int nL = (l - 1 + N + 1) % (N + 1);
                    int nR = (r + 1) % (N + 1);

                    if (nL != r) {
                        ll nCost = cCost + dist(nL, s ? r : l);
                        int nVal = cVal;
                        if (nCost <= t[nL]) {
                            nVal++;
                        }
                        dp[nVal][nL][r][0] = min(dp[nVal][nL][r][0], nCost);
                    }

                    if (nR != l) {
                        ll nCost = cCost + dist(s ? r : l, nR);
                        int nVal = cVal;
                        if (nCost <= t[nR]) {
                            nVal++;
                        }
                        dp[nVal][l][nR][1] = min(dp[nVal][l][nR][1], nCost);
                    }
                }
            }

        }
    }

    int ans = 0;
    for (int cVal = 0; cVal <= N; cVal++) {
        for (int l = 0; l <= N; l++) {
            for (int r = 0; r <= N; r++) {
                for (int s = 0; s < 2; s++) {
                    ll cCost = dp[cVal][l][r][s];
                    if (cCost == INF) continue;

                    ans = max(ans, cVal);
                }
            }
        }
    }

    cout << ans << endl;
}