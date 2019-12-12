#include <bits/stdc++.h>
#include "dreaming.h"

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


vector<pi> aL[101000];

vi visTime;
vi dist0, distD1, distD2;
bool fComp;
vi cComp;

int cTime = 0;

int intDist = 0;

void fDist(int cV, int cD, vi &dists) {
    if (visTime[cV] == cTime) {
        return;
    }
    visTime[cV] = cTime;
    dists[cV] = cD;
    if (fComp) {
        cComp.pb(cV);
    }

    for (pi aE : aL[cV]) {
        fDist(aE.f, cD + aE.s, dists);
    }
}

int fFurthest(int cM) {
    cComp.clear();
    fComp = true;
    cTime++;
    fDist(cM, 0, dist0);

    int d1 = cM;
    for (int i : cComp) {
        if (dist0[i] > dist0[d1]) {
            d1 = i;
        }
    }

    fComp = false;
    cTime++;
    fDist(d1, 0, distD1);

    int d2 = cM;
    for (int i : cComp) {
        if (distD1[i] > distD1[d2]) {
            d2 = i;
        }
    }

    cTime++;
    fDist(d2, 0, distD2);

    intDist = max(intDist, distD1[d2]);

    int minD = 1e9;

    for (int i : cComp) {
        int cFur = max(distD1[i], distD2[i]);
        minD = min(minD, cFur);
    }
    return minD;
}

int travelTime(int N, int M, int L, int A[], int B[], int T[]) {
    for (int i = 0; i < M; i++) {
        aL[A[i]].pb({B[i], T[i]});
        aL[B[i]].pb({A[i], T[i]});
    }
    visTime.resize(N);
    fill(visTime.begin(), visTime.end(), -1);

    visTime.resize(N);
    fill(visTime.begin(), visTime.end(), -1);

    dist0.resize(N);
    distD1.resize(N);
    distD2.resize(N);

    vi furthests;
    for (int i = 0; i < N; i++) {
        if (visTime[i] == -1) {
            furthests.pb(fFurthest(i));
        }
    }

    sort(furthests.begin(), furthests.end());
    reverse(furthests.begin(), furthests.end());

    int ans = intDist;
    if (furthests.size() >= 2) {
        ans = max(ans, L + furthests[0] + furthests[1]);
    }

    if (furthests.size() >= 3) {
        ans = max(ans, 2 * L + furthests[1] + furthests[2]);
    }

    return ans;
}