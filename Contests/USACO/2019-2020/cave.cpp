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

const int MAXN = 1e3 + 10;
const int MAXV = MAXN * MAXN;
const ll MOD = 1e9 + 7;

int grid[MAXN][MAXN];
int id[MAXN][MAXN];

vi aL[MAXV];
int start[MAXV];

int nNode = 1;
int par[MAXV];

int fRoot(int x) {
    return par[x] == x ? x : par[x] = fRoot(par[x]);
}

void merge(int a, int b) {
    a = fRoot(a);
    b = fRoot(b);
    par[a] = b;
}

ll dfs(int cV) {
    ll prod = 1;
    for (int aV : aL[cV]) {
        prod = prod * dfs(aV) % MOD; // default cV inactive
    }
    prod = (prod + 1) % MOD; // in 1 case where all children are active, another option to active
    return prod;
}

int main() {
    freopen("cave.in", "r", stdin);
    freopen("cave.out", "w", stdout);
    int N, M;
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        string line;
        cin >> line;
        for (int j = 0; j < M; j++) {
            grid[i][j] = line[j] == '.';
        }
    }

    fill(start, start + MAXV, 0);

    for (int i = 0; i < MAXV; i++) {
        par[i] = i;
    }

    for (int i = N - 2; i >= 1; i--) {
        for (int j = 0; j < M; j++) {
            if (grid[i][j]) {
                id[i][j] = nNode++;

                if (j > 0 && grid[i][j - 1]) {
                    merge(id[i][j - 1], id[i][j]);
                }
                if (grid[i + 1][j]) {
                    merge(id[i + 1][j], id[i][j]);
                }
            }
        }

        for (int j = 0; j < M; j++) {
            if(grid[i][j]){
                id[i][j] = fRoot(id[i][j]);
                start[id[i][j]] = true;
            }
        }

        for (int j = 0; j < M; j++) {
            if (grid[i][j] && grid[i + 1][j]) {
                aL[id[i][j]].pb(id[i + 1][j]);
                start[id[i + 1][j]] = false;
            }
        }
    }

    for (int i = 0; i < nNode; i++) {
        sort(aL[i].begin(), aL[i].end());
        aL[i].erase(unique(aL[i].begin(), aL[i].end()), aL[i].end());
//        ps(aL[i]);
    }

    ll ans = 1;
    for (int i = 0; i < nNode; i++) {
        if (start[i]) {
//            ps("consider", i);
            ans = ans * dfs(i) % MOD;
        }
    }
    cout << ans << endl;
}