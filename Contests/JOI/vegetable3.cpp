#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const int INF = 1e8;

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

int pre[3][500][500]; //index, ending color, red delta, green delta, (inferred) yellow delta
int nxt[3][500][500];
int color[500];
int cnt[500][3];
int loc[500][3];

int main() {
    int N;
    cin >> N;

    string colorStr;
    cin >> colorStr;

    cnt[0][0] = cnt[0][1] = cnt[0][2] = 0;

    for (int i = 1; i <= N; i++) {
        if (colorStr[i - 1] == 'R') {
            color[i] = 0;
        } else if (colorStr[i - 1] == 'G') {
            color[i] = 1;
        } else {
            color[i] = 2;
        }
        cnt[i][0] = cnt[i - 1][0];
        cnt[i][1] = cnt[i - 1][1];
        cnt[i][2] = cnt[i - 1][2];

        cnt[i][color[i]]++;

        loc[cnt[i][color[i]]][color[i]] = i;
    }

//    ps("read", N);
//
//    ps("allocate");

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < N; j++) {
            for (int k = 0; k < N; k++) {
                pre[i][j][k] = nxt[i][j][k] = INF;
            }
        }
    }

    pre[0][0][0] = 0;
    pre[1][0][0] = 0;
    pre[2][0][0] = 0;


    for (int i = 1; i <= N; i++) {
        for (int r = 0; r < N; r++) {
            for (int g = 0; g < N; g++) {
                int y = i - r - g;
                if (y < 0) {
                    continue;
                }

                // iterate over cases of last color
                if (r > 0) {
                    int oLoc = loc[r][0];
                    int eC = abs(cnt[oLoc][1] - g) + abs(cnt[oLoc][2] - y);
                    nxt[0][r][g] = min(pre[1][r - 1][g], pre[2][r - 1][g]) + eC;
                }
                if (g > 0) {
                    int oLoc = loc[g][1];
                    int eC = abs(cnt[oLoc][0] - r) + abs(cnt[oLoc][2] - y);
                    nxt[1][r][g] = min(pre[0][r][g - 1], pre[2][r][g - 1]) + eC;
                }
                if (y > 0) {
                    int oLoc = loc[y][2];
                    int eC = abs(cnt[oLoc][0] - r) + abs(cnt[oLoc][1] - g);
                    nxt[2][r][g] = min(pre[0][r][g], pre[1][r][g]) + eC;
                }
            }
        }

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k <= N; k++) {
                for (int l = 0; l <= N; l++) {
//                    if (i - k - l >= 0 && i <= 2) {
//                        ps("end:", j, "cnts:", k, l, i - k - l, "cost:", nxt[j][k][l]);
//                    }
                    pre[j][k][l] = nxt[j][k][l];
                    nxt[j][k][l] = INF;
                }
            }
        }
    }

    int ans = min(pre[0][cnt[N][0]][cnt[N][1]], min(pre[1][cnt[N][0]][cnt[N][1]], pre[2][cnt[N][0]][cnt[N][1]]));
    if (ans >= INF) {
        cout << -1 << endl;
        return 0;
    }
    ans /= 2;
    cout << ans << endl;
}