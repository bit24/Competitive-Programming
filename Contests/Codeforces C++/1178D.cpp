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

vector<bool> isP(2001, true);
vi ps;

int main() {
    for (int i = 2; i <= 2000; i++) {
        if (isP[i]) {
            for (int m = 2 * i; m <= 2000; m += i) {
                isP[m] = false;
            }
            ps.pb(i);
        }
    }

    int N;
    cin >> N;
    int nxtP = N;
    while (!isP[nxtP]) {
        nxtP++;
    }

    vector<string> edges;
    for (int i = 1; i < N; i++) {
        edges.pb(to_string(i) + " " + to_string(i + 1));
    }
    edges.pb(to_string(N) + " 1");

    int diff = nxtP - N;
    int opp = N / 2;
    for (int i = 0; i < diff; i++) {
        edges.pb(to_string(i + 1) + " " + to_string(i + opp + 1));
    }
    cout << edges.size() << "\n";

    for (string s : edges) {
        cout << s << "\n";
    }
    cout << flush;
}