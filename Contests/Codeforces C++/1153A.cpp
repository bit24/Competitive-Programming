#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

int main() {
    ios_base::sync_with_stdio(false);
    int N, T;
    cin >> N >> T;

    int w = 1e9;
    int b = -1;
    for (int i = 1; i <= N; i++) {
        int s, d;
        cin >> s >> d;
        if (s >= T) {
            if (w > (s - T)) {
                w = (s - T);
                b = i;
            }
        } else {
            int dif = T - s;
            s += ((dif + d - 1) / d) * d;
            if (w > (s - T)) {
                w = (s - T);
                b = i;
            }
        }
    }
    cout << b << endl;
}