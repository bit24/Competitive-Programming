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

const int MAXN = 10000;

struct PerLSegTr {
    const static int NNODES = 1000000;
    int tr[NNODES], lz[NNODES], lCh[NNODES], rCh[NNODES];
    int nN = 0;

    int copy(int x) {
        int nw = nN++;
        tr[nw] = tr[x], lz[nw] = lz[x], lCh[nw] = lCh[x], rCh[nw] = rCh[x];
        return nw;
    }

    int olap(int l, int r, int s, int e) {
        return min(r, e) - max(l, s) + 1;
    }

    int u(int i, int l, int r, int s, int e, int d) {
        if (e < l || r < s) {
            return i;
        }
        i = copy(i);
        if (s <= l && r <= e) {
            lz[i] += d;
            return i;
        }

        if (lz[i]) {
            tr[i] += (r - l + 1) * lz[i];
            if (l != r) {
                lCh[i] = copy(lCh[i]);
                tr[lCh[i]] += lz[i];

                rCh[i] = copy(rCh[i]);
                tr[rCh[i]] += lz[i];
            }
            lz[i] = 0;
        }

        int mid = (l + r) / 2;
        lCh[i] = u(lCh[i], l, mid, s, e, d);
        rCh[i] = u(rCh[i], mid + 1, r, s, e, d);
        tr[i] += olap(l, r, s, e) * d;
        return i;
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }

        // don't ps as that modifies tree
        if (s <= l && r <= e) {
            return (r - l + 1) * lz[i] + tr[i];
        }

        int mid = (l + r) / 2;
        return olap(l, r, s, e) * lz[i] + q(lCh[i], l, mid, s, e) + q(rCh[i], mid + 1, r, s, e);
    }

    int bld(int i, int l, int r){
        if(l == r){

        }
    }
};