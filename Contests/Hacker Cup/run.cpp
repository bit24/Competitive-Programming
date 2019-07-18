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
    freopen("run.in", "r", stdin);
    freopen("run.out", "w", stdout);
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        int N, M, K;
        cin >> N >> M >> K;
        int x, y;
        cin >> x >> y;

        if (K == 1) {
            int a, b;
            cin >> a >> b;
            cout << "Case #" << cT << ": N" << endl;
        } else {
            int a, b, c, d;
            cin >> a >> b >> c >> d;
            int dist1 = abs(a - x) + abs(b - y);
            int dist2 = abs(c - x) + abs(d - y);
            if (dist1 % 2 == 0 && dist2 % 2 == 0) {
                cout << "Case #" << cT << ": Y" << endl;
            } else {
                cout << "Case #" << cT << ": N" << endl;
            }
        }
    }
}