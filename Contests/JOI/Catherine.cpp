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


void Init(int A, int B) {
    assert(A == 2);
}

vi hist;

int lastT = -1;

int directed = false;

vector<vi> matches = {{1, 1, 0, 1},
                      {1, 0, 1, 0},
                      {0, 1, 0, 0},
                      {1, 0, 0, 1, 1},
                      {0, 0, 1, 1},
                      {0, 1, 1, 0, 1}};


int Move0(vi y) {
    ps("cycle", y);
    vi cnts(y);
    assert(cnts.size() == 2);

    if (cnts[0] + cnts[1] == 0) {
        directed = true;
        return -1;
    } else if (cnts[0] + cnts[1] + (lastT != -1) > 2) {
        directed = true;

        if (lastT != -1) {
            cnts[lastT]++;
        }
//        ps(cnts);
        assert(cnts[0] == 1 || cnts[1] == 1);

        if (cnts[0] == 1) {
            return lastT == 0 ? -1 : 0;
        } else {
            assert(cnts[1] == 1);
            return lastT == 1 ? -1 : 1;
        }
    } else if (directed) {
        assert(cnts[0] == 1 || cnts[1] == 1);

        if (cnts[0] == 1) {
            return 0;
        } else {
            return 1;
        }
    } else {
        if (lastT == -1) {
//            ps(cnts);
            if (cnts[0] + cnts[1] == 1) {
                directed = true;
                if (cnts[0] == 1) {
                    return 0;
                } else {
                    return 1;
                }
            } else if (cnts[0] == 1 && cnts[1] == 1) {
                // pick a random direction
                hist.pb(1), hist.pb(0);
                return 0;
            } else if (cnts[0] == 2) {
                hist.pb(0), hist.pb(0);
                return 0;
            } else {
                assert(cnts[1] == 2);
                hist.pb(1), hist.pb(1);
                return 1;
            }
        } else {
            if (cnts[0] == 1) {
                hist.pb(0);
            } else {
                hist.pb(1);
            }

            if (hist.size() >= 4) {
                if (find(matches.begin(), matches.end(), hist) != matches.end()) {
                    // wrong direction reverse
                    directed = true;
                    return -1;
                } else {
                    if (hist.size() == 5) {
                        // right direction keep on going
                        directed = true;
                        if (cnts[0] == 1) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                }
            }
            if (cnts[0] == 1) {
                return 0;
            } else {
                return 1;
            }
        }
    }
    assert(false);
}

int Move(vi y) {
    int res = Move0(y);
    if (res != -1) {
        lastT = res;
    }
//    ps("chose:", res);
    return res;
}