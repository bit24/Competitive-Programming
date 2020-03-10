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

const int MAXN = 2000 + 10;

int H, W;

int grid[MAXN][MAXN];
int taken[MAXN][MAXN];

inline int lin(int i, int j) {
    return i * W + j;
}

inline pi dLin(int x) {
    return {x / W, x % W};
}

inline bool check(int i, int j) {
    if (i - 1 >= 0 && !taken[i - 1][j]) {
        return false;
    }
    if (j - 1 >= 0 && !taken[i][j - 1]) {
        return false;
    }
    return true;
}

int part1[MAXN * MAXN];
vi rem[MAXN * MAXN];

// compute min cost assuming TL-BR groups and that TL group is smaller
int compute() {
    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            taken[i][j] = 0;
        }
    }
    vi nums;

    int cur = grid[0][0];

    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            if (grid[i][j] >= cur) {
                nums.pb(grid[i][j]);
            }
        }
    }

    sort(nums.begin(), nums.end());
    nums.erase(unique(nums.begin(), nums.end()), nums.end());

    priority_queue<pi, vector<pi>, greater<pi>> q;

    q.push({cur, lin(0, 0)});

    int cMin = cur;
    int cMax = cur;

    int ans = 1e9;

    int nStep = 0;

    for (int uBI = 0; uBI < nums.size(); uBI++) {
        int uB = nums[uBI];

        rem[nStep].clear();

        while (q.size()) {
            pi nxt = q.top();
            if (nxt.f > uB) {
                break;
            }
            q.pop();

            int x = nxt.s;
            pi coor = dLin(x);

            int cV = grid[coor.f][coor.s];

            rem[nStep].pb(cV);

            cMin = min(cMin, cV);
            cMax = max(cMax, cV);

            taken[coor.f][coor.s] = true;

            if (coor.f + 1 < H && check(coor.f + 1, coor.s)) {
                q.push({grid[coor.f + 1][coor.s], lin(coor.f + 1, coor.s)});
            }
            if (coor.s + 1 < W && check(coor.f, coor.s + 1)) {
                q.push({grid[coor.f][coor.s + 1], lin(coor.f, coor.s + 1)});
            }
        }

        int cost = cMax - cMin;
        part1[nStep++] = cost;
    }

    int min2 = 1e9;
    int max2 = 0;

    for (int cStep = nStep - 1; cStep >= 0; cStep--) {
        if (cStep != nStep - 1) {
            ans = min(ans, max(max2 - min2, part1[cStep]));
        }
        for(int x : rem[cStep]){
            min2 = min(min2, x);
            max2 = max(max2, x);
        }
    }

    return ans;
}

void flipMag() {
    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            grid[i][j] = 1000000001 - grid[i][j];
        }
    }
}

void flip() {
    for (int j = 0; j < W; j++) {
        for (int i = 0; i < H / 2; i++) {
            int tmp = grid[i][j];
            grid[i][j] = grid[H - 1 - i][j];
            grid[H - 1 - i][j] = tmp;
        }
    }
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> H >> W;

    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            cin >> grid[i][j];
        }
    }

    int ans = compute();

    flipMag();
    int ans2 = compute();

    flip();
    int ans3 = compute();

    flipMag();
    int ans4 = compute();

    int fAns = min(min(ans, ans2), min(ans3, ans4));
    cout << fAns << endl;
}