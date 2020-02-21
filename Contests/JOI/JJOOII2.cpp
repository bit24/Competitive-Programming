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

const int MAXN = 2e5 + 10;

int N, K;
string txt;

int nums[MAXN];

vi pos[3];
int posI[MAXN];

int nxt[MAXN];

int toInt(char inp) {
    if (inp == 'J') {
        return 0;
    } else if (inp == 'O') {
        return 1;
    } else {
        assert(inp == 'I');
        return 2;
    }
}

int main() {
    cin >> N >> K;
    cin >> txt;

    for (int i = 0; i < N; i++) {
        nums[i] = toInt(txt[i]);
        posI[i] = pos[nums[i]].size();
        pos[nums[i]].pb(i);
    }

    int last[] = {-1, -1, -1};

    fill(nxt, nxt + MAXN, -1);

    for (int i = N - 1; i >= 0; i--) {
        if (nums[i] < 2) {
            nxt[i] = last[nums[i] + 1];
        }
        last[nums[i]] = i;
    }

    int ans = 1e9;
    for (int fJ = 0; fJ < N; fJ++) {
        if (nums[fJ] != 0) {
            continue;
        }

        if (posI[fJ] + K - 1 >= pos[0].size()) {
            continue;
        }
        int lJ = pos[0][posI[fJ] + K - 1];
        int fO = nxt[lJ];

        if (fO == -1) {
            continue;
        }

        if (posI[fO] + K - 1 >= pos[1].size()) {
            continue;
        }
        int lO = pos[1][posI[fO] + K - 1];
        int fI = nxt[lO];

        if (fI == -1) {
            continue;
        }

        if (posI[fI] + K - 1 >= pos[2].size()) {
            continue;
        }
        int lI = pos[2][posI[fI] + K - 1];

        ans = min(ans, lI - fJ + 1 - 3 * K);
    }
    if (ans >= 1e9) {
        ans = -1;
    }
    cout << ans << endl;
}