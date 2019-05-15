#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const int MAXN = 5000;
int a[MAXN][MAXN];
int b[MAXN][MAXN];

bool cmp(int i, int j) {
    return a[0][i] < a[0][j];
}

bool cmp2(int i, int j) {
    return b[i][0] < b[j][0];
}

int main() {
    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> a[i][j];
        }
    }

    vi ord;
    for (int i = 0; i < N; i++) {
        ord.pb(i);
    }

    sort(ord.begin(), ord.end(), cmp);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            b[i][j] = a[i][ord[j]];
        }
    }

    sort(ord.begin(), ord.end(), cmp2);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            a[i][j] = b[ord[i]][j];
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j + 1 < N; j++) {
            if (a[i][j] >= a[i][j + 1]) {
                cout << "N" << endl;
                return 0;
            }
        }
    }

    for (int i = 0; i + 1 < N; i++) {
        for (int j = 0; j < N; j++) {
            if (a[i][j] >= a[i + 1][j]) {
                cout << "N" << endl;
                return 0;
            }
        }
    }
    cout << "Y" << endl;
}