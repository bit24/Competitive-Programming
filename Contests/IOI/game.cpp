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

int cnt[1500][1500];

int id[1500];

void initialize(int iN) {
    N = iN;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cnt[i][j] = i != j;
        }
    }

    for (int i = 0; i < N; i++) {
        id[i] = i;
    }
}

int hasEdge(int u, int v) {

    int c1 = id[u], c2 = id[v];
    cnt[c1][c2]--;
    cnt[c2][c1]--;
    if (cnt[c1][c2] > 0) {
        return false;
    }

    for (int i = 0; i < N; i++) {
        if (i != c1 && i != c2) {
            cnt[c1][i] += cnt[c2][i];
        }
    }
    for (int i = 0; i < N; i++) {
        cnt[i][c1] = cnt[c1][i];
    }

    for (int i = 0; i < N; i++) {
        cnt[c2][i] = 0;
        cnt[i][c2] = 0;
    }

    for (int i = 0; i < N; i++) {
        if (id[i] == c2) {
            id[i] = c1;
        }
    }

    return true;
}