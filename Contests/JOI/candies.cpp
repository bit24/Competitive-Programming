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


const int MAXN = 2e5 + 10;
const ll INF = 1e15;

vl costs[4 * MAXN][4];

ll v[MAXN];
int N;

void merge(vl &a, vl &b, vl &res) {
    int most = a.size() + b.size() - 2;
//    ps(res.size(), most + 1);
    if (res.size() <= most + 1) {
        res.resize(most + 1, 0);
    }

    ll sum = 0;
    int aI = 1, bI = 1;

    res[0] = 0;
    for (int i = 1; i <= most; i++) {
        if (aI < a.size() && bI < b.size()) {
            if (a[aI] - a[aI - 1] > b[bI] - b[bI - 1]) {
                sum += a[aI] - a[aI - 1];
                aI++;
            } else {
                sum += b[bI] - b[bI - 1];
                bI++;
            }
        } else if (aI < a.size()) {
            sum += a[aI] - a[aI - 1];
            aI++;
        } else {
            assert(bI < b.size());
            sum += b[bI] - b[bI - 1];
            bI++;
        }
        res[i] = max(res[i], sum);
    }
}

void compute(int i, int l, int r) {
    if (l != r) {
        int mid = (l + r) / 2;
        compute(i * 2, l, mid);
        compute(i * 2 + 1, mid + 1, r);

        for (int cS = 0; cS < 4; cS++) {
            for (int wSide = 0; wSide < 2; wSide++) {
                int lS = (cS & 1) + (wSide == 0 ? 2 : 0);
                int rS = (cS & 2) + (wSide == 1 ? 1 : 0);

//                ps(cS, wSide, lS, rS);
//                ps(l, r);
//
//                ps(costs[i * 2][lS]);
//                ps(costs[i * 2 + 1][rS]);

                merge(costs[i * 2][lS], costs[i * 2 + 1][rS], costs[i][cS]);

//                if (i == 1 && cS == 0) {
//                    ps(costs[i][cS]);
//                    ps("flag");
//                }
            }
        }
    } else {
        costs[i][0] = {0, v[l]};
        costs[i][1] = {0};
        costs[i][2] = {0};
        costs[i][3] = {0};
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> v[i];
    }

    compute(1, 0, N - 1);
    vl &ans = costs[1][0];

    assert(ans.size() - 1 == (N + 1) / 2);

    for (int i = 1; i < ans.size(); i++) {
        cout << ans[i] << endl;
    }

//    ps("debug");
//    ps(costs[1][0]);
//    ps(costs[2][0]);
//    ps(costs[3][0]);
//    ps(costs[3][1]);
}