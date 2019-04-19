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

namespace debug {
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
    void pr(const T &x) { cout << x; }

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

const int MOD = 1000000007;

namespace binexp {
    ll bPow(ll b, ll p = MOD - 2) {
        b %= MOD;
        ll c = 1;
        while (p) {
            // ps("p:", p);
            if (p & 1) {
                c = c * b % MOD;
            }
            b = b * b % MOD;
            // ps(b);
            p >>= 1;
        }
        return c;
    }
}

using namespace binexp;

int N, K;
int a[105];
ll mat[105][105];

void mult(ll a[105][105], ll b[105][105], ll res[105][105]) {
    memset(res, 0, 8 * 105 * 105);

    /*
    ps("abres");
    for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= N; j++) {
            pr(a[i][j], " ");
        }
        ps();
    }
    ps();

    for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= N; j++) {
            pr(b[i][j], " ");
        }
        ps();
    }
    ps();

    for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= N; j++) {
            pr(res[i][j], " ");
        }
        ps();
    }
    ps();*/

    for (int i = 0; i < 105; i++) {
        for (int j = 0; j < 105; j++) {
            for (int k = 0; k < 105; k++) {
                res[i][j] = (res[i][j] + a[i][k] * b[k][j]) % MOD;
            }
        }
    }

    /*
    for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= N; j++) {
            pr(res[i][j], " ");
        }
        ps();
    }
    ps();*/
}

ll exp(int p, int x, int y) {
    ll c[105][105];
    memset(c, 0, sizeof(c));

    for (int i = 0; i < 105; i++) {
        c[i][i] = 1;
    }

    ll b[105][105];
    memcpy(b, mat, sizeof(mat));

    /*
    for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= N; j++) {
            pr(b[i][j], " ");
        }
        ps();
    }
    ps();*/

    ll tmp[105][105];
    while (p > 0) {
        if (p & 1) {
            mult(c, b, tmp);

            memcpy(c, tmp, sizeof(tmp));
        }

        mult(b, b, tmp);
        memcpy(b, tmp, sizeof(tmp));
        p >>= 1;

        /*
        ps("b");
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                pr(b[i][j], " ");
            }
            ps();
        }
        ps();

        ps("c");
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                pr(c[i][j], " ");
            }
            ps();
        }
        ps();*/
    }
    return c[x][y];
}

int main() {
    cin >> N >> K;

    int cnt = 0;
    for (int i = 0; i < N; i++) {
        cin >> a[i];
        if (a[i] == 0) {
            cnt++;
        }
    }

    int oo = 0;
    for (int i = 0; i < cnt; i++) {
        if (a[i] == 1) {
            oo++;
        }
    }

    memset(mat, 0, sizeof(mat));
    for (int i = 0; i <= min(cnt, N - cnt); i++) {
        if (i + 1 <= N) {
            mat[i][i + 1] = 1LL * (cnt - i) * (N - cnt - i) % MOD;
            //ps("a", 1LL * (cnt - i) * (N - cnt - i));
        }
        if (i - 1 >= 0) {
            mat[i][i - 1] = 1LL * i * i % MOD;
            //ps("b", 1LL * i * i % MOD);
        }
        mat[i][i] = (1LL * cnt * (cnt - 1) / 2) % MOD; // swap 2 o's
        mat[i][i] = (mat[i][i] + 1LL * (N - cnt) * (N - cnt - 1) / 2) % MOD; // swap 2 1's
        mat[i][i] = (mat[i][i] + 1LL * i * (N - cnt - i)) % MOD; // swap 0 (oo) & 1 (io)
        mat[i][i] = (mat[i][i] + 1LL * (cnt - i) * (i)) % MOD;// swap 0 (io) & 1 (oo)

    }
    ll num = exp(K, oo, 0);
    ll ch2 = (N * (N - 1LL) / 2) % MOD;
    ll den = bPow(ch2, K);
    ll iDen = bPow(den);
    // ps(num, den, iDen);

    cout << num * iDen % MOD << endl;
}