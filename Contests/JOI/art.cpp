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
    vector<pl> items(N);
    for (int i = 0; i < N; i++) {
        cin >> items[i].f >> items[i].s;
    }

    sort(items.begin(), items.end());

    ll preB = 0;
    ll maxFirst = -1e15;

    ll ans = -1e15;

    for (int i = 0; i < N; i++) {
        maxFirst = max(maxFirst, -preB + items[i].f);

        ll second = preB + items[i].s - items[i].f;

        ans = max(ans, maxFirst + second);

        preB += items[i].s;
    }
    cout << ans << endl;
}