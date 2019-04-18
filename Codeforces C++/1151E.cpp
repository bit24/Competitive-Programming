#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

int a[200000];
int N;

ll calc(int a, int b) {
    ll sum = 0;
    // cout << (N - b + 1) * (b - a) << endl;
    sum += 1LL * (N - b + 1) * (b - a);
    // cout << ((a) * (b - a)) << endl;
    sum += 1LL * (a) * (b - a);
    return sum;
}

int main() {
    cin >> N;

    ll ans = 0;
    for (int i = 1; i <= N; i++) {
        cin >> a[i];
    }
    a[0] = 0;
    a[N + 1] = 0;

    for (int i = 0; i <= N; i++) {
        ans += calc(min(a[i], a[i + 1]), max(a[i], a[i + 1]));
    }

    cout << ans / 2 << endl;
}