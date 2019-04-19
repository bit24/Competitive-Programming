#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

const int MAXN = 150005;

struct E {
    ll sc;
    ll t;
    ld cMin;
    ld cMax;

    bool operator<(E o) const { // largest sc/t first, then smallest sc
        if (sc * o.t != o.sc * t) {
            return sc * o.t > o.sc * t;
        }
        return sc < o.sc;
    }
} e[MAXN];


bool BySc(E e1, E e2) {
    return e1.sc < e2.sc;
}

int N;
ld T;

bool paradox(ld C) {
    sort(e, e + N);

    ll sT = 0;
    for (int i = 0; i < N;) {
        ll eT = 0;

        eT += e[i].t;

        int j = i;
        while (j + 1 < N && e[j].sc * e[j + 1].t == e[j + 1].sc * e[j].t) {
            eT += e[j + 1].t;
            j++;
        }

        for (int k = i; k <= j; k++) {
            e[k].cMin = e[k].sc * (1 - C * (sT + eT) / T);
            e[k].cMax = e[k].sc * (1 - C * (sT + e[k].t) / T);
        }
        i = j + 1;
        sT += eT;
    }

    sort(e, e + N, BySc);

    ld pMax = 0;
    for (int i = 0; i < N;) {
        int j = i;
        while (j + 1 < N && e[j + 1].sc == e[j].sc) {
            j++;
        }

        for(int k = i; k <= j; k++){
            if(pMax > e[k].cMin){
                return true;
            }
        }

        for(int k = i; k <= j; k++){
            pMax = max(pMax, e[k].cMax);
        }

        i = j + 1;
    }
    return false;
}


int main() {
    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> e[i].sc;
    }

    for (int i = 0; i < N; i++) {
        cin >> e[i].t;
        T += e[i].t;
    }

    ld low = 0;
    ld hi = 1;

    for (int i = 0; i < 25; i++) {
        ld mid = (low + hi) / 2;
        //cout << mid << endl;
        if (paradox(mid)) {
            hi = mid;
        } else {
            low = mid;
        }
    }
    cout << low << endl;
}