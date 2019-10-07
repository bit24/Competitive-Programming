#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef tuple<int, int, int> ti;

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

int main() {
    int N, M;
    cin >> N >> M;
    vector<vi> aL(N);

    for (int i = 0; i < M; i++) {
        int x, y;
        cin >> x >> y;
        x--, y--;
        aL[x].pb(y);
        aL[y].pb(x);
    }

    priority_queue<pi, vector<pi>, greater<pi>> q;
    vi minT(N, -1);

    q.push({0, 0});
    minT[0] = 0;

    while (!q.empty()) {
        pi nxt = q.top();
        q.pop();

        for (int a : aL[nxt.s]) {
            if (minT[a] == -1) {
                if (N - 1 - ((nxt.f + 1) % N) == a) { // blocked by viewing
                    minT[a] = nxt.f + 2;
                } else {
                    minT[a] = nxt.f + 1;
                }
                q.push({minT[a], a});
            }
        }
    }

    int getT = minT[N - 1];

    int tRem = N - (getT % N);

    fill(minT.begin(), minT.end(), -1);
    q.push({getT, N - 1});

    while (!q.empty()) {
        pi nxt = q.top();
        q.pop();

        for (int a : aL[nxt.s]) {
            if (minT[a] == -1) {
                if (N - 1 - ((nxt.f + 1) % N) == a) { // blocked by viewing
                    minT[a] = nxt.f + 2;
                } else {
                    minT[a] = nxt.f + 1;
                }
                q.push({minT[a], a});
            }
        }
    }

    int leaveT = minT[0];
    if (getT + tRem >= leaveT) {
        cout << leaveT << endl;
        return 0;
    }

    getT += tRem;
    tRem = N;

    fill(minT.begin(), minT.end(), -1);
    q.push({getT, N - 1});

    while (!q.empty()) {
        pi nxt = q.top();
        q.pop();

        for (int a : aL[nxt.s]) {
            if (minT[a] == -1) {
                if (N - 1 - ((nxt.f + 1) % N) == a) { // blocked by viewing
                    minT[a] = nxt.f + 2;
                } else {
                    minT[a] = nxt.f + 1;
                }
                q.push({minT[a], a});
            }
        }
    }

    leaveT = minT[0];
    if (getT + tRem >= leaveT) {
        cout << leaveT << endl;
        return 0;
    }

    cout << "IMPOSSIBLE" << endl;
}