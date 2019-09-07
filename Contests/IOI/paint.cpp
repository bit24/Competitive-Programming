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
    const int DEBUG = false;

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

int N;
int K;

vi wSum;

bool canBlack(int l, int r) {
    return (wSum[r] - (l == 0 ? 0 : wSum[l - 1])) == 0;
}

vector<vi> calcDP(string str, vi bS) {
    wSum.resize(N);
    wSum[0] = 1;
    for (int i = 1; i < N; i++) {
        wSum[i] = wSum[i - 1] + (str[i] == '_');
    }

    vector<vi> dp(N);
    for (int i = 0; i < N; i++) {
        dp[i].resize(K + 1, false);
    }

    dp[0][0] = true;

    for (int i = 0; i + 1 < N; i++) {
        for (int numF = 0; numF <= K; numF++) {
            if (dp[i][numF] == false) {
                continue;
            }
//            ps("consider:", i, numF);

            if (str[i + 1] == 'X') {
                if (numF == K) {
                    continue; // cannot lead to any valid combinations
                }
                int cBS = bS[numF];

                // start a new block and end with white
                if (i + 1 + cBS < N && canBlack(i + 1, i + cBS) && str[i + 1 + cBS] != 'X') {
                    dp[i + 1 + cBS][numF + 1] = true;
                }
            } else if (str[i + 1] == '_') {
                dp[i + 1][numF] = true; // maintain status
            } else {
//                ps("3rd path");
                assert(str[i + 1] == '.');
                dp[i + 1][numF] = true; // maintain status

                if (numF == K) {
                    continue;
                }

                int cBS = bS[numF]; // or start a new block
//                ps(canBlack(i + 1, i + cBS), wSum[i + cBS] - wSum[i]);
                if (i + 1 + cBS < N && canBlack(i + 1, i + cBS) && str[i + 1 + cBS] != 'X') {
                    dp[i + 1 + cBS][numF + 1] = true;
//                    ps(i, "->", i + 1 + cBS);
                }
            }
        }
    }
    return dp;
}

string solve_puzzle(string str, vi bS) {
    str = '_' + str + '_'; // white padding on sides
    N = str.length();
    K = bS.size();

    string rStr(str.rbegin(), str.rend());
    vi rBS = vi(bS.rbegin(), bS.rend());

//    ps(str);
//    ps(bS);

    vector<vi> rInf = calcDP(rStr, rBS);
    vector<vi> lInf = calcDP(str, bS);


    reverse(rInf.begin(), rInf.end());
    for (int i = 0; i < N; i++) {
        reverse(rInf[i].begin(), rInf[i].end());
    }
//    ps(rInf);


    //test if it can be black to see if must be white

    vl dSum(N + 1, 0);

    for (int i = 1; i < N; i++) { // block starts at i
        for (int j = 0; j < K; j++) {
            int rS = i + bS[j];
            if (lInf[i - 1][j] && rS < N && rInf[rS][j + 1] && canBlack(i, rS - 1)) {
//                ps(j, i, rS - 1);
                dSum[i]++;
                dSum[rS]--;
            }
        }
    }

//    ps("p1");

    for (int i = 1; i < N; i++) {
        dSum[i] += dSum[i - 1];
    }

//    ps(dSum, dSum);

    string ans;
    for (int i = 1; i < N - 1; i++) {
        if (str[i] == '_') {
            ans.pb('_');
        } else if (str[i] == 'X') {
            ans.pb('X');
        } else if (dSum[i] == 0) {
            ans.pb('_');
        } else {
            bool undet = false;
            for (int j = 0; j <= K; j++) {
                if (lInf[i][j] && rInf[i][j]) {
                    ans.pb('?');
                    undet = true;
                    break;
                }
            }
            if (!undet) ans.pb('X');
        }
    }
    return ans;
}