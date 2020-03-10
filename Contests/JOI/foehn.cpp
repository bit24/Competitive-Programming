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

const int MAXN = 2e5 + 10;

ll d[MAXN];
int N, Q;
ll S, T;

ll cost = 0;

void update(ll d, int x) {
    if (d > 0) {
        cost -= d * S * x;
    } else {
        cost += abs(d) * T * x;
    }
}

int main() {
    cin >> N >> Q >> S >> T;

    int last = 0;
    cin >> last;
    assert(last == 0);
    for (int i = 0; i < N; i++) {
        int c;
        cin >> c;
        d[i] = c - last;
        last = c;
    }

    for (int i = 0; i < N; i++) {
        update(d[i], 1);
    }
//    cout << cost << endl;

    for (int i = 0; i < Q; i++) {
        int l, r, x;
        cin >> l >> r >> x;

        l--;
        update(d[l], -1);
        d[l] += x;
        update(d[l], 1);

        if (r < N) {
            update(d[r], -1);
            d[r] -= x;
            update(d[r], 1);
        }

//        for (int i = 0; i < N; i++) {
//            cout << d[i] << " ";
//        }
//        cout << endl;

        cout << cost << endl;
    }
}