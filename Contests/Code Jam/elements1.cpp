#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<long, long> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

struct frac {
    ll n;
    ll d;
};

namespace debug {
    const int DEBUG = false;

    void pr(const frac &x);

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

    void pr(const frac &x) { pr("{", x.n, ", ", x.d, "}"); }

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

bool cmp(frac a, frac b) {
    if (a.d == 0) {
        return false;
    }
    return 1LL * a.n * b.d < 1LL * b.n * a.d;
}

bool equals(frac a, frac b) {
    return 1LL * a.n * b.d == 1LL * b.n * a.d;
}

int main() {
    int nT;
    cin >> nT;

    for (int cT = 1; cT <= nT; cT++) {
        int N;
        cin >> N;
        vi a, b;
        vector<frac> fracs;

        for (int i = 0; i < N; i++) {
            int x, y;
            cin >> x >> y;
            a.pb(x);
            b.pb(y);
        }

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                frac nF = {b[j] - b[i], a[i] - a[j]};
                if (nF.d < 0) {
                    nF.n = -nF.n;
                    nF.d = -nF.d;
                }
                if (nF.n > 0 && nF.d > 0) {
                    fracs.pb(nF);
                }
            }
        }
        sort(fracs.begin(), fracs.end(), cmp);

        ps(fracs);

        int cnt = 1;

        for (int i = 0; i < fracs.size(); i++) {
            if (i != 0 && equals(fracs[i], fracs[i - 1])) {
                ps("a");
                continue;
            }
            cnt++;
        }
        cout << "Case #" << cT << ": " << cnt << endl;
    }
    cout << flush;
}