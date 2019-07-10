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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;


vector<vi> aL;
vi dist;

namespace treeDiameter {

    void calcDist(int cV, int pV) {
        for (int aV : aL[cV]) {
            if (aV != pV) {
                dist[aV] = dist[cV] + 1;
                calcDist(aV, cV);
            }
        }
    }

    pi fDiameter() {
        dist[0] = 0;
        calcDist(0, -1);
        int end1 = max_element(dist.begin(), dist.end()) - dist.begin();

        dist[end1] = 0;
        calcDist(end1, -1);
        int end2 = max_element(dist.begin(), dist.end()) - dist.begin();
        return {end1, end2};
    }
}
using namespace treeDiameter;

vi cl;

vi subDep;
vi ans;

vi cCnt;
int tCnt = 0;

stack<int> stk;

void addCl(int x) {
    if (++cCnt[x] == 1) {
        tCnt++;
    }
}

void remCl(int x) {
    if (--cCnt[x] == 0) {
        tCnt--;
    }
}

void calcMaxSubDep(int cV, int pV) {
    int cM = 0;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            calcMaxSubDep(aV, cV);
            cM = max(cM, subDep[aV]);
        }
    }
    subDep[cV] = cM + 1;
}


void cntPath(int cV, int pV) {
//    ps("frame", cV);
    vector<pi> ch;
    for (int aV : aL[cV]) {
        if (aV != pV) {
            ch.pb({subDep[aV], aV});
        }
    }

    if (ch.size()) {
        swap(ch[0], *max_element(ch.begin(), ch.end()));
        int maxSibDep = ch.size() > 1 ? (*max_element(ch.begin() + 1, ch.end())).f : 0;
//        ps(maxSibDep);

        for (pi cCh: ch) {
            int aV = cCh.s;

            while (stk.size() && dist[stk.top()] >= dist[cV] - maxSibDep) {
//                ps("removed1", stk.top(), dist[stk.top()], dist[cV] - maxSibDep);
                remCl(cl[stk.top()]);
                stk.pop();
            }
            addCl(cl[cV]);
            stk.push(cV);
//            ps("added", cV);

            cntPath(aV, cV);

            if (stk.size() && stk.top() == cV) {
//                ps("removed", stk.top());
                remCl(cl[stk.top()]);
                stk.pop();
            }
            maxSibDep = ch[0].f;
        }
        while (stk.size() && dist[stk.top()] >= dist[cV] - maxSibDep) {
//            ps("removed", stk.top());
            remCl(cl[stk.top()]);
            stk.pop();
        }
    }
    ans[cV] = max(ans[cV], tCnt);
//    ps(cCnt);
}

int main() {
    int N, M;
    cin >> N >> M;
    aL.resize(N);
    cl.resize(N);
    cCnt.resize(M, 0);

    for (int i = 0; i < N - 1; i++) {
        int a, b;
        cin >> a >> b;
        a--, b--;
        aL[a].pb(b);
        aL[b].pb(a);
    }

    for (int i = 0; i < N; i++) {
        cin >> cl[i];
        cl[i]--;
    }

//    ps("input");

    dist.resize(N);
    pi diameter = fDiameter();

//    ps("diameter");

    subDep.resize(N);
    ans.resize(N);

//    ps(diameter);
//    ps(dist);

    calcMaxSubDep(diameter.f, -1);

    cntPath(diameter.f, -1);

//    ps("part1");

    dist[diameter.s] = 0;
    calcDist(diameter.s, -1);
    calcMaxSubDep(diameter.s, -1);
    cntPath(diameter.s, -1);

    for (int i = 0; i < N; i++) {
        cout << ans[i] << endl;
    }
}