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


int findSample(int N, int c[], int p[], int t[]) {
    int take[N], nTake[N];

    for (int i = 0; i < N; i++) {
        take[i] = c[i];
        nTake[i] = 0;
    }

    for (int i = N - 1; i > 0; i--) {
        int cP = p[i], cT = t[i];
        if (cT == 0) {
            take[cP] += nTake[i];
            nTake[cP] += max(take[i], nTake[i]);
        } else if (cT == 1) {
            take[cP] = max(take[cP] + nTake[i], max(nTake[cP] + take[i], take[cP] + take[i]));
            nTake[cP] += nTake[i];
        } else {
            assert(cT == 2);
            take[cP] = max(take[cP] + nTake[i], nTake[cP] + take[i]);
            nTake[cP] += nTake[i];
        }
//        if (true) {
//            ps();
//            ps(cP, cT);
//            ps(i);
//            for (int i = 0; i < N; i++) {
//                ps(take[i], nTake[i]);
//            }
//        }
    }
//    ps();
//    for (int i = 0; i < N; i++) {
//        ps(take[i], nTake[i]);
//    }
    return max(take[0], nTake[0]);
}