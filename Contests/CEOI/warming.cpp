#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

const int INF = 2e9 + 1e3;

int N;

vi a;
vi minV;
vi lis;

int binSearch(int cV) {
    int lo = 0;
    int hi = N;

    while (lo != hi) {
        int mid = (lo + hi + 1) / 2;
        if (minV[mid] < cV) {
            lo = mid;
        } else {
            hi = mid - 1;
        }
    }
    return lo;
}

void fLIS(bool sec = false) {
    minV.resize(N + 1);
    lis.resize(N);
    for (int i = 0; i < N + 1; i++) {
        minV[i] = INF;
    }
    minV[0] = 0;

    for (int i = 0; i < N; i++) {
        int bPrevLen = binSearch(a[i]);

        minV[bPrevLen + 1] = min(minV[bPrevLen + 1], a[i]);
        lis[i] = bPrevLen + 1;
    }
}

int main() {
    int x;
    cin >> N >> x;
    a.resize(N);
    for (int i = 0; i < N; i++) {
        int in;
        cin >> in;
        a[i] = in;
    }

    vi oA = a;

    fLIS();
    vi lLIS = lis;

    for (int i = 0; i < N; i++) {
        a[i] = 1e9 + 1 - a[i];
    }
    reverse(a.begin(), a.end());

//     ps(a);

    fLIS(true);
    reverse(lis.begin(), lis.end());
    vi rLIS = lis;

    a = oA;

//    ps(a);
//    ps(lLIS);
//    ps(rLIS);

    int ans = 0;
    for (int i = 0; i < N; i++) {
        ans = max(ans, lLIS[i]);
        ans = max(ans, rLIS[i]);
    }

    map<int, int> cMap; // val, len
    cMap.insert({0, 0});
    cMap.insert({INF, INF});
    for (int i = 0; i < N; i++) {
        pi cMatch = *(--cMap.lower_bound(a[i] + x));
        ans = max(ans, cMatch.s + rLIS[i]);

        if ((*(--cMap.upper_bound(a[i]))).s >= lLIS[i]) {
            continue;
        }

        while ((*cMap.lower_bound(a[i])).s <= lLIS[i]) {
            cMap.erase((*cMap.lower_bound(a[i])).f);
        }
        cMap.insert({a[i], lLIS[i]});
    }

    cout << ans << endl;
}