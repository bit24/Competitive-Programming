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

namespace debug {
    const int DEBUG = false;

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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

const int INF = 100000000;

static int searchF(vi &a, int x) { // returns first element ge x
    return lower_bound(a.begin(), a.end(), x) - a.begin();
}

static int searchL(vi &a, int x) { // returns last element le x
    return upper_bound(a.begin(), a.end(), x) - a.begin() - 1;
}

struct BIT2DRUpd { // 2D BIT with 0 indexing; range updates, points queries

    struct node {
        vi yCoor;
        vi tr;

        void in_u(int i, int d) {
            while (i < tr.size()) {
                tr[i] += d;
                i = i | (i + 1);
            }
        }

        int in_q(int i) {
            int s = 0;
            while (i >= 0) {
                s += tr[i];
                i = (i & (i + 1)) - 1;
            }
            return s;
        }

        void rU(int l, int r, int d) {
            l = searchF(yCoor, l);
            r = searchL(yCoor, r);
            if (l > r) return;
            in_u(l, d);
            in_u(r + 1, -d);
        }

        int q(int i) {
            return in_q(searchL(yCoor, i));
        }
    };

    vector<node> tr;

    void init(vector<pi> &pts) {
        int maxX = 0;
        for (pi cP: pts) {
            maxX = max(maxX, cP.f);
        }
        tr.resize(maxX + 1);

        // coordinate compress on x if necessary in future
        for (pi cP: pts) {
            int i = cP.f;
            while (i >= 0) {
                tr[i].yCoor.pb(cP.s);
                i = (i & (i + 1)) - 1;
            }
        }

        for (node &cN : tr) {
            sort(cN.yCoor.begin(), cN.yCoor.end());
            cN.yCoor.erase(unique(cN.yCoor.begin(), cN.yCoor.end()), cN.yCoor.end());
            cN.tr.resize(cN.yCoor.size(), 0);
        }
    }

    void in_rU(int i, int l, int r, int d) {
        while (i < tr.size()) {
            tr[i].rU(l, r, d);
            i = i | (i + 1);
        }
    }

    int q(int i1, int i2) {
        int s = 0;
        while (i1 >= 0) {
            s += tr[i1].q(i2);
            i1 = (i1 & (i1 + 1)) - 1;
        }
        return s;
    }

    void rU(int l1, int r1, int l2, int r2, int d) {
        if (l1 > r1) return;
        in_rU(l1, l2, r2, d);
        in_rU(r1 + 1, l2, r2, -d);
    }
} bit2D;


int main() {
    int N, Q;
    cin >> N >> Q;
    string line;
    cin >> line;

    vector<bool> state(N);
    for (int i = 0; i < N; i++) {
        state[i] = line[i] == '1';
    }

    vector<pi> qs;
    vector<pi> pts;

    for (int i = 0; i < Q; i++) {
        string t;
        cin >> t;
        if (t == "toggle") {
            int x;
            cin >> x;
            x--;
            qs.pb({-1, x});
        } else {
            int l, r;
            cin >> l >> r;
            l--, r -= 2;
            qs.pb({l, r});
            pts.pb({l, r});
        }
    }


    bit2D.init(pts);



    set<pi> segs;
    for (int i = 0; i < N;) {
        while (!state[i]) {
            i++;
            continue;
        }

        int j = i;
        while (j + 1 < N && state[j + 1]) {
            j++;
        }
        segs.insert({i, j});
        i = j + 1;
    }


    for (int cT = 1; cT <= Q; cT++) {
        pi cQ = qs[cT - 1];

        auto aftIt = segs.upper_bound({cQ.s, INF});
        if (cQ.f == -1) { // update
            int x = cQ.s;

            if (state[x]) {
                pi cSeg = *(--aftIt);

                segs.erase(cSeg);
                if (cSeg.f < x) {
                    segs.insert({cSeg.f, x - 1});
                }
                if (x < cSeg.s) {
                    segs.insert({x + 1, cSeg.s});
                }

                bit2D.rU(cSeg.f, x, x, cSeg.s, cT);
            } else {
                int l = x, r = x;

                if (aftIt != segs.end()) {
                    pi aft = *aftIt;
                    if (x + 1 == aft.f) {
                        segs.erase(aft);
                        r = aft.s;
                        aftIt = segs.upper_bound({x, INF});
                    }
                }

                if (aftIt != segs.begin()) {
                    pi bef = *(--aftIt);

                    if (bef.s == x - 1) {
                        segs.erase(bef);
                        l = bef.f;
                    }
                }

                segs.insert({l, r});

                bit2D.rU(l, x, x, r, -cT);
            }
            state[x] = !state[x];
        } else {
            int ans = bit2D.q(cQ.f, cQ.s);

            if (aftIt != segs.begin()) {
                pi around = *(--aftIt);
                if (around.f <= cQ.f && cQ.s <= around.s) { // interval is still active
                    ans += cT;
                }
            }
            cout << ans << '\n';
        }
    }
    cout << flush;
}