#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back


const int INF = 1e9;
const int MAXN = 200000;

struct MinTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r, int o[]) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = min(tr[i * 2], tr[i * 2 + 1]);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return INF;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return min(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }
} minTr;

struct MaxTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r, int o[]) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }
} maxTr;

int a[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int N, Q;
    cin >> N >> Q;

    for (int i = 1; i <= N; i++) {
        cin >> a[i];
    }

    minTr.b(1, 1, N, a);
    maxTr.b(1, 1, N, a);

    while (Q--) {
        int l, r;
        cin >> l >> r;
        cout << maxTr.q(1, 1, N, l, r) - minTr.q(1, 1, N, l, r) << '\n';
    }
    cout << flush;
}