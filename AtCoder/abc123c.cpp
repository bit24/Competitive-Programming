#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

ll a[5];

int main() {
    ll N;
    cin >> N;
    ll cM = 1e16;
    for (int i = 0; i < 5; i++) {
        cin >> a[i];
        cM = min(cM, a[i]);
    }

    cout << (N + cM - 1) / cM + 4 << endl;
}