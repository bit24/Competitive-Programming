#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

int main() {
    ll a, b, c;
    cin >> a >> b >> c;
    ll ans = 2 * c;
    if (a >= b) {
        ans += min(2 * b + 1, a + b);
    } else {
        ans += min(2 * a + 1, a + b);
    }
    cout << ans << endl;
}