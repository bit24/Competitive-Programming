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

const ll MOD = 998244353;

int main() {
    int w, h;
    cin >> w >> h;
    ll ans = 4;
    for (int i = 0; i < w + h - 2; i++) {
        ans = ans * 2 % MOD;
    }
    cout << ans << endl;
}