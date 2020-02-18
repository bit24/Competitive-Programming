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

const ll MOD = 1e9 + 7;
const int MAXC = 8001;

ll cDP[101][MAXC][4];
ll nDP[101][MAXC][4];

int h[105];

int main() {
    int N, L;
    cin >> N >> L;

    if (N == 1) {
        cout << 1 << endl;
        return 0;
    }

    for (int i = 1; i <= N; i++) {
        cin >> h[i];
    }

    sort(h + 1, h + N + 1);
    reverse(h + 1, h + N + 1);

//    for (int i = 1; i <= N; i++) {
//        ps(h[i]);
//    }

    //number of components, cost, type (bitmask for whether or not we've already selected left/right ends)

    memset(cDP, 0, sizeof(cDP));
    cDP[0][0][0] = 1; // 1 way to have no components, no cost, and no endpoints at beginning

    for (int i = 0; i < N; i++) { // push transitions
        memset(nDP, 0, sizeof(nDP));
        for (int cComp = 0; cComp <= 100; cComp++) {
            for (int cCost = 0; cCost < MAXC; cCost++) {
                for (int cType = 0; cType < 4; cType++) { //typing stores status of left and right ends
                    if (cDP[cComp][cCost][cType] == 0) continue;

//                    ps("pushing", i, cComp, cCost, cType);

                    ll cCnt = cDP[cComp][cCost][cType];
                    int nFixed = ((cType & 0b10) != 0) + ((cType & 1) != 0);

                    // same type

                    // case: new component, it will be greater than all later so height will be added twice
                    int nCost = cCost + 2 * h[i + 1];
                    if (nCost < MAXC) {
                        // number of places we can put it is cComp + 1 - number of already fixed sides
                        nDP[cComp + 1][nCost][cType] =
                                (nDP[cComp + 1][nCost][cType] + cCnt * (cComp + 1 - nFixed)) % MOD;
                    }

                    // case: add to 1 component, less than attached but greater than later attached so net change is 0
                    if (cComp > 0) {
                        nCost = cCost;
                        // number of places we can attach it to is 2 * nComp - number of already fixed sides
                        nDP[cComp][nCost][cType] = (nDP[cComp][nCost][cType] + cCnt * (2 * cComp - nFixed)) % MOD;
                    }

                    // case: combine 2 components
                    if (cComp >= 2) {
                        nCost = cCost - 2 * h[i + 1]; // will be lower than both attached
                        assert(nCost >= 0);
                        // number of 2 components we can combine is cComp - 1
                        nDP[cComp - 1][nCost][cType] = (nDP[cComp - 1][nCost][cType] + cCnt * (cComp - 1)) % MOD;
                    }

                    // type change

                    for (int side = 1; side < 3; side++) {
                        if ((cType & side) == 0) {
                            int nType = cType | side;

                            // case: new component on left/right
                            nCost = cCost + h[i + 1];
                            if (nCost < MAXC) {
                                nDP[cComp + 1][nCost][nType] = (nDP[cComp + 1][nCost][nType] + cCnt) % MOD;
                            }

                            // case: attach to component on left/right
                            if (cComp > 0) {
                                nCost = cCost - h[i + 1];
                                assert(nCost >= 0);
                                nDP[cComp][nCost][nType] = (nDP[cComp][nCost][nType] + cCnt) % MOD;
                            }
                        }
                    }
                }
            }
        }
//        ps(i);
        memcpy(cDP, nDP, sizeof(nDP));
//        ps("finswap");
    }

    ll ans = 0;

    cout << cDP[0][0][-1];

    for (int cCost = 0; cCost <= L; cCost++) {
        ans = (ans + cDP[1][cCost][0b11]) % MOD;
    }

    cout << ans << endl;
}