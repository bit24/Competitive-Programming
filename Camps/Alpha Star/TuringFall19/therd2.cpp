#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef tuple<char, int, int> ti;

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


static int searchF(vi &a, int x) { // returns first element ge x
    return lower_bound(a.begin(), a.end(), x) - a.begin();
}

static int searchL(vi &a, int x) { // returns last element le x
    return upper_bound(a.begin(), a.end(), x) - a.begin() - 1;
}

struct BIT {
    vi yCoor;
    vi tr;

    void init(vi &inp) {
        yCoor.pb(-1);
        yCoor.insert(yCoor.end(), inp.begin(), inp.end());
        yCoor.pb(200000);
        tr.resize(yCoor.size(), 0);
    }

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

    void u(int i, int d) {
        in_u(searchL(yCoor, i), d);
    }

    int rQ(int l, int r) {
        l = searchF(yCoor, l);
        r = searchL(yCoor, r);
        if (l > r) return 0;
        return in_q(r) - in_q(l - 1);
    }
};


int N;

vector<vi> chL;

vi dep, s, e, l;

void dfs(int v) {
    l.pb(v);
    s[v] = l.size() - 1;
    for (int aV : chL[v]) {
        dep[aV] = dep[v] + 1;
        dfs(aV);
    }
    e[v] = l.size() - 1;
}

int main() {
    int Q;
    cin >> Q;

    vector<ti> queries;

    for (int i = 0; i < Q; i++) {
        char t;
        int x, y;
        cin >> t >> x >> y;
        queries.emplace_back(t, x, y);
    }

    map<int, int> iMap;

    iMap[1] = 0;

//    ps(iMap.size());

    N = 1;
    for (int i = 0; i < Q; i++) {
        if (get<0>(queries[i]) == 'Z') {
            N++;
//            ps(get<1>(queries[i]), iMap.size());
            iMap.insert({get<1>(queries[i]), iMap.size()});
//            ps(iMap[get<1>(queries[i])]);
        }
    }

//    ps(iMap);
//    ps(N);

    chL.resize(N);

//    ps(queries.size());
    for (int i = 0; i < Q; i++) {
        if (get<0>(queries[i]) == 'Z') {
            chL[iMap[get<2>(queries[i])]].pb(iMap[get<1>(queries[i])]);
        }
    }

    dep.resize(N);
    s.resize(N);
    e.resize(N);

    dep[0] = 0;
    dfs(0);

//    ps(chL);
//    ps(l);
//    ps(s);
//    ps(e);
//    ps(dep);

    vector<vi> dLists(N);

    for (int i = 0; i < N; i++) {
        dLists[dep[l[i]]].pb(i);
    }

//    ps(dLists);

    vector<BIT> bits(N);
    for (int i = 0; i < N; i++) {
        bits[i].init(dLists[i]);
    }


    for (int i = 0; i < Q; i++) {
        if (get<0>(queries[i]) == 'Z') {
            int cV = iMap[get<1>(queries[i])];
            int cD = dep[cV];
            bits[cD].u(s[cV], 1);
        } else {
            int cV = iMap[get<1>(queries[i])];
            int cD = dep[cV] + get<2>(queries[i]) + 1;
            if (cD >= N) {
                cout << 0 << endl;
                continue;
            }
            cout << bits[cD].rQ(s[cV], e[cV]) << endl;
        }
    }
}