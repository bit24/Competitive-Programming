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

const int MAXN = 1e5 + 10;

struct LSegTr {
    ll tr[4 * MAXN], lz[4 * MAXN];

    void push(int i, int l, int r) {
        tr[i] += (r - l + 1ll) * lz[i];
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    ll q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        push(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
    }

    void u(int i, int l, int r, int s, int e, int d) {
        push(i, l, r); // pushed early to use in recalculation of parent
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            lz[i] += d;
            push(i, l, r);
            return;
        }
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }
} lSegTr;

int N, Q, MAXT;

vi aL[MAXN];

int cT = 0;
int first[MAXN], last[MAXN];

void undo(pi cP) {
//    ps("undo", cP.f, cP.s);
    lSegTr.u(1, 0, MAXT, cP.f, cP.s, -1);
}

bool oLap(pi a, pi b) {
    if (a.s < b.f || b.s < a.f) {
        return false;
    } else {
        return true;
    }
}

void insertInt(pi cInt, set<pi> &all) {
//    ps("inserting");
//    ps(all);
    auto it = all.lower_bound(cInt);

    while (it != all.end()) {
        pi right = *it;
        if (!oLap(cInt, right)) {
            break;
        }
        undo(right);
        all.erase(right);

        cInt.f = min(cInt.f, right.f);
        cInt.s = max(cInt.s, right.s);

        it = all.lower_bound(cInt);
    }

    it = all.lower_bound(cInt);

    while (it != all.begin()) {
        it--;
        pi left = *it;
        if (!oLap(left, cInt)) {
            break;
        }
        undo(left);
        all.erase(left);

        cInt.f = min(cInt.f, left.f);
        cInt.s = max(cInt.s, left.s);

        it = all.lower_bound(cInt);
    }
//    ps("add", cInt.f, cInt.s);
    lSegTr.u(1, 0, MAXT, cInt.f, cInt.s, 1);
    all.insert({cInt.f, cInt.s});
}

void dfs(int cV, int pV) {
    first[cV] = cT++;
    for (int aV :aL[cV]) {
        if (aV != pV) {
            dfs(aV, cV);
        }
    }
    last[cV] = cT - 1;
}

set<pi> cInt[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    freopen("snowcow.in", "r", stdin);
    freopen("snowcow.out", "w", stdout);
    cin >> N >> Q;

    for (int i = 0; i < N - 1; i++) {
        int u, v;
        cin >> u >> v;
        u--, v--;
        aL[u].pb(v);
        aL[v].pb(u);
    }
    dfs(0, -1);
    MAXT = cT;
//    ps("MAXT", MAXT);

    memset(lSegTr.tr, 0, sizeof(lSegTr.tr));
    memset(lSegTr.lz, 0, sizeof(lSegTr.lz));

    for (int i = 0; i < Q; i++) {
        int t;
        cin >> t;
        if (t == 1) {
            int cV, cC;
            cin >> cV >> cC;
            cV--, cC--;

            int l = first[cV];
            int r = last[cV];

//            ps("insert", l, r, cC);

            insertInt({l, r}, cInt[cC]);
        } else {
            int cV;
            cin >> cV;
            cV--;

            int l = first[cV];
            int r = last[cV];

            ll ans = lSegTr.q(1, 0, MAXT, l, r);
            cout << ans << endl;
        }
    }
}