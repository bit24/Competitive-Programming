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


ll findI(ll N, ll x) {
    if (x & 1) {
        return (N + 1) / 2 + x / 2;
    } else {
        return x / 2;
    }
}

int main() {
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        ll N, a, b;
        cin >> N >> a >> b;
        a--, b--;

        if (a > b) {
            swap(a, b);
        }
        b -= a;
        a = 0;

        cout << "Graph #" << cT << ": ";

        if (N == 3) {
            if (a == b) {
                cout << 0 << endl;
            } else {
                cout << 1 << endl;
            }
            continue;
        }

        if (N & 1) {
            ll ans = findI(N, b);
            cout << min(ans, N - ans) << endl;
        } else {
            if (b & 1) {
                cout << "Impossible" << endl;
            } else {
                ll d1 = b / 2;
                ll d2 = (N - b) / 2;
                cout << min(d1, d2) << endl;
            }
        }
    }
}