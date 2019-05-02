#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

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
    int nT;
    cin >> nT;

    for (int cT = 1; cT <= nT; cT++) {
        int P, Q;
        cin >> P >> Q;
        vi abX;
        vi blX;
        vi abY;
        vi blY;

        abX.pb(0);
        abY.pb(0);

        for (int i = 0; i < P; i++) {
            int x, y;
            string t;
            cin >> x >> y >> t;
            if (t == "N" && y < Q) {
                abY.pb(y + 1);
            }
            if (t == "S" && y > 0) {
                blY.pb(y - 1);
            }
            if (t == "E" && x < Q) {
                abX.pb(x + 1);
            }
            if (t == "W" && x > 0) {
                blX.pb(x - 1);
            }
        }

        sort(abX.begin(), abX.end());
        sort(abY.begin(), abY.end());

        sort(blX.begin(), blX.end());
        sort(blY.begin(), blY.end());

        ps(abY);
        ps(blY);

        int maxC = 0;
        int maxV = 0;
        int j = 0;
        for (int i = 0; i < abX.size(); i++) {
            while (j < blX.size() && abX[i] > blX[j]) {
                j++;
            }
            int cC = i + 1 + (blX.size() - j);
            if (cC > maxC) {
                maxC = cC;
                maxV = abX[i];
            }
        }

        int outX = maxV;

        maxC = 0;
        maxV = 0;
        j = 0;
        for (int i = 0; i < abY.size(); i++) {
            while (j < blY.size() && abY[i] > blY[j]) {
                j++;
            }
            int cC = i + 1 + (blY.size() - j);
            ps(i, cC);
            if (cC > maxC) {
                maxC = cC;
                maxV = abY[i];
            }
        }

        int outY = maxV;

        cout << "Case #" << cT << ": " << outX << " " << outY << "\n";
    }
    cout << flush;
}