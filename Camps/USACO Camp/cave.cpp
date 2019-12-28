#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef map<int, int> mi;

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

int N;
vi aL[MAXN];
ll keys[MAXN];

int vis[MAXN];
ll mK[MAXN];

vi path;

mi ops[MAXN];

bool fPath(int cV, int pV) {
    if (cV == N - 1) {
        path.pb(cV);
        return true;
    }
    for (int aV : aL[cV]) {
        if (aV != pV) {
            if (fPath(aV, cV)) {
                path.pb(cV);
                return true;
            }
        }
    }
    return false;
}

void add(mi *cM, pi x) {
    if (cM->count(x.f)) {
        (*cM)[x.f] += x.s;
    } else {
        (*cM)[x.f] = x.s;
    }
}

mi *computeOp(int cV, int fPhase = 0) {
    vis[cV] = true;

    mi *cOps = new mi();

    for (int aV : aL[cV]) {
        if (!vis[aV]) {
            mi *aOps = computeOp(aV);

            if (cOps->size() < aOps->size()) {
                swap(cOps, aOps);
            }

            for (pi x : *aOps) {
                add(cOps, x);
            }
        }
    }
    if (keys[cV] > 0) {
        add(cOps, {0, keys[cV]});
    }

    if (fPhase) {
        return cOps;
    }

    if (!cOps->empty()) {
        auto fIt = cOps->begin();
        pi x = *fIt;

        cOps->erase(fIt);

        x.f++;
        x.s--;
        if (x.s > 0) {
            add(cOps, x);
        }
    }

    return cOps;
}

bool pos(int numK) {

    mi *cOps = new mi();
    for (int cV : path) {
//        ps(cV);
        if (cV == N - 1) {
            return true;
        }

        mi *vOps = &ops[cV];

        for (pi x : *vOps) {
            add(cOps, x);
        }

        while (!cOps->empty()) {
            auto fIt = cOps->begin();
            pi x = *fIt;

            if (x.f <= numK) {
                numK += x.s;
                cOps->erase(fIt);
            } else {
                break;
            }
        }

        if (numK == 0) {
            return false;
        }
        numK--;
    }
    return true;
}

int binSearch() {
    int low = 0;
    int high = N;
    while (low != high) {
        int mid = (low + high) / 2;

        if (pos(mid)) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }
    return low;
}

int main() {
    cin >> N;

    for (int i = 0; i < N - 1; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
        aL[v].pb(u);
    }

    for (int i = 0; i < N; i++) {
        cin >> keys[i];
    }

    bool res = fPath(0, -1);
    assert(res);

    reverse(path.begin(), path.end());

    fill(mK, mK + N, 0);
    fill(vis, vis + N, 0);

    for (int cV : path) {
        vis[cV] = true;
    }

    for (int cV : path) {
        if (cV == N - 1) {
            break;
        }
        ops[cV] = *computeOp(cV, 1);
    }

//    ps("computed ops");

//    for (int cV : path) {
//        if (cV == N - 1) {
//            break;
//        }
//        ps(ops[cV]);
//    }

    int ans = binSearch();
    cout << ans << endl;
}