#include <bits/stdc++.h>
#include "gardenlib.h"

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

const int MAXN = 3e5 + 100;

vector<pi> aL[MAXN];

pi maxI[MAXN];

int nxt[MAXN];

int vis[MAXN];
pi res[MAXN];

int cTime = 1;
int cTar;

vi cPath;

map<int, map<int, vi>> modGroups;

void search(int cV) {
    vis[cV] = cTime;
    cPath.pb(cV);

    int nV = nxt[cV];
    if (vis[nV] == cTime) {
        // cycle found

        int iTar = -1;

        int i;
        for (i = cPath.size() - 1; i >= 0; i--) {
            if (cPath[i] == cTar) {
                iTar = i;
            }
            if (cPath[i] == nV) {
                break;
            }
        }
//
//        ps(cPath);
//        ps(cV, iTar, i);

        int cycleLen = cPath.size() - 1 - i + 1;

        if (iTar != -1) {
            for (i = cPath.size() - 1; i >= 0; i--) {
                int cycleV = cPath[i];
                res[cycleV] = {cycleLen, (iTar - i + cycleLen) % cycleLen};
                if (cPath[i] == nV) {
                    break;
                }
            }
        }

        return;
    }

    if (vis[nV] == 0) {
        search(nxt[cV]);
    }

    if (res[cV].f != -1) { // assigned a value due to cycling behavior
        return;
    }

    pi nRes = res[nV];

    if (nRes.f == -1) {
        if (cV == cTar) {
            res[cV] = {1e9, 0};
        }
        return;
    }
    res[cV] = {nRes.f, nRes.s + 1};
}

void count_routes(int N, int M, int P, int R[][2], int Q, int G[]) {

    for (int i = 0; i < M; i++) {
        aL[R[i][0]].pb({R[i][1], M - i});
        aL[R[i][1]].pb({R[i][0], M - i});
    }

    memset(maxI, 0, sizeof(maxI));

    for (int cV = 0; cV < N; cV++) {
        for (pi aE : aL[cV]) {
            maxI[cV] = max(maxI[cV], {aE.s, aE.f});
        }
    }

    for (int cV = 0; cV < N; cV++) {
        assert (aL[cV].size() != 0);

        if (aL[cV].size() == 1) {
            int aV = aL[cV][0].f;
//            if (cV == 4) {
//                ps(aV);
//            }
            if (maxI[cV].f == maxI[aV].f) {
                nxt[cV] = aV + N;
                nxt[cV + N] = aV + N;
            } else {
                nxt[cV] = aV;
                nxt[cV + N] = aV;
            }
            continue;
        }


        pi secMaxI = {-1, -1};

        for (pi aE : aL[cV]) {
            if (aE.s != maxI[cV].f) {
                secMaxI = max(secMaxI, {aE.s, aE.f});
            }
        }

        int aV = maxI[cV].s;
        if (maxI[aV].f == maxI[cV].f) {
            nxt[cV] = aV + N;
        } else {
            nxt[cV] = aV;
        }

        aV = secMaxI.s;
        if (maxI[aV].f == secMaxI.f) {
            nxt[cV + N] = aV + N;
        } else {
            nxt[cV + N] = aV;
        }
    }



//    ps("nxts");
//    for (int i = 0; i < 2 * N; i++) {
//        ps(nxt[i]);
//    }
//    ps("endsnxts");


    for (int phase = 0; phase < 2; phase++) {
        for (int i = 0; i < 2 * N; i++) {
            vis[i] = 0;
            res[i] = {-1, -1};
        }
        cTime = 1;
        cTar = P + N * phase;

        for (int i = 0; i < 2 * N; i++) {
            if (!vis[i]) {
                cPath.clear();
                search(i);
                cTime++;
            }
        }

//        ps("phase print");
//
//        for (int i = 0; i < 2 * N; i++) {
//            ps(res[i]);
//        }
//
//        ps("complete phase");

        for (int i = 0; i < N; i++) {
            if (res[i].f != -1) {
                int cycleLen = res[i].f;
                map<int, vi> &cGroup = modGroups[cycleLen];
                cGroup[res[i].s % cycleLen].pb(res[i].s);
            }
        }
    }

//
//    ps("Q:", Q);
//    ps();

    for (auto &x: modGroups) {
        for (auto &y : x.s) {
            auto &z = y.s;
            sort(z.begin(), z.end());
//            ps(x.f, y.f);
//            ps(z);
        }
    }

    for (int cQ = 0; cQ < Q; cQ++) {
        int K = G[cQ];
        int sum = 0;
        for (auto &x : modGroups) {
            map<int, vi> &cGroup = x.s;
            int rem = K % x.f;

            vi &vals = cGroup[rem];

            sum += upper_bound(vals.begin(), vals.end(), K) - vals.begin();
        }
        answer(sum);
//        ps(sum);
    }

//    for (auto &x : modGroups) {
//        vi &cGroup = x.s;
//        if (x.f != 1e6) {
//            ps(x.f);
//            ps(cGroup);
//        }
//    }

}