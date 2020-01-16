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

namespace SCC {
    const int MAXN = 2e5 + 100;
    const int INF = 1e9;

    int N, M, K;

    vi aL[MAXN];

    int cT = 0;
    int disc[MAXN], low[MAXN];

    stack<int> stk;

    int nC = 0;
    int cID[MAXN];

    void dfs(int cV) {
        disc[cV] = low[cV] = cT++;
        stk.push(cV);

        for (int aV : aL[cV]) {
            if (disc[aV] == -1) { // tree edge
                dfs(aV);
                low[cV] = min(low[cV], low[aV]);
            } else {
                low[cV] = min(low[cV], disc[aV]); // look for back edges
            }
        }

        if (low[cV] == disc[cV]) {
            while (stk.top() != cV) {
                cID[stk.top()] = nC;
                disc[stk.top()] = INF;
                stk.pop();
            }
            cID[stk.top()] = nC;
            disc[stk.top()] = INF;
            stk.pop();

            nC++;
        }

//    disc[cV] = INF; // to avoid cross edge interference
    } //rev

    vi cAL[MAXN];
    vi topOrd;

    int vis[MAXN];

    void condGraph() {
        for (int cV = 0; cV < N; cV++) {
            int id1 = cID[cV];
            for (int aV : aL[cV]) {
                int id2 = cID[aV];

                if (id1 != id2) {
                    cAL[id1].pb(id2);
                }
            }
        }

        for (int i = 0; i < nC; i++) {
            vi &cL = cAL[i];
            sort(cL.begin(), cL.end());
            cL.erase(unique(cL.begin(), cL.end()), cL.end());
        }
    }

    void dfs2(int cV) {
        vis[cV] = true;
        for (int aV : cAL[cV]) {
            if (!vis[aV]) {
                dfs2(aV);
            }
        }
        topOrd.pb(cV);
    } //rev
}
using namespace SCC;


int main() {
    cin >> N >> M >> K;

    for (int i = 0; i < M; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
    }

    fill(disc, disc + MAXN, -1);

    for (int i = 0; i < N; i++) {
        if (disc[i] == -1) {
//            ps("in");
            dfs(i);
        }
    }

//    ps(nC);

    if (K > nC) {
        cout << "impossible" << endl;
        return 0;
    }

    condGraph();

    fill(vis, vis + MAXN, 0);
    for (int i = 0; i < nC; i++) {
        if (!vis[i]) {
            dfs2(i);
        }
    }

    reverse(topOrd.begin(), topOrd.end());

    vi cType(nC);
    fill(cType.begin(), cType.end(), K - 1);

    for (int i = 0; i < K; i++) {
        cType[topOrd[i]] = i;
    }

//    for (int i = 0; i < N; i++) {
//        ps(cID[i]);
//    }

//    ps();
//
//    ps(nC);
//    ps(topOrd);
//    ps(cType);

    for (int i = 0; i < N; i++) {
        int cColor = cType[cID[i]] + 1;
        cout << cColor << "\n";
    }
    cout << endl;
}