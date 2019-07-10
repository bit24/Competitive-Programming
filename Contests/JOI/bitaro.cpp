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

int main() {
    int H, W;
    cin >> H >> W;

    int grid[H][W];
    for (int i = 0; i < H; i++) {
        string line;
        cin >> line;
        for (int j = 0; j < W; j++) {
            grid[i][j] = line[j];
        }
    }

    vi vCnts(W, 0);

    ll ans = 0;
    for (int i = H - 1; i >= 0; i--) {
        int hCnt = 0;
        for (int j = W - 1; j >= 0; j--) {
            if (grid[i][j] == 'J') {
                ans += (ll) hCnt * vCnts[j];
            }

            if (grid[i][j] == 'O') {
                hCnt++;
            }
            if (grid[i][j] == 'I') {
                vCnts[j]++;
            }
        }
    }
    cout << ans << endl;
}
