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
        //pr("{");
        bool fst = 1;
        for (auto &a : x) {
            pr(fst ? "" : " ", a), fst = 0;
        }
        // pr("}");
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

void run() {
    int N;
    cin >> N;
    vi op;
    for (int i = 2; i <= N; i++) {
        op.pb(i);
    }

    ps(1, op.size(), 1, op);
    cout << flush;
    int max;
    cin >> max;
    while (op.size() != 1) {
        vi a;
        vi b;
        for (int i = 0; i < op.size() / 2; i++) {
            a.pb(op[i]);
        }
        for (int i = op.size() / 2; i < op.size(); i++) {
            b.pb(op[i]);
        }

        ps(1, a.size(), 1, a);
        cout << flush;
        int nMax;
        cin >> nMax;
        if (nMax == max) {
            op = a;
        } else {
            op = b;
        }
    }

    int ft = op[0];
    vi oth;
    for (int i = 1; i <= N; i++) {
        if (i != ft) {
            oth.pb(i);
        }
    }
    ps(1, oth.size(), ft, oth);
    cout << flush;
    cin >> max;
    ps(-1, max);
    cout << flush;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int T;
    cin >> T;

    for (int i = 0; i < T; i++) {
        run();
    }
}