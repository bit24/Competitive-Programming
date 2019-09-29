#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef pair<double, pi> api;

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


int N;

pi operator-(pi a, pi b) {
    return {a.f - b.f, a.s - b.s};
}

bool isCCW(pi a, pi b) {
    return (ll) a.f * b.s - (ll) a.s * b.f > 0;
}

ll process(vector<pi> &p, pi ref) {
    vector<api> pts;
    for (int i = 0; i < N; i++) {
        if (p[i] != ref) {
            pts.pb({atan2(p[i].s - ref.s, p[i].f - ref.f), p[i]});
        }
    }
    sort(pts.begin(), pts.end());
//    ps(ref);
//    ps(pts);

    int nR = 0, cnt = 0;

    ll sum = 0;
    for (int t = 0; t < 2; t++) {
        for (int i = 0; i < pts.size(); i++) {
            while (cnt > 0) {
                if (!isCCW(pts[nR].s - ref, pts[i].s - ref)) {
                    nR = (nR + 1) % pts.size();
                    cnt--;
//                    ps("removed");
                } else {
                    break;
                }
            }
            if (t == 1) {
//                ps(i, nR, cnt);
//                ps("vectors");
//                ps(pts[nR].s - ref, " and ", pts[i].s - ref);
//                ps(isCCW(pts[nR].s - ref, pts[i].s - ref));
                sum += (ll) cnt * (cnt - 1) / 2;
            }
            cnt++;
        }
    }
//    ps("sum total:");
//    ps(sum);
//    ps((ll) pts.size() * (pts.size() - 1) * (pts.size() - 2) / 6);
    return (ll) pts.size() * (pts.size() - 1) * (pts.size() - 2) / 6 - sum;
}

int main() {
    cin >> N;
    vector<pi> p;
    for (int i = 0; i < N; i++) {
        int x, y;
        cin >> x >> y;
        p.pb({x, y});
    }

    ll ans = 0;
    for (int i = 0; i < N; i++) {
        ans += process(p, p[i]);
    }
    cout << ans << endl;
}