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

const ll MOD = 1000000007LL;

ll sumO(ll i, ll N) {
    i %= MOD;
    N %= MOD;
    //cout << 'O' << i << " " << N << endl;
    return (2 * i + N - 2 + MOD) % MOD * N % MOD;
}

ll sumE(ll i, ll N) {
    i %= MOD;
    N %= MOD;
    // cout << 'E' << i << " " << N << endl;
    return (2 * i + N - 1 + MOD) % MOD * N % MOD;
}

ll cnt(ll N) {
    // cout << "start";
    if (N == 0) {
        return 0;
    }
    ll cL = 1;
    ll cT = 0;
    ll fin = 0;
    ll nE = 1;
    ll nO = 1;

    ll ans = 0;
    while (fin + cL < N) {
        //cout << "loop";
        //cout << fin << endl;
        if (fin + cL <= 0 || cL < 0 || ans < 0) {
           // cout << "weird";
        }
        // cout << cL << endl;
        if (cT == 0) { // odd
            if (sumO(nO, cL) < 0) {
                //cout << "weird";
            }
            ans = (ans + sumO(nO, cL)) % MOD;
            nO += cL;
        } else {
            if (sumE(nE, cL) < 0) {
               // cout << "weird";
            }
            ans = (ans + sumE(nE, cL)) % MOD;
            nE += cL;
        }
        fin += cL;

        cL *= 2;
        cT ^= 1;
    }

    if (cT == 0) {
        ans = (ans + sumO(nO, N - fin)) % MOD;
    } else {
        ans = (ans + sumE(nE, N - fin)) % MOD;
    }
    // cout << "ans" << ans;
    //cout << "done";
    return ans;
}

int main() {
    ll a, b;
    cin >> a >> b;
    cout << (cnt(b) - cnt(a - 1) + MOD) % MOD << endl;
}