#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp> // Common file
#include <ext/pb_ds/tree_policy.hpp> // Including tree_order_statistics_node_update

using namespace std;
using namespace __gnu_pbds;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef tuple<int, int, int, int> qi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

typedef tree<
        pi,
        null_type,
        less<pi>,
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


const int MAXN = 1e5 + 5;

int N, Q;

pi pts[MAXN];

qi q[MAXN];

int ans[MAXN];

bool lessSum(pi a, pi b) {
    return a.f + a.s < b.f + b.s;
}

int main() {
    cin >> N >> Q;
    for (int i = 0; i < N; i++) {
        cin >> pts[i].f >> pts[i].s;
    }

    for (int i = 0; i < Q; i++) {
        int x, y, z;
        cin >> x >> y >> z;
        z = max(z, x + y);
        q[i] = {z, x, y, i};
    }

    sort(pts, pts + N, lessSum);
    reverse(pts, pts + N);

    sort(q, q + Q);
    reverse(q, q + Q);

    ordered_set byX;
    ordered_set byY;

    int nP = 0;
    for (int i = 0; i < Q; i++) {
        auto cQ = q[i];
        int sum, xL, yL, qI;
        tie(sum, xL, yL, qI) = cQ;

        while (nP < N && pts[nP].f + pts[nP].s >= sum) {
            byX.insert({pts[nP].f, nP});

            byY.insert({pts[nP].s, nP});

            nP++;
        }

        assert(byX.size() == byY.size());
        int cnt = byX.size();
//        cout << cnt << endl;
        cnt -= byX.order_of_key({xL, -1});
        cnt -= byY.order_of_key({yL, -1});

        assert(cnt >= 0);
        ans[qI] = cnt;
    }
    for (int i = 0; i < Q; i++) {
        cout << ans[i] << endl;
    }
}