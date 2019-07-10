#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

int main() {
    int N, M;
    cin >> N >> M;
    vector<pi> pictures;
    for (int i = 0; i < N; i++) {
        int s, v;
        cin >> s >> v;
        pictures.pb({v, s});
    }

    vi frames;
    for (int i = 0; i < M; i++) {
        int s;
        cin >> s;
        frames.pb(s);
    }

    sort(pictures.begin(), pictures.end());
    sort(frames.begin(), frames.end());

    reverse(pictures.begin(), pictures.end());
    reverse(frames.begin(), frames.end());

    int nP = 0, ans = 0;
    for (int i = 0; i < frames.size(); i++) {
        while (nP < pictures.size() && pictures[nP].s > frames[i]) {
            nP++;
        }
        if (nP == pictures.size()) {
            break;
        }
        ans++;
        nP++;
    }
    cout << ans << endl;
}