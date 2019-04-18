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

int k[] = {'A' - 'A', 'C' - 'A', 'T' - 'A', 'G' - 'A'};

int dist(int a, int b) {
    return min((b - a + 26) % 26, (a - b + 26) % 26);
}

int main() {
    int N;
    string str;
    cin >> N >> str;

    // cout << k[0] << endl;

    int ans = 1e9;
    for (int i = 0; i + 3 < N; i++) {
        int cost = 0;
        for (int j = 0; j < 4; j++) {
            cost += dist(str[i + j] - 'A', k[j]);
        }
        ans = min(ans, cost);
    }
    cout << ans << endl;
}