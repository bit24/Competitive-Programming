#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

const int MAXN = 100005;

int N;

struct SegTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r, vi &o) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    // find first greater to left
    int qLG(int i, int l, int r, int x, int bd) {
        if (tr[i] <= bd) {
            return -1;
        }
        if (l == r) {
            return l;
        }
        int mid = (l + r) / 2;
        if (x > mid) {
            int qF = qLG(i * 2 + 1, mid + 1, r, x, bd);
            if (qF != -1) {
                return qF;
            }
        }
        return qLG(i * 2, l, mid, x, bd);
    }

    // find first greater to right
    int qRG(int i, int l, int r, int x, int bd) {
        // ps(i, l, r, x, bd);
        if (tr[i] <= bd) {
            // ps("N:", N);
            return N;
        }
        if (l == r) {
            return l;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            int qF = qRG(i * 2, l, mid, x, bd);
            if (qF != N) {
                return qF;
            }
        }
        return qRG(i * 2 + 1, mid + 1, r, x, bd);
    }
} aTr;

int main() {
    int nT;
    cin >> nT;

    for (int cT = 1; cT <= nT; cT++) {
        int K;
        cin >> N >> K;
        vi a(N);
        vi b(N);

        for (int i = 0; i < N; i++) {
            cin >> a[i];
        }
        for (int i = 0; i < N; i++) {
            cin >> b[i];
        }

        aTr.b(1, 0, N - 1, a);

        stack<int> grtL;

        vi lG(N);
        for (int i = 0; i < N; i++) {
            while (grtL.size() && b[grtL.top()] < b[i]) {
                grtL.pop();
            }
            if (grtL.size()) {
                lG[i] = grtL.top();
            } else {
                lG[i] = -1;
            }
            grtL.push(i);
        }

        stack<int> grtR;

        vi rG(N);
        for (int i = N - 1; i >= 0; i--) {
            while (grtR.size() && b[grtR.top()] <= b[i]) {
                grtR.pop();
            }
            if (grtR.size()) {
                rG[i] = grtR.top();
            } else {
                rG[i] = N;
            }
            grtR.push(i);
        }

        //ps(lG);
        //ps(rG);

        ll ans = 0;
        for (int i = 0; i < N; i++) {
            int lB = lG[i] + 1;
            int rB = rG[i] - 1;

            if (a[i] > b[i] + K) {
                continue;
            }

            ps(i);
            //ps(aTr.qLG(1, 0, N - 1, i, b[i] + K));
            //ps(aTr.qRG(1, 0, N - 1, i, b[i] + K));
            lB = max(lB, aTr.qLG(1, 0, N - 1, i, b[i] + K) + 1);
            rB = min(rB, aTr.qRG(1, 0, N - 1, i, b[i] + K) - 1);

            int sL = aTr.qLG(1, 0, N - 1, i, b[i] - K - 1);
            // ps("sR");
            int sR = aTr.qRG(1, 0, N - 1, i, b[i] - K - 1);

            ps(lB, rB, sL, sR);

            if (lB <= sL) {
                ans += (sL - lB + 1LL) * (rB - i + 1);
            }
            if (sR <= rB) {
                ans += (i - lB + 1) * (rB - sR + 1LL);
            }

            if (lB <= sL && sR <= rB) {
                ans -= (sL - lB + 1LL) * (rB - sR + 1LL);
            }

            ps("ans:", ans);
        }
        cout << "Case #" << cT << ": " << ans << "\n";
    }
    cout << flush;
}