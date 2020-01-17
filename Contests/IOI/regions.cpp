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


const int MAXN = 2e5 + 100;
const int SQRTN = 400; // slightly smaller as smaller groups have extra lg factor

int N, R, Q;

int color[MAXN], par[MAXN];
vi aL[MAXN];

vi mems[MAXN];
vi locs[MAXN];

int tSt[MAXN], tEnd[MAXN];
int cT = 0;

void dfs(int cV) {
    tSt[cV] = cT++;
    for (int aV : aL[cV]) {
        dfs(aV);
    }
    tEnd[cV] = cT - 1;
}

vi bigC;
int cMap[MAXN];

int actCnt[MAXN];
int cCnt[MAXN / SQRTN + 10][MAXN];

void dfs2(int cV) {
    for (int cBigC : bigC) {
        cCnt[cMap[cBigC]][color[cV]] += actCnt[cBigC];
    }

    actCnt[color[cV]]++;

    for (int aV : aL[cV]) {
        dfs2(aV);
    }

    actCnt[color[cV]]--;
}

int main() {
    cin >> N >> R >> Q;

    cin >> color[0];
    color[0]--;
    for (int cV = 1; cV < N; cV++) {
        cin >> par[cV] >> color[cV];
        par[cV]--, color[cV]--;
        aL[par[cV]].pb(cV);
    }

    dfs(0);

//    for (int i = 0; i < N; i++) {
//        pr(tSt[i], " ");
//    }
//    ps();

    for (int cV = 0; cV < N; cV++) {
        mems[color[cV]].pb(cV);
        locs[color[cV]].pb(tSt[cV]);
    }

    fill(cMap, cMap + MAXN, -1);
    for (int cC = 0; cC < N; cC++) {
        sort(locs[cC].begin(), locs[cC].end());
        if (mems[cC].size() > SQRTN) {
            cMap[cC] = bigC.size();
            bigC.pb(cC);
        }
    }

    dfs2(0);

    for (int cQ = 0; cQ < Q; cQ++) {
        int cAnc, cDes;
        cin >> cAnc >> cDes;
        cAnc--, cDes--;

        int ans = 0;
        if (mems[cAnc].size() <= SQRTN) {
//            ps(locs[cDes]);
            for (int cV : mems[cAnc]) {
                int cTSt = tSt[cV];
                int cTEnd = tEnd[cV];

//                ps(cV, cTSt, cTEnd);

                auto r = upper_bound(locs[cDes].begin(), locs[cDes].end(), cTEnd);
                auto l = upper_bound(locs[cDes].begin(), locs[cDes].end(), cTSt); // exclude cTSt

                ans += r - l;
            }
        } else {
            int mapped = cMap[cAnc];
            ans = cCnt[mapped][cDes];
        }
//        ps("ans");
        cout << ans << endl;
    }

}