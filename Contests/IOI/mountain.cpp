#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef tuple<ll, ll, ll, ll> ql;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

const ll NONE = -1e16;
const int MAXN = 300000;

ll x[MAXN];

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

namespace LSegTr {
    ll dH[4 * MAXN], mD[4 * MAXN], lz[4 * MAXN];

    void push(int i, int l, int r) {
        if (lz[i] == NONE) return;

        dH[i] = (x[r + 1] - x[l]) * lz[i];
        mD[i] = max(dH[i], 0ll);
        if (l != r) {
            lz[i * 2] = lz[i];
            lz[i * 2 + 1] = lz[i];
        }
        lz[i] = NONE;
    }

    void u(int i, int l, int r, int s, int e, ll d) {
        push(i, l, r); // pushed early to use in recalculation of parent
//        ps("i, l, r:", i, l, r);
//        ps("mD", mD[i]);
        if (e < l || r < s) {
            return;
        }
        if (s <= l && r <= e) {
            lz[i] = d;
            push(i, l, r);
//            ps("leafed mD", mD[i]);
            return;
        }
        int mid = (l + r) / 2;
        u(i * 2, l, mid, s, e, d);
        u(i * 2 + 1, mid + 1, r, s, e, d);
        dH[i] = dH[i * 2] + dH[i * 2 + 1];
        mD[i] = max(mD[i * 2], dH[i * 2] + mD[i * 2 + 1]);
//        ps(i, mD[i]);
    }

    ll qStop(int i, int l, int r, ll h) {
//        ps("i, l, r:", i, l, r, h);
        push(i, l, r);
//        ps(mD[i]);
//        ps(dH[i]);
        if (l == r) {
//            ps("leaf!");
            ll m = dH[i] / (x[l + 1] - x[l]);
//            ps(m);
//            ps(h);
            return x[l] + (m > 0 ? h / m : 0);
        }
        int mid = (l + r) / 2;

        push(i * 2, l, mid);
//        ps(dH[i * 2]);

        if (mD[i * 2] > h) {
            return qStop(i * 2, l, mid, h);
        } else {
            return qStop(i * 2 + 1, mid + 1, r, h - dH[i * 2]);
        }
    }
}

using namespace LSegTr;

int main() {
    ll N;
    cin >> N;

    vector<ql> com;
    vl sigX;

    while (true) {
        char c;
        cin >> c;
        if (c == 'E') {
            break;
        }
        if (c == 'Q') {
            ll x;
            cin >> x;
            com.emplace_back(0, x, -1, -1);
        } else {
            ll l, r, d;
            cin >> l >> r >> d;
            sigX.pb(l);
            sigX.pb(r + 1);
            com.emplace_back(1, l, r, d);
        }
    }

    sigX.pb(1);
    sigX.pb(N + 1);
    sigX.pb(N + 2);

    sort(sigX.begin(), sigX.end());
    sigX.erase(unique(sigX.begin(), sigX.end()), sigX.end());

    sigX.insert(sigX.begin(), -1); // 1-indexing
//    ps(sigX);

    for (int i = 1; i < sigX.size(); i++) {
        x[i] = sigX[i];
    }

    N = sigX.size() - 2;

    memset(dH, 0, sizeof(dH));
    memset(mD, 0, sizeof(mD));
    fill(lz, lz + 4 * MAXN, NONE);

    u(1, 1, N, N, N, 1e16);

//    ps("initialized");

    for (ql cC : com) {
        if (get<0>(cC) == 0) {
//            ps("stQ");
            int mH = get<1>(cC);
//            ps(mH);

            ll ans = qStop(1, 1, N, mH);
            ans--;
            cout << ans << endl;
//            ps("finQ");
        } else {
//            ps("stU");
            int l = get<1>(cC), r = get<2>(cC), d = get<3>(cC);
            l = lower_bound(sigX.begin(), sigX.end(), l) - sigX.begin();
            r = (upper_bound(sigX.begin(), sigX.end(), r) - sigX.begin()) - 1;
//            ps(l, r);
            u(1, 1, N, l, r, d);
//            ps("finU");
        }
//        ps("fin!!!!!!!");
    }
}