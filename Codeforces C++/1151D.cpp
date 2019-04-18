#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef pair<int, int> pi;

#define pb push_back
#define f first
#define s second

bool cmp(pi a, pi b) {
    return -a.f + a.s < -b.f + b.s;
}

int main() {
    int N;
    cin >> N;

    vector<pi> list;

    for (int i = 0; i < N; i++) {
        int a, b;
        cin >> a >> b;
        list.pb({a, b});
    }
    sort(list.begin(), list.end(), cmp);

    ll cost = 0;
    for (int i = 0; i < N; i++) {
        pi c = list[i];
        //cout << c.f << " " << c.s << endl;
        //cout << 1LL * c.f * i + 1LL * c.s * (N - i - 1) << endl;
        cost += 1LL * c.f * i + 1LL * c.s * (N - i - 1);
    }
    cout << cost << endl;
}