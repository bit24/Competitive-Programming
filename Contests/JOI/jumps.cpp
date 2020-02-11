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


const int MAXN = 5e5 + 10;

int N, Q;

int val[MAXN];

int qL[MAXN], qR[MAXN];

int ans[MAXN];

struct LSegTr {
    int tr[4 * MAXN + 5], lz[4 * MAXN + 5];

    void push(int i, int l, int r) {
        tr[i] += lz[i];
        if (l != r) {
            lz[i * 2] += lz[i];
            lz[i * 2 + 1] += lz[i];
        }
        lz[i] = 0;
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        push(i, l, r);
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
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
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    void b(int i, int l, int r) {
        if (l == r) {
            if (l >= N) {
                return;
            }
            tr[i] = val[l];
            return;
        }

        int mid = (l + r) / 2;
        b(i * 2, l, mid);
        b(i * 2 + 1, mid + 1, r);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

} lSegTr;

set<pi> ints;

bool evaluate(pi nInt, pi oInt) {
    if (nInt.s <= oInt.s) {
        return false;
    }
    int delta = nInt.s - oInt.s;
    int oIntR = ints.upper_bound(oInt)->f - 1;
    int updL = max(nInt.f, oInt.f);

//    ps("oInt:", oInt);
//    ps("segTr adding");
//    ps(updL, oIntR, delta);

    lSegTr.u(1, 0, MAXN, updL, oIntR, delta);

    if (nInt.f <= oInt.f) {
        ints.erase(oInt);
    }
    return true;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> val[i];
    }

    cin >> Q;
    vector<pair<pi, int>> queries;
    for (int i = 0; i < Q; i++) {
        cin >> qL[i] >> qR[i];
        qL[i]--, qR[i]--;
        queries.pb({{qL[i], qR[i]}, i});
    }

    sort(queries.begin(), queries.end());
    reverse(queries.begin(), queries.end());

    vi stack;
    vector<pi> lmInts;

    for (int i = 0; i < N; i++) {
        for (int j = stack.size() - 1; j >= 0; j--) {
            lmInts.pb({stack[j], i});

            if (val[stack[j]] >= val[i]) {
                break;
            }
        }

        while (stack.size() && val[stack[stack.size() - 1]] <= val[i]) {
            stack.erase(--stack.end());
        }
        stack.pb(i);
    }
    // lmInts should contain all pairs of (l, m) s.t. all values with indices in-between are strictly less

    sort(lmInts.begin(), lmInts.end());
    reverse(lmInts.begin(), lmInts.end());

//    ps(lmInts);
//    ps(queries);

    ints.insert({N, (int) 1e9});
    ints.insert({N + 1, (int) 1e9});
    ints.insert({0, 0});

    memset(lSegTr.tr, 0, sizeof(lSegTr.tr));
    memset(lSegTr.lz, 0, sizeof(lSegTr.lz));
    lSegTr.b(1, 0, MAXN);

//    ps(ints);

    int nLM = 0, nQ = 0;
    for (int i = N - 1; i >= 0; i--) {
        while (nLM < lmInts.size() && i <= lmInts[nLM].f) {
            pi cLM = lmInts[nLM];
            int cLMVal = val[cLM.f] + val[cLM.s];

            int r = cLM.s + cLM.s - cLM.f;
//            ps("adding");
//            ps(cLM.f, cLM.s, r, cLMVal);

            pi nInt = {r, cLMVal};
            pi cInt = *(--ints.upper_bound({r, (int) 1e9}));

            bool lRes = true;

            int sCnt = 0;

            while (lRes) {
                lRes = evaluate(nInt, cInt);
                cInt = *ints.upper_bound(cInt);
                sCnt++;
            }

            if (sCnt > 1) {
                ints.insert(nInt);
            }


            nLM++;

//            ps("post add");
//            ps(ints);
        }

        while (nQ < Q && queries[nQ].f.f == i) {
            int l = queries[nQ].f.f;
            int r = queries[nQ].f.s;
            int qI = queries[nQ].s;

//            ps("querying", l, r, qI);

//            if (true) {
//                ps("spec", lSegTr.q(1, 0, MAXN, 3, 3));
//            }

            ans[qI] = lSegTr.q(1, 0, MAXN, l, r);
//            ps(ans[qI]);
            nQ++;
        }
    }

    for (int i = 0; i < Q; i++) {
        cout << ans[i] << '\n';
    }
    cout << flush;
}