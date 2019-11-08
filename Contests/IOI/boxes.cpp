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


ll delivery(int N, int K, int L, int posI[]) {
    vi pos(posI, posI + N);
    pos.insert(pos.begin(), 0);
    pos.pb(0);

    vl costR(N + 1);
    costR[0] = 0;
    for (int i = 1; i <= N; i++) {
        costR[i] = 2ll * pos[i] + costR[max(0, i - K)];
    }

    vl costL(N + 2);
    costL[N + 1] = 0;
    for (int i = N; i >= 1; i--) {
        costL[i] = 2ll * (L - pos[i]) + costL[min(N + 1, i + K)];
    }

    ll ans = 1e15;

    // no full circles
    for (int i = 0; i <= N; i++) {
        ans = min(ans, costR[i] + costL[i + 1]);
    }

    // one full circle
    for (int i = 0; i <= N; i++) {
        ans = min(ans, L + costR[i] + costL[min(N + 1, i + K + 1)]);
    }

    return ans;
}