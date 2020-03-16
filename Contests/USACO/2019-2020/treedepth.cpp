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

const int MAXN = 305;
const int MAXK = MAXN * MAXN / 2 + 10;

int N, K;
ll MOD;

// rBndCnt[i] stores number of ways to have a full permutation s.t. a certain index is forced to be less than i elements to left of it
ll rBndCnt[MAXN];

ll lBndCnt[MAXN];
// rBndCnt[i] stores number of ways to have a full permutation s.t. a certain index is forced to be less than i elements to right of it

// preC[i][j] stores number of ways to build a permutation of length i with j inversions
ll preC[MAXN][MAXK];

// sufC[i][j] stores number of ways to finish a permutation of length i while adding j inversions
ll sufC[MAXN][MAXK];

ll preSum[MAXK];

ll ans[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    freopen("treedepth.in", "r", stdin);
    freopen("treedepth.out", "w", stdout);

    cin >> N >> K >> MOD;

    memset(preC, 0, sizeof(preC));
    memset(sufC, 0, sizeof(sufC));

    preC[0][0] = 1;
    preC[1][0] = 1;

    for (int l = 2; l <= N; l++) {
        preSum[0] = preC[l - 1][0];
        for (int i = 1; i <= K; i++) {
            preSum[i] = (preSum[i - 1] + preC[l - 1][i]) % MOD;
        }

        for (int inv = 0; inv <= K; inv++) {
            int minNeed = max(0, inv - (l - 1));
            int maxNeed = inv;
            preC[l][inv] = (preSum[maxNeed] - (minNeed > 0 ? preSum[minNeed - 1] : 0) + MOD) % MOD;
        }
    }

    sufC[N][0] = 1;
    for (int l = N - 1; l >= 0; l--) {
        preSum[0] = sufC[l + 1][0];
        for (int i = 1; i <= K; i++) {
            preSum[i] = (preSum[i - 1] + sufC[l + 1][i]) % MOD;
        }

        for (int inv = 0; inv <= K; inv++) {
            int minNeed = max(0, inv - l);
            int maxNeed = inv;
            sufC[l][inv] = (preSum[maxNeed] - (minNeed > 0 ? preSum[minNeed - 1] : 0) + MOD) % MOD;
        }
    }

    for (int numLess = 0; numLess < N; numLess++) {
        int invNeed = K - numLess;
        ll sum = 0;
//        ps("numLess:", numLess);
        for (int invF = 0; invF <= invNeed; invF++) {
//            ps(preC[numLess][invF], sufC[numLess + 1][invNeed - invF]);
            sum = (sum + preC[numLess][invF] * sufC[numLess + 1][invNeed - invF]) % MOD;
        }
        rBndCnt[numLess] = sum;
    }

    for (int numLess = 0; numLess < N; numLess++) {
        int invNeed = K;
        ll sum = 0;
        for (int invF = 0; invF <= invNeed; invF++) {
            sum = (sum + preC[numLess][invF] * sufC[numLess + 1][invNeed - invF]) % MOD;
        }
        lBndCnt[numLess] = sum;
    }

    for (int i = 1; i <= N; i++) {
        ll sum = preC[N][K];
        for (int j = 1; j < i; j++) {
            sum = (sum + lBndCnt[i - j]) % MOD;
        }

        for (int j = i + 1; j <= N; j++) {
            sum = (sum + rBndCnt[j - i]) % MOD;
        }
        ans[i] = sum;
    }

    for (int i = 1; i < N; i++) {
        cout << ans[i] << ' ';
    }
    cout << ans[N] << endl;
}