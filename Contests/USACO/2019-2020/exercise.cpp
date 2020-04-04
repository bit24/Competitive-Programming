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

typedef unsigned long long ull;
typedef __uint128_t L;

struct FastMod {
    ull b, m;

    FastMod(ull b) : b(b), m(ull((L(1) << 64) / b)) {}

    ull reduce(ull a) {
        ull q = (ull) ((L(m) * a) >> 64);
        ull r = a - q * b; // can be proven that 0 <= r < 2*b
        return r >= b ? r - b : r;
    }
};

FastMod F(2);

const int MAXN = 7.5e3 + 10;
ll MOD;
ll MOD2;

int N;

ll prodR[MAXN][MAXN];

int prime[MAXN];

ll bPow(ll b, ll e = MOD - 2) {
    b %= MOD;
    ll c = 1;
    while (e) {
        if (e & 1) {
            c = c * b % MOD;
        }
        b = b * b % MOD;
        e >>= 1;
    }
    return c;
}


ll byRem[MAXN];

// should compute answers MOD2
ll cntCond(int k) {
    memset(byRem, 0, sizeof(byRem));

    ll sum = 1;
    byRem[0] = 1;

    ll cAns;

    for (int i = 1; i <= N; i++) {
        int cRem = i % k;

        // moves from last with same remainder to one before current
        ll update = i - k >= 0 ? prodR[i - k + 1][i - 1] : 0;
        cAns = F.reduce((sum + (MOD2 - byRem[cRem]) * update + MOD2));

        // update sum, move it along 1 or from current
        sum = F.reduce(sum * i + cAns);

        // update byRem, moves from last to current, or from current
        update = i - k >= 0 ? prodR[i - k + 1][i] : 0;
        byRem[cRem] = F.reduce(byRem[cRem] * update + cAns);
    }

    return cAns;
}


int main() {
    freopen("exercise.in", "r", stdin);
    freopen("exercise.out", "w", stdout);

    cin >> N >> MOD;
    MOD2 = MOD - 1;
    F = FastMod(MOD2);

    for (int i = 1; i <= N; i++) {
        ll cProd = 1;
        for (int j = i; j <= N; j++) {
            cProd = F.reduce(cProd * j);
            prodR[i][j] = cProd;
        }
    }

    fill(prime, prime + MAXN, 1);
    for (int p = 2; p < MAXN; p++) {
        if (prime[p]) {
            for (int m = p * 2; m < MAXN; m += p) {
                prime[m] = false;
            }
        }
    }

    ll ans = 1;
    for (int p = 2; p <= N; p++) {
        if (prime[p]) {
            ll sumExp = 0;

            ll prev = 0;
            for (int pow = 1, exp = 0; pow <= N; pow *= p, exp++) {
                ll cCnt = cntCond(pow * p) % MOD2;

                sumExp = (sumExp + (cCnt + MOD2 - prev) * exp) % MOD2;
                prev = cCnt;
            }

            ans = ans * bPow(p, sumExp) % MOD;
        }
    }
    cout << ans << endl;
}