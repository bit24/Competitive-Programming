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

const int MAXN = 700;

bool grid[MAXN][MAXN];

// delay, diag, index
int mDiag[2][2 * MAXN][MAXN];
int sDiag[2][2 * MAXN][MAXN];

int N;

bool valid(int r, int c) {
    return 0 <= r && r < N && 0 <= c && c < N;
}

int gGrid(int r, int c) {
    if (valid(r, c)) {
        return grid[r][c];
    }
    return 0;
}

int main_fColumn(int diag, int r) {
    int c = diag - r;
    return c;
}

int second_fColumn(int diag, int r) {
    int c = (N - 1) - (diag - r);
    return c;
}

int main_fDiag(int r, int c) {
    int diag = r + c;
    return diag;
}

int second_fDiag(int r, int c) {
    int diag = N - 1 + r - c;
    return diag;
}

void prep(int cDelay, int cDiag[MAXN][2 * MAXN][MAXN], int cType) {

    int skip = cDelay == 0 ? 0 : 1;
    // main diagonal, count from upper left corner
    for (int diag = 0; diag < 2 * N; diag++) {
        for (int r = 0; r <= diag; r++) {
            int c;

            if (cType == 0) {
                c = main_fColumn(diag, r);
            } else {
                c = second_fColumn(diag, r);
            }

            if (r == 0) {
                if (cDelay == 0) {
                    cDiag[skip][diag][0] = gGrid(r, c); // when delay is 0, only cGrid needs to be on
                } else {
                    cDiag[skip][diag][0] = 0; // impossible to be on with nonzero delay
                }
                continue;
            }

            if (!gGrid(r, c)) {
                cDiag[skip][diag][r] = cDiag[skip][diag][r - 1];
                continue;
            }

            int dR = r - cDelay;
            int dC;
            if (cType == 0) {
                dC = c + cDelay;
            } else {
                dC = c - cDelay;
            }

            if (gGrid(dR, dC)) {
                cDiag[skip][diag][r] = cDiag[skip][diag][r - 1] + 1;
            } else {
                cDiag[skip][diag][r] = cDiag[skip][diag][r - 1];
            }
        }
    }
}

int ans = 0;

void calculate(int r, int c, int K) {
    assert(K % 2 == 0);
    int rDiag = main_fDiag(r, c + K);
    int lDiag = second_fDiag(r, c - K);

    int halfK = K / 2;

    //case 1: right side only
    int cnt = mDiag[1][rDiag][r + K] - mDiag[1][rDiag][r + halfK - 1];
    ans += cnt;

//    if (r == 0 && c == 1 && K == 2) {
//        ps(ans);
//    }

    // case 2: left side only
    cnt = sDiag[1][lDiag][r + K] - sDiag[1][lDiag][r + halfK];
    ans += cnt;
//
//    if (r == 0 && c == 1 && K == 2) {
//        ps(ans);
//    }


    //case 2: both sides
    if (gGrid(r + halfK, c + halfK)) {
        cnt = sDiag[0][lDiag][r + K - 1] - sDiag[0][lDiag][r + halfK - 1];
        ans += cnt;
    }
    if (gGrid(r + halfK, c - halfK)) {
        cnt = mDiag[0][rDiag][r + K - 1] - mDiag[0][rDiag][r + halfK];
        ans += cnt;
    }
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    freopen("triangles.in", "r", stdin);
    freopen("triangles.out", "w", stdout);
    cin >> N;

    for (int i = 0; i < MAXN; i++) {
        for (int j = 0; j < MAXN; j++) {
            grid[i][j] = false;
        }
    }

    for (int i = 0; i < N; i++) {
        string line;
        cin >> line;
        for (int j = 0; j < N; j++) {
            if (line[j] == '*') {
                grid[i][j] = true;
            }
        }
    }

    for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
            int diag = main_fDiag(r, c);
            assert(main_fColumn(diag, r) == c);

            diag = second_fDiag(r, c);
            assert(second_fColumn(diag, r) == c);
        }
    }

    prep(0, mDiag, 0);
    prep(0, sDiag, 1);


    for (int K = 2; K <= 2 * N; K += 2) {
//        memset(sDiag[1], 0, sizeof(sDiag[1]));
//        memset(mDiag[1], 0, sizeof(mDiag[1]));

        prep(K / 2, mDiag, 0);
        prep(K / 2, sDiag, 1);

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (gGrid(r, c)) {
                    calculate(r, c, K);
                }
            }
        }
    }
//    int ans2 = main0();
//    ps(ans);
//    ps(ans2);
//    assert(ans == ans2);
    cout << ans << endl;
}