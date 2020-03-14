#include <bits/stdc++.h>

#pragma GCC optimize("Ofast")

using namespace std;

typedef unsigned long long ll;
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


const int MAXN = 505;
const int UNITS = 8;

int pos[MAXN][MAXN];
int nPos[MAXN][MAXN];

int R, C, M;

int dI[] = {-1, 0, 1, 0};
int dJ[] = {0, -1, 0, 1};

int getD(char x) {
    if (x == 'N') {
        return 0;
    } else if (x == 'W') {
        return 1;
    } else if (x == 'S') {
        return 2;
    } else if (x == 'E') {
        return 3;
    } else {
        assert(x == '?');
        return 4;
    }
}

struct bset {
    ll a[UNITS];

    void lShift() {
        for (int i = UNITS - 1; i >= 1; i--) {
            a[i] <<= 1;
            a[i] |= a[i - 1] >> 63;
        }
        a[0] <<= 1;
    }

    void rShift() {
        for (int i = 0; i < UNITS - 1; i++) {
            a[i] >>= 1;
            a[i] |= a[i + 1] << 63;
        }
        a[UNITS - 1] >>= 1;
    }

    void init(string s) {
        for (int i = 0; i < s.size(); i++) {
            a[i / 64] |= ((ll) (s[i] == '.')) << (i % 64);
        }
    }

    void copy(bset &o) {
        for (int i = 0; i < UNITS; i++) {
            a[i] = o.a[i];
        }
    }

    void zero() {
        for (int i = 0; i < UNITS; i++) {
            a[i] = 0;
        }
    }

    void andeq(bset &o) {
        for (int i = 0; i < UNITS; i++) {
            a[i] &= o.a[i];
        }
    }

    void oreq(bset &o) {
        for (int i = 0; i < UNITS; i++) {
            a[i] |= o.a[i];
        }
    }

    ll get(int x) {
        return a[x / 64] & (1ull << (x % 64));
    }

    void print() {
        for (int i = UNITS - 1; i >= 0; i--) {
            string x = bitset<64>(a[i]).to_string();
//            reverse(x.begin(), x.end());
            pr(x, " ");
        }
        ps();
    }
};


bset grid[MAXN], sets[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> R >> C >> M;

    for (int i = 0; i < R; i++) {
        string line;
        cin >> line;
        grid[i].init(line);
        sets[i].copy(grid[i]);
    }
//
//    for (int i = 0; i < R; i++) {
//        sets[i].print();
//    }
//    ps();

    string obs;
    cin >> obs;
    for (int x = 0; x < M; x++) {
        int d = getD(obs[x]);

        if (d == 1) {
            for (int i = 0; i < R; i++) {
                sets[i].rShift();
            }
        } else if (d == 3) {
            for (int i = 0; i < R; i++) {
                sets[i].lShift();
            }
        } else if (d == 0) {
            for (int i = 0; i < R - 1; i++) {
                sets[i].copy(sets[i + 1]);
            }
            sets[R - 1].zero();
        } else if (d == 2) {
            for (int i = R - 1; i > 0; i--) {
                sets[i].copy(sets[i - 1]);
            }
            sets[0].zero();
        } else {
            assert(d == 4);
            bset nsets[MAXN];

            for (int i = 0; i < R; i++) {
                nsets[i].zero();
                if (i - 1 >= 0) {
                    nsets[i].oreq(sets[i - 1]);
                }
                if (i + 1 < R) {
                    nsets[i].oreq(sets[i + 1]);
                }
                bset wcopy;

                wcopy.copy(sets[i]);
                wcopy.lShift();
                nsets[i].oreq(wcopy);

//                nsets[i].print();

                wcopy.copy(sets[i]);
                wcopy.rShift();
                nsets[i].oreq(wcopy);

//                nsets[i].print();
            }

            for (int i = 0; i < R; i++) {
                sets[i].copy(nsets[i]);
            }
        }

        for (int i = 0; i < R; i++) {
            sets[i].andeq(grid[i]);
//            sets[i].print();
        }

//        ps();
    }

    int sum = 0;
    for (int i = 0; i < R; i++) {
        for (int j = 0; j < C; j++) {
            if (sets[i].get(j)) {
//                ps(i, j);
                sum++;
            }
        }
    }

    cout << sum << endl;
}
