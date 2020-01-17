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

const int M_MANA = 3e4;
const int M_NEG = 1e4;

const int INF = 1e9;

int dp[M_MANA], nDP[M_MANA];

int main() {
    int MCAP, R, N;
    cin >> MCAP >> R >> N;

    vector<pair<int, pi>> spells(N);
    for (int i = 0; i < N; i++) {
        cin >> spells[i].s.f >> spells[i].s.s >> spells[i].f;
    }
    spells.push_back({0, {0, 0}}); // forced last
    N++;

    sort(spells.begin(), spells.end());
    reverse(spells.begin(), spells.end()); // spells in decreasing duration

    fill(dp, dp + M_MANA, -INF);

    for (int sI = 0; sI < N; sI++) {
        fill(nDP, nDP + M_MANA, -INF);

        auto cSpell = spells[sI];
        int cDur = cSpell.f;
        int cVal = cSpell.s.f;
        int cCost = cSpell.s.s;

//        ps(MCAP - cCost + M_NEG);
        nDP[MCAP - cCost + M_NEG] = cVal;

//        ps(cDur, cVal, cCost);

        int deltT = sI == 0 ? 0 : spells[sI - 1].f - cDur;
        assert(deltT >= 0);
        int extM = deltT * R;

        for (int cM = -M_NEG; cM <= MCAP; cM++) {
            int nM = min(cM + extM, MCAP) - cCost;
            if (-M_NEG <= nM) {
                nDP[nM + M_NEG] = max(nDP[nM + M_NEG], dp[cM + M_NEG] + cVal);
            }
            nM = min(cM + extM, MCAP);
            nDP[nM + M_NEG] = max(nDP[nM + M_NEG], dp[cM + M_NEG]);
        }

        swap(dp, nDP);

//        ps("dp");
//
//        for (int eM = -10; eM <= MCAP; eM++) { //only consider non-negative final manas
//            ps(eM, dp[eM + M_NEG]);
//        }
//        ps("end");
    }
//    ps("dp");

    int ans = 0;
    for (int eM = 0; eM <= MCAP; eM++) { //only consider non-negative final manas
        ans = max(ans, dp[eM + M_NEG]);
//        ps(dp[eM + M_NEG]);
    }

    cout << ans << endl;
}