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

const int MAXN = 1e5 + 10;

int N;

vi aL[MAXN];

int dp[MAXN];

int K;

bool matchAll(vi &vals) {
    assert(vals.size() % 2 == 0);

    bool pos = true;
    for (int i = 0; i < vals.size() / 2; i++) {
        if (vals[i] + vals[vals.size() - 1 - i] < K) {
            pos = false;
            break;
        }
    }

    return pos;
}

int fMaxRet(vi vals) {
    assert(vals.size() % 2 == 1);

    vi subList = vals;
    subList.erase(subList.begin());

    if (!matchAll(subList)) {
        return -1; // no returning will work
    }

    int low = 0;
    int hi = vals.size() - 1;

    while (low != hi) {
        int mid = (low + hi + 1) / 2;
        subList = vals;
        subList.erase(subList.begin() + mid);

        if (matchAll(subList)) {
            low = mid;
        } else {
            hi = mid - 1;
        }
    }
    return vals[low];
}

bool impos;

void calc(int cV, int pV) {
    if (impos) {
        return;
    }
    vi cVal;

    for (int aV : aL[cV]) {
        if (aV != pV) {
            calc(aV, cV);
            cVal.pb(dp[aV]);
        }
    }

    sort(cVal.begin(), cVal.end());

    if (cVal.empty()) {
        dp[cV] = 1;
        return;
    }

    int maxR = -1;

    // we're trying to look for the maximum that we can return
    if (cVal.size() % 2 == 0) {

        // case: match all and return nothing
        if (matchAll(cVal)) {
            maxR = 0;
        }

        // case: match all but 2 and return 1
        // if we return any, another must also be unmatched
        if (cVal[cVal.size() - 1] >= K) {
            vi subList(cVal.begin(), --cVal.end());
            maxR = max(maxR, fMaxRet(subList));
        }

    } else {
        // return 1
        maxR = fMaxRet(cVal);

    }

    if (maxR == -1) {
        impos = true;
        return;
    }
    dp[cV] = maxR + 1; // +1 for link from cV to pV
}

bool isPossible() {
    fill(dp, dp + MAXN, -1);

    impos = false;

    vi cVal;
    for (int aV : aL[0]) {
        calc(aV, 0);

        if (impos) {
            return false;
        }
        cVal.pb(dp[aV]);
    }

    sort(cVal.begin(), cVal.end());

    bool canMatch = false;

    if (cVal.size() % 2 == 0) {
        canMatch = matchAll(cVal);
    } else {
        if (cVal[cVal.size() - 1] >= K) {
            vi subList(cVal.begin(), --cVal.end());
            canMatch = matchAll(subList);
        }
    }

    return canMatch;
}

int binSearch() {
    int low = 0;
    int hi = N - 1;

    while (low != hi) {
        int mid = (low + hi + 1) / 2;
        K = mid;
        if (isPossible()) {
            low = mid;
        } else {
            hi = mid - 1;
        }
    }
    return low;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    freopen("deleg.in", "r", stdin);
    freopen("deleg.out", "w", stdout);

    cin >> N;
    for (int i = 0; i < N - 1; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
        aL[v].pb(u);
    }

    int ans = binSearch();
    cout << ans << endl;
}