namespace Hash {
    const ll MOD = 1e9 + 7;
    const ll BASE = 30689;
    ll POW[MAXN];

    void cPow() {
        POW[0] = 1;
        for (int i = 1; i < MAXN; i++) {
            POW[i] = POW[i - 1] * BASE % MOD;
        }
    }

    // assume string is 1-indexed
    void cPreH(string &str, ll preH[]) {
        preH[0] = 0;
        for (int i = 1; i < str.length(); i++) {
            preH[i] = (preH[i - 1] * BASE + str[i]) % MOD;
        }
    }

    ll cHash(ll preH[], int s, int e) {
        return (preH[e] - preH[s - 1] * POW[e - s + 1] % MOD + MOD) % MOD;
    }
}
using namespace Hash;
