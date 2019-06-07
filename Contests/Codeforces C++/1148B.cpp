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
    int N, M, K;
    ll tN, tM;
    cin >> N >> M >> tN >> tM >> K;

    vi nA, mA;

    for (int i = 0; i < N; i++) {
        ll x;
        cin >> x;
        nA.pb(x);
    }

    for (int i = 0; i < M; i++) {
        ll x;
        cin >> x;
        mA.pb(x);
    }

    if (N <= K || M <= K) {
        cout << -1 << endl;
        return 0;
    }

    ll ans = 0;
    int j = 0;
    ll dTime = 0;
    for (int i = 0; i <= K; i++) {
        dTime = nA[i] + tN;
        //cout << dTime << endl;
        while (j < M && mA[j] < dTime) {
            j++;
        }
        //cout << j << endl;

        int qI = j + (K - i);
        if (qI < M) {
            ans = max(ans, mA[qI] + tM);
            // cout << i << " " << ans << endl;
        } else {
            ans = 1e20;
        }
    }

    if (ans >= 1e10) {
        cout << -1 << endl;
        return 0;
    }

    cout << ans << endl;
}