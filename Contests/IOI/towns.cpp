#include <bits/stdc++.h>
#include "towns.h"

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

namespace debug {
    const int DEBUG = true;

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x);

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x);

    template<class T>
    void pr(const vector<T> &x);

    template<class T>
    void pr(const set<T> &x);

    template<class T1, class T2>
    void pr(const map<T1, T2> &x);

    template<class T>
    void pr(const T &x) { if (DEBUG) cout << x; }

    template<class T, class... Ts>
    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }

    template<class T>
    void prIn(const T &x) {
        pr("{");
        bool fst = 1;
        for (auto &a : x) {
            pr(fst ? "" : ", ", a), fst = 0;
        }
        pr("}");
    }

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x) { prIn(x); }

    template<class T>
    void pr(const vector<T> &x) { prIn(x); }

    template<class T>
    void pr(const set<T> &x) { prIn(x); }

    template<class T1, class T2>
    void pr(const map<T1, T2> &x) { prIn(x); }

    void ps() { pr("\n"), cout << flush; }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

int N;

vi fDists(int x) {
    vi dists(N);
    for (int i = 0; i < N; i++) {
        if (x == i || i == 0) { // dist to 0 = dist from 0
            dists[i] = 0;
        } else {
            dists[i] = getDistance(x, i);
        }
    }
    return dists;
}

vector<pair<int, vi>> centV;
vi x;

bool sameTree(int a, int b) {
    return getDistance(a, b) < x[a] + x[b];
}

bool checkSplit(vi &list) {
    vi record;

    int cur = -1;
    int cnt = 0;

    for (int i = 0; i < list.size(); i++) {
        if (cnt == 0) {
            record.pb(-1);
            cur = list[i];
            cnt = 1;
        } else {
            if (sameTree(cur, list[i])) {
                record.pb(cur);
                cnt++;
            } else {
                record.pb(-1);
                cnt--;
            }
        }
    }

    int tCnt = 0;

    vi saved(N);

    for (int i = 0; i < list.size(); i++) {
        if (record[i] != -1) {
            tCnt += saved[record[i]];
        } else {
            int res = sameTree(list[i], cur);
            saved[list[i]] = res;
            tCnt += res;
        }
    }
    return tCnt <= N / 2;
}


int hubDistance(int iN, int sub) {
    centV.clear();
    x.clear();
    N = iN;
    vi dists0 = fDists(0);

    int d1 = -1;
    for (int i = 0; i < N; i++) {
        if (d1 == -1 || dists0[i] > dists0[d1]) {
            d1 = i;
        }
    }

    vi distsD1 = fDists(d1);
    distsD1[0] = dists0[d1];

    int d2 = -1;
    for (int i = 0; i < N; i++) {
        if (d2 == -1 || distsD1[i] > distsD1[d2]) {
            d2 = i;
        }
    }

    int diameter = distsD1[d2];

    int pd = distsD1[0] - (distsD1[0] + dists0[d2] - diameter) / 2;

    map<int, vi> cent;

    for (int i = 0; i < N; i++) {
        int cX = (dists0[i] + distsD1[i] - dists0[d1]) / 2;
        x.pb(cX);
        int y = distsD1[i] - cX;

        if (y < pd) {
            cent[y].pb(i);
        }
        if (y == pd) {
            cent[pd].pb(i);
        }
        if(y > pd){
            cent[pd].pb(i);
            x[i] = distsD1[i] - pd;
        }
    }

    for (auto &dv : cent) {
        centV.pb(dv);
    }

    int R = 1e9;

    for (auto &dv : centV) {
        int cD = dv.f;
        if (R > max(cD, diameter - cD)) {
            R = max(cD, diameter - cD);
        }
    }

    vi hubs;

    for (int i = 0; i < centV.size(); i++) {
        int cD = centV[i].f;
        if (R == max(cD, diameter - cD)) {
            hubs.pb(i);
        }
    }

    vi bef(centV.size()), aft(centV.size());

    bef[0] = 0;
    for (int i = 1; i < centV.size(); i++) {
        bef[i] = bef[i - 1] + centV[i - 1].s.size();
    }

    aft[centV.size() - 1] = 0;
    for (int i = centV.size() - 2; i >= 0; i--) {
        aft[i] = aft[i + 1] + centV[i + 1].s.size();
    }

    bool bal = false;
    for (int hub :hubs) {
        assert(bef[hub] + aft[hub] + centV[hub].s.size() == N);
        if (bef[hub] <= N / 2 && aft[hub] <= N / 2) {
            if (centV[hub].s.size() <= N / 2) {
                bal = true;
            } else if (!bal) {
                // does splitting into different subtrees make it <= N/2 ?
                bal |= checkSplit(centV[hub].s);
            }
        }
    }

    if (bal) {
        return R;
    } else {
        return -R;
    }
}