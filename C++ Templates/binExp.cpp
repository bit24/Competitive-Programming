namespace binExp {
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
}
