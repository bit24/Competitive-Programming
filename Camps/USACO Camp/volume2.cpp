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

struct data {
    int d, maxD, minD;
    int lowB, hiB;
};

const data PLUS = {1, 1, 0, 0, 0};
const data MINUS = {-1, 0, -1, 0, 0};


data operator+(data a, data b) {
    data ret;
    ret.d = a.d + b.d;
    ret.maxD = max(a.maxD, a.d + b.maxD);
    ret.minD = min(a.minD, a.d + b.minD);

    ret.lowB = max(a.lowB + b.d, b.lowB);
    ret.hiB = min(a.hiB + b.d, b.hiB);
    return ret;
}

const int MAXN = 1e5 + 10;

data tr[4 * MAXN];

int type[MAXN];

void build(int i, int l, int r) {
    if (l == r) {
        tr[i] = type[l] ? PLUS : MINUS;
    } else {
        int mid = (l + r) / 2;
        build(i * 2, l, mid);
        build(i * 2 + 1, mid + 1, r);
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }
}

int M;

// x = input into interval
int query(int i, int l, int r, int x) {
//    cout << i << " " << l << " " << r << " " << x << endl;
    if (l == r) {
        return max(0, min(M, x + tr[i].d));
    } else {
        int mid = (l + r) / 2;

        data &rCh = tr[i * 2 + 1];
        if (rCh.maxD - rCh.minD >= M) {
            // double bounded which means that input doesn't matter
            return query(i * 2 + 1, mid + 1, r, 0);
        } else {
            int y = query(i * 2, l, mid, x);

            int z;
            if (y + rCh.maxD >= M) {
                assert(y + rCh.minD >= 0); // only hits upper bound
                z = M + min(rCh.hiB, y - M + rCh.d);
            } else {
                z = max(rCh.lowB, y + rCh.d);
            }
            return z;
        }
    }
}

void upd(int i, int l, int r, int x) {
    if (l == r) {
        tr[i] = type[l] ? PLUS : MINUS;
    } else {
        int mid = (l + r) / 2;
        if (x <= mid) {
            upd(i * 2, l, mid, x);
        } else {
            upd(i * 2 + 1, mid + 1, r, x);
        }
        tr[i] = tr[i * 2] + tr[i * 2 + 1];
    }
}

int main() {
    int N, Q;
    cin >> N >> Q;

    string line;
    cin >> line;

    for (int i = 0; i < N; i++) {
        type[i] = line[i] == '+';
    }

    build(1, 0, N - 1);

    for (int cQ = 0; cQ < Q; cQ++) {
        char op;
        cin >> op;
        if (op == 'u') {
            int i;
            char c;
            cin >> i >> c;
            i--;
            type[i] = c == '+';
            upd(1, 0, N - 1, i);
        } else {
            int i;
            cin >> M >> i;
            int res = query(1, 0, N - 1, i);
            cout << res << '\n';
        }
    }
    cout << flush;
}