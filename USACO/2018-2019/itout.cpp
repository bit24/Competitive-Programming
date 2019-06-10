#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

typedef pair<int, ll> plc;

#define pb push_back
#define f first
#define s second

namespace debug {
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
    void pr(const T &x) { cout << x; }

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...); // print w/ spaces
    }
}

using namespace debug;

const int MAXN = 100005;
const ll INF = 1e18;

plc operator+(plc a, plc b) {
    if (a.f != b.f) {
        return max(a, b);
    }
    return {a.f, min(a.s + b.s, INF)};
}

plc tr[4 * MAXN];

plc q(int i, int l, int r, int s, int e) {
    // ps(i, l, r, s, e);
    if (e < l || r < s) {
        return {0, 0};
    }
    if (s <= l && r <= e) {
        // ps("ret:", tr[i]);
        return tr[i];
    }
    int mid = (l + r) / 2;
    return q(i * 2, l, mid, s, e) + q(i * 2 + 1, mid + 1, r, s, e);
}

void u(int i, int l, int r, int x, plc nw) {
    // ps(i, l, r, x, nw);
    if (l == r) {
        // ps("upd:", nw);
        tr[i] = nw;
        return;
    }
    int mid = (l + r) / 2;
    if (x <= mid) {
        u(i * 2, l, mid, x, nw);
    } else {
        u(i * 2 + 1, mid + 1, r, x, nw);
    }
    tr[i] = tr[i * 2] + tr[i * 2 + 1];
    // ps("ass:", i, tr[i]);
}

int N;
ll K;
int a[MAXN];

int len[MAXN];
ll cnt[MAXN];

vi iByLen[MAXN];

bool seq[MAXN];

int main() {
    ifstream fin("itout.in");
    ofstream fout("itout.out");

    fin >> N >> K;
    // ps(N, K);
    for (int i = 1; i <= N; i++) {
        fin >> a[i];
    }

    for (int i = 0; i < 4 * MAXN; i++) {
        tr[i] = {0, 0};
    }

    int mL = 0;
    for (int i = N; i >= 1; i--) {
        // ps("i:", i);
        plc cur = {1, 1};
        if (a[i] + 1 <= N) {
            // ps("query", a[i] + 1);
            plc aft = q(1, 1, N, a[i] + 1, N);
            // ps(aft);
            aft.f++;

            cur = cur + aft;
        }

        u(1, 1, N, a[i], cur);

        // ps(cur);

        len[i] = cur.f;
        cnt[i] = cur.s;
        if(cnt[i] <= 0){
            ps(i);
        }

        iByLen[len[i]].pb(i);
        mL = max(mL, len[i]);
        // ps();
    }

    for (int i = 0; i < MAXN; i++) {
        if (iByLen[i].size()) {
            reverse(iByLen[i].begin(), iByLen[i].end());
        }
    }

    // ps(iByLen[0], iByLen[1], iByLen[2]);

    memset(seq, 0, sizeof(seq));

    for (int i = 0, cL = mL; cL >= 1; cL--) {
        // ps(cL);
        int nxt = -1;
        for (int j : iByLen[cL]) {
            // ps(j);
            if (j < i) {
                continue;
            }
            if (cnt[j] < K) {
                K -= cnt[j];
            } else {
                nxt = j;
                break;
            }
        }
        if (nxt == -1) {
            ps(cL);
            ps(iByLen[cL]);
            return 1;
        }
        // ps();
        seq[nxt] = true;
        i = nxt + 1;
    }

    vi ans;
    for (int i = 1; i <= N; i++) {
        if (!seq[i]) {
            ans.pb(a[i]);
        }
    }
    fout << ans.size() << '\n';

    sort(ans.begin(), ans.end());
    for (int i : ans) {
        fout << i << '\n';
    }

    fout << flush;
}