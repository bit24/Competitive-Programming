#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp> // Common file
#include <ext/pb_ds/tree_policy.hpp> // Including tree_order_statistics_node_update

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

using namespace __gnu_pbds;
typedef tree<
        int,
        null_type,
        less<int>,
        rb_tree_tag,
        tree_order_statistics_node_update>
        ordered_set;

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

const int MAXN = 2505;

int N, M;
ll ans = 0;

struct block {
    int l, r;
    int len;

    inline bool operator<(block o) {
        if (l != o.l) return l < o.l;
        if (r != o.r) return r < o.r;
        return len < o.len;
    }

    inline bool operator<(pi o) {
        if (l != o.f) return l < o.f;
        return r < o.s;
    }

    inline bool operator==(pi o) {
        return l == o.f && r == o.s;
    }
};

bool longestFirst(block a, block b) {
    if (a.len != b.len) return a.len > b.len;
    return a < b;
}

vector<pi> findRanges(vi &a) {
    vector<pi> ranges;

    vi stk;

    for (int i = 0; i < a.size(); i++) {
        for (int j = stk.size() - 1; j >= 0; j--) {
            int pB = stk[j];
            if (pB + 1 < i) {
                ranges.pb({pB + 1, i - 1});
            }

            if (a[stk[j]] >= a[i]) {
                break;
            }
        }

        while (stk.size() && a[stk[stk.size() - 1]] <= a[i]) {
            stk.erase(--stk.end());
        }
        stk.pb(i);
    }

    sort(ranges.begin(), ranges.end());
    return ranges;
}

vector<pi> ranges[MAXN];

vector<block> rColumnQ[MAXN];

ordered_set osets[MAXN];

int mode = 0;

vector<block> matchPrev(vector<block> &blocks, vector<pi> &ranges, int cRow = -1) {

    vector<block> newBlocks;

//    sort(blocks.begin(), blocks.end());
//    sort(ranges.begin(), ranges.end());

    int nBI = 0;
    for (pi cR : ranges) {
        while (nBI < blocks.size() && blocks[nBI] < cR) {
            if (mode == 0) {
                block cBlock = blocks[nBI];
                // end of cBlock (no longer extended)
                rColumnQ[cBlock.r].pb({cRow - cBlock.len, cRow - 1, cBlock.r - cBlock.l + 1});
            }

            nBI++;
        }

        if (nBI < blocks.size() && blocks[nBI] == cR) {
            block cBlock = blocks[nBI];

            newBlocks.pb({cBlock.l, cBlock.r, cBlock.len + 1});

            nBI++;
        } else {
            newBlocks.pb({cR.f, cR.s, 1});
        }
    }

    while (nBI < blocks.size()) {
        if (mode == 0) {
            block cBlock = blocks[nBI];
            // end of cBlock (no longer extended)
            rColumnQ[cBlock.r].pb({cRow - cBlock.len, cRow - 1, cBlock.r - cBlock.l + 1});
        }

        nBI++;
    }

    return newBlocks;
}

vi getColumn(vector<vi> &a, int cI) {
    vi col;
    for (int i = 0; i < N; i++) {
        col.pb(a[i][cI]);
    }
    return col;
}

ll count_rectangles(vector<vi> a) {
    N = a.size();
    M = a[0].size();

//    ps("read");

    for (int cR = 1; cR < N; cR++) {
        ranges[cR] = findRanges(a[cR]);
//        if(cR% 100 == 0){
//            ps(cR);
//        }
    }

//    ps("step1");

    mode = 0;
    vector<block> cBlocks;
    for (int cR = 1; cR <= N; cR++) { // include N to flush all blocks at the end
        cBlocks = matchPrev(cBlocks, ranges[cR], cR);
    }

    cBlocks.clear();
    mode = 1;


//    ps("here");

    for (int cC = 1; cC < M; cC++) {
        vi cCol = getColumn(a, cC);
        vector<pi> cRanges = findRanges(cCol);

        cBlocks = matchPrev(cBlocks, cRanges);

        sort(cBlocks.begin(), cBlocks.end(), longestFirst);
        sort(rColumnQ[cC].begin(), rColumnQ[cC].end(), longestFirst);

        int nBI = 0;
        for (block cQ : rColumnQ[cC]) {
            while (nBI < cBlocks.size() && cBlocks[nBI].len >= cQ.len) {
                block cBlock = cBlocks[nBI];
                osets[cBlock.r].insert(cBlock.l);
                nBI++;
            }

            for (int cR = cQ.l; cR <= cQ.r; cR++) {
                ans += osets[cR].size() - osets[cR].order_of_key(cQ.l);
            }
        }
        for (int cR = 0; cR <= N; cR++) {
            osets[cR].clear();
        }

        sort(cBlocks.begin(), cBlocks.end());
    }

    return ans;
}
