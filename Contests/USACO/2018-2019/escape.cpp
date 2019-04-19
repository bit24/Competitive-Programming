#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

typedef pair<ll, ll> pcc;

#define pb push_back
#define f first
#define s second

const ll MOD = 1000000007;
const ll INF = 1e18;
int N, K;

map<vi, int> sI;
vi st[203];

pcc cDP[6][203];
pcc nDP[6][203];
int trans[6][203][4];

ll cH[30005][6];
ll cV[6][30005];

vi cS;

void print(pcc a) {
    cout << "{cost: " << a.f << ", #: " << a.s << "}\n";
}

void print(vi a) {
    cout << "[" << a[0];
    for (int i = 1; i < a.size(); i++) {
        cout << ", " << a[i];
    }
    cout << "]\n";
}

vi norm(vi a) {
    int m[7]; //one extra for new items
    fill_n(m, 7, -1);

    int n = 0;
    for (int i = 0; i < K; i++) {
        if (m[a[i]] == -1) {
            m[a[i]] = n++;
        }
        a[i] = m[a[i]];
    }
    return a;
}

vi rep(vi a, int b, int c) {
    for (int i = 0; i < a.size(); i++) {
        if (a[i] == b) {
            a[i] = c;
        }
    }
    return a;
}

bool cnct(vi a, int b) {
    int cnt = 0;
    for (int i = 0; i < a.size(); i++) {
        if (a[i] == b) {
            cnt++;
        }
    }
    return cnt > 1;
}

void gS(int i, int mx) {
    if (i == K) {
        st[sI.size()] = cS;
        sI.insert({cS, sI.size()});
    } else {
        for (int j = 0; j < mx; j++) {
            cS[i] = j;
            gS(i + 1, mx);
        }
        cS[i] = mx;
        gS(i + 1, mx + 1);
    }
}

pcc operator+(pcc a, ll b) {
    return {a.f + b, a.s};
}

pcc operator+(pcc a, pcc b) {
    if (a.f != b.f) {
        return min(a, b);
    }
    return {a.f, (a.s + b.s) % MOD};
}

void precalcTrans(int j, int cSI) {
    if (j == K - 1) { // new row
        // case: unlock
        trans[j][cSI][0] = cSI;

        // case: lock
        vi nS = st[cSI];
        if (cnct(nS, nS[0])) { // make sure it's still connected
            nS[0] = 6; // new number, nobody should have 6
            nS = norm(nS);
            trans[j][cSI][1] = sI[nS];
        }
        return;
    }

    int cJ = j + 1;
    // case: unlock, unlock (left, up)
    vi nS = st[cSI];
    nS = rep(nS, nS[cJ - 1], nS[cJ]);
    nS = norm(nS);
    trans[j][cSI][0] = sI[nS];

    // case: unlock, lock
    nS = st[cSI];
    if (cnct(nS, nS[cJ])) { // make sure it's still connected
        nS[cJ] = nS[cJ - 1];
        nS = norm(nS);
        trans[j][cSI][1] = sI[nS];
    }

    // case: lock, unlock
    trans[j][cSI][2] = cSI;

    // case: lock, lock
    nS = st[cSI];
    if (cnct(nS, nS[cJ])) {
        nS[cJ] = 6;
        nS = norm(nS);
        trans[j][cSI][3] = sI[nS];
    }
}

void pushTrans(int i, int j, int cSI) { // i > 0

    if (cDP[j][cSI].f >= INF) {
        return;
    }

    // cout << "(" << i << ", " << j << ")\n";
    // print(st[cSI]);
    // print(cDP[j][cSI]);

    if (j == K - 1) {

        // case: unlock
        int nSI = trans[j][cSI][0];
        nDP[0][nSI] = nDP[0][nSI] + (cDP[j][cSI] + cV[0][i]);
        // print(st[nSI]);
        // print(dp[i + 1][0][nSI]);

        // case: lock
        nSI = trans[j][cSI][1];
        if (nSI != -1) {
            nDP[0][nSI] = nDP[0][nSI] + cDP[j][cSI];
            // print(st[nSI]);
            // print(dp[i + 1][0][nSI]);
        }
        // cout << '\n';
        return;
    }

    int cJ = j + 1;
    // case: unlock, unlock (left, up)

    int nSI = trans[j][cSI][0];
    cDP[j + 1][nSI] = cDP[j + 1][nSI] + (cDP[j][cSI] + cH[i][cJ - 1] + cV[cJ][i - 1]);
    // print(st[nSI]);
    // print(cDP[j + 1][nSI]);
    // print((cDP[j][cSI] + cH[i][cJ - 1] + cV[cJ][i - 1]));
    // cout << "********\n";

    // case: unlock, lock
    nSI = trans[j][cSI][1];
    if (nSI != -1) {
        cDP[j + 1][nSI] = cDP[j + 1][nSI] + (cDP[j][cSI] + cH[i][cJ - 1]);
        // print(st[nSI]);
        //print(cDP[j + 1][nSI]);
    }

    // case: lock, unlock
    nSI = trans[j][cSI][2];
    cDP[j + 1][nSI] = cDP[j + 1][nSI] + (cDP[j][cSI] + cV[cJ][i - 1]);
    // print(st[nSI]);
    // print(cDP[j + 1][nSI]);

    // case: lock, lock
    nSI = trans[j][cSI][3];
    if (nSI != -1) {
        cDP[j + 1][nSI] = cDP[j + 1][nSI] + cDP[j][cSI];
        // print(st[nSI]);
        // print(cDP[j + 1][nSI]);
    }
    // cout << '\n';
}


int main1() {
    freopen("escape.in", "r", stdin);
    freopen("escape.out", "w", stdout);
    cout << 10 << endl;
}

int main() {
    freopen("escape.in", "r", stdin);
    freopen("escape.out", "w", stdout);
    cin >> N >> K;
    cS.resize(K);
    gS(0, 0);

    for (int j = 0; j < 6; j++) {
        for (int cSI = 0; cSI < 203; cSI++) {
            for (int x = 0; x < 4; x++) {
                trans[j][cSI][x] = -1;
            }
        }
    }

    for (int j = 0; j < 6; j++) {
        for (int cSI = 0; cSI < 203; cSI++) {
            cDP[j][cSI] = {INF, 0};
            nDP[j][cSI] = {INF, 0};
        }
    }

    for (int j = 0; j < K; j++) {
        for (int cSI = 0; cSI < sI.size(); cSI++) {
            precalcTrans(j, cSI);
        }
    }

    for (int i = 1; i <= N; i++) {
        for (int j = 0; j < K - 1; j++) {
            cin >> cH[i][j];
        }
    }

    for (int j = 0; j < K; j++) {
        for (int i = 1; i < N; i++) {
            cin >> cV[j][i];
        }
    }
    for (int j = 0; j < K; j++) {
        cV[j][0] = INF;
    }
    cV[0][0] = 0;

    nDP[0][0] = {0, 1};

    // cout << "read" << endl;

    for (int i = 1; i <= N; i++) {
        for (int j = 0; j < K; j++) {
            for (int cSI = 0; cSI < sI.size(); cSI++) {
                cDP[j][cSI] = nDP[j][cSI];
                nDP[j][cSI] = {INF, 0};
            }
        }

        for (int j = 0; j < K; j++) {
            for (int cSI = 0; cSI < sI.size(); cSI++) {
                pushTrans(i, j, cSI);
            }
        }
    }

    // cout << dp[N][K - 1][0].f << endl;
    cout << cDP[K - 1][0].s << endl;
    return 0;
}