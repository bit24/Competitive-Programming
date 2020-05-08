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
const int LOGN = 20;
const int MAXV = MAXN * LOGN;

int N;
int HN;

int anc[MAXV];

int fRt(int cV) {
    return anc[cV] == cV ? cV : (anc[cV] = fRt(anc[cV]));
}

void merge(int a, int b) {
    anc[fRt(a)] = fRt(b);
}

int group[MAXN][LOGN];
int last[MAXV][2];


int log2d(int x) {
    return 31 - __builtin_clz(x);
}

void bind(int a, int b, int l) {
    int pow = log2d(l);
    int val = 1 << pow;
    merge(group[a][pow], group[b][pow]);

    a += l - val;
    b += l - val;
    merge(group[a][pow], group[b][pow]);
}

void push() {
    for (int i = 0; i < MAXV; i++) {
        for (int j = 0; j < 2; j++) {
            last[i][j] = -1;
        }
    }

    for (int j = LOGN - 1; j > 0; j--) {
        for (int i = 0; i < N; i++) {
            int len = 1 << j;
            if (i + len > N) {
                continue; // ignore impossible intervals
            }

            int cRoot = fRt(group[i][j]);
            int halfLen = len / 2;

            if (last[cRoot][0] != -1) {
                merge(last[cRoot][0], group[i][j - 1]);
                merge(last[cRoot][1], group[i + halfLen][j - 1]);
            }
            assert(cRoot == fRt(cRoot));

            last[cRoot][0] = group[i][j - 1];
            last[cRoot][1] = group[i + halfLen][j - 1];
        }
    }
}

void palindrome(int a, int b) {
    int l = b - a + 1;
    bind(a, 2 * HN - 1 - b, l);
}

string txt;
int color[MAXV];

int main() {
    cin >> txt;
    string rTxt = txt;
    reverse(rTxt.begin(), rTxt.end());
    txt += rTxt;

    N = txt.size();
    HN = N / 2;

    // initialize data structures
    int nV = 0;
    for (int j = 0; j < LOGN; j++) {
        for (int i = 0; i < N; i++) {
            group[i][j] = nV++;
        }
    }
    for (int cV = 0; cV < nV; cV++) {
        anc[cV] = cV;
    }

    for (int i = 0; i < HN; i++) {
        bind(i, N - 1 - i, 1);
    }

    int Q;
    cin >> Q;

    for (int i = 0; i < Q; i++) {
        char op;
        cin >> op;
        if (op == 'P') {
            int a, b;
            cin >> a >> b;
            a--, b--;
            palindrome(a, b);
        } else {
            int a, b, l;
            cin >> a >> b >> l;
            a--, b--;
            bind(a, b, l);
        }
    }


    push();

    memset(color, 0, sizeof(color));

    for (int i = 0; i < N; i++) {
        if (txt[i] == 'O') {
            color[fRt(group[i][0])] = 1;
        }
    }

    string output;
    for (int i = 0; i < HN; i++) {
        if (color[fRt(group[i][0])]) {
            output += 'O';
        } else {
            output += 'M';
        }
    }
    cout << output << endl;
}