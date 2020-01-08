#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef pair<ll, ll> pil;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

int N, K;

const int MAXN = 2e5 + 100;

vector<pl> aL[MAXN];

int ctPar[MAXN], vCnt[MAXN], rem[MAXN];

int gSize;

void fCnt(int cV, int pV) {
    vCnt[cV] = 1;
    for (pl aE : aL[cV]) {
        int aV = aE.f;
        if (aV != pV && !rem[aV]) {
            fCnt(aV, cV);
            vCnt[cV] += vCnt[aV];
        }
    }
}

int fCent(int cV, int pV) {
    for (pl aE : aL[cV]) {
        int aV = aE.f;
        if (aV != pV && !rem[aV] && vCnt[aV] > gSize / 2) {
            return fCent(aV, cV);
        }
    }
    return cV;
}

multiset<pl> pPaths;

ll ans = 1e9;

void cPaths(int cV, int pV, ll cD, int eCnt, int mode) {
    if (mode == 1) {
        pPaths.insert({cD, eCnt});
    } else if (mode == -1) {
        pPaths.erase({cD, eCnt});
    } else {
        auto match = pPaths.lower_bound({K - cD, 0});
        pl mPair = *match;
        if (match != pPaths.end() && mPair.f == K - cD) {
            ans = min(ans, eCnt + mPair.s);
        }
    }

    for (pl aE : aL[cV]) {
        int aV = aE.f;
        if (aV != pV && !rem[aV]) {
            cPaths(aV, cV, cD + aE.s, eCnt + 1, mode);
        }
    }
}

void decomp(int root, int cCtPar) {
    fCnt(root, -1);
    gSize = vCnt[root];
    int cent = fCent(root, -1);

    ctPar[cent] = cCtPar;
    rem[cent] = true;

    pPaths.clear();

    cPaths(cent, -1, 0, 0, 1);

    for (pl aE : aL[cent]) {
        int aV = aE.f;

        if (!rem[aV]) {
            cPaths(aV, cent, aE.s, 1, -1);

            cPaths(aV, cent, aE.s, 1, 0);

            cPaths(aV, cent, aE.s, 1, 1);
        }
    }

    for (pl aE : aL[cent]) {
        int aV = aE.f;
        if (!rem[aV]) {
            decomp(aV, cent);
        }
    }
}


int best_path(int iN, int iK, int H[][2], int L[]) {
    N = iN;
    K = iK;

    memset(rem, 0, sizeof(rem));

    for (int i = 0; i < N - 1; i++) {
        aL[H[i][0]].pb({H[i][1], L[i]});
        aL[H[i][1]].pb({H[i][0], L[i]});
    }

    decomp(0, -1);

    if (ans == 1e9) {
        return -1;
    } else {
        return ans;
    }
}