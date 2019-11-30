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

using namespace suffixArray;

string str;

vi lcpA;

int main() {
    int Q, A, B;
    cin >> N >> A >> B >> Q;

    cin >> str;

    for (int i = 0; i < N; i++) {
        a.pb(str[i]);
    }

    build();

    for (int i = 0; i < N - 1; i++) {
        lcpA.pb(lcp(suffixArray::list[i], suffixArray::list[i + 1]));
    }

    vector<pi> bars;
    for (int i = 0; i < N - 1; i++) {
        bars.pb({lcpA[i], i});
    }

    sort(bars.begin(), bars.end());

    set<int> exist;
    exist.insert(-1);
    exist.insert(N - 1);

    vi freqs;

    int nB = 0;

    for (int len = 0; len <= N; len++) { // string length
        for (int j = nB; j < bars.size(); j++) { // N-1 bars
            if (bars[j].f > len) {
                break;
            }

            int right = *exist.upper_bound(bars[j].s);
            int left = *(--exist.upper_bound(bars[j].s));

            int iLen = (right - left - 1) + 1;

            if (A <= len && len <= B) {
                freqs.pb(iLen);
            }
        }

        for (; nB < bars.size(); nB++) { // N-1 bars
            if (bars[nB].f > len) {
                break;
            }
            exist.insert(bars[nB].s);
        }
    }
    sort(freqs.begin(), freqs.end());
    freqs.erase(unique(freqs.begin(), freqs.end()), freqs.end());

    reverse(freqs.begin(), freqs.end());

    for (int i = 0; i < Q && i < freqs.size(); i++) {
        cout << freqs[i] << endl;
    }
}