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

vi arr;
vi loc;
vector<string> op;

void swap(int i, int j) {
    if (i == j) {
        return;
    }

    int t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;

    loc[arr[i]] = i;
    loc[arr[j]] = j;
    op.pb("" + to_string(i + 1) + " " + to_string(j + 1) + '\n');
}

int main() {
    int N;
    cin >> N;

    loc.resize(N);
    for (int i = 0; i < N; i++) {
        int x;
        cin >> x;
        x--;
        arr.pb(x);
        loc[x] = i;
    }

    int minD = N / 2;

    ps("read");

    for (int i = 0; i < N - 1; i++) {
        int j = loc[i];
        if (i == j) {
            continue;
        }
        ps(j);
        if (j - i >= minD) {
            swap(i, j);
        } else if (i - 0 >= minD) {
            swap(0, j);
            swap(0, i);
            swap(0, j);
        } else if (N - 1 - j >= minD) {
            swap(N - 1, i);
            swap(N - 1, j);
            swap(N - 1, i);
        } else {
            swap(0, j);
            swap(i, N - 1);
            swap(0, N - 1);
            swap(0, j);
            swap(i, N - 1);
        }
        ps("fin", i);
        ps(arr);
    }
    cout << op.size() << endl;
    for (string x : op) {
        cout << x;
    }
    cout << flush;
}