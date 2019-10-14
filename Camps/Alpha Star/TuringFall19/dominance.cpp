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


struct seg {
    int x, lo, hi, c;

    bool operator<(const seg o) const {
        return x < o.x;
    }
};

//namespace debug {
//    const int DEBUG = true;
//
//    void pr(const seg &x);
//
//    template<class T1, class T2>
//    void pr(const pair<T1, T2> &x);
//
//    template<class T, size_t SZ>
//    void pr(const array<T, SZ> &x);
//
//    template<class T>
//    void pr(const vector<T> &x);
//
//    template<class T>
//    void pr(const set<T> &x);
//
//    template<class T1, class T2>
//    void pr(const map<T1, T2> &x);
//
//    template<class T>
//    void pr(const T &x) { if (DEBUG) cout << x; }
//
//    template<class T, class... Ts>
//    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }
//
//    void pr(const seg &x) {
//        pr("{", x.x, ", ", x.lo, ", ", x.hi, ", ", x.c, "}");
//    }
//
//    template<class T1, class T2>
//    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }
//
//    template<class T>
//    void prIn(const T &x) {
//        pr("{");
//        bool fst = 1;
//        for (auto &a : x) {
//            pr(fst ? "" : ", ", a), fst = 0;
//        }
//        pr("}");
//    }
//
//    template<class T, size_t SZ>
//    void pr(const array<T, SZ> &x) { prIn(x); }
//
//    template<class T>
//    void pr(const vector<T> &x) { prIn(x); }
//
//    template<class T>
//    void pr(const set<T> &x) { prIn(x); }
//
//    template<class T1, class T2>
//    void pr(const map<T1, T2> &x) { prIn(x); }
//
//    void ps() { pr("\n"), cout << flush; }
//
//    template<class Arg, class... Args>
//    void ps(const Arg &first, const Args &... rest) {
//        pr(first, " ");
//        ps(rest...);
//    }
//}
//using namespace debug;

ll bSum = 0, wSum = 0;

int N;
ll W, H;

void process(vector<seg> &segs) {
    sort(segs.begin(), segs.end());

    vl yCoor;
    vl delta;

    for (int i = 0; i < segs.size(); i++) {
        yCoor.pb(segs[i].lo);
        yCoor.pb(segs[i].hi);
    }

    sort(yCoor.begin(), yCoor.end());
    yCoor.erase(unique(yCoor.begin(), yCoor.end()), yCoor.end());

    delta.resize(yCoor.size(), 0);

    for (int i = 0; i < segs.size(); i++) {
        seg cS = segs[i];
        int lo = find(yCoor.begin(), yCoor.end(), cS.lo) - yCoor.begin();
        int hi = find(yCoor.begin(), yCoor.end(), cS.hi) - yCoor.begin();
        delta[lo] += cS.c;
        delta[hi] -= cS.c;


        ll cur = 0, cBSum = 0, cWSum = 0;

        for (int j = 0; j < yCoor.size(); j++) {
            cur += delta[j];
            if (j + 1 < yCoor.size()) {
                if (cur > 0) {
                    cBSum += yCoor[j + 1] - yCoor[j];
                } else if (cur < 0) {
                    cWSum += yCoor[j + 1] - yCoor[j];
                }
            }
        }

        if (i + 1 < segs.size()) {
            bSum += cBSum * (segs[i + 1].x - segs[i].x);
            wSum += cWSum * (segs[i + 1].x - segs[i].x);
        }
    }
}

int dType(int x, int y) {
    return (x + y) & 1;
}

int fDiag(int x, int y) {
    return (x + y) / 2;
}

int fDev(int x, int y) {
    if ((y + x) & 1) {
        return (y - 1 - x) / 2;
    } else {
        return (y - x) / 2;
    }
}

int main() {
    cin >> W >> H;
    cin >> N;

    vector<seg> segs[2];

    for (int i = 0; i < N; i++) {
        char ch;
        int x, y, r;
        cin >> ch >> x >> y >> r;
        int c = ch == 'B' ? 1 : -1;

        int lT = dType(x, y);
        lT ^= r & 1; // if r is even, long type is same as center, else long type is opposite of center
        int sT = lT ^1;

        int cL = fDev(x, y - r);
        int cH = fDev(x, y + r) + 1;

        int cS = fDiag(x, y - r);
        int cE = fDiag(x, y + r) + 1;

        segs[lT].pb({cS, cL, cH, c});
        segs[lT].pb({cE, cL, cH, -c});

        if (r != 0) {
            r--;
            int cL = fDev(x, y - r);
            int cH = fDev(x, y + r) + 1;

            int cS = fDiag(x, y - r);
            int cE = fDiag(x, y + r) + 1;

            segs[sT].pb({cS, cL, cH, c});
            segs[sT].pb({cE, cL, cH, -c});
        }
    }


    process(segs[0]);
    process(segs[1]);

//    ps(segs[0]);
//    ps(segs[1]);

    cout << wSum << " " << bSum << endl;
}