#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

int a[1005];
int b[1005];

int N;

int searchC(int c) {
    int lo = 1;
    int hi = N;

    while (lo != hi) {
        int mid = (lo + hi) / 2;
        cout << "? " << 1 << " " << c << " " << mid << " " << c << endl;
        int a;
        cin >> a;
        if (a & 1) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    return lo;
}

int searchR(int r) {
    int lo = 1;
    int hi = N;

    while (lo != hi) {
        int mid = (lo + hi) / 2;
        cout << "? " << r << " " << 1 << " " << r << " " << mid << endl;
        int a;
        cin >> a;
        if (a & 1) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    return lo;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin >> N;
    for (int i = 1; i + 1 <= N; i++) {
        cout << "? " << 1 << " " << 1 << " " << N << " " << i << endl;
        cin >> a[i];
    }

    for (int i = 1; i + 1 <= N; i++) {
        cout << "? " << 1 << " " << 1 << " " << i << " " << N << endl;
        cin >> b[i];
    }

    int fA = 1005;
    int lA = 0;
    for (int i = 1; i + 1 <= N; i++) {
        if (a[i] & 1) {
            fA = min(fA, i);
            lA = max(lA, i);
        }
    }
    int fB = 1005;
    int lB = 0;
    for (int i = 1; i + 1 <= N; i++) {
        if (b[i] & 1) {
            fB = min(fB, i);
            lB = max(lB, i);
        }
    }

    if (fA != 1005) {
        int c1 = fA;
        int c2 = lA + 1;

        int r1 = searchC(c1);
        int r2 = searchC(c2);

        cout << "! " << r1 << " " << c1 << " " << r2 << " " << c2 << " " << endl;
    } else {
        int r1 = fB;
        int r2 = lB + 1;

        int c1 = searchR(r1);
        int c2 = searchR(r2);
        cout << "! " << r1 << " " << c1 << " " << r2 << " " << c2 << " " << endl;
    }
}
