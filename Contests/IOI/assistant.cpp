#include <bits/stdc++.h>
#include "assistant.h"

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

//namespace debug {
//    const int DEBUG = true;
//
//    template<class T1, class T2>
//    void pr(const pair<T1, T2> &x);
//
//    template<class T, size_t SZ>
//    void pr(const array<T, SZ> &x);
//
//    template<class T>
//    void pr(const vector<T> &x);
//
//    template<class T>
//    void pr(const set<T> &x);
//
//    template<class T1, class T2>
//    void pr(const map<T1, T2> &x);
//
//    template<class T>
//    void pr(const T &x) { if (DEBUG) cout << x; }
//
//    template<class T, class... Ts>
//    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }
//
//    template<class T1, class T2>
//    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }
//
//    template<class T>
//    void prIn(const T &x) {
//        pr("{");
//        bool fst = 1;
//        for (auto &a : x) {
//            pr(fst ? "" : ", ", a), fst = 0;
//        }
//        pr("}");
//    }
//
//    template<class T, size_t SZ>
//    void pr(const array<T, SZ> &x) { prIn(x); }
//
//    template<class T>
//    void pr(const vector<T> &x) { prIn(x); }
//
//    template<class T>
//    void pr(const set<T> &x) { prIn(x); }
//
//    template<class T1, class T2>
//    void pr(const map<T1, T2> &x) { prIn(x); }
//
//    void ps() { pr("\n"), cout << flush; }
//
//    template<class Arg, class... Args>
//    void ps(const Arg &first, const Args &... rest) {
//        pr(first, " ");
//        ps(rest...);
//    }
//}
//using namespace debug;

const int MAXN = 1e5;

void Assist(unsigned char *data, int N, int K, int R) {
    queue<int> unused;
    vi contain(N, 0);

//    cout << "data   ";
//    for (int i = 0; i < 2 * N; i++) {
//        cout << (data[i] == 1) << ", ";
//    }
//    cout << endl;

    for (int i = 0; i < K; i++) {
        if (data[i] == 0) {
            unused.push(i);
//            ps("push", i);
        }
        contain[i] = true;
    }

    for (int i = 0; i < N; i++) {
        int cC = GetRequest();
        if (contain[cC]) {
            if (data[i + K] == 0) {
                unused.push(cC);
//                ps("push:", cC);
            }
        } else {
            assert(unused.size() > 0);

            int remC = unused.front();
            unused.pop();

            contain[remC] = false;
            PutBack(remC);

            contain[cC] = true;
            if (data[i + K] == 0) {
                unused.push(cC);
//                ps("push:", cC);
            }
        }
    }
}
