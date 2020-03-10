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


const int MAXM = 3e3 + 10;

int dp[MAXM][MAXM];

int N, M, K;
ll A, B, C;
ll T;

int stop[MAXM];

ll nGet[MAXM];

void computeGet(ll bC, int limit) {
    memset(nGet, 0, sizeof(nGet));

    for (int i = 0; i <= K; i++) {
        if (bC > T) {
            break;
        }
        ll more = 1 + (T - bC) / A;
        nGet[i] = (i > 0 ? nGet[i - 1] : 0) + more;
        bC += C * more;
    }

    for (int i = 0; i <= K; i++) {
        nGet[i] = min(nGet[i], (ll) limit);
    }
}

int main() {
    cin >> N >> M >> K;
    cin >> A >> B >> C;
    cin >> T;

    K -= M;

    memset(dp, 0, sizeof(dp));

    for (int i = 0; i < M; i++) {
        cin >> stop[i];
        stop[i]--;
    }

    for (int i = 1; i < M; i++) {
        computeGet(stop[i - 1] * B, stop[i] - stop[i - 1]);

        for (int j = 0; j <= K; j++) {

            for (int lU = 0; lU <= j; lU++) {
                dp[i][j] = max(dp[i][j], dp[i - 1][j - lU] + (int) nGet[lU]);
            }

//            ps(i, j);
//            ps(nGet[j]);
//            ps(dp[i][j]);
        }
    }

    int ans = dp[M - 1][K] - 1; // number of stations we can reach other than 1 before the last station
    if ((N - 1) * B <= T) {
//        cout << "lol" << endl;
        ans++;
    }

    cout << ans << endl;
}