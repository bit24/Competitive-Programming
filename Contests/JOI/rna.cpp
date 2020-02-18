#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp> // Common file
#include <ext/pb_ds/tree_policy.hpp> // Including tree_order_statistics_node_update

using namespace __gnu_pbds;
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


const int MAXN = 1e5 + 10;
const int MAXT = 3 * MAXN;

string nxt(string inp) {
    inp[inp.size() - 1]++;
    return inp;
}

string texts[MAXN];
string rTexts[MAXN];

struct rComp : binary_function<int, int, bool> {
    bool operator()(int a, int b) const {
        int cVal = rTexts[a].compare(rTexts[b]);
        if (cVal < 0) return true;
        if (cVal == 0) return a > b;
        return false;
    }
};

typedef tree<
        int,
        null_type,
        rComp,
        rb_tree_tag,
        tree_order_statistics_node_update>
        ordered_set;


int N, Q;

pair<pair<string, string>, int> fullQueries[MAXN];

ordered_set actInd;

int ans[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N >> Q;
    for (int i = 0; i < N; i++) {
        cin >> texts[i];
    }

    for (int i = 0; i < Q; i++) {
        cin >> fullQueries[i].f.f;
        cin >> fullQueries[i].f.s;
        fullQueries[i].s = i;
    }

    sort(texts, texts + N);

    for (int i = 0; i < N; i++) {
        rTexts[i] = texts[i];
        reverse(rTexts[i].begin(), rTexts[i].end());
//        ps(texts[i]);
    }

    sort(fullQueries, fullQueries + Q);

    int nTI = 0;

    vector<pair<pair<string, string>, int>> halfQueries;

    for (int i = 0; i < Q; i++) {
        halfQueries.pb({{nxt(fullQueries[i].f.f), fullQueries[i].f.s}, fullQueries[i].s});
        halfQueries.pb({{fullQueries[i].f.f, fullQueries[i].f.s}, fullQueries[i].s + Q});
    }

    sort(halfQueries.begin(), halfQueries.end());

    fill(ans, ans + MAXN, 0);

    // queries are sorted by start string
    for (auto &cQ : halfQueries) {
        string cLast = cQ.f.f;

//        ps(cLast);

        while (nTI < N && texts[nTI] < cLast) {
            actInd.insert(nTI);
            nTI++;
        }

//        ps(actInd.size());

//        ps(actInd);

        string qFirst = cQ.f.s;
        reverse(qFirst.begin(), qFirst.end());

        string qLast = nxt(qFirst);

//        ps(qFirst, qLast);

        rTexts[N] = qLast;
        int c2 = actInd.order_of_key(N);

        rTexts[N] = qFirst;
        int c1 = actInd.order_of_key(N);

//        ps(c1, c2);

        int cAns = c2 - c1;
//        ps("cAns:", cAns);
        if (cQ.s < Q) {
            ans[cQ.s] += cAns;
        } else {
            ans[cQ.s - Q] -= cAns;
        }
    }

    for (int i = 0; i < Q; i++) {
        cout << ans[i] << "\n";
    }
    cout << flush;
}