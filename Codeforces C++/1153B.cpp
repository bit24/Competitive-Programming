#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

int a[101][101];

int main() {
    ios_base::sync_with_stdio(false);
    int N, M, H;
    cin >> N >> M >> H;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            a[i][j] = H;
        }
    }

    for (int i = 0; i < M; i++) {
        int bnd;
        cin >> bnd;
        for (int j = 0; j < N; j++) {
            a[j][i] = min(a[j][i], bnd);
        }
    }

    for (int i = 0; i < N; i++) {
        int bnd;
        cin >> bnd;
        for (int j = 0; j < M; j++) {
            a[i][j] = min(a[i][j], bnd);
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            int p;
            cin >> p;
            if (!p) {
                a[i][j] = 0;
            }
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cout << a[i][j] << ' ';
        }
        cout << '\n';
    }
    cout << flush;
}