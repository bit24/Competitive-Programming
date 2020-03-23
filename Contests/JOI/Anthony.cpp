#include <bits/stdc++.h>
#include "Anthony.h"

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

const int MAXN = 2e4 + 10;

int seq[] = {1, 1, 0, 1, 0, 0};

vector<pi> aL[MAXN];

vi marks;

void compute(int cV, int pV, int last, int cont) {
//    cout << cV << endl;
    if (aL[cV].size() == 2 && pV != -1) {
        int aV, eI;
        if (aL[cV][0].f != pV) {
            aV = aL[cV][0].f;
            eI = aL[cV][0].s;
        } else {
            aV = aL[cV][1].f;
            eI = aL[cV][1].s;
        }

        if (cont == -1) {
            cont = last == 0 ? 5 : 0;
        }

        cont = (cont + 1) % 6;
        marks[eI] = seq[cont];

        compute(aV, cV, marks[eI], cont);
    } else {
        for (pi aE : aL[cV]) {
            int aV = aE.f;
            int eI = aE.s;

            if (aV != pV) {
                marks[eI] = last ^ 1;
                compute(aV, cV, last ^ 1, -1);
            }
        }
    }
}

vi Mark(int N, int M, int A, int B, vi U, vi V) {
//    assert(A == 2);
//    if (A == 2) {
//        assert(M == N - 1);
//    }
    marks.resize(M);

    for (int i = 0; i < M; i++) {
        aL[U[i]].pb({V[i], i});
        aL[V[i]].pb({U[i], i});
    }

    compute(0, -1, 0, -1);

//    for (int i = 0; i < M; i++) {
//        cout << marks[i] << endl;
//    }

    return marks;
}
