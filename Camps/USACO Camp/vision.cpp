#include <bits/stdc++.h>
#include "vision.h"

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


int NR, NC;

int lInd(int r, int c) {
    return r * NC + c;
}

int nLoc;
int rOR, cOR;
int preR, sufR, preC, sufC;

int zeroBit;
int oneBit;
int ansBit;

vi sumBits;

void inc(int arg) { // 32 instructions, 64 values read
    assert(sumBits.size() == 9);
    vi newBits;

    int carry = arg;

    for (int i = 0; i < 9; i++) {
        add_xor({sumBits[i], carry});
        newBits.pb(nLoc++);

        add_and({sumBits[i], carry});
        carry = nLoc++;
    }
    sumBits = newBits;
}

void construct_network(int iH, int iW, int K) {
//    ps("started");
    NR = iH, NC = iW;
    // compute prefix tables

    nLoc = NR * NC;

    zeroBit = add_xor({lInd(0, 0), lInd(0, 0)});
    nLoc++;
    oneBit = add_not(zeroBit);
    nLoc++;

    for (int i = 0; i < 9; i++) {
        sumBits.pb(zeroBit);
    }

    rOR = nLoc;
    for (int i = 0; i < NR; i++) {
        vi row;
        for (int j = 0; j < NC; j++) {
            row.pb(lInd(i, j));
        }
        add_or(row);
        nLoc++;
    }

    cOR = nLoc;
    for (int j = 0; j < NC; j++) {
        vi column;
        for (int i = 0; i < NR; i++) {
            column.pb(lInd(i, j));
        }
        add_or(column);
        nLoc++;
    }

    // prefix ORs

    preR = nLoc;
    add_or({rOR});
    nLoc++;
    for (int i = 1; i < NR; i++) {
        add_or({nLoc - 1, rOR + i});
        nLoc++;
    }

    preC = nLoc;
    add_or({cOR});
    nLoc++;
    for (int j = 1; j < NC; j++) {
        add_or({nLoc - 1, cOR + j});
        nLoc++;
    }

    sufR = nLoc;
    add_or({rOR + NR - 1});
    nLoc++;
    for (int i = NR - 2; i >= 0; i--) {
        add_or({nLoc - 1, rOR + i});
        nLoc++;
    }

    sufC = nLoc;
    add_or({cOR + NC - 1});
    nLoc++;
    for (int j = NC - 2; j >= 0; j--) {
        add_or({nLoc - 1, cOR + j});
        nLoc++;
    }

    for (int i = 0; i + 1 < NR; i++) {
        int i1 = preR + i;
        int i2 = sufR + NR - (i + 1) - 1;
        add_and({i1, i2});
        nLoc++;
        inc(nLoc - 1);
    }

    for (int j = 0; j + 1 < NC; j++) {
        int j1 = preC + j;
        int j2 = sufC + NC - (j + 1) - 1;
        add_and({j1, j2});
        nLoc++;
        inc(nLoc - 1);
    }

    ansBit = oneBit;

    for (int i = 0; i < 9; i++) {
        if (K & (1 << i)) {
            ansBit = add_and({ansBit, sumBits[i]});
            nLoc++;
        } else {
            add_not(sumBits[i]);
            nLoc++;

            ansBit = add_and({ansBit, nLoc - 1});
            nLoc++;
        }
    }

    add_or({ansBit});
}