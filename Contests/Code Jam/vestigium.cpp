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

const int MAXN = 200;

int a[MAXN][MAXN];

int used[MAXN];

int N;

bool checkRow(int i) {
    memset(used, 0, sizeof(used));

    for (int j = 0; j < N; j++) {
        if (used[a[i][j]]) return true;
        used[a[i][j]] = true;
    }
    return false;
}

bool checkCol(int j) {
    memset(used, 0, sizeof(used));

    for (int i = 0; i < N; i++) {
        if (used[a[i][j]]) return true;
        used[a[i][j]] = true;
    }
    return false;
}

int main() {
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        cin >> N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cin >> a[i][j];
            }
        }
        int tr = 0, rCnt = 0, cCnt = 0;

        for (int i = 0; i < N; i++) {
            tr += a[i][i];
            rCnt += checkRow(i);
            cCnt += checkCol(i);
        }

        cout << "Case #" << cT << ": " << tr << " " << rCnt << " " << cCnt << endl;
    }
}