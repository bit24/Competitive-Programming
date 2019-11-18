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
#define ts(x) to_string(x)

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

int used[6];
int a[6];
int ord[720][6];
string ordS[720];

vector<pi> cond;

int nOrd = 0;

void rec(int i) {
    if (i == 6) {
        for (int j = 0; j < 6; j++) {
            ordS[nOrd] += ts(a[j]);
            ord[nOrd][a[j]] = j;
        }
        nOrd++;
        return;
    }
    for (int j = 0; j < 6; j++) {
        if (!used[j]) {
            used[j] = true;
            a[i] = j;
            rec(i + 1);
            used[j] = false;
        }
    }
}

int mode = 1;

vi filter(vi &inp, pi cond) {
    if (mode == 2) {
        swap(cond.f, cond.s);
    }
    vi res;
    for (int i : inp) {
        if (ord[i][cond.f] < ord[i][cond.s]) {
            res.pb(i);
        }
    }
    return res;
}

// first 1
vi operation1(vi &inp, int arg[3], int r) {
    int o1 = arg[(r + 1) % 3], o2 = arg[(r + 2) % 3];
    vi partial = filter(inp, {o1, arg[r]});
    vi res = filter(partial, {o2, arg[r]});
    return res;
}

vi operation3(vi &inp, int arg[3], int r) {
    int res = arg[r];
    int o1 = arg[(r + 1) % 3], o2 = arg[(r + 2) % 3];
    vi ret;
    for (int i : inp) {
        if ((ord[i][res] < ord[i][o1]) + (ord[i][res] < ord[i][o2]) == 1) {
            ret.pb(i);
        }
    }
    return ret;
}

vi operation4(vi &inp, int arg[4], int r) {
    int res = arg[r];
    int o1 = arg[(r + 1) % 3], o2 = arg[(r + 2) % 3];
    int stan = arg[3];
    vi ret;
    for (int i : inp) {
        if (ord[i][res] < ord[i][stan]) {
            if (ord[i][res] < ord[i][o1] && ord[i][res] < ord[i][o2] && ord[i][o1] < ord[i][stan] &&
                ord[i][o2] < ord[i][stan]) {
                ret.pb(i);
            }
        } else {
            if ((ord[i][o1] < ord[i][stan] || ord[i][res] < ord[i][o1]) &&
                (ord[i][o2] < ord[i][stan] || ord[i][res] < ord[i][o2])) {
                ret.pb(i);
            }
        }
    }
    return ret;
}

int nState = 0;
string stateDec[10000000];
int stateTrans[100000000][3];

bool search(vi &inp, int rOp, int cState) {
    if (inp.size() <= 1) {
        if (inp.size() == 1) {
            stateDec[cState] = ordS[inp[0]];
        } else {
            stateDec[cState] = "impos";
        }
        return true;
    }
    if (rOp == 0) {
        return false;
    }
    if (pow(3, rOp) < inp.size()) {
        return false;
    }

    for (int i = 0; i < 6; i++) {
        for (int j = i + 1; j < 6; j++) {
            for (int k = j + 1; k < 6; k++) {
                if (rOp == 5) {
                    i = 3;
                    j = 4;
                    k = 5;
                }

                int arg[] = {i, j, k};

                // operation 1,2,3
                for (int cMode = 1; cMode <= 3; cMode++) {
                    bool pos = true;
                    for (int r = 0; r < 3; r++) {
                        vi ret;
                        if (cMode <= 2) {
                            mode = cMode;
                            ret = operation1(inp, arg, r);
                        } else {
                            ret = operation3(inp, arg, r);
                        }
                        stateTrans[cState][r] = nState;

                        if (!search(ret, rOp - 1, nState++)) {
                            pos = false;
                            break;
                        }
                    }
                    if (pos) {
//                        stateDec[cState] = ts(cMode) + " " + ts(i) + " " + ts(j) + " " + ts(k);
                        stateDec[cState] = ts(cMode) + ts(i) + ts(j) + ts(k);
                        return true;
                    }
                }
            }
        }
    }

    // option 4 is a last resort
    for (int i = 0; i < 6; i++) {
        for (int j = i + 1; j < 6; j++) {
            for (int k = j + 1; k < 6; k++) {
                for (int l = 0; l < 6; l++) {
                    if (l == i || l == j || l == k) {
                        continue;
                    }
                    int arg4[] = {i, j, k, l};

                    bool pos = true;
                    for (int r = 0; r < 3; r++) {
                        vi ret = operation4(inp, arg4, r);
                        stateTrans[cState][r] = nState;

                        if (!search(ret, rOp - 1, nState++)) {
                            pos = false;
                            break;
                        }
                    }
                    if (pos) {
//                        stateDec[cState] = "4 " + ts(i) + " " + ts(j) + " " + ts(k) + " " + ts(l);
                        stateDec[cState] = "4" + ts(i) + ts(j) + ts(k) + ts(l);
                        return true;
                    }
                }
            }
        }
    }

    return false;
}

void printAll(int i, int cState) {
    if (stateDec[cState] == "") {
        ps(i);
    }
    assert(stateDec[cState] != "");
    pr("\"", stateDec[cState], "\", ");
    if (i < 6) {
        printAll(i + 1, stateTrans[cState][0]);
        printAll(i + 1, stateTrans[cState][1]);
        printAll(i + 1, stateTrans[cState][2]);
    }
}

int main() {
    freopen("scalesB.out", "w", stdout);
    rec(0);
    assert(nOrd == 720);
    vi all;
    for (int i = 0; i < 720; i++) {
        all.pb(i);
    }
    ps(search(all, 6, nState++) ? "true" : "false");

    ps("realtext");
    printAll(0, 0);
}

