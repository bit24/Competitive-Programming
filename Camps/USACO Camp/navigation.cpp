#include "grader.h"

using namespace std;

typedef vector<int> vi;

#define pb push_back
#define f first
#define s second

const int MAXN = 1e3 + 5;
const int MAXK = 10;

string encode(int x) {
    assert(x < 1024);
    string ret;
    for (int i = MAXK - 1; i >= 0; i--) {
        ret += (x & (1 << i)) ? '1' : '0';
    }
    assert(ret.size() == 10);
    return ret;
}

int decode(string s) {
    assert(s.size() == 10);
    int ret = 0;

    for (int i = 0; i < MAXK; i++) {
        ret <<= 1;
        ret += s[i] == '1' ? 1 : 0;
    }
    return ret;
}

string encodeA(vi &x) {
    string ret;
    for (int i : x) {
        ret += encode(i);
    }
    assert(ret.size() == 300);
    return ret;
}

vi decodeA(string s) {
    assert(s.size() == 300);

    vi ret;
    for (int i = 0; i < 300; i += 10) {
        ret.pb(decode(s.substr(i, 10)));
    }

    return ret;
}


vi aL[MAXN];

int ct[MAXN][MAXK];
int p[MAXN][MAXK];
int ac[MAXN][MAXK];

int vis[MAXN];
int cnt[MAXN];

void fCnt(int cV, int pV) {
    cnt[cV] = 1;
    for (int aV :aL[cV]) {
        if (!vis[aV] && aV != pV) {
            fCnt(aV, cV);
            cnt[cV] += cnt[aV];
        }
    }
//    cout << "cnt " << cV << " " << cnt[cV] << endl;
}

int CSZ;

int fCentroid(int cV, int pV) {
    for (int aV : aL[cV]) {
        if (!vis[aV] && aV != pV && cnt[aV] > CSZ / 2) {
            return fCentroid(aV, cV);
        }
    }
    return cV;
}

void calculate(int cV, int pV, int k, int cent, int cAC) {
    ct[cV][k] = cent;
    p[cV][k] = pV;
    ac[cV][k] = cAC;

    for (int aV : aL[cV]) {
        if (!vis[aV] && aV != pV) {
            calculate(aV, cV, k, cent, cAC);
        }
    }
}

void decompose(int k, int v) {
    fCnt(v, -1);
    CSZ = cnt[v];
    int cent = fCentroid(v, -1);
//    cout << "cent" << cent;

    for (int aV : aL[cent]) {
        if (!vis[aV]) {
            calculate(aV, cent, k, cent, aV);
        }
    }
    ct[cent][k] = cent;

    vis[cent] = true;

    for (int aV : aL[cent]) {
        if (!vis[aV]) {
            decompose(k + 1, aV);
        }
    }
}

vector<string> init_farms(int N, vector<pair<int, int>> edges, int max_b) {
    for (auto x : edges) {
        x.f++, x.s++;
        aL[x.f].pb(x.s);
        aL[x.s].pb(x.f);
    }

    memset(ct, 0, sizeof(ct));
    memset(p, 0, sizeof(p));
    memset(ac, 0, sizeof(ac));
    memset(vis, 0, sizeof(vis));

    decompose(0, 1);

    vector<string> ret;
    for (int cV = 1; cV <= N; cV++) {
        vi inf;
        for (int i = 0; i < MAXK; i++) {
            inf.pb(ct[cV][i]);
        }
        for (int i = 0; i < MAXK; i++) {
            inf.pb(p[cV][i]);
        }
        for (int i = 0; i < MAXK; i++) {
            inf.pb(ac[cV][i]);
        }
//        cout << cV << endl;
//        for (int i: inf) {
//            cout << i << " ";
//        }
//        cout << endl;

        assert(inf.size() == 3 * MAXK);
        string s = encodeA(inf);
        ret.pb(s);
    }
    return ret;
}

int next_farm(int a, int b, string signa, string signb, int max_b) {
    a++, b++;
    vi vA = decodeA(signa);
    vi vB = decodeA(signb);

    // find lowest shared centroid
    int lCI = 0;
    while (lCI + 1 < MAXK && vA[lCI + 1] == vB[lCI + 1]) {
        lCI++;
    }

    assert(vA[lCI] != 0);

    if (vA[lCI] == a) { // a is the centroid
        int cAC = ac[b][lCI];
        return cAC - 1;
    }

    assert(p[a][lCI] != 0);
    return p[a][lCI] - 1;
}