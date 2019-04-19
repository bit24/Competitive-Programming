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

const int MAXN = 760;
const int INF = 1e9;

int lID[MAXN][MAXN];
int h[MAXN * MAXN];
int fd[MAXN * MAXN];
int curv[MAXN * MAXN];

int N;

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

int cnt = 0;

namespace dsu {
    int a[MAXN * MAXN];
    int s[MAXN * MAXN];

    void prep() {
        for (int i = 0; i < MAXN * MAXN; i++) {
            a[i] = i;
            s[i] = 1;
        }
    }

    int fR(int i) {
        return a[i] == i ? i : a[i] = fR(a[i]);
    }

    void m(int i, int j) {
        i = fR(i), j = fR(j);
        if (i != j) {
            a[i] = j;
            s[j] += s[i];
            curv[j] += curv[i];
            // ps("", i, j, "");
            cnt++;
        } else {
            //  ps("already merged");
        }
    }
}

using namespace dsu;

int lin(int a, int b) {
    return a * (N + 5) + b;
}

bool cmp(int a, int b) {
    return h[a] < h[b];
}

int di[] = {-1, 0, 1, 0, -1, 0, 1, 0};
int dj[] = {0, -1, 0, 1, 0, -1, 0, 1};

int d2i[] = {-1, 1, 1, -1};
int d2j[] = {-1, -1, 1, 1};

int fDCurv(int a, int b, int c) {
    if (a + b + c == 0) {
        return 1;
    }
    if (a + b + c == 1) {
        if (b) {
            return 1;
        }
        return -1; // becomes edge
    }
    if (a + b + c == 2) {
        if (!b) {
            return -3;
        }
        return -1;
    }
    return 1;
}

bool sC(int i, int j) {
    return fR(i) == fR(j);
}

int main() {
    ifstream fin("valleys.in");
    ofstream fout("valleys.out");

    fin >> N;

    for (int i = 0; i < MAXN * MAXN; i++) {
        h[i] = INF;
    }

    vi ord;
    for (int i = 0; i <= N + 1; i++) {
        for (int j = 0; j <= N + 1; j++) {
            lID[i][j] = lin(i, j);
            if (1 <= i && i <= N && 1 <= j && j <= N) {
                fin >> h[lID[i][j]];
                ord.pb(lID[i][j]);
            }
        }
    }

    sort(ord.begin(), ord.end(), cmp);

    memset(fd, 0, sizeof(fd));

    prep();

    // ps(ord);
    int cnt2 = 0;

    ll ans = 0;
    for (int crID : ord) {
        //ps("run");
        fd[crID] = 1;
        int i = crID / (N + 5);
        int j = crID % (N + 5);

        // ps(i, j, crID);

        for (int d = 0; d < 4; d++) {
            int nI = i + di[d];
            int nJ = j + dj[d];
            int nID = lID[nI][nJ];
            if (fd[nID]) {
                cnt2++;
                m(crID, nID);
            }
        }

        int dCurv = 0;

        for (int d = 0; d < 4; d++) {
            int aID = lID[i + di[d]][j + dj[d]];
            int bID = lID[i + d2i[d]][j + d2j[d]]; // diagonals
            int cID = lID[i + di[d + 1]][j + dj[d + 1]];
            dCurv += fDCurv(sC(crID, aID), sC(crID, bID), sC(crID, cID));
            // ps(fDCurv(sC(crID, aID), sC(crID, bID), sC(crID, cID)));
            // ps(aID, bID, cID);
        }

        curv[fR(crID)] += dCurv;

        //ps("curv", dCurv, curv[fR(crID)]);
        //curv = 4-4h
        if (curv[fR(crID)] == 4) {
            // ps("adding", crID, s[fR(crID)]);
            ans += s[fR(crID)];
        }
    }
    // ps(cnt, cnt2);
    fout << ans << endl;
}