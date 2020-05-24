#include <bits/stdc++.h>

using namespace std;

typedef vector<int> vi;

#define pb push_back

const int MAXN = 1e5 + 10;

vi aL[MAXN];

int adjI[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int N, M;
    cin >> N >> M;

    for (int i = 0; i < M; i++) {
        int a, b;
        cin >> a >> b;
        a--, b--;
        aL[a].pb(b);
        aL[b].pb(a);
    }

    memset(adjI, 0, sizeof(adjI));

    int ans = 0;
    for (int i = 0; i < N; i++) {
        for (int j :aL[i]) {
            adjI[j] = true;
        }

        for (int j : aL[i]) {
            if (aL[i].size() > aL[j].size() || (aL[i].size() == aL[j].size() && i >= j)) {
                for (int k : aL[j]) {
                    if (k <= i || k <= j) {
                        continue;
                    }

                    if (adjI[k]) {
                        ans++;
                    }
                }
            }
        }

        for (int j :aL[i]) {
            adjI[j] = false;
        }
    }
    cout << ans << endl;
}