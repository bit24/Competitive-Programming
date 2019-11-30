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

namespace suffixArray {
    const static int MAXN = 2e5 + 100, LOGN = 18;

    int ord[LOGN + 1][MAXN], c[MAXN + 1];

    vi a, list, srtd;

    int N;

    bool cmpA(int i, int j) {
        return a[i] < a[j];
    }

    bool equal(int i, int j, int cP) {
        int len = 1 << cP;
        return ord[cP][i] == ord[cP][j] &&
               (i + len < N ? (j + len < N && ord[cP][i + len] == ord[cP][j + len]) : j + len >= N);
    }

    void sort(int cP, int len) {
        memset(c, 0, sizeof(c));
        for (int i : list) {
            int sec = i + len < N ? ord[cP][i + len] : -1;
            c[sec + 1]++;
        }

        int s = 0;
        for (int i = 0; i <= N; i++) {
            s += c[i];
            c[i] = s - c[i];
        }

        for (int i : list) {
            int sec = i + len < N ? ord[cP][i + len] : -1;
            srtd[c[sec + 1]++] = i;
        }
        swap(srtd, list);
    }


    void build() {
        for (int i = 0; i < N; i++) {
            list.pb(i);
            srtd.pb(i);
        }

        sort(list.begin(), list.end(), cmpA);

        ord[0][list[0]] = 0;
        for (int i = 1; i < N; i++) {
            ord[0][list[i]] = a[list[i]] == a[list[i - 1]] ? ord[0][list[i - 1]] : i;
        }

        for (int cP = 1; cP <= LOGN; cP++) {
            sort(cP - 1, 1 << (cP - 1));
            sort(cP - 1, 0);

            ord[cP][list[0]] = 0;
            for (int i = 1; i < N; i++) {
                ord[cP][list[i]] = equal(list[i], list[i - 1], cP - 1) ? ord[cP][list[i - 1]] : i;
            }
        }
    }

    int lcp(int i, int j) {
        int ans = 0;
        for (int cP = LOGN; cP >= 0 && i < N && j < N; cP--) {
            int len = 1 << cP;
            if (ord[cP][i] == ord[cP][j]) {
                i += len, j += len, ans += len;
            }
        }
        return ans;
    }
}
