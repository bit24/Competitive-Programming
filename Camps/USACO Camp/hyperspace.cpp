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


const int MAXN = 3e4;

vi aL[MAXN];

int edge[MAXN][2];

int st[MAXN][2];

int vis[MAXN];
// find topological order in final direction
vi top;

int N, M;

int state = 0;

void dfs(int cV) {
    if (vis[cV]) return;
    vis[cV] = true;

    for (int eI : aL[cV]) {
        if (edge[eI][st[eI][state]] == cV) {
            int aV = edge[eI][!st[eI][state]];
            dfs(aV);
        }
    }
    top.pb(cV);
}

void topsort() {
    top.clear();
    memset(vis, 0, sizeof(vis));
    for (int i = 1; i <= N; i++) {
        if (!vis[i]) {
            dfs(i);
        }
    }
}

int tInd[MAXN];

int main() {
    cin >> N >> M;

    for (int i = 1; i <= M; i++) {
        cin >> edge[i][0] >> edge[i][1];
        aL[edge[i][0]].pb(i);
        aL[edge[i][1]].pb(i);
    }

    for (int i = 1; i <= M; i++) {
        cin >> st[i][0];
    }
    for (int i = 1; i <= M; i++) {
        cin >> st[i][1];
    }

    state = 0;
    topsort();
    vi top0 = top;
    reverse(top0.begin(), top0.end());

    for (int i = 0; i < N; i++) {
        tInd[top0[i]] = i;
    }

//    for (int i = 1; i <= N; i++) {
//        ps(i, tInd[i]);
//    }

    state = 1;
    topsort();

    int cnt = 0;
    for (int i = 1; i <= M; i++) {
        cnt += st[i][0] != st[i][1];
    }
    cout << cnt << endl;

    // top is currently reverse topological order of final direction

//    ps(top);

    memset(vis, 0, sizeof(vis));

    for (int cV : top) {
        vis[cV] = true;
        vector<pi> cand;

        for (int eI : aL[cV]) {
            if (st[eI][0] == st[eI][1]) continue;

            if (edge[eI][st[eI][0]] == cV) {
                int aV = edge[eI][!st[eI][0]];
                assert(!vis[aV]);

                cand.pb({tInd[aV], eI});
            }
        }
        sort(cand.begin(), cand.end());
        // prioritize edges earlier in original topological ordering

//        ps(cand);

        for (pi x : cand) {
            cout << x.s << endl;
        }
    }
}