#include <bits/stdc++.h>
#include "robots.h"

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

int N, nA, nB;

vector<pi> reqs;

vi aL, bL;

bool pos(int T) {
//    ps(T);

    vi a;
    multiset<pi> b;

    for (int i = aL.size() - 1; i >= 0; i--) {
        for (int j = 0; j < T && a.size() < N; j++) {
            a.pb(aL[i]);
        }
    }

    int rem = N;
    for (int i = bL.size() - 1; i >= 0; i--) {
        if (rem > 0) {
            int cur = min(rem, T);
            b.insert({bL[i], cur});
            rem -= cur;
        }
    }

//    if (T == 2) {
//        ps("T == 2");
//        for (auto x : b) {
//            ps(x);
//        }
//        ps("fin print");
//    }

    if (a.size() + min(N, T * nB) < N) {
        return false;
    }

    priority_queue<int, vi, greater<int>> swap;

    int nA = 0;
    for (int i = N - 1; i >= 0; i--) {
        swap.push(reqs[i].s);
        if (nA < a.size() && a[nA] >= reqs[i].f) {
            nA++;
        } else {
            int needB = swap.top();
            swap.pop();

            auto it = b.lower_bound({needB, -1});
            if (it == b.end()) {
                return false;
            }

            pi valCnt = *it;
            b.erase(it);

//            ps(valCnt);

            valCnt.s--;
            if (valCnt.s > 0) {
                b.insert(valCnt);
            }
        }
    }
    return true;
}

int binSearch() {
    int low = 1;
    int hi = N + 1;

    while (low != hi) {
        int mid = (low + hi) / 2;
        if (pos(mid)) {
            hi = mid;
        } else {
            low = mid + 1;
        }
    }
    return low;
}

int putaway(int A, int B, int T, int X[], int Y[], int W[], int S[]) {
    N = T, nA = A, nB = B;

    for (int i = 0; i < nA; i++) {
        aL.pb(X[i] - 1);
    }
    for (int i = 0; i < nB; i++) {
        bL.pb(Y[i] - 1);
    }

    for (int i = 0; i < N; i++) {
        reqs.pb({W[i], S[i]});
    }

    sort(aL.begin(), aL.end());
    sort(bL.begin(), bL.end());
    sort(reqs.begin(), reqs.end());
//
//    ps(aL);
//    ps(bL);
//    ps(reqs);

    int time = binSearch();
    if (time == N + 1) {
        return -1;
    }
    return time;
}