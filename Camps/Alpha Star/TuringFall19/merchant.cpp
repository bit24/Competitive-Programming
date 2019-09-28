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

    void ps() { pr("\n"), cout << flush; }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;


multiset<pi> potential;

pi getF() {
    pi x = *potential.begin();
    potential.erase(potential.begin());
    return x;
}

pi getL() {
    pi x = *(--potential.end());
    potential.erase(--potential.end());
    return x;
}

int main() {
    int N, C;
    cin >> N >> C;
    int pS = 0;

    ll sum = 0;
    for (int i = 0; i < N; i++) {
        int bN, bC, sN, sC;
        cin >> bN >> bC >> sN >> sC;

        int rev = 0;
        while (potential.size() > 0 && sN > 0) {
            pi x = getF();
            if (x.f < sC) {
                int dN = min(sN, x.s);
                if (dN < x.s) {
                    potential.insert({x.f, x.s - dN});
                }
                sN -= dN;
                sum += (ll) dN * (sC - x.f);
                rev += dN;
            } else {
                potential.insert(x);
                break;
            }
        }
        if (rev > 0) {
            potential.insert({sC, rev});
        }

        potential.insert({bC, bN});
        pS += bN;

        while (pS > C) {
            pi x = getL();
            if (pS - x.s >= C) {
                pS -= x.s;
//                ps(pS);
            } else {
                int dN = min(pS - C, x.s);
                pS -= dN;
                potential.insert({x.f, x.s - dN});
            }
        }

//        ps("i:", i);
//        ps(sum);
//        ps(potential);
//        ps(pS);
    }
    cout << sum << endl;
}
