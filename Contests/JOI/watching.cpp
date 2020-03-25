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


const int MAXN = 2e3 + 10;

// dp[i][j] stores maximum number of small cameras left given j large left and all up to i inclusive is taken
int dp[MAXN][MAXN];

int x[MAXN];

int N, P, Q;

bool possible(int w) {
    for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= Q; j++) {
            dp[i][j] = -1;
        }
    }

    dp[0][0] = P;

    int lPI = 0;
    int sPI = 0;

    for (int i = 1; i <= N; i++) {
        for (int nL = 0; nL <= Q; nL++) {
            // case 1: use large
            if (nL > 0) {
                while (lPI + 1 <= N && x[lPI + 1] < x[i] - 2 * w + 1) {
                    lPI++;
                }

                dp[i][nL] = max(dp[i][nL], dp[lPI][nL - 1]);
            }

            //  case 2: use small

            while (sPI + 1 <= N && x[sPI + 1] < x[i] - w + 1) {
                sPI++;
            }

            dp[i][nL] = max(dp[i][nL], dp[sPI][nL] - 1);
        }
    }

    for (int nL = 0; nL <= Q; nL++) {
        if (dp[N][nL] >= 0) {
            return true;
        }
    }
    return false;
}


int binSearch() {
    int low = 1;
    int hi = 1e9;
    while (low != hi) {
        assert(low < hi);

        int mid = (low + hi) / 2;
        if (possible(mid)) {
            assert(hi != mid);

            hi = mid;
        } else {
            low = mid + 1;
        }
    }
    return low;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N >> P >> Q;
    P = min(P, N);
    Q = min(Q, N);

    x[0] = -2e9;

    for (int i = 1; i <= N; i++) {
        cin >> x[i];
    }

    sort(x, x + N + 1);

    int ans = binSearch();
    cout << ans << endl;
}