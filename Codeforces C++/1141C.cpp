#include <bits/stdc++.h>

typedef long long ll;

using namespace std;

const int MAXN = 200005;

int d[MAXN];
int v[MAXN * 2];

int main() {
    int N;
    cin >> N;

    for (int i = 0; i < N - 1; i++) {
        cin >> d[i];
    }

    memset(v, 0, sizeof(d));

    int cur = 0;
    v[MAXN + cur] = 1;

    int cMax = 0;
    int cMin = 0;

    for (int i = 0; i < N - 1; i++) {
        cur += d[i];
        cMax = max(cMax, cur);
        cMin = min(cMin, cur);
        if (cMax - cMin + 1 > N) {
            cout << -1;
            return 0;
        }
        if (v[MAXN + cur]) {
            cout << -1;
            return 0;
        }
        v[MAXN + cur] = 1;
    }

    cur = -cMin + 1;
    cout << cur << " ";
    for (int i = 0; i < N - 1; i++) {
        cur += d[i];
        cout << cur << " ";
    }
}