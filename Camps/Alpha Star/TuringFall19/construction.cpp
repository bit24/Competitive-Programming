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

int main() {
    int N, M, C;
    cin >> N >> M >> C;

    vector<pi> pts;
    for (int i = 0; i < N; i++) {
        int x, r;
        cin >> x >> r;
        pts.pb({x, r + x * C});
    }
    sort(pts.begin(), pts.end());

    multiset<int> tRem;

    ll sum = 0;
    ll ans = 0;
    for (int i = 0; i < N; i++) {
        sum += pts[i].s;
        tRem.insert(pts[i].s);

        while (*tRem.begin() <= pts[i].f * C) {
            sum -= *tRem.begin();
            tRem.erase(tRem.begin());
        }

        ans = max(ans, sum - (ll) pts[i].f * C * (ll) tRem.size() - M * pts[i].f);
    }
    cout << ans << endl;
}