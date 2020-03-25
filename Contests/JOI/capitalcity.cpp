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


const int MAXN = 2e5 + 10;
const int LOGN = 20;

int N, K;

vi aL[MAXN];
int c[MAXN];

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
    depth[0] = 0; // if root is different change
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

int ans = 1e9;

int own[MAXN];

int status[MAXN];

int gCnt[MAXN];

vi mem[MAXN];
set<pi, greater<pi>> reqs[MAXN];

int cT = 1;

stack<int> stk;

int mergeGroups(int a, int b) {
    if (a == b) return a;
    ps("merging", a, b);
    if (mem[a].size() < mem[b].size()) {
        swap(a, b);
    }

    reqs[a].insert(reqs[b].begin(), reqs[b].end());
    mem[a].insert(mem[a].end(), mem[b].begin(), mem[b].end());

    for (int x :mem[b]) {
        own[x] = a;
    }
    assert(mem[a].size() > 0);

    gCnt[a] += gCnt[b];

    dsu.merge(b, a);
    return a;
}

void compute(int cG) {
    ps("computing", cG);
    status[cG] = cT;
    stk.push(cG);

    while (reqs[cG].size()) {
        pi cR = *reqs[cG].begin();
        reqs[cG].erase(reqs[cG].begin());

        int steps = cR.f;
        int cV = cR.s;

        if (own[cV] == cG) { // already visited by req with at least as many steps so redundant
            continue;
        }

        int sup = dsu.fRt(c[cV]);
        if (status[sup] != -1) {
            if (status[sup] < cT) {
                return; // that group (sup) is strictly better
            } else {
                assert(status[sup] == cT);

                if (sup == cG) {
                    own[cV] = cG;
                    mem[cG].pb(cV);

                    if (steps > 1) {
                        reqs[cG].insert({steps - 1, anc[cV][0]});
                    }
                } else {
                    // sup on stack
                    ps("inside");

//                int nMerge = 0;
                    while (stk.top() != sup) {
                        cG = mergeGroups(cG, stk.top());
                        stk.pop();
                    }
                    cG = mergeGroups(cG, stk.top());
                    stk.pop();

                    ps("merged");

                    stk.push(cG);

                }
            }
        } else {
            compute(sup);
            return;
        }
    }

    ps(cG, gCnt[cG] - 1);
    ans = min(ans, gCnt[cG] - 1);

    stk.pop();
}

vi groupies[MAXN];

int main() {
    cin >> N >> K;
    for (int i = 0; i < N - 1; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
        aL[v].pb(u);
    }

    for (int i = 0; i < N; i++) {
        cin >> c[i];
        c[i]--;
        groupies[c[i]].pb(i);
    }

    prep();
    dsu.init();

    for (int cG = 0; cG < N; cG++) {
        for (int i = 0; i + 1 < groupies[cG].size(); i++) {
            int a = groupies[cG][i], b = groupies[cG][i + 1];

            int lca = fLCA(a, b);
            reqs[cG].insert({depth[a] - depth[lca] + 1, a});
            reqs[cG].insert({depth[b] - depth[lca] + 1, b});
        }
    }

    fill(status, status + N, -1);
    fill(own, own + N, -1);
    fill(gCnt, gCnt + N, 1);

    for (int cG = 0; cG < N; cG++) {
        if (groupies[cG].size() && status[cG] == -1) {
            compute(cG);
            cT++;
            while (stk.size()) stk.pop();
        }
    }

    cout << ans << endl;
}