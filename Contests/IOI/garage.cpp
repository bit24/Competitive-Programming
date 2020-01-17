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

int rate[200];
int filled[200];
int weight[3000];
int loc[3000];

int main() {
    int N, M;
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        cin >> rate[i];
    }

    for (int i = 0; i < M; i++) {
        cin >> weight[i];
    }

    memset(filled, 0, sizeof(filled));

    queue<int> q;

    ll tCost = 0;
    for (int i = 0; i < 2 * M; i++) {
        int x;
        cin >> x;
        if (x > 0) {
            x--;

            bool fSpace = false;
            for (int j = 0; j < N; j++) {
                if (!filled[j]) {
                    filled[j] = true;
                    loc[x] = j;

                    fSpace = true;
                    break;
                }
            }
            if (!fSpace) {
                q.push(x);
            }
        } else {
            x = -x;
            x--;

            int cLoc = loc[x];
            filled[cLoc] = false;

            tCost += (ll) rate[cLoc] * weight[x];

            if (q.size()) {
                int nxt = q.front();
                q.pop();

                filled[cLoc] = true;
                loc[nxt] = cLoc;
            }
        }
    }

    cout << tCost << endl;
}