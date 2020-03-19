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


int N, K;

const int MAXN = 5e5 + 100;
const int LOGN = 20;

vi aL[MAXN];

int anc[MAXN][LOGN];
int depth[MAXN];

void dfs(int cV, int pV) {
    for (int aV : aL[cV]) {
        if (aV != pV) {
            depth[aV] = depth[cV] + 1;
            dfs(aV, cV);
            anc[aV][0] = cV;
        }
    }
}

void prep() {
    depth[0] = 0;
    dfs(0, -1);
    anc[0][0] = 0;
    for (int k = 1; k < LOGN; k++) {
        for (int i = 0; i < N; i++) {
            anc[i][k] = anc[anc[i][k - 1]][k - 1];
        }
    }
}

int j(int a, int d) {
    for (int i = 0; i < LOGN; i++) {
        if (d & (1 << i)) {
            a = anc[a][i];
        }
    }
    return a;
}

int fLCA(int a, int b) {
    if (depth[a] > depth[b]) swap(a, b);

    b = j(b, depth[b] - depth[a]);

    if (a == b) return a;

    for (int i = LOGN - 1; i >= 0; i--) {
        if (anc[a][i] != anc[b][i]) {
            a = anc[a][i];
            b = anc[b][i];
        }
    }
    return anc[a][0];
}

int st[MAXN];

vi bySt[MAXN];

int mark[MAXN];

vector<pi> mrg;

int dfs2(int cV, int pV) {
    int sum = mark[cV];
    for (int aV : aL[cV]) {
        if (aV != pV) {
            sum += dfs2(aV, cV);
        }
    }
//    ps(cV, sum);
    if (sum) {
        mrg.pb({cV, pV});
    }
    return sum;
}

struct DSU {
    int anc[MAXN];

    void init() {
        for (int i = 0; i < MAXN; i++) {
            anc[i] = i;
        }
    }

    int fRt(int i) {
        return anc[i] == i ? i : anc[i] = fRt(anc[i]);
    }

    bool merge(int a, int b) {
        a = fRt(a), b = fRt(b);
        if (a == b) {
            return false;
        }
        anc[a] = b;
        return true;
    }
} dsu;

int id[MAXN];

vi cAL[MAXN];

int main() {
    cin >> N >> K;
    for (int i = 0; i < N - 1; i++) {
        int x, y;
        cin >> x >> y;
        x--, y--;
        aL[x].pb(y);
        aL[y].pb(x);
    }

    for (int i = 0; i < N; i++) {
        cin >> st[i];
        bySt[st[i]].pb(i);
    }


    prep();
//    ps(fLCA(5, 7));

    memset(mark, 0, sizeof(mark));

    for (vi &list : bySt) {
        for (int i = 0; i + 1 < list.size(); i++) {
            int x = list[i];
            int y = list[i + 1];
            int lca = fLCA(x, y);
//            ps(x, y, lca);
            mark[x]++;
            mark[y]++;
            mark[lca] -= 2;
        }
    }

//    for (int i = 0; i < N; i++) {
//        ps(mark[i]);
//    }

    dfs2(0, -1);

//    ps("mrg:", mrg);

    dsu.init();
    for (pi x : mrg) {
        dsu.merge(x.f, x.s);
    }

    for (int i = 0; i < N; i++) {
        id[i] = dsu.fRt(i);
    }

    for (int cV = 0; cV < N; cV++) {
        for (int aV :aL[cV]) {
            if (id[cV] != id[aV]) {
                cAL[id[cV]].pb(id[aV]);
            }
        }
    }

    for (int i = 0; i < N; i++) {
        sort(cAL[i].begin(), cAL[i].end());
        cAL[i].erase(unique(cAL[i].begin(), cAL[i].end()), cAL[i].end());
//        ps(cAL[i]);
    }

    int leaves = 0;
    for (int i = 0; i < N; i++) {
        if (cAL[i].size() == 1) {
            leaves++;
        }
    }
    cout << (leaves + 1) / 2 << endl;
}