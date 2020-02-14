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


int query(int s, int t);

void answer(int i, int a);

void solve(int N) {
    vi sDiff(N + 1), dDiff(N + 1);

    for (int i = 1; i <= N; i++) {
        if (i + 1 <= N) {
            sDiff[i] = query(i, i + 1);
        }
        if (i + 2 <= N) {
            dDiff[i] = query(i, i + 2);
        }
    }

    vi a(N + 1);
    a[1] = 0;
    a[2] = sDiff[1];

    for (int i = 3; i <= N; i++) {
        bool lUp = a[i - 1] > a[i - 2];
        bool cUp;

        if (sDiff[i - 2] + sDiff[i - 1] == dDiff[i - 2]) {
            cUp = lUp;
        } else {
            cUp = !lUp;
        }
        if (cUp) {
            a[i] = a[i - 1] + sDiff[i - 1];
        } else {
            a[i] = a[i - 1] - sDiff[i - 1];
        }
    }

    int minV = 1e9;
    for (int i = 1; i <= N; i++) {
        minV = min(minV, a[i]);
    }

    int delta = 1 - minV;
    for (int i = 1; i <= N; i++) {
        a[i] += delta;
    }

    int minI = min_element(++a.begin(), a.end()) - a.begin();
    int maxI = max_element(++a.begin(), a.end()) - a.begin();

    if (minI > maxI) {
        for (int i = 1; i <= N; i++) {
            a[i] = N + 1 - a[i];
        }
    }

    for (int i = 1; i <= N; i++) {
        answer(i, a[i]);
    }

}
