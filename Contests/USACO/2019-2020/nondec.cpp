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

const int MAXN = 5e4 + 5;
const int LOGN = 18;
const int MAXK = 21;
const ll MOD = 1e9 + 7;

int val[MAXN];

int cnt[LOGN][MAXK][MAXN];
int cntSum[LOGN][MAXK][MAXN];
ll cntAct[MAXK];

int N, K;

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

void process(int level, int l, int r) {
    assert(level < LOGN);

    if (l == r) {
        cnt[level][val[l]][l] = 1;
        return;
    }

    int mid = (l + r) / 2;

    // left side
    for (int lVal = 1; lVal <= 20; lVal++) {
        memset(cntAct, 0, sizeof(cntAct)); // cntAct stores number of sequences that begin greater than or equal to x

        for (int i = mid; i >= l; i--) {
            int cV = val[i];

            int numNewSeq = cntAct[cV];

            if (lVal == cV) {
                numNewSeq = (numNewSeq + 1) % MOD; // sequence with one 1 item
            }

            cnt[level][lVal][i] = numNewSeq;

            cntSum[level][lVal][i] = ((ll) numNewSeq + (i < mid ? cntSum[level][lVal][i + 1] : 0)) % MOD;

            for (int x = 1; x <= cV; x++) {
                cntAct[x] = (cntAct[x] + numNewSeq) % MOD;
            }
        }
    }

    // right side
    for (int fVal = 1; fVal <= 20; fVal++) {
        memset(cntAct, 0, sizeof(cntAct)); // cntAct stores number of sequences that end less than or equal to x

        for (int i = mid + 1; i <= r; i++) {
            int cV = val[i];

            int numNewSeq = cntAct[cV];

            if (fVal == cV) {
                numNewSeq = (numNewSeq + 1) % MOD; // sequence with one 1 item
            }

            cnt[level][fVal][i] = numNewSeq;

            cntSum[level][fVal][i] = ((ll) numNewSeq + (mid + 1 < i ? cntSum[level][fVal][i - 1] : 0)) % MOD;

            for (int x = cV; x <= 20; x++) {
                cntAct[x] = (cntAct[x] + numNewSeq) % MOD;
            }
        }
    }

    process(level + 1, l, mid);
    process(level + 1, mid + 1, r);
}

ll ans = 0;

void calculate(int level, int l, int r, int s, int e) {
    if (l == r) {
//        ps(s, e);
        assert(s == e);
        ans = 2;
        return;
    }

    int mid = (l + r) / 2;
    if (e <= mid) {
        calculate(level + 1, l, mid, s, e);
        return;
    }
    if (mid < s) {
        calculate(level + 1, mid + 1, r, s, e);
        return;
    }
    assert(s <= mid && mid + 1 <= e);
//    ps("calculating", level, l, r, s, e);

    // slow can optimize later
    for (int lMax = 1; lMax <= 20; lMax++) {
        for (int rMin = lMax; rMin <= 20; rMin++) {
            ans = (ans + (ll) cntSum[level][lMax][s] * cntSum[level][rMin][e]) % MOD;
        }
    }

    for (int lMax = 1; lMax <= 20; lMax++) {
        ans = (ans + cntSum[level][lMax][s]) % MOD; // left only
    }

    for (int rMin = 1; rMin <= 20; rMin++) {
        ans = (ans + cntSum[level][rMin][e]) % MOD; // right only
    }
    ans++; //empty sequence

    return;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    freopen("nondec.in", "r", stdin);
    freopen("nondec.out", "w", stdout);

    memset(cnt, 0, sizeof(cnt));
    memset(cnt, 0, sizeof(cntSum));

    cin >> N >> K;

    for (int i = 0; i < N; i++) {
        cin >> val[i];
    }

    process(0, 0, N - 1);

    int Q;
    cin >> Q;

    for (int i = 0; i < Q; i++) {
        int s, e;
        cin >> s >> e;
        s--, e--;
//        ps(s, e);
        ans = 0;
        calculate(0, 0, N - 1, s, e);
        ans %= MOD;
        cout << ans << '\n';
    }
    cout << flush;
}
