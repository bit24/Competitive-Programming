#pragma GCC optimize ("O3")

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

vector<pl> pts;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    freopen("connect.in", "r", stdin);
    freopen("connect.out", "w", stdout);

    auto t1 = chrono::system_clock::now();

    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        pts.clear();

        int N, H, V;
        cin >> N >> H >> V;

        ll x1, x2, ax, bx, cx, dx;
        cin >> x1 >> x2 >> ax >> bx >> cx >> dx;

        ll y1, y2, ay, by, cy, dy;
        cin >> y1 >> y2 >> ay >> by >> cy >> dy;

        if (H + V < N) {
            cout << "Case #" << cT << ": -1" << endl;
            continue;
        }

        pts.pb({x1, y1});
        pts.pb({x2, y2});

        for (int i = 2; i < N; i++) {
            pts.pb({(ax * pts[i - 2].f + bx * pts[i - 1].f + cx) % dx + 1,
                    (ay * pts[i - 2].s + by * pts[i - 1].s + cy) % dy + 1});
        }

        sort(pts.begin(), pts.end());

        vector<ll> maxY(N + 1);
        maxY[N] = 0;

        for (int i = N - 1; i >= 0; i--) {
            maxY[i] = max(maxY[i + 1], pts[i].s);
        }

        ll ans = 1e16;

        if (V >= N) {
            ans = min(ans, maxY[0]);
        }

        priority_queue<ll, vl, greater<ll>> lftY; // stores y-vals of stuff to left with least on top
        for (int i = 0; i < N; i++) {
            lftY.push(pts[i].s);
            if (lftY.size() > H + 1) { // H + 1 largest element will be largest remaining vertical
                lftY.pop();
            }

            ll cMaxX = pts[i].f;
            ll cMaxY = maxY[i + 1];
            if (lftY.size() > H) {
                cMaxY = max(cMaxY, lftY.top());
            }

            if (N - (i + 1) <= V) {
                ans = min(ans, cMaxX + cMaxY);
            }
        }
        cout << "Case #" << cT << ": ";
        cout << ans << "\n";
    }
    cout << flush;

    auto t2 = chrono::system_clock::now();
    // cout << "time: " << chrono::duration_cast<chrono::milliseconds>(t2 - t1).count() << endl;
}