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

const int MAXN = 5e5 + 10;
ll d[MAXN], c[MAXN];

int N, Q;

ll fPos(int i, ll t) {
    ll step = t / c[i];
    return -i + step * c[i];
}

// returns number of people with position >= lBound
ll searchAfter(ll lBound, ll t) {
    int low = 0;
    int hi = N + 1;

    while (low != hi) {
        int mid = (low + hi + 1) / 2;

        if (fPos(mid - 1, t) >= lBound) { // implies that 0...mid-2 works too as earlier is greater
            assert(low != mid);
            low = mid;
        } else {
            hi = mid - 1;
        }
    }
    return low;
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N >> Q;

    for (int i = 1; i <= N; i++) {
        cin >> d[i];
    }
    c[0] = 1;

    for (int i = 1; i <= N; i++) {
        ll step = (d[i] + c[i - 1] - 1) / c[i - 1];
        c[i] = step * c[i - 1];
    }

    for (int q = 0; q < Q; q++) {
        ll t, l, r;
        cin >> t >> l >> r;

        ll lCnt = searchAfter(l, t);
        ll rCnt = searchAfter(r + 1, t);

        ll cnt = lCnt - rCnt;
        cout << cnt << endl;
    }
}