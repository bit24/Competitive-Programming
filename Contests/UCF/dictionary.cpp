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


string strs[100100];
int cnt[100100][26];

bool cmp(int a, int b) {
    for (int i = 0; i < 26; i++) {
        if (cnt[a][i] != cnt[b][i]) {
            return cnt[a][i] < cnt[b][i] ? 0 : 1;
        }
    }

    for (int i = 0; i < strs[a].size(); i++) {
        if (strs[a][i] != strs[b][i]) {
            return strs[a][i] < strs[b][i] ? 1 : 0;
        }
    }

    return 0;
}

int main() {
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        int N;
        cin >> N;
        for (int i = 0; i < N; i++) {
            cin >> strs[i];

            for (int j = 0; j < 26; j++) {
                cnt[i][j] = 0;
            }

            for (int j = 0; j < strs[i].size(); j++) {
                cnt[i][strs[i][j] - 'a']++;
            }
        }

        vi ord;
        for (int i = 0; i < N; i++) {
            ord.pb(i);
        }
        sort(ord.begin(), ord.end(), cmp);

        cout << "Dictionary #" << cT << ": " << endl;
        for (int i = 0; i < N; i++) {
            cout << strs[ord[i]] << endl;
        }
        cout << endl;
    }
}