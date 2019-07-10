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


int main() {
    int N;
    cin >> N;
    vector<pl> pts(2 * N);
    for (int i = 0; i < 2 * N; i++) {
        cin >> pts[i].f;
        cin >> pts[i].s;
    }

    vector<vi> grid(2);
    grid[0].resize(N);
    grid[1].resize(N);

    ll ans = 0;
    for (int i = 0; i < 2 * N; i++) {
        if (pts[i].s > 2) {
            ans += pts[i].s - 2;
            pts[i].s = 2;
        } else if (pts[i].s < 1) {
            ans += 1 - pts[i].s;
            pts[i].s = 1;
        }

        if (pts[i].f < 1) {
            ans += 1 - pts[i].f;
            pts[i].f = 1;
        } else if (pts[i].f > N) {
            ans += pts[i].f - N;
            pts[i].f = N;
        }
//        cout << pts[i].s - 1 << " " << pts[i].f - 1 << endl;
        grid[2 - pts[i].s][pts[i].f - 1]++;
    }

    // ps(grid);

    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < N; j++) {
            grid[i][j]--;
        }
    }

    // ps(grid);

    int u = 0;
    int l = 0;
    for (int i = 0; i < N; i++) {
        ans += abs(u) + abs(l);
        u += grid[0][i];
        l += grid[1][i];
        if (u > 0 && l < 0) {
            int d = min(u, -l);
            u -= d;
            l += d;
            ans += d;
        }
        if (u < 0 && l > 0) {
            int d = min(-u, l);
            u += d;
            l -= d;
            ans += d;
        }
        //ps(u, l);
    }
    cout << ans << endl;
//    cout << u << l << endl;
}