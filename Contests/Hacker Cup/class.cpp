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

const ll MOD = 1e9 + 7;

ll pow2[2000000];

int main() {
    freopen("class.in", "r", stdin);
    freopen("class.out", "w", stdout);
    int T;
    cin >> T;

    pow2[0] = 1;
    for (int i = 1; i < 2000000; i++) {
        pow2[i] = pow2[i - 1] * 2 % MOD;
    }

    for (int cT = 1; cT <= T; cT++) {
        int N, K;
        cin >> N >> K;
        string line;
        cin >> line;

        int BminA = 0;

        ll cost = 0;
        for (int i = N - 1; i >= 0; i--) {
            BminA = max(BminA, 0);
            if (line[i] == 'B') {
                BminA++;
                if (BminA > K) {
                    // change current character to A
                    cost = (cost + pow2[i + 1]) % MOD;
                    BminA -= 2;
                }
            } else {
                BminA--;
            }
        }
        cout << "Case #" << cT << ": ";
        cout << cost << endl;
    }
}