#include <bits/stdc++.h>
#include "advisor.h"

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


const int MAXN = 1e5;

queue<int> nUse[MAXN];

void ComputeAdvice(int *C, int N, int K, int M) {
    vi used(2 * N, 0);
    vi tInsert(N, 0);
    vi contain(N, 0);

    for (int i = 0; i < N; i++) {
        nUse[C[i]].push(i);
    }

    for (int cC = 0; cC < N; cC++) {
        nUse[cC].push(N);
    }

    set<pi> allNext;

    for (int cC = 0; cC < K; cC++) {
        allNext.insert({nUse[cC].front(), cC});
        contain[cC] = true;
        tInsert[cC] = cC;
    }

    int cntRem = 0;

    for (int i = 0; i < N; i++) {
        int cC = C[i];
        assert(nUse[cC].front() == i);
        nUse[cC].pop();

//        ps("at time", i, " cC: ", cC);

        if (contain[cC]) {
//            ps("contained");
            allNext.erase({i, cC});
            allNext.insert({nUse[cC].front(), cC});

            used[tInsert[cC]] = true;
            tInsert[cC] = i + K;
        } else {
//            ps("added");
            cntRem++;
            pi furtUse = *(--allNext.end());
            allNext.erase(--allNext.end());
            contain[furtUse.s] = false;

//            ps("remove:", furtUse.s);

            allNext.insert({nUse[cC].front(), cC});
            contain[cC] = true;
            tInsert[cC] = i + K;
        }
    }

//    cout << "cntRem: " << cntRem << endl;

    for (int i = 0; i < 2 * N; i++) {
        WriteAdvice(used[i]);
    }
}
