#include <bits/stdc++.h>
#include "grader.h"


using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back


int N = -1;
int Q;

int lS[100005];
int eV[2 * 100005];
int nE[2 * 100005];
int aCnt[100005];
int cE = 0;

int sz[100005];

int x[100005];
int y[100005];

int anc[100005][17];
int depth[100005];

void addRoad(int a, int b) {
    if (N == -1) {
        N = getN();
        Q = getQ();
        for (int i = 0; i < 100005; i++) {
            lS[i] = -1;
        }
    }

    aCnt[a]++;
    aCnt[b]++;

    nE[cE] = lS[a];
    lS[a] = cE;
    eV[cE++] = b;

    nE[cE] = lS[b];
    lS[b] = cE;
    eV[cE++] = a;
}

void complete(int cV, int pV, int cX, int cY) {
    x[cV] = cX;
    y[cV] = cY;
    int aE = lS[cV];
    while (aE != -1) {
        int aV = eV[aE];
        if (aV != pV) {
            complete(aV, cV, cX + x[aV], cY + y[aV]);
        }
        aE = nE[aE];
    }
}

void build(int cV, int pV) {
    anc[cV][0] = pV;
    if (cV == 0 ? aCnt[cV] == 0 : aCnt[cV] == 1) {
        sz[cV] = 1;
        return;
    }

    int aE = lS[cV];
    while (aE != -1) {
        int aV = eV[aE];
        if (aV == pV) {
            aE = nE[aE];
            continue;
        }
        depth[aV] = depth[cV] + 1;
        build(aV, cV);
        aE = nE[aE];
    }

    if (cV == 0 ? aCnt[cV] == 1 : aCnt[cV] == 2) {
        int aV = eV[lS[cV]];
        if (aV == pV) {
            aV = eV[nE[lS[cV]]];
        }

        sz[cV] = sz[aV] + 1;
        x[aV] = 0;
        y[aV] = 1;
        return;
    }

    aE = lS[cV];
    while (aE != -1) {
        int aV = eV[aE];
        if (aV == pV) {
            aE = nE[aE];
            continue;
        }
        sz[cV] += sz[aV];
        aE = nE[aE];
    }

    int cDx = 0;
    int cDy = sz[cV] - 1;

    aE = lS[cV];
    while (aE != -1) {
        int aV = eV[aE];
        if (aV == pV) {
            aE = nE[aE];
            continue;
        }
        x[aV] = cDx;
        y[aV] = cDy - (sz[aV] - 1);

        cDx += sz[aV];
        cDy -= sz[aV];
        aE = nE[aE];
    }
}

void buildFarms() {
    build(0, -1);

    complete(0, -1, 1, 1);

    for (int i = 0; i < N; i++) {
        setFarmLocation(i, x[i], y[i]);
    }

    anc[0][0] = 0;
    for (int k = 1; k <= 16; k++) {
        for (int i = 0; i < N; i++) {
            anc[i][k] = anc[anc[i][k - 1]][k - 1];
        }
    }
}

int jmp(int cV, int d) {
    for (int i = 0; i <= 16; i++) {
        if ((d & (1 << i)) != 0) {
            cV = anc[cV][i];
        }
    }
    return cV;
}

int fLCA(int a, int b) {
    if (depth[a] > depth[b]) {
        int temp = b;
        b = a;
        a = temp;
    }
    int dif = depth[b] - depth[a];
    b = jmp(b, dif);

    if (a == b) {
        return a;
    }

    for (int i = 16; i >= 0; i--) {
        if (anc[a][i] != anc[b][i]) {
            a = anc[a][i];
            b = anc[b][i];
        }
    }
    return anc[a][0];
}

void notifyFJ(int a, int b) {
    int lca = fLCA(a, b);

    if (depth[a] > depth[b]) {
        int tmp = a;
        a = b;
        b = tmp;
    }

    if (a == lca) {
        addBox(x[a], y[a], x[b], y[b]);
        return;
    }

    addBox(x[lca], y[lca], x[a], y[a]);
    int below = jmp(b, (depth[b] - depth[lca]) - 1);
    addBox(x[below], y[below], x[b], y[b]);
    return;
}