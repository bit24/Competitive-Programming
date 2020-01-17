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

const int MAXN = 1000;
const int INF = 1e9;

int unuse[MAXN][MAXN];
int reach[MAXN][MAXN];

const int dI[] = {-1, 0, 1, 0};
const int dJ[] = {0, -1, 0, 1};

int N, S;
pi st, goal;

bool reachGoal(int delay) {
    if (delay == -1) return true;
    for (int i = 0; i <= N + 1; i++) {
        for (int j = 0; j <= N + 1; j++) {
            reach[i][j] = INF;
        }
    }

    reach[st.f][st.s] = delay * S;
    queue<pi> q;
    q.push(st);

    while (q.size()) {
        pi cur = q.front();
        q.pop();

        if (reach[cur.f][cur.s] < unuse[cur.f][cur.s] * S) {
            for (int d = 0; d < 4; d++) {
                int nI = cur.f + dI[d];
                int nJ = cur.s + dJ[d];

                if (reach[nI][nJ] == INF) {
                    reach[nI][nJ] = reach[cur.f][cur.s] + 1;
                    q.push({nI, nJ});
                }
            }
        }
    }

    return reach[goal.f][goal.s] != INF;
}

int binSearch() {
    int low = -1;
    int high = 800 * 800;

    while (low != high) {
        int mid = (low + high + 1) / 2;

        if (reachGoal(mid)) {
            low = mid;
        } else {
            high = mid - 1;
        }
    }
    return low;
}

int main() {
    cin >> N >> S;

    for (int i = 0; i <= N + 1; i++) {
        for (int j = 0; j <= N + 1; j++) {
            unuse[i][j] = -1;
        }
    }

    for (int i = 0; i <= N + 1; i++) {
        unuse[0][i] = 0;
        unuse[N + 1][i] = 0;
        unuse[i][0] = 0;
        unuse[i][N + 1] = 0;
    }

    queue<pi> q;
    for (int i = 1; i <= N; i++) {
        string line;
        cin >> line;
        for (int j = 1; j <= N; j++) {
            char cur = line[j - 1];
            if (cur == 'T') {
                unuse[i][j] = 0;
            } else if (cur == 'H') {
                unuse[i][j] = 0;
                q.push({i, j});
            } else if (cur == 'M') {
                st = {i, j};
            } else if (cur == 'D') {
                goal = {i, j};
                unuse[i][j] = INF;
            } else {
                assert(cur == 'G');
            }
        }
    }

    while (!q.empty()) {
        pi cur = q.front();
        q.pop();

        for (int d = 0; d < 4; d++) {
            int nI = cur.f + dI[d];
            int nJ = cur.s + dJ[d];
            if (unuse[nI][nJ] == -1) {
                unuse[nI][nJ] = unuse[cur.f][cur.s] + 1;
                q.push({nI, nJ});
            }
        }
    }
    int ans = binSearch();
    cout << ans << endl;
}