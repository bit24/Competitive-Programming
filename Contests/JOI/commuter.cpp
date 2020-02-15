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
const ll INF = 1e15;

vector<pi> aL[MAXN];
vector<pi> sL[MAXN];

vector<pi> pre[MAXN];

int N, M;
int S, T, U, V;

// finds DAG for routes from S to T, adds directed edges to sL

void fOPath() {
    ll cost[MAXN];
    fill(cost, cost + MAXN, INF);
    cost[S] = 0;

    priority_queue<pl, vector<pl>, greater<pl>> q;
    q.push({0, S});

    while (q.size()) {
        auto cS = q.top();
        q.pop();

        int cV = cS.s;
        ll cC = cS.f;

        if (cost[cV] < cC) {
            continue;
        }

        for (pi aE : aL[cV]) {
            int aV = aE.f;
            ll eC = aE.s;

            if (cC + eC < cost[aV]) {
                cost[aV] = cC + eC;
                pre[aV].clear();
                pre[aV].pb({cV, eC});

                q.push({cC + eC, aV});
            } else if (cC + eC == cost[aV]) {
                pre[aV].pb({cV, eC});
            }
        }
    }

    queue<int> q2;

    bool inQ2[MAXN];
    fill(inQ2, inQ2 + MAXN, false);

    inQ2[T] = true;
    q2.push(T);

    while (q2.size()) {
        int cV = q2.front();
        q2.pop();

        for (pi preE : pre[cV]) {
            if (!inQ2[preE.f]) {
                q2.push(preE.f);
                inQ2[preE.f] = true;
            }
            sL[preE.f].pb({cV, 0});

//            ps(preE.f, "->", cV);
        }
    }
}

// state depends on cV and whether we've used, are using, or haven't used the special edges
ll dp[MAXN][3];

// given aL and sL, finds best route
ll fBest() {
    priority_queue<pair<ll, pi>, vector<pair<ll, pi>>, greater<pair<ll, pi>>> q;

    for (int i = 0; i < MAXN; i++) {
        for (int j = 0; j < 3; j++) {
            dp[i][j] = INF;
        }
    }

    q.push({0, {U, 0}});
    dp[U][0] = 0;

    while (q.size()) {
        auto cS = q.top();
        q.pop();

        ll cC = cS.f;
        int cV = cS.s.f;
        int cCond = cS.s.s;

        if (dp[cV][cCond] < cC) {
            continue;
        }

        // normal transitions
        for (pi aE : aL[cV]) {
            int aV = aE.f;
            ll eC = aE.s;
            int nCond = cCond == 0 ? 0 : 2;

            if (dp[aV][nCond] > cC + eC) {
                dp[aV][nCond] = cC + eC;
                q.push({cC + eC, {aV, nCond}});
            }
        }

        // special transitions
        if (cCond != 2) {
            for (pi aE : sL[cV]) {
                int aV = aE.f;
                ll eC = aE.s;
                int nCond = 1;

                if (dp[aV][nCond] > cC + eC) {
                    dp[aV][nCond] = cC + eC;
                    q.push({cC + eC, {aV, nCond}});
                }
            }
        }
    }
    return min(min(dp[V][0], dp[V][1]), dp[V][2]);
}

int main() {
    cin >> N >> M;
    cin >> S >> T >> U >> V;
    for (int i = 0; i < M; i++) {
        int A, B, C;
        cin >> A >> B >> C;
        aL[A].pb({B, C});
        aL[B].pb({A, C});
    }
    fOPath();

    ll a1 = fBest();
    swap(U, V);

    ll a2 = fBest();

    ll ans = min(a1, a2);

    cout << ans << endl;
}