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

const int MAXN = 1e5 + 10;
const ll MOD = 1e9 + 7;

ll fact[MAXN];
ll iFact[MAXN];

ll comb(int n, int k) {
    return fact[n] * iFact[k] % MOD * iFact[n - k] % MOD;
}

ll binExp(ll base, int exp) {
    base %= MOD;

    ll prod = 1;

    while (exp > 0) {
        if (exp & 1) {
            prod = prod * base % MOD;
        }
        base = base * base % MOD;
        exp >>= 1;
    }

    return prod;
}

int main() {
    fact[0] = 1;
    for (int i = 1; i < MAXN; i++) {
        fact[i] = fact[i - 1] * i % MOD;
    }
    iFact[MAXN - 1] = 549915853;

    assert(fact[MAXN - 1] * iFact[MAXN - 1] % MOD == 1);

    for (int i = MAXN - 2; i >= 0; i--) {
        iFact[i] = (i + 1) * iFact[i + 1] % MOD;
        assert(fact[i] * iFact[i] % MOD == 1);
    }

//    cout << comb(23134, 242) << endl;

//    cout << binExp(2342, 124515) << endl;

    int N, K;
    cin >> N >> K;
    K--;

    ll sum = 0;
    for (int i = 0; i <= K; i++) {
        ll val = comb(N + 1, i) * binExp(K + 1 - i, N) % MOD;
//        cout << val << endl;
        if (i % 2 == 0) {
            sum = (sum + val) % MOD;
        } else {
            sum = (sum - val + MOD) % MOD;
        }
    }

    cout << sum << endl;
}