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


const int MAXN = 2e5 + 100;
const int LOGN = 20;
const ll INF = 1e15;
int N, K;

ll v[MAXN], c[MAXN];

ll bTotal = -INF;


template<class T, class Compare = less<T>>
struct lazyPriorityQueue {
    priority_queue<T, vector<T>, Compare> q;
    priority_queue<T, vector<T>, Compare> r;

    void push(T x) {
        q.push(x);
    }

    void erase(T x) {
        r.push(x);
    }

    void clearLazy() {
        while (q.size() && r.size() && q.top() == r.top()) {
            q.pop();
            r.pop();
        }
    }

    T top() {
        clearLazy();
        return q.top();
    }

    void pop() {
        clearLazy();
        q.pop();
    }

    int size() {
        return q.size() - r.size();
    }
};


pi sRange[LOGN];
ll sSum[LOGN];

lazyPriorityQueue<pl, greater<pl>> sel[LOGN];
lazyPriorityQueue<pl> extq[LOGN];

int grp[LOGN][MAXN];

void reRange(int level, pi nRange) {
    auto &cSelect = sel[level];
    auto &cExt = extq[level];

    auto &cGrp = grp[level];
    pi &cRange = sRange[level];
    ll &cSum = sSum[level];

    while (cRange.s < nRange.s) {
        cExt.push({v[cRange.s + 1], cRange.s + 1});
        cGrp[cRange.s + 1] = 1;
        cRange.s++;
    }

    while (cRange.f < nRange.f) {
        if (cGrp[cRange.f] == 0) {
            cSelect.erase({v[cRange.f], cRange.f});
            cSum -= v[cRange.f];
        } else {
            cExt.erase({v[cRange.f], cRange.f});
        }
        cRange.f++;
    }

    while (cSelect.size() < K && cExt.size()) {
        cSum += cExt.top().f;
        cGrp[cExt.top().s] = 0;

        cSelect.push(cExt.top());
        cExt.pop();
    }

    while (cExt.size() && cSelect.top().f < cExt.top().f) {
        cSum -= cSelect.top().f;
        cSum += cExt.top().f;

        cGrp[cSelect.top().s] = 1;
        cGrp[cExt.top().s] = 0;

        cExt.push(cSelect.top());
        cSelect.push(cExt.top());

        cSelect.pop();
        cExt.pop();
    }

//    assert(cRange == nRange);
//    assert(cSet.size() == min(K, cRange.s - cRange.f + 1));
}

ll getTotal(int setI) {
    auto &cSelect = sel[setI];
//    assert(cSet.size() <= K);
    if (cSelect.size() == K) {
        return sSum[setI];
    } else {
        return -INF;
    }
}

int call = 0;

void compute(int level, int l1, int l2, int r1, int r2) {
    int cL = (l1 + l2) / 2;
//    cout << call++ << endl;

    ll maxTotal = -INF;
    int maxR = -1;

//    assert(0 <= l1 && 0 <= r1);
    for (int cR = max(cL + K - 1, r1); cR <= r2; cR++) {
        reRange(level, {cL, cR});
        ll cTotal = getTotal(level) + c[cL] - c[cR];
        if (cTotal > maxTotal) {
            maxTotal = cTotal;
            maxR = cR;
        }
    }
//    assert(maxR != -1);
    bTotal = max(bTotal, maxTotal);

    if (l1 < cL) {
        compute(level + 1, l1, cL - 1, r1, maxR);
    }
    if (cL < l2) {
        compute(level + 1, cL + 1, l2, maxR, r2);
    }
}

pi items[MAXN];

int main() {
//    freopen("cake3.in", "r", stdin);

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N >> K;

//    cout << "start" << endl;

    for (int i = 0; i < N; i++) {
        int x, y;
        cin >> x >> y;
        items[i] = {y, x};
    }

    sort(items, items + N);

    for (int i = 0; i < N; i++) {
        v[i] = items[i].s;
        c[i] = items[i].f;
        c[i] *= 2;
    }

    fill(sSum, sSum + LOGN, 0);
    fill(sRange, sRange + LOGN, make_pair(0, -1));

//    cout << "computing" << endl;

    compute(0, 0, N - K, 0, N - 1);
    cout << bTotal << endl;
}