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
    int N;
    cin >> N;
    vi a(N);
    int s;

    for (int i = 0; i < N; i++) {
        cin >> a[i];
        s += a[i];
    }

    int sz = a[0];

    vi ans;
    ans.pb(0);
    for (int i = 1; i < N; i++) {
        if (a[i] * 2 <= a[0]) {
            sz += a[i];
            ans.pb(i);
        }
    }

    if (sz > s / 2) {
        cout << ans.size() << endl;
        for (int i : ans) {
            cout << (i + 1) << " ";
        }
        cout << endl;
    } else {
        cout << 0 << endl;
    }
}