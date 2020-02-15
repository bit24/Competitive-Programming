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
    int N, K;
    cin >> N >> K;
    vi times(N);
    for (int i = 0; i < N; i++) {
        cin >> times[i];
    }
    sort(times.begin(), times.end());

    int sum = times[times.size() - 1] - times[0] + 1;

    vi spaces;
    for (int i = 0; i + 1 < N; i++) {
        int space = times[i + 1] - times[i] - 1;
        if (space > 0) {
            spaces.pb(space);
        }
    }
    sort(spaces.begin(), spaces.end());
    reverse(spaces.begin(), spaces.end());

    for (int i = 0; i < spaces.size() && i < K - 1; i++) {
        sum -= spaces[i];
    }
    cout << sum << endl;
}