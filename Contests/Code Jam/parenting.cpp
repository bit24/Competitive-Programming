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

vector<pair<pi, int>> l;

int avail[2];

int assign[2000];

int main() {
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        int N;
        cin >> N;

        l.clear();

        for (int i = 0; i < N; i++) {
            pi x;
            cin >> x.f >> x.s;
            l.pb({x, i});
        }

        sort(l.begin(), l.end());

        avail[0] = -1, avail[1] = -1;

        bool impos = false;

        for (int i = 0; i < N; i++) {
            if (avail[0] <= l[i].f.f) {
                avail[0] = l[i].f.s;
                assign[l[i].s] = 0;
            } else if (avail[1] <= l[i].f.f) {
                avail[1] = l[i].f.s;
                assign[l[i].s] = 1;
            } else {
                impos = true;
                break;
            }
        }

        cout << "Case #" << cT << ": ";

        if (impos) {
            cout << "IMPOSSIBLE" << endl;
        } else {
            for (int i = 0; i < N; i++) {
                cout << (assign[i] ? 'C' : 'J');
            }
            cout << endl;
        }
    }

}