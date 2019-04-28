#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef pair<int, int> pi;
typedef pair<int, vi> ivi;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}

using namespace debug;

void answer(vi C, vi X, vi Y);

vi lk[2]; // if link is negative, is exit
vi p;
vi s;

vi lfV;
vi rmvV;
vi rmvE;

int nxtV = 1;

int N;
int P;

int cns(int cnt, int cP, int cS, int stt, int stp) { // cnt >= 2
    int cV = nxtV++;
    p[cV] = cP;
    s[cV] = cS;

    if (cnt == 2) {
        lk[0][cV] = stt;
        lk[1][cV] = stt + stp;
        lfV.pb(cV);
    } else {
        lk[0][cV] = cns(cnt / 2, cV, 0, stt, stp * 2);
        lk[1][cV] = cns(cnt / 2, cV, 1, stt + stp, stp * 2);
    }
    return cV;
}

void check(int cV) {
    if (cV == -1) {
        return;
    }
    if (rmvV[cV]) {
        return;
    }
    if (lk[0][cV] == lk[1][cV]) {
        int pV = p[cV];
        if (pV == -1) {
            throw "-p";
        }
        lk[s[cV]][pV] = lk[0][cV];

        rmvV[cV] = true;

        check(pV);
    }
}

void create_circuit(int M, vi ord) {
    ord.pb(0); // after visiting all, return to 0

    N = ord.size();
    P = 1 << (32 - __builtin_clz(N - 1)); // smallest pow of 2 greater or equal

    lk[0].resize(2 * N); // construction will not use more than 2*N
    lk[1].resize(2 * N);
    p.resize(2 * N);
    s.resize(2 * N);
    rmvV.resize(2 * N);
    rmvE.resize(P);

    ps("N, P:", N, P);
    cout << flush;
    cns(P, -1, -1, -1, -1); // constructs tree with positive vertices and negative exits

    //ps(lk[0]);
    //ps(lk[1]);
    //ps(p);
    //ps(s);

    int tRmv = P - N;

    for (int i = 0; tRmv; i++) {
        ps(-lk[0][lfV[i]]);
        rmvE[-lk[0][lfV[i]]] = true;
        lk[0][lfV[i]] = 1; // link back exit to root of tree

        if (tRmv >= 2) {
            rmvE[-lk[1][lfV[i]]] = true;
            lk[1][lfV[i]] = 1;
            tRmv -= 2;
        } else {
            tRmv--;
        }
        check(lfV[i]);
    }

    ps(rmvV);
    ps(rmvE);

    ps(lk[0]);
    ps(lk[1]);

    int nxtI = 0;
    vi rE(P + 1);
    for (int i = 1; i <= P; i++) { //note exits actually have negative indices
        if (!rmvE[i]) {
            rE[i] = ord[nxtI++];
        }
    }

    int nRV = -1;
    vi outV(nxtV);
    for (int i = 1; i < nxtV; i++) {
        if (!rmvV[i]) {
            outV[i] = nRV--;
        }
    }

    vi rX(-nRV - 1);
    vi rY(-nRV - 1);

    for (int i = 1; i < nxtV; i++) {
        if (!rmvV[i]) {
            int outCV = -outV[i] - 1;
            if (lk[0][i] > 0) { // links to another vertex
                rX[outCV] = outV[lk[0][i]];
            } else { // is actually an exit
                rX[outCV] = rE[-lk[0][i]];
            }
            if (lk[1][i] > 0) { // links to another vertex
                rY[outCV] = outV[lk[1][i]];
            } else { // is actually an exit
                rY[outCV] = rE[-lk[1][i]];
            }
        }
    }

    vi dir(M + 1);
    for (int i = 0; i <= M; i++) {
        dir[i] = -1;
    }

    answer(dir, rX, rY);
}