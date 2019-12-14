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

const int MAXN = 1e6 + 10;
const int LOGN = 20;

char lets[MAXN];
int L[MAXN], jmp[MAXN][LOGN];

void Init() {
    lets[0] = 0;
    L[0] = 0;
    for (int i = 0; i < LOGN; i++) {
        jmp[0][i] = 0;
    }
}

int cI = 1;

void TypeLetter(char cLet) {
    lets[cI] = cLet;
    L[cI] = L[cI - 1] + 1;

    if (lets[cI - 1] == 0) {
        jmp[cI][0] = jmp[cI - 1][0];
    } else {
        jmp[cI][0] = cI - 1;
    }

    for (int log = 1; log < LOGN; log++) {
        jmp[cI][log] = jmp[jmp[cI][log - 1]][log - 1];
    }

    cI++;
}

void UndoCommands(int U) {
    lets[cI] = 0;
    L[cI] = L[cI - U - 1];

    if (lets[cI - U - 1] == 0) {
        jmp[cI][0] = jmp[cI - U - 1][0];
    } else {
        jmp[cI][0] = cI - U - 1;
    }

    for (int log = 1; log < LOGN; log++) {
        jmp[cI][log] = jmp[jmp[cI][log - 1]][log - 1];
    }

    cI++;
}

char GetLetter(int P) {
    int nJ = L[cI - 1] - P - 1;
    int i = cI - 1;

    if (lets[i] == 0) {
        i = jmp[i][0];
    }

    for (int log = LOGN - 1, jmps = 1 << log; log >= 0; log--, jmps /= 2) {
        if (jmps <= nJ) {
            i = jmp[i][log];
            nJ -= jmps;
        }
    }
    return lets[i];
}