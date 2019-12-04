#include <bits/stdc++.h>
#include "gondola.h"

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

int valid(int N, int a[]) {
    set<int> seen;

    int off = -1;

    for (int i = 0; i < N; i++) {
        if (seen.count(a[i])) {
            return false;
        }
        seen.insert(a[i]);

        if (a[i] <= N) {
            int nOff = (N + i - (a[i] - 1)) % N;
            if (off == -1) {
                off = nOff;
            } else {
                if (off != nOff) {
                    return false;
                }
            }
        }
    }
    return true;
}

int replacement(int N, int a[], int rep[]) {

    int off = 0;
    for (int i = 0; i < N; i++) {
        if (a[i] <= N) {
            off = (N + i - (a[i] - 1)) % N;
        }
    }
//    ps(off);

    vi mod(a, a + N);

    for (int i = 0; i < N; i++) {
        if (mod[i] > N) {
            mod[i] = (i + 1 + N - off) % N;
            if (mod[i] == 0) {
                mod[i] = N;
            }
        }
    }

//    ps(mod);

    vector<pi> ord;

    for (int i = 0; i < N; i++) {
        if (a[i] > N) {
            ord.pb({a[i], i});
        }
    }

    sort(ord.begin(), ord.end());

    int nxt = N + 1;
    int L = 0;

    for (pi cur : ord) {
        while (nxt <= cur.f) {
            rep[L++] = mod[cur.s];
            mod[cur.s] = nxt++;
        }
    }

    return L;
}

const ll MOD = 1e9 + 9;

ll binExp(ll b, ll p) {
//    ps(b, p);
    b %= MOD;
    ll c = 1;
    while (p > 0) {
        if (p & 1) {
            c = c * b % MOD;
        }
        b = b * b % MOD;
        p >>= 1;
    }
    return c;
}

int countReplacement(int N, int a[]) {
    if (!valid(N, a)) {
        return 0;
    }

    int fixed = 0;

    vi free;
    for (int i = 0; i < N; i++) {
        if (a[i] <= N) {
            fixed++;
        } else {
            free.pb(a[i]);
        }
    }
//    ps(fixed);
//    ps(free);

    sort(free.begin(), free.end());

    int nxt = N + 1;

    ll cnt = 1;

    for (int i = 0; i < free.size(); i++) {
        cnt = cnt * binExp(N - fixed - i, free[i] - nxt) % MOD;
        nxt = free[i] + 1;
    }

    if (fixed == 0) {
        cnt = cnt * N % MOD;
    }

    return cnt;
}

//int main() {
//    int inp[] = {3, 4};
//    ps(countReplacement(2, inp));
//}