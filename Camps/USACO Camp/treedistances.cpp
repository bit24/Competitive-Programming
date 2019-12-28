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

vi aL[MAXN];

int blocked[MAXN];
int N;

const int LOGN = 18;

int anc[MAXN][LOGN];
int depth[MAXN];

void dfs(int cV, int pV) {
    anc[cV][0] = pV;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            depth[aV] = depth[cV] + 1;
            dfs(aV, cV);
        }
    }
}

void prep() {
    memset(anc, 0, sizeof(anc));
    depth[0] = 0;
    dfs(0, -1);
    anc[0][0] = 0;

    for (int k = 1; k < LOGN; k++) {
        for (int i = 0; i < N; i++) {
//            ps(i, k - 1);
//            ps(anc[i][k - 1]);
            anc[i][k] = anc[anc[i][k - 1]][k - 1];
        }
    }
}

int j(int a, int d) {
//    assert(d >= 0);
    for (int i = 0; i < LOGN; i++) {
        if (d & (1 << i)) {
            a = anc[a][i];
        }
    }
    return a;
}

int fLCA(int a, int b) {
//    ps(a, b);
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

int dist(int a, int b) {
    int lca = fLCA(a, b);
    return depth[a] + depth[b] - 2 * depth[lca];
}

int fIncV(int cV, int eV) {
    int lca = fLCA(cV, eV);

    if (eV != lca) {
        return anc[eV][0];
    }
    return j(cV, depth[cV] - depth[eV] - 1);
}

int traverse(int cV, int eV, int d) {
    int lca = fLCA(cV, eV);
    int d1 = depth[cV] - depth[lca];

//    ps(d1);
    if (d <= d1) {
        return j(cV, d);
    }
    d -= d1;
    return j(eV, depth[eV] - depth[lca] - d);
}

int main() {
    int Q;
    cin >> N >> Q;

    for (int i = 0; i < N - 1; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
        aL[v].pb(u);
    }
    prep();

    vector<pi> rList;

    for (int i = 0; i < Q; i++) {
        int v, d;
        cin >> v >> d;
        v--;
        rList.pb({v, d});
    }
    memset(blocked, 0, sizeof(blocked));

    int cV = rList[0].f;
    int d1 = rList[0].s;

//    ps(rList);

    for (int cRI = 1; cRI < Q; cRI++) {
//        ps("cRI", cRI);
        int oV = rList[cRI].f;
        int d2 = rList[cRI].s;

//        ps(cV, oV);
        int bD = dist(cV, oV);

        if (d1 + d2 < bD || max(d1, d2) - bD > min(d1, d2)) {
            cout << 0 << endl;
            return 0;
        }
        if ((d1 + d2) % 2 != bD % 2) {
            cout << 0 << endl;
            return 0;
        }

//        ps("half");

        int out = (d1 + d2 - bD) / 2;
//        ps(cV, oV);
//        ps(d1, d2);
//        ps(out, bD);
//        assert(0 <= d1 - out && d1 - out <= bD);

        int incV = fIncV(oV, cV);

//        ps("fIncV");
//        ps(cV, oV, d1 - out);
        int nV = traverse(cV, oV, d1 - out);

//        ps("finT");
        if (blocked[incV] && nV != cV) {
            cout << 0 << endl;
            return 0;
        }

        if (cV != nV) {
            blocked[fIncV(cV, nV)] = true;
        }
        if (oV != nV) {
            blocked[fIncV(oV, nV)] = true;
        }

//        ps("blockProp");

        cV = nV;
        d1 = out;
    }

//    ps("exitloop");

    vi ans;
    for (int i = 0; i < N; i++) {
        if (dist(i, cV) == d1 && (i == cV || !blocked[fIncV(i, cV)])) {
            ans.pb(i + 1);
        }
    }

    cout << ans.size() << endl;
    for (int i = 0; i < ans.size(); i++) {
        cout << ans[i] << endl;
    }
}