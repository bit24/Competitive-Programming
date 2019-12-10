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

ll target;
ll orig;

int conv(ll b) {
//    ps("attempt", b);

    ll x = orig;
    ld pow = 1;
    ld res = 0;

    while (true) {
        res = res + (x % 10) * pow;

        if (res > target) {
            return 1;
        }

        x /= 10;
        if (x == 0) {
            break;
        }

        pow *= b;
        if (pow > target) {
            return 1;
        }
    }
//    ps(res);

    if (res > target) {
        return 1;
    }
    if (res == target) {
        return 0;
    }
    if (res < target) {
        return -1;
    }
}

ll binSearch() {
    ll low = 10;
    ll hi = 1e18;
    while (low != hi) {
        ll mid = (low + hi)/2;
        int res = conv(mid);
        if (res == 0) {
            return mid;
        }
        if (res == -1) {
            low = mid + 1;
        } else {
            hi = mid - 1;
        }
    }
    return low;
}

int main() {
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        cin >> orig >> target;
        ll ans = binSearch();
        cout << "Hotel Visit #" << cT << ": Base " << ans << endl;
    }
}