#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back


int a[5];

int main() {
    int k;
    for (int i = 0; i < 5; i++) {
        cin >> a[i];
    }
    cin >> k;


    if (a[4] - a[0] > k) {
        cout << ":(" << endl;
        return 0;
    }

    cout << "Yay!" << endl;
}