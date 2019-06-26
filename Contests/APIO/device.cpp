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

ll N, A, B;

int main() {
    cin >> N >> A >> B;
    ll gcd = __gcd(A, B + 1);
    ll AP = A / gcd;

    if (AP > (ll) 1e18 / B) {
        // period is so big it won't matter
        ll ans = 0;
        for (int i = 0; i < N; i++) {
            ll l, r;
            cin >> l >> r;
            ans += (r - l + 1);
        }
        cout << ans << endl;
        return 0;
    }

    ll P = AP * B;

    vector<pl> delts;

    for (int i = 0; i < N; i++) {
        ll l, r;
        cin >> l >> r;
        if (r - l + 1 >= P) {
            cout << P << endl;
            return 0;
        }

        l %= P;
        r %= P;

        if (l <= r) {
            delts.pb({l, +1});
            delts.pb({r + 1, -1});
        } else {
            delts.pb({l, +1});
            delts.pb({P, -1});
            delts.pb({0, +1});
            delts.pb({r + 1, -1});
        }
    }

    sort(delts.begin(), delts.end());

    int cAct = 0;
    ll ans = 0;
    for (int i = 0; i < delts.size(); i++) {
        cAct += delts[i].s;
        if (i + 1 < delts.size()) {
            ll dist = delts[i + 1].f - delts[i].f;
            if (cAct > 0) {
                ans += dist;
            }
        }
    }
    cout << ans << endl;
}
