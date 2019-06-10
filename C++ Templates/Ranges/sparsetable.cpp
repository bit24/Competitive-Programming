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

const int MAXN = 4;
const int LOGN = 2;

int log2d(int x) {
    return 31 - __builtin_clz(x);
}

pi tb[MAXN][LOGN];

void init() {
    for (int k = 1; k < LOGN; k++) {
        for (int i = 0; i < MAXN; i++) {
            int nI = i + (1 << (k - 1));
            if (nI < MAXN) {
                tb[i][k] = max(tb[i][k - 1], tb[nI][k - 1]);
            } else {
                tb[i][k] = tb[i][k - 1];
            }
        }
    }
}

pi qMax(int l, int r) {
    int k = log2d(r - l + 1);
    return max(tb[l][k], tb[r - (1 << k) + 1][k]);
}

int main() {


}