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


vi dp[2000];

void removeDup(vi &inp) {
    int nxtI = 0;
    for (int i = 0; i < inp.size(); i++) {
        if (nxtI == 0 || inp[i] != inp[nxtI - 1]) {
            inp[nxtI++] = inp[i];
        }
    }
    inp.resize(nxtI);
}

int main() {
    int N;
    cin >> N;
    if(N == 1){
        cout << 1 << endl;
        cout << 0 << endl;
        return 0;
    }

    vi factors;
    for (int d = 1; d * d <= N; d++) {
        if (N % d == 0) {
            factors.pb(d);
            if (d * d != N) {
                factors.pb(N / d);
            }
        }
    }
    sort(factors.begin(), factors.end());

    for (int i = 1; i < factors.size(); i++) {
        dp[i].pb(factors[i] - 1);
        sort(dp[i].begin(), dp[i].end());
        removeDup(dp[i]);

        for (int j = i + 1; j < factors.size(); j++) {
            if (factors[j] % factors[i] == 0) {
                for (int x : dp[i]) {
                    dp[j].pb(x + factors[j] / factors[i] - 1);
                }
            }
        }
    }

    vi &ans = dp[factors.size() - 1];

    cout << ans.size() << endl;
    for (int i = 0; i < ans.size(); i++) {
        cout << ans[i] << " ";
    }
    cout << endl;
}