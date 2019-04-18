#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const int MAXN = 505;

int a[MAXN][MAXN];

int main() {
    int N, M;
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> a[i][j];
        }
    }

    int b = 0;
    int aI = -1;
    int aJ = -1;
    for (int i = 0; i < N; i++) {
        b ^= a[i][0];
        for (int j = 0; j < M; j++) {
            if (j != 0 && a[i][j] != a[i][j - 1]) {
                aI = i;
                aJ = j;
                break;
            }
        }
    }

    if (b != 0) {
        cout << "TAK" << '\n';
        for (int i = 0; i < N; i++) {
            cout << 1 << " ";
        }
        cout << endl;
        return 0;
    } else {
        if (aJ == -1) {
            cout << "NIE" << endl;
            return 0;
        }

        cout << "TAK" << '\n';
        for (int i = 0; i < N; i++) {
            if (i == aI) {
                cout << (aJ + 1) << " ";
            } else {
                cout << 1 << " ";
            }
        }
        cout << endl;
        return 0;
    }
}