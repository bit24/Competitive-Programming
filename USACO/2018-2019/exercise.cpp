#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef pair<int, int> pi;

#define pb push_back
#define f first
#define s second

const int MAXN = 200005;
const int LOGN = 18;

int N, M;

vi aL[MAXN];

int st[MAXN];
int ed[MAXN];

map<pi, int> ocnt;

namespace lca {
    const int LOGN = 18;

    int anc[MAXN][LOGN];
    int depth[MAXN];

    void prep() {
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
};

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
        ps(rest...); // print w/ spaces
    }
}

using namespace lca;
using namespace debug;

void dfs(int cV, int pV) {
    anc[cV][0] = pV;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            depth[aV] = depth[cV] + 1;
            dfs(aV, cV);
        }
    }
}

ll tCnt = 0;

ll ch2(ll n) {
    return n * (n - 1) / 2;
}

int dfsCnt(int cV, int pV) {
    int act = 0;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            act += dfsCnt(aV, cV);
        }
    }

    act += st[cV];
    // ps(cV, act, ed[cV], ed[cV] * (act - 1LL));

    tCnt += ed[cV] * (act - 1LL) - ch2(ed[cV]);
    act -= ed[cV];
    return act;
}

int main() {
    ifstream fin("exercise.in");
    ofstream fout("exercise.out");

    fin >> N >> M;
    // ps(N, M);
    for (int i = 0; i < N - 1; i++) {
        int a, b;
        fin >> a >> b;
        a--, b--;
        aL[a].pb(b);
        aL[b].pb(a);
    }
    vector<pi> aux;

    for (int i = N - 1; i < M; i++) {
        int a, b;
        fin >> a >> b;
        a--, b--;
        aux.pb({a, b});
    }
    // ps("Read", M);

    memset(depth, 0, sizeof(depth));
    dfs(0, 0);

    prep();

    memset(st, 0, sizeof(st));
    memset(ed, 0, sizeof(st));

    for (pi e : aux) {
        int cLCA = fLCA(e.f, e.s);

        int fL = -1, sL = -1;
        if (e.f != cLCA) {
            fL = j(e.f, depth[e.f] - depth[cLCA] - 1);
            st[e.f]++;
            ed[fL]++;
        }
        if (e.s != cLCA) {
            sL = j(e.s, depth[e.s] - depth[cLCA] - 1);
            st[e.s]++;
            ed[sL]++;
        }
        if (fL != -1 && sL != -1) {
            if(fL > sL){
                swap(fL, sL);
            }
            ocnt[{fL, sL}]++;
        }
    }

    dfsCnt(0, 0);
    ll ans = tCnt;
    // ps(tCnt);

    for (auto a : ocnt) {
        ans -= ch2(a.s);
    }
    // ps(tCnt - ans);
    fout << ans << endl;
}