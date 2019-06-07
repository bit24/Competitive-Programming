#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<ll, int> pi;

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

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int N;
    cin >> N;
    vector<pi> s;
    vl t;

    for (int i = 1; i <= N; i++) {
        ll x;
        cin >> x;
        s.pb({x, i});
    }
    for (int i = 0; i < N; i++) {
        ll x;
        cin >> x;
        t.pb(x);
    }

    sort(s.begin(), s.end());
    sort(t.begin(), t.end());
    // ps(s, t);

    vector<string> ans;

    stack<pi> stk;
    for (int i = 0; i < N; i++) {
        if (s[i].f < t[i]) {
            stk.push({t[i] - s[i].f, s[i].s});
        } else {
            ll need = s[i].f - t[i];
            while (need) {
                if (stk.empty()) {
                    cout << "NO" << endl;
                    return 0;
                }
                pi cTop = stk.top();
                int d = min(need, cTop.f);
                ans.pb(to_string(cTop.s) + " " + to_string(s[i].s) + " " + to_string(d));
                stk.pop();
                if (cTop.f - d) {
                    stk.push({cTop.f - d, cTop.s});
                }
                need -= d;
            }
        }
        // ps(s, t);
    }

    if(stk.size()){
        cout << "NO" << endl;
        return 0;
    }

    cout << "YES\n";
    cout << ans.size() << "\n";
    for (string x : ans) {
        cout << x << '\n';
    }
    cout << flush;
}