#include <bits/stdc++.h>

typedef long long ll;

using namespace std;

int main() {
    ll
    a, b;
    cin >> a >> b;

    if (b % a != 0) {
        cout << -1;
        return 0;
    }

    b /= a;

    int cnt = 0;
    while (b % 2 == 0) {
        b /= 2;
        cnt++;
    }g

    while(b % 3 == 0){
        b /= 3;
        cnt++;
    }

    if(b != 1){
        cout << -1;
        return 0;
    }
    cout << cnt;
}
