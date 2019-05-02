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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

ll ask(const vi &w);

void answer(int s, int t);

int N, M;
vi e1, e2;
ll A, B;
vector<vector<pi>> aList;
ll base;

vi query;

int searchE() {
    int low = 0;
    int high = M - 1;

    while (low != high) {
        int mid = (low + high) / 2;
        for (int i = 0; i < M; i++) {
            query[i] = i <= mid;
        }
        ll res = ask(query);
        if (res == base) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }
    return low;
}

vi dist[3];
int cT;

void fDist(int r) {
    queue<int> q;
    q.push(r);

    dist[cT][r] = 0;

    while (q.size()) {
        int cV = q.front();
        q.pop();
        for (pi aE : aList[cV]) {
            if (dist[cT][aE.f] == -1) {
                dist[cT][aE.f] = dist[cT][cV] + 1;
                q.push(aE.f);
            }
        }
    }
}

vi t, pE;

void bTree(int r, vi &mbr) {
    queue<int> q;
    q.push(r);

    for (int j = 0; j < N; j++) {
        dist[cT][j] = -1;
    }
    dist[cT][r] = 0;
    mbr.pb(r);

    while (q.size()) {
        int cV = q.front();
        q.pop();
        for (pi aE : aList[cV]) {
            if (t[aE.f] == cT && dist[cT][aE.f] == -1) {
                dist[cT][aE.f] = dist[cT][cV] + 1;
                mbr.pb(aE.f);
                query[aE.s] = 0;
                pE[aE.f] = aE.s;

                q.push(aE.f);
            }
        }
    }
}

bool cmp(int a, int b) {
    return dist[cT][a] < dist[cT][b];
}

int find(vi &mbr) {
    sort(mbr.begin(), mbr.end(), cmp);
    reverse(mbr.begin(), mbr.end());

    if (cT == 0) {
        ps("srtd");
        ps(mbr);
    }

    //decreasing dist
    int low = 0;
    int high = mbr.size() - 1;

    while (low != high) {
        int mid = (low + high) / 2;
        for (int i = 0; i < mbr.size(); i++) {
            int cE = pE[mbr[i]];
            if(cE >= 0){
                query[cE] = i <= mid;
            }
        }
        ll res = ask(query);
        if (res == base) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    for (int i = 0; i < mbr.size(); i++) {
        int cE = pE[mbr[i]];
        if(cE >= 0){
            query[cE] = 0;
        }
    }
    return mbr[low];
}

void find_pair(int iN, vi ie1, vi ie2, int iA, int iB) {
    ps("start");
    N = iN;
    e1 = ie1, e2 = ie2;
    A = iA, B = iB;

    aList.resize(N);
    M = e1.size();
    for (int i = 0; i < M; i++) {
        aList[e1[i]].pb({e2[i], i});
        aList[e2[i]].pb({e1[i], i});
    }

    vi empty(M);
    for(int i = 0; i < M; i++){
        empty[i] = 0;
    }
    base = ask(empty);
    ps(base);

    query.resize(M);
    int split = searchE();
    ps("split", split, e1[split], e2[split]);

    dist[0].resize(N);
    dist[1].resize(N);
    for (int j = 0; j < N; j++) {
        dist[0][j] = -1;
        dist[1][j] = -1;
    }

    cT = 0;
    fDist(e1[split]);
    cT = 1;
    fDist(e2[split]);

    ps("dist");
    ps(dist[0]);
    ps(dist[1]);

    t.resize(N);
    pE.resize(N);
    for (int i = 0; i < N; i++) {
        pE[i] = -1;
    }
    for (int i = 0; i < N; i++) {
        t[i] = dist[0][i] > dist[1][i];
    }

    for (int i = 0; i < M; i++) {
        query[i] = 1;
    }
    query[split] = 0;

    vi mbrS, mbrT;
    cT = 0;
    bTree(ie1[split], mbrS);
    cT = 1;
    bTree(ie2[split], mbrT);

    ps("mbr");
    ps(mbrS);
    ps(mbrT);
    ps("query");
    ps(query);

    ps("pE");
    ps(pE);

    cT = 0;
    int s = find(mbrS);
    cT = 1;
    int t = find(mbrT);
    ps(s, t);
    answer(s, t);
}