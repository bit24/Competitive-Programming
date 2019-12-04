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

const int MAXN = 2000100;

struct Restrict {
    int l, u;

    Restrict operator+(Restrict o) {
        int nL = max(l, o.l);
        int nU = max(u, o.l);
        nU = min(nU, o.u);
        nL = min(nL, o.u);
        return {nL, nU};
    }

    void operator+=(Restrict o) {
        int nL = max(l, o.l);
        int nU = max(u, o.l);
        nU = min(nU, o.u);
        nL = min(nL, o.u);
        l = nL;
        u = nU;
    }

    bool operator==(Restrict o) {
        return l == o.l && u == o.u;
    }
};

Restrict ID = {(int) -1e9, (int) 1e9};

struct SegTr {
    Restrict tr[4 * MAXN];

    void push(int i, int l, int r) {
        if (tr[i] == ID || l == r) return;
        tr[i * 2] += tr[i];
        tr[i * 2 + 1] += tr[i];
        tr[i] = ID;
    }

    void u(int i, int l, int r, int s, int e, Restrict d) {
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            tr[i] += d;
            return;
        }
        push(i, l, r);
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
    }

    void init(int i, int l, int r) {
        tr[i] = ID;
        if (l != r) {
            int mid = (l + r) / 2;
            init(i * 2, l, mid);
            init(i * 2 + 1, mid + 1, r);
        }
    }

    void output(int i, int l, int r, vector<Restrict> &out) {
        if (l == r) {
            out.pb(tr[i]);
        } else {
            if (!(tr[i] == ID)) {
                tr[i * 2] += tr[i];
                tr[i * 2 + 1] += tr[i];
            }

            int mid = (l + r) / 2;
            output(i * 2, l, mid, out);
            output(i * 2 + 1, mid + 1, r, out);
        }
    }

} segTr;

int N;

void buildWall(int n, int k, int op[], int left[], int right[],
               int height[], int finalHeight[]) {
    N = n;
    int Q = k;

    segTr.init(1, 1, N);

    for (int i = 0; i < Q; i++) {
        if (op[i] == 1) {
            segTr.u(1, 1, N, left[i] + 1, right[i] + 1, {height[i], (int) 1e9});
        } else {
            segTr.u(1, 1, N, left[i] + 1, right[i] + 1, {(int) -1e9, height[i]});
        }
    }

    vector<Restrict> restrictions;
    segTr.output(1, 1, N, restrictions);

    for (int i = 0; i < N; i++) {
        Restrict final = {0, 0};
        final += restrictions[i];
//        ps(restrictions[i].l);
//        ps(restrictions[i].u);
        assert(final.l == final.u);
        finalHeight[i] = final.l;
    }
}