#include <bits/stdc++.h>

using namespace std;

typedef pair<int, int> pi;
typedef long long ll;

#define f first
#define s second
#define pb push_back

const int MAXN = 3e3 + 10;
const ll MOD = 1e9 + 7;
const ll DIV2 = (1e9 + 8) / 2;
int N;

pi pts[MAXN];

int ins[MAXN];

ll crossP(pi a, pi b) {
    return ((ll) a.f) * b.s - ((ll) a.s) * b.f;
}


int quad(pi p) {
    if (p.f >= 0 && p.s >= 0) {
        return 0;
    }
    if (p.f < 0 && p.s >= 0) {
        return 1;
    }
    assert(p.s < 0);
    if (p.f <= 0) {
        return 2;
    }
    return 3;
}

// by polar angle
bool lessA(pi a, pi b) {
    int qA = quad(a);
    int qB = quad(b);

    if (qA != qB) {
        return qA < qB;
    }

    ll cross = crossP(a, b);
//    if(cross == 0){
//
//    }
    return cross > 0;
}

vector<pi> cPts;

int main() {
    freopen("triangles.in", "r", stdin);
    freopen("triangles.out", "w", stdout);
    assert(2 * DIV2 % MOD == 1);

    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> pts[i].f >> pts[i].s;
    }

//    N = 5;
//
//    for (int i = 0; i < N; i++) {
//        pts[i].f = rand() % 1000;
//        pts[i].s = rand() % 1000;
//        cout << pts[i].f << " " << pts[i].s << endl;
//    }

    ll ans = 0;

    for (int pivI = 0; pivI < N; pivI++) {
//        cout << "pivI " << pivI << endl;
        memset(ins, 0, sizeof(ins));

        pi piv = pts[pivI];

        cPts.clear();
        for (int i = pivI + 1; i < N; i++) {
            cPts.pb({pts[i].f - piv.f, pts[i].s - piv.s});
        }

        if (cPts.size() < 2) continue;

        sort(cPts.begin(), cPts.end(), lessA);

//        cout << "piv " << piv.f << " " << piv.s << endl;

//        for (pi p : cPts) {
//            cout << p.f << " " << p.s << endl;
//        }

        ins[0] = true;
        ll sumF = cPts[0].f, sumS = cPts[0].s;

        int nI = 1;

        for (int j = 0; j < cPts.size(); j++) {
            pi piv2 = cPts[j];

//            cout << piv2.f << " " << piv2.s << endl;
//            cout << cPts[nI].f << " " << cPts[nI].s << endl;
            while (!ins[nI] && (nI == j || crossP(piv2, cPts[nI]) > 0 ||
                                (crossP(piv2, cPts[nI]) > 0) == 0 && quad(piv2) == quad(cPts[nI]) && j < nI)) {
                ins[nI] = true;
                sumF += cPts[nI].f;
                sumS += cPts[nI].s;
                nI = (nI + 1) % cPts.size();
            }

//            cout << "nI " << nI << endl;

            ll area = piv2.f * sumS - piv2.s * sumF;
            assert(area >= 0);

            ans += area;
            ans %= MOD;
            ans += MOD;
            ans %= MOD;
//            cout << sumF << " " << sumS << endl;
//            cout << ans << endl;

            assert(ins[j]);
            ins[j] = false;
            sumF -= cPts[j].f;
            sumS -= cPts[j].s;
        }
    }

//    ll brute = 0;
//
//    for (int i = 0; i < N; i++) {
//        for (int j = i + 1; j < N; j++) {
//            for (int k = j + 1; k < N; k++) {
//                pi a = {pts[j].f - pts[i].f, pts[j].s - pts[i].s};
//                pi b = {pts[k].f - pts[i].f, pts[k].s - pts[i].s};
//                ll area = abs(crossP(a, b));
//                brute = (brute + area) % MOD;
//            }
//        }
//    }

    assert(ans >= 0);
//
//    assert(ans == brute);

    cout << ans << endl;
    return 0;
}

//int main() {
//    while (true) {
//        main0();
//    }
//}