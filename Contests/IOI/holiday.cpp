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

const int MAXN = 200000;

vl initA;

struct SegTr {
    ll sumA[4 * MAXN], act[4 * MAXN];

    void b(int i, int l, int r) {
        act[i] = 0;
        sumA[i] = 0;
        if (l == r) {
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid);
        b(i * 2 + 1, mid + 1, r);
    }

    void toggle(int i, int l, int r, int x) {
        if (l == r) {
            act[i] ^= 1;
            sumA[i] = act[i] ? initA[l] : 0;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            toggle(i * 2, l, mid, x);
        } else {
            toggle(i * 2 + 1, mid + 1, r, x);
        }
        act[i] = act[i * 2] + act[i * 2 + 1];
        sumA[i] = sumA[i * 2] + sumA[i * 2 + 1];
    }

    ll sumK(int i, int l, int r, int k) {
        if (l == r) {
            if (act[i] && k > 0) {
                return sumA[i];
            } else {
                return 0;
            }
        }

        int mid = (l + r) / 2;
        if (act[i * 2] >= k) {
            return sumK(i * 2, l, mid, k);
        } else {
            return sumA[i * 2] + sumK(i * 2 + 1, mid + 1, r, k - act[i * 2]);
        }
    }

    void dumpTree(int i, int l, int r) {
        ps("dump", i, l, r, ":", act[i], sumA[i]);
        if (l != r) {
            int mid = (l + r) / 2;
            dumpTree(i * 2, l, mid);
            dumpTree(i * 2 + 1, mid + 1, r);
        }
    }

} segTr;

int N;
int st;
int tCost;

// used for computeSide
int M;
int movC;

vi segI;

int lastA = -1;

vl ans;

ll toggleCnt = 0;

vector<pair<pi, pi>> cLevel, nLevel;

void computeRange(pair<pi, pi> range) {
    int lCost = range.f.f, hCost = range.f.s, lEnd = range.s.f, rEnd = range.s.s;

    int midCost = (lCost + hCost) / 2;

    while (lastA >= lEnd) {
        segTr.toggle(1, 1, M, segI[lastA]);
        toggleCnt++;
//        ps("toggle", segI[lastA]);
        lastA--;
    }
    while (lastA < lEnd - 1) {
        lastA++;
        segTr.toggle(1, 1, M, segI[lastA]);
        toggleCnt++;
//        ps("toggle", segI[lastA]);
    }
    // above loops should be amortized O(N log^2 N)

    ll bVal = -1, bEnd = -1;
    for (int cEnd = lEnd; cEnd <= rEnd; cEnd++) {
        lastA = cEnd;
        segTr.toggle(1, 1, M, segI[lastA]);
        toggleCnt++;
//        ps("toggle", segI[lastA]);

        if (midCost - cEnd * movC < 0) break;

//        segTr.dumpTree(1, 1, M);

        ll cVal = segTr.sumK(1, 1, M, midCost - cEnd * movC);
//        ps("cVal", cVal);
//        ps("k:", midCost - cEnd * movC);
//        ps(cVal);
        if (bVal < cVal) {
            bVal = cVal;
            bEnd = cEnd;
        }
    }
//    ps(midCost, lEnd, rEnd, movC);
    assert(bVal >= 0);

//    ps(midCost);

    ans[midCost] = bVal;
//    ps("midCost, bVal:", midCost, bVal);
//    ps(bEnd);

    if (lCost < midCost) {
        nLevel.pb({{lCost, midCost - 1},
                   {lEnd,  bEnd}});
    }
    if (midCost < hCost) {
        nLevel.pb({{midCost + 1, hCost},
                   {bEnd,        rEnd}});
    }
}

void computeRanges() {
    nLevel.pb({{0, tCost},
               {0, M - 1}});

    while (!nLevel.empty()) {
        cLevel.clear();
        cLevel.insert(cLevel.begin(), nLevel.begin(), nLevel.end());
        nLevel.clear();

        for (auto x: cLevel) {
            computeRange(x);
        }
    }
}


vl computeSide(vi &val) {
    M = val.size();

    vector<pi> vali;

    for (int i = 0; i < M; i++) {
        vali.pb({val[i], i});
    }
    sort(vali.begin(), vali.end());
    reverse(vali.begin(), vali.end()); // largest val first

    initA.clear();
    initA.pb(0);
    segI.resize(M);
    for (int i = 0; i < M; i++) {
        initA.pb(vali[i].f);
        segI[vali[i].s] = i + 1;
    }
//    ps("segI", segI);
//    ps(initA);

    segTr.b(1, 1, M);
    lastA = -1;

    ans.resize(tCost + 1);
    fill(ans.begin(), ans.end(), 0);

    computeRanges();
    return ans;
}

long long int findMaxAttraction(int iN, int ist, int iD,
                                int iA[]) {
    N = iN, st = ist, tCost = iD;

    vi a;
    for (int i = 0; i < N; i++) {
        a.pb(iA[i]);
    }

    vi lSide;
    for (int i = st; i >= 0; i--) {
        lSide.pb(a[i]);
    }

    vi rSide;
    for (int i = st; i < N; i++) {
        rSide.pb(a[i]);
    }
    rSide[0] = 0; // don't take it twice

    movC = 1;
    vl left1 = computeSide(lSide);
    vl right1 = computeSide(rSide);

    movC = 2;
    vl left2 = computeSide(lSide);
    vl right2 = computeSide(rSide);

    ll best = 0;
    for (int i = 0; i <= tCost; i++) {
        best = max(best, left1[i] + right2[tCost - i]);
        best = max(best, left2[i] + right1[tCost - i]);
    }

//    ps(left1);
//    ps(left2);
//    ps(right1);
//    ps(right2);
//    ps(toggleCnt);

    return best;
}