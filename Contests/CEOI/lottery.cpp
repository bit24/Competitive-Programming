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

    void ps() { pr("\n"); }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;

vi a;
vector<pi> queries;
vi qBegin;

vector<vi> aD;
vector<vi> ans;

int update(int i, int mis) {
    aD[i][qBegin[mis]]++;
}

int main() {
    int N, L, Q;
    cin >> N >> L;
    a.resize(N);
    for (int i = 0; i < N; i++) {
        cin >> a[i];
    }
    cin >> Q;

    for (int i = 0; i < Q; i++) {
        pi cQ = {-1, i};
        cin >> cQ.f;
        queries.pb(cQ);
    }
    queries.pb({1000000, -1});

    sort(queries.begin(), queries.end());
    aD.resize(N - L + 1);
    for (int i = 0; i < N - L + 1; i++) {
        aD[i].resize(Q + 1, 0);
    }

    qBegin.resize(N + 1);
    int nxt = 0;
    int pos = 0;
    for (pi cP : queries) {
        while (nxt <= cP.f && nxt <= N) {
            qBegin[nxt++] = pos;
        }
        pos++;
    }

//    ps(a);
//    ps(queries);
//    ps(qBegin);

    for (int j = 1; j + L <= N; j++) {
        int cMis = 0;
        for (int k = 0; k < L; k++) {
            cMis += a[k] != a[j + k];
        }

        // ps(0, j, cMis);

        update(0, cMis);
        update(j, cMis);

        for (int k = 1; j + k + L <= N; k++) {
            cMis -= a[k - 1] != a[j + k - 1];
            cMis += a[k + L - 1] != a[j + k + L - 1];

            // (k, j + k, cMis);
            update(k, cMis);
            update(j + k, cMis);
        }

    }

    // ps(aD);

    ans.resize(Q);
    for (int i = 0; i < Q; i++) {
        ans[i].resize(N - L + 1);
    }

    for (int i = 0; i < N - L + 1; i++) {
        int sum = 0;
        for (int j = 0; j < Q; j++) {
            sum += aD[i][j];
            ans[queries[j].s][i] = sum;
        }
    }

    for (int i = 0; i < Q; i++) {
        for (int j = 0; j < N - L + 1; j++) {
            cout << ans[i][j] << " ";
        }
        cout << "\n";
    }
    cout << endl;
}