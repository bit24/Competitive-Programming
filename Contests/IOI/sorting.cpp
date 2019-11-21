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

int N;
vi a, X, Y;

vector<pi> swaps;

vi mut;

bool sortK(int K) {
//    ps("call:", K);
    swaps.clear();
    mut.clear();
    mut.insert(mut.end(), a.begin(), a.end());

    for (int i = 0; i < K; i++) {
        swap(mut[X[i]], mut[Y[i]]);
    }

    vector<pi> aSwaps;

    for (int i = 0; i < N; i++) {
        while (mut[i] != i) {
            int goI = mut[i];
            aSwaps.pb({i, goI});
            swap(mut[i], mut[goI]);
        }
    }

//    ps("false check");

    if (aSwaps.size() > K) {
        return false;
    }

    vi loc(N);
    mut.clear();
    mut.insert(mut.end(), a.begin(), a.end());
    for (int i = 0; i < N; i++) {
        loc[mut[i]] = i;
    }
//    ps("final step");

    for (int i = 0; i < aSwaps.size(); i++) {
        swap(mut[X[i]], mut[Y[i]]);
        loc[mut[X[i]]] = X[i];
        loc[mut[Y[i]]] = Y[i];

//        ps("added:", i);

        swaps.pb({loc[aSwaps[i].f], loc[aSwaps[i].s]});
    }
    return true;
}

int findSwapPairs(int iN, int iS[], int M, int iX[], int iY[], int P[], int Q[]) {
//    ps("called");
    N = iN;
    for (int i = 0; i < N; i++) {
        a.pb(iS[i]);
    }
    for (int i = 0; i < M; i++) {
        X.pb(iX[i]);
        Y.pb(iY[i]);
    }

    int low = 0;
    int hi = M;
    while (low != hi) {
        int mid = (low + hi) / 2;
        if (sortK(mid)) {
            hi = mid;
        } else {
            low = mid + 1;
        }
    }

//    ps("here");

    sortK(low);

    for (int i = 0; i < low; i++) {
        if (swaps.size() <= i) {
            P[i] = 0;
            Q[i] = 0;
        } else {
            P[i] = swaps[i].f;
            Q[i] = swaps[i].s;
        }
    }
    return low;
}
