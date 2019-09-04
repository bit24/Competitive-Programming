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

const ll INF = 1e16;

int N;
vl x;
vi d;
ll C;

vector<pl> order, process;;

namespace debug {
    const int DEBUG = false;

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

pair<pl, pl> specMax(pair<pl, pl> a, pl c) {
    if (c >= a.f) {
        return {c, a.f};
    }
    if (c >= a.s) {
        return {a.f, c};
    }
    return a;
}

pair<pl, pl> specMin(pair<pl, pl> a, pl c) {
    if (c <= a.f) {
        return {c, a.f};
    }
    if (c <= a.s) {
        return {a.f, c};
    }
    return a;
}

bool binSearch(ll K) {
    ll P = -INF;
    ll Q = INF;
    ll R = -INF;
    ll S = INF;

    pair<pl, pl> maxP = {{-INF, -1},
                         {-INF, -1}};
    pair<pl, pl> minM = {{INF, 1},
                         {INF, 1}};

    int nP = 0;
    for (pl cI: order) {
        ll cP = x[cI.s] + d[cI.s];
        ll cM = x[cI.s] - d[cI.s];

        while (nP < N && cI.f - K > process[nP].f) {
            int pI = process[nP].s;
//            ps("added", cI.s, pI);
            maxP = specMax(maxP, {x[pI] + d[pI], pI});
            minM = specMin(minM, {x[pI] - d[pI], pI});
            nP++;
        }

//        ps(maxP, minM);
//        ps(cM);
//        ps(C, K);

        if (maxP.f.s == cI.s) {
            P = max(P, cP + maxP.s.f + C - K);
            S = min(S, cM - maxP.s.f - C + K);
        } else {
            P = max(P, cP + maxP.f.f + C - K);
            S = min(S, cM - maxP.f.f - C + K);
        }

        if (minM.f.s == cI.s) {
            Q = min(Q, cM + minM.s.f - C + K);
            R = max(R, cP - minM.s.f + C - K);
        } else {
            Q = min(Q, cM + minM.f.f - C + K);
            R = max(R, cP - minM.f.f + C - K);
        }
    }

//    ps("PQRS", P, Q, R, S);

    int bPt = 0;

    for (int i = 0; i < N; i++) {
        ll aBnd = min(Q - x[i], x[i] - R);
        ll bBnd = max(P - x[i], x[i] - S);
        while (bPt + 1 < N && x[bPt + 1] < bBnd) {
            bPt++;
        }
        while (bPt >= 0 && x[bPt] >= bBnd) {
            bPt--;
        }

//        ps("in", aBnd, bBnd, bPt);

        if (bPt + 1 < N && x[bPt + 1] <= aBnd) {
//            ps(i, bPt);
            return true;
        }
    }
    return false;
}

ll find_shortcut(int iN, vi len, vi iD, int iC) {
    N = iN;
    x.resize(N, 0);
    d = iD;
    C = iC;

    x[0] = 0;
    for (int i = 0; i < N - 1; i++) {
        x[i + 1] = x[i] + len[i];
    }

    for (int i = 0; i < N; i++) {
        order.pb({x[i] + d[i], i});
        process.pb({x[i] - d[i], i});
    }

    sort(order.begin(), order.end());
    sort(process.begin(), process.end());

//    for (int i = 0; i < N; i++) {
//        loc[process[i].s] = i;
//        processA.pb(x[process[i].s] + d[process[i].s]);
//        processS.pb(x[process[i].s] - d[process[i].s]);
//    }
//
//    preA.pb(processA[0]);
//    preS.pb(processS[0]);
//    for (int i = 1; i < N; i++) {
//        preA.pb(max(processA[i], preA[i - 1]));
//        preS.pb(min(processS[i], preS[i - 1]));
//    }
//
//    sufA.resize(N);
//    sufS.resize(N);
//
//    sufA[N-1] = processA[]


    ll lo = 0;
    ll hi = INF;

//    ps(x);
//    ps(C);
//    ps(binSearch(62));
//    exit(0);
//    ps("finnnnnnnnnn");

    ll sum1 = 0;
    ll dMax = d[N - 1];
    for (int i = 0; i < N - 1; i++) {
        sum1 += len[i];
        dMax = max(dMax, (ll) d[i]);
    }

    hi = min(hi, sum1 + 2 * dMax);

    while (lo != hi) {
        ll mid = (lo + hi) / 2;
        if (binSearch(mid)) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    return lo;
}