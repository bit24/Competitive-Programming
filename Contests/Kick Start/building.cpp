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

const int MAXN = 200000;

int T;
int cnt[MAXN][26];

int main() {
    cin >> T;
    memset(cnt, 0, sizeof(cnt));

    for (int cT = 1; cT <= T; cT++) {
        int N, Q;
        string str;
        cin >> N >> Q >> str;

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < 26; j++) {
                cnt[i][j] = cnt[i - 1][j];
            }
            cnt[i][str[i - 1] - 'A']++;
        }

        int ans = 0;
        for (int i = 0; i < Q; i++) {
            int l, r;
            cin >> l >> r;
            int odd = 0;
            for (int j = 0; j < 26; j++) {
                if ((cnt[r][j] - cnt[l - 1][j]) & 1) {
                    odd++;
                }
            }

            if (odd < 2) {
                ans++;
            }
        }
        cout << "Case #" << cT << ": " << ans << '\n';
    }
    cout << flush;
}