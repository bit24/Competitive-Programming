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

const int MAXN = 5e5 + 100;
const int INF = 1e9;

struct SegTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r) {
        if (l == r) {
            tr[i] = -INF;
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid);
        b(i * 2 + 1, mid + 1, r);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return -INF;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }

    void u(int i, int l, int r, int x, int d) {
        if (l == r) {
            tr[i] = max(tr[i], d);
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            u(i * 2, l, mid, x, d);
        } else {
            u(i * 2 + 1, mid + 1, r, x, d);
        }
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }
} dTr, uTr;

int N, U, D, S;

vector<pi> fairs[MAXN];

int sVal[MAXN], bVal[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

//    ps("start");
    cin >> N >> U >> D >> S;

    U = -U, D = -D; // let change in value = negative coefficient * distance
    for (int i = 0; i < N; i++) {
        int t, l, v;
        cin >> t >> l >> v;
        fairs[t].pb({l, v});
    }

//    ps("read");

    for (int i = 0; i < MAXN; i++) {
        if (fairs[i].empty()) {
            continue;
        }
        sort(fairs[i].begin(), fairs[i].end());
//        ps(i);
    }

//    ps("sorted");

    dTr.b(1, 1, MAXN);
    uTr.b(1, 1, MAXN);

    dTr.u(1, 1, MAXN, S, S * D); // down update = positive
    uTr.u(1, 1, MAXN, S, -S * U); // up update = negative

    for (int cT = 0; cT < MAXN; cT++) {
        if (fairs[cT].empty()) {
            continue;
        }

        vector<pi> &cF = fairs[cT];
        fill(sVal, sVal + cF.size(), -INF);
        fill(bVal, bVal + cF.size(), -INF);

        for (int i = 0; i < cF.size(); i++) {
            int dVal = dTr.q(1, 1, MAXN, cF[i].f, MAXN) - cF[i].f * D;
            int uVal = uTr.q(1, 1, MAXN, 1, cF[i].f) + cF[i].f * U;
            sVal[i] = max(dVal, uVal) + cF[i].s;
        }

        int prev = -INF;
        for (int i = 0; i < cF.size(); i++) {
            int cur = (i == 0) ? -INF : prev + (cF[i].f - cF[i - 1].f) * U + cF[i].s;
            cur = max(cur, sVal[i]);
            bVal[i] = max(bVal[i], cur);
            prev = cur;
        }

        prev = -INF;
        for (int i = cF.size() - 1; i >= 0; i--) {
            int cur = (i == cF.size() - 1) ? -INF : prev + (cF[i + 1].f - cF[i].f) * D + cF[i].s;
            cur = max(cur, sVal[i]);
            bVal[i] = max(bVal[i], cur);
            prev = cur;
        }

        for (int i = 0; i < cF.size(); i++) {
            dTr.u(1, 1, MAXN, cF[i].f, bVal[i] + cF[i].f * D);
            uTr.u(1, 1, MAXN, cF[i].f, bVal[i] - cF[i].f * U);
        }
    }

    int dAns = dTr.q(1, 1, MAXN, S, MAXN) - S * D;
    int uAns = uTr.q(1, 1, MAXN, 1, S) + S * U;

    int ans = max(dAns, uAns);
    cout << ans << endl;
}