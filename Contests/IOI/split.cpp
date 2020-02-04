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


const int MAXN = 1e5 + 100;

vi aL[MAXN];
vi tAL[MAXN];
vi cAL[MAXN];

bool vis[MAXN];
int cnt[MAXN];

int halfN;

int cID = -1;
int id[MAXN];

int rem;
int color[MAXN];

int compSizes[MAXN];

vi sComps;

void fSpanTree(int cV) {
    vis[cV] = true;
    for (int aV : aL[cV]) {
        if (!vis[aV]) {
            tAL[cV].pb(aV);
            tAL[aV].pb(cV);

            fSpanTree(aV);
        }
    }
} // reviewed

void fCnt(int cV, int pV) {
    cnt[cV] = 1;
    id[cV] = cID;
    for (int aV : tAL[cV]) {
        if (aV != pV) {
            fCnt(aV, cV);
            cnt[cV] += cnt[aV];
        }
    }
} // reviewed

int fCent(int cV, int pV) {
    for (int aV : tAL[cV]) {
        if (aV != pV && cnt[aV] > halfN) {
            return fCent(aV, cV);
        }
    }
    return cV;
} // reviewed

void markColor(int cV, int pV, int cC) {
    if (rem == 0) return;

    color[cV] = cC;
    rem--;

    for (int aV : tAL[cV]) {
        if (aV != pV && color[aV] == -1) {
            markColor(aV, cV, cC);
        }
    }
} // reviewed

void fEdges(int cV, int pV) {
    for (int aV : aL[cV]) {
        if (id[aV] != -1 && id[aV] != id[cV]) {
            cAL[id[cV]].pb(id[aV]);
//            ps(aV, id[aV]);
            cAL[id[aV]].pb(id[cV]);
        }
    }

    for (int aV : tAL[cV]) {
        if (aV != pV) {
            fEdges(aV, cV);
        }
    }
} // reviewed

void fCompConnect(int cV) {
    vis[cV] = true;
    if (rem <= 0) return;
    rem -= compSizes[cV];

    sComps.pb(cV);

    for (int aV : cAL[cV]) {
        if (!vis[aV]) {
            fCompConnect(aV);
        }
    }
} // reviewed

vi find_split(int N, int A, int B, int C, vi p, vi q) {
//    ps("start");
    pi goals[] = {{A, 1},
                  {B, 2},
                  {C, 3}};
    sort(goals, goals + 3);

    A = goals[0].f;
    B = goals[1].f;
    C = goals[2].f;

    for (int i = 0; i < p.size(); i++) {
        aL[p[i]].pb(q[i]);
        aL[q[i]].pb(p[i]);
    }

    fill(vis, vis + MAXN, 0);
    fSpanTree(0);

    fCnt(0, -1);

    halfN = cnt[0] / 2;
    int cent = fCent(0, -1);

    int nComp = tAL[cent].size();

    cID = 0;
    for (int i = 0; i < nComp; i++) {
        int aV = tAL[cent][i];
        fCnt(aV, cent);
        compSizes[i] = cnt[aV];
        cID++;
    }

//    ps("check1");

//    for (int i = 0; i < N; i++) {
//        ps(id[i]);
//    }

    assert(id[cent] == -1);

    fill(color, color + MAXN, -1);
    fill(vis, vis + MAXN, 0);

    bool smaller = true;
    for (int i = 0; i < nComp; i++) {
        int aV = tAL[cent][i];
        if (compSizes[i] >= A) {
            smaller = false;

            rem = A;
            markColor(aV, cent, goals[0].s);

            rem = B;
            markColor(cent, aV, goals[1].s);

            break;
        }
    }

//    ps("check2");

    assert(cID == nComp);

    if (smaller) {
        for (int i = 0; i < nComp; i++) {
            int aV = tAL[cent][i];
            fEdges(aV, cent);
        }

        for (int i = 0; i < nComp; i++) {
            sort(cAL[i].begin(), cAL[i].end());
            cAL[i].erase(unique(cAL[i].begin(), cAL[i].end()), cAL[i].end());
        }

        bool found = false;
        fill(vis, vis + MAXN, 0);
        for (int i = 0; i < nComp; i++) {
            if (!vis[i]) {
                sComps.clear();
                rem = A;
                fCompConnect(i);
                if (rem <= 0) {
                    found = true;

                    rem = A;
                    for (int cComp : sComps) {
                        markColor(tAL[cent][cComp], cent, goals[0].s);
                    }

                    rem = B;
                    markColor(cent, -1, goals[1].s);
                    break;
                }
            }
        }

        if (!found) {
            return vi(N, 0);
        }
    }


    vi ans(N);
    for (int i = 0; i < N; i++) {
        if (color[i] == -1) {
            color[i] = goals[2].s;
        }
        ans[i] = color[i];
    }
    return ans;
}