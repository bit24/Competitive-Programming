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
#define str second

namespace debug {
    const int DEBUG = true;

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x);

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x);

    template<class T>
    void pr(const vector<T> &x);

    template<class T>
    void pr(const set<T> &x);

    template<class T1, class T2>
    void pr(const map<T1, T2> &x);

    template<class T>
    void pr(const T &x) { if (DEBUG) cout << x; }

    template<class T, class... Ts>
    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }

    template<class T>
    void prIn(const T &x) {
        pr("{");
        bool fst = 1;
        for (auto &a : x) {
            pr(fst ? "" : ", ", a), fst = 0;
        }
        pr("}");
    }

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x) { prIn(x); }

    template<class T>
    void pr(const vector<T> &x) { prIn(x); }

    template<class T>
    void pr(const set<T> &x) { prIn(x); }

    template<class T1, class T2>
    void pr(const map<T1, T2> &x) { prIn(x); }

    void ps() { pr("\n"), cout << flush; }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;


const int MAXN = 1e5 + 100;

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

string a, r;
int N;

ll preH[MAXN], revH[MAXN];

bool isPalin(int s, int e) {
    int rS = N - e + 1;
    int rE = N - s + 1;

    ll hash = cHash(preH, s, e);
    ll rHash = cHash(revH, rS, rE);

    return hash == rHash;
}

int cLong(int cent, int mode) { // cent is left of middle, mode = 0 if even, = 1 if odd
    int low = 0;
    int high = N;

    while (low != high) {
        int mid = (low + high + 1) / 2;
//        ps(low, high, mid);
        int s = cent - mid + 1;
        int e = cent + mid - mode;

        if (s < 1 || N < e) {
            high = mid - 1;
            continue;
        }

//        ps("call", s, e);
        if (isPalin(s, e)) {
//            ps("weird");
            low = mid;
        } else {
            high = mid - 1;
        }
    }
    return low;
}

int main() {
    cin >> a;
    N = a.size();
    r = a;
    reverse(r.begin(), r.end());
    a = " " + a;
    r = " " + r;

    cPow();
    cPreH(a, preH);
    cPreH(r, revH);

    ll ans = 0;
    for (int i = 1; i <= N; i++) {
        ans += cLong(i, 0);
//        ps(ans);

//        if (a == " MOO") {
//            return 0;
//        }
        ans += cLong(i, 1);
//        ps(ans);
    }
    cout << ans << endl;
}