#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

int vT[300000];

int main() {
    for (int i = 0; i < 300000; i++) {
        vT[i] = -1;
    }
    ll m;
    int a, b;
    cin >> m >> a >> b;

    vT[0] = 0;
    int x = a;
    int maxR = a;

    while (x) { // modular arithmetic => will eventually cycle back to 0
        vT[x] = maxR; // from then on, will always be visitable
        if (x >= b) {
            x -= b;
        } else {
            x += a;
        }
        maxR = max(maxR, x);
    }

    ll ans = 0;
    for (int i = 0; i < a + b; i++) {
        if (vT[i] != -1) {
            ans += max(m - vT[i] + 1, 0ll);
        }
    }

    if (m >= a + b) {
        ll d = __gcd(a, b);
        ll fCnt = m - (a + b) + 1; // index (a+b)
        ll lCnt = m % d + 1; // number behind and including last visitable
        ps(fCnt, lCnt);
        ll num = (fCnt - lCnt) / d + 1; // fCnt is greater
        ans += (fCnt + lCnt) * num / 2;
    }
    cout << ans << endl;
}