#include <bits/stdc++.h>
#include "prize.h"

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


int callCnt = 0;

int gGrt(int i) {
    callCnt++;
//    ps("callCnt", callCnt);
    vi ret = ask(i);
    return ret[0] + ret[1];
}

int maxG, ans = -1;

void srch(int l, int r, int cnt, int lS, int rS) {
    if (ans != -1) {
        return;
    }
    if (l > r) {
        return;
    }

    if (cnt == 0) {
        return;
    }
    assert(cnt >= 0);
    if(cnt < 0){
        exit(0);
    }

    assert(r - l + 1 >= cnt);
    assert(l <= r);

    if (r - l + 1 == cnt) {
        for (int i = l; i <= r; i++) {
            if (gGrt(i) == 0) {
                ans = i;
                return;
            }
        }
        return;
    }

    int oMid = (l + r) / 2;
    int mid = oMid;
    vi ret = ask(mid);

    while (ret[0] + ret[1] != maxG) {
        if (ret[0] + ret[1] == 0) {
            ans = mid;
            return;
        }

        mid++;
        if (mid > r) {
            int found = mid - oMid;
            srch(l, oMid - 1, cnt - found, lS, rS + found);
            if(ans != -1){
                return;
            }
        }
        ret = ask(mid);
    }

    int found = mid - oMid;
    srch(l, oMid - 1, ret[0] - found - lS, lS, ret[1] + found);
    srch(mid + 1, r, ret[1] - rS, ret[0], rS);
}

int find_best(int N) {
    if (N < 4000) {
        for (int i = 0; i < N; i++) {
            if (gGrt(i) == 0) {
                return i;
            }
        }
    }

    int found = 0;
    maxG = 0;

    for(int rep = 0; rep < 100; rep++){
        int qInd = rand() % N;
        maxG = max(maxG, gGrt(qInd));
    }

    srch(0, N - 1, maxG - found, found, 0);
    return ans;
}