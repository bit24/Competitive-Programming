#include <bits/stdc++.h>
#include "cave.h"

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

int state[10000];
int door[10000];

int oFClosed;

int find(int cDoor, vi &cand) {
    if (cand.size() == 1) {
        return cand[0];
    }

    for (int i = 0; i < cand.size() / 2; i++) {
        state[cand[i]] = 1;
    }

    int nFClosed = tryCombination(state);

    for (int i = 0; i < cand.size() / 2; i++) {
        state[cand[i]] = 0;
    }

    vi nCand;
    if ((oFClosed == cDoor) == (nFClosed == cDoor)) {
        for (int i = cand.size() / 2; i < cand.size(); i++) {
            nCand.pb(cand[i]);
        }
    } else {
        for (int i = 0; i < cand.size() / 2; i++) {
            nCand.pb(cand[i]);
        }
    }

    return find(cDoor, nCand);
}

int findInit(int cDoor, vi &cand) {
    oFClosed = tryCombination(state);
    return find(cDoor, cand);
}

int fPos(int x) {
    state[x] = 0;
    int fClosed = tryCombination(state);
    if (fClosed == -1 || door[x] < fClosed) {
        return 0;
    } else {
        return 1;
    }
}

void exploreCave(int N) {
    vi cand;
    for (int i = 0; i < N; i++) {
        cand.pb(i);
    }

    memset(state, 0, sizeof(state));
    memset(door, 0, sizeof(door));

    for (int doorI = 0; doorI < N; doorI++) {
        int switchI = findInit(doorI, cand);
        door[switchI] = doorI;

        state[switchI] = fPos(switchI);

        for (int j = 0; j < cand.size(); j++) {
            if (cand[j] == switchI) {
                cand.erase(cand.begin() + j);
                break;
            }
        }
    }

    answer(state, door);
}