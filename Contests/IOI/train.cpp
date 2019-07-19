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

int V;
vi own, isC, numO;
vector<vi> aL;
vector<vi> rL;
vector<bool> rm;

vi fReach(vi &goal, int pro) {
    vi reach(V, 0);
    queue<int> q;

    for (int i : goal) {
        reach[i] = -1;
        q.push(i);
    }

    while (q.size()) {
        int cV = q.front();
        q.pop();
        for (int pV : rL[cV]) {
            if (rm[pV] || reach[pV] == -1) {
                continue;
            }
            if (own[pV] == pro) {
                reach[pV] = -1;
                q.push(pV);
            } else {
                reach[pV]++;
                if (reach[pV] == numO[pV]) {
                    reach[pV] = -1;
                    q.push(pV);
                }
            }
        }
    }
    for (int i = 0; i < V; i++) {
        if (reach[i] == -1) {
            reach[i] = 1;
        } else {
            reach[i] = 0;
        }
    }
    return reach;
}

vi who_wins(vi a, vi r, vi u, vi v) {
    V = a.size();
    own = a;
    isC = r;

    aL.resize(V);
    rL.resize(V);
    for (int i = 0; i < u.size(); i++) {
        aL[u[i]].pb(v[i]);
        rL[v[i]].pb(u[i]);
    }

    rm.resize(V);
    fill(rm.begin(), rm.end(), false);
    int rem = V;

    vi ans(V);

    numO.resize(V);
    for (int i = 0; i < V; i++) {
        numO[i] = aL[i].size();
    }

    while (rem > 0) {
        vi charge;
        for (int i = 0; i < V; i++) {
            if (!rm[i] && isC[i]) {
                charge.pb(i);
            }
        }

        vi reachCharge = fReach(charge, 1);
        vi cannotReach;
        for (int i = 0; i < V; i++) {
            if (!rm[i] && !reachCharge[i]) {
                cannotReach.pb(i);
            }
        }

        if (cannotReach.empty()) {
            for (int i = 0; i < V; i++) {
                if (!rm[i] && reachCharge[i]) {
                    ans[i] = 1;
                }
            }
            return ans;
        }

        vi trappable = fReach(cannotReach, 0);

        for (int i = 0; i < V; i++) {
            if (!rm[i] && trappable[i]) {
                ans[i] = 0;
                rm[i] = true;
                rem--;

                for (int pV : rL[i]) {
                    if(!rm[pV]){
                        numO[pV]--;
                    }
                }
            }
        }
    }

    return ans;
}