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
    const int DEBUG = false;

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

void add_element(string str);

void compile_set();

bool check_element(string str);

int N;

void add(int l, int r) {
//    ps(l, r);
    if (l == r) {
        return;
    }
    int sz = r - l + 1;
    int hf = sz / 2;

    for (int i = 0; i < hf; i++) {
        vector<char> str(N, '0');
        for (int j = 0; j < l; j++) {
            str[j] = '1';
        }
        for (int j = r + 1; j < N; j++) {
            str[j] = '1';
        }
        str[l + i] = '1';
//        ps("add:", string(str.begin(), str.end()));
        add_element(string(str.begin(), str.end()));
    }

    add(l, l + hf - 1);
    add(l + hf, r);
}

vi find(int l, int r, vi set) {
//    ps("!", l, r);
//    ps(set);
    if (l == r) {
        assert(set.size() == 1);
        return set;
    }

    int sz = r - l + 1;
    int hf = sz / 2;

    vi lSet, rSet;

    vector<char> str(N, '1');

    for (int x : set) {
        str[x] = '0';
    }

    for (int x : set) {
        str[x] = '1';
        if (check_element(string(str.begin(), str.end()))) {
            lSet.pb(x);
        } else {
            rSet.pb(x);
        }

        str[x] = '0';
    }

//    ps(lSet);
//    ps(rSet);

    assert(lSet.size() == hf);
    assert(lSet.size() + rSet.size() == sz);
    vi resL = find(l, l + hf - 1, lSet);
    vi resR = find(l + hf, r, rSet);
    resL.insert(resL.end(), resR.begin(), resR.end());
    return resL;
}

vi restore_permutation(int iN, int w, int r) {
//    ps("called");
    N = iN;
    add(0, N - 1);
//    ps("added");
    compile_set();
//    ps("compiled");
    vi tSet;
    for (int i = 0; i < N; i++) {
        tSet.pb(i);
    }
    vi res = find(0, N - 1, tSet);
//    ps(ans);
    vi ans(N);
    for (int i = 0; i < N; i++) {
        ans[res[i]] = i;
    }
    return ans;
}