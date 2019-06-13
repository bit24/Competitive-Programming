#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef tuple<ll, ll, ll> ti;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const int MAXC = 2000 * 50 + 10;
const ll INF = 1e16;
ll mCost[MAXC];

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
    vector<ti> trans;

    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        ll c, f, v;
        cin >> c >> f >> v;
        trans.pb({f, c, v}); // will come first in case of f-tie as c is positive
    }

    int M;
    cin >> M;

    for (int i = 0; i < M; i++) {
        ll c, f, v;
        cin >> c >> f >> v;
        trans.pb({f, -c, -v});
    }

    sort(trans.begin(), trans.end());
    reverse(trans.begin(), trans.end());
    // decreasing frequency, computers first

    // ps("read");

    for (int i = 0; i < MAXC; i++) {
        mCost[i] = INF;
    }
    mCost[0] = 0;

    for (ti cT : trans) {
        int cD = get<1>(cT);
        int vD = get<2>(cT);
        // ps("printed");
        if (cD > 0) {
            for (int j = MAXC - cD - 1; j >= 0; j--) {
                mCost[j + cD] = min(mCost[j + cD], mCost[j] + vD);
            }
        } else {
            for (int j = -cD; j < MAXC; j++) {
                mCost[j + cD] = min(mCost[j + cD], mCost[j] + vD);
            }
        }
    }

    ll ans = 0;
    for (int i = 0; i < MAXC; i++) {
        ans = min(ans, mCost[i]);
    }

    ans = -ans;
    cout << ans << endl;
}
