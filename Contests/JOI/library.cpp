#include <bits/stdc++.h>
#include "library.h"

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


const int MAXN = 1005;
int found[MAXN];

void Solve(int N) {
    if(N == 1){
        Answer({1});
        return;
    }

    memset(found, 0, sizeof(found));

    vi x;
    for (int i = 0; i < N; i++) {
        x.pb(1);
    }

    int anchor = -1;

    for (int i = 0; i < N; i++) {
        x[i] = 0;
        int res = Query(x);
        if (res == 1) {
            anchor = i;
            break;
        }

        x[i] = 1;
    }

    assert(anchor != -1);

    found[anchor] = true;

    vi ans;
    ans.pb(anchor);

    fill(x.begin(), x.end(), 0);
    x[anchor] = 1;

    for (int i = 1; i < N; i++) {
        vi search;
        for (int j = 0; j < N; j++) {
            if (!found[j]) {
                search.pb(j);
            }
        }
        while (search.size() > 1) {
            fill(x.begin(), x.end(), 0);
//            ps(search);
            vi half(search.begin(), search.begin() + search.size() / 2);

            for (int a : half) {
                x[a] = 1;
            }

            int res1 = Query(x);

            for (int j = 0; j < N; j++) {
                if (found[j]) {
                    x[j] = 1;
                }
            }

            int res2 = Query(x);

//            ps(res1, res2);

            if (res1 == res2) {
                search = half;
            } else {
                search.erase(search.begin(), search.begin() + search.size() / 2);
            }
        }
//        ps(search);

        int cur = search[0];
        found[cur] = true;
        x[cur] = 1;
        ans.pb(cur);
    }

    for (int i = 0; i < N; i++) {
        ans[i]++;
    }
//    ps(ans);
    Answer(ans);
}
