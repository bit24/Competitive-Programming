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


int link[400000][26], flag[400000], dp[400000];
int root = 0;
int nN = 1;
int L;

void add(string &str) {
    int cNode = root;
    for (int i = 0; i < str.size(); i++) {
        if (link[cNode][str[i] - 'a'] == -1) {
            link[cNode][str[i] - 'a'] = nN++;
        }
        cNode = link[cNode][str[i] - 'a'];
    }
    flag[cNode]++;
}

vi useable;

void calc(int cNode) {
//    ps(cNode);
    if (flag[cNode]) {
//        ps("flagged");
        dp[cNode] = -1;
        return;
    }
//    ps("inside");

    int best = -1;

    for (int i = 0; i < L; i++) {
        if (link[cNode][useable[i]] != -1) {
            int adj = link[cNode][useable[i]];
//            ps("link", cNode, adj);
            calc(adj);
            if (dp[adj] == -1) {
                best = 1;
            } else if (dp[adj] == 0) {
                best = max(best, 0);
            }
        }
        else{
            best = max(best, 0);
        }
    }
    dp[cNode] = best;
}

int main() {
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        for (int i = 0; i < 400000; i++) {
            for (int j = 0; j < 26; j++) {
                link[i][j] = -1;
            }
            flag[i] = 0;
            dp[i] = -2;
        }

        root = 0;
        nN = 1;

        int N;
        cin >> L >> N;

        string lets;
        cin >> lets;

        useable.clear();
        for (int i = 0; i < L; i++) {
            useable.pb(lets[i] - 'a');
        }

        for (int i = 0; i < N; i++) {
            string cStr;
            cin >> cStr;
            add(cStr);
        }

        calc(root);

        cout << "Game #" << cT << ": ";
        if (dp[root] == -1) {
            cout << "Bob wins" << endl;
        } else if (dp[root] == 0) {
            cout << "Draw" << endl;
        } else {
            cout << "Alice wins" << endl;
        }
    }

}