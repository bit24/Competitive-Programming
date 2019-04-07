#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back


int main() {
    vi a;
    int ans = 0;
    for (int i = 0; i < 5; i++) {
        int c;
        cin >> c;
        a.pb(c);
    }


    int sRem = 10;
    for (int i = 0; i < a.size(); i++) {
        ans += ((a[i] + 9) / 10) * 10;
        int cRem = a[i] % 10;
        if(cRem != 0){
            sRem = min(sRem, cRem);
        }
    }

    ans -= 10 - sRem;
    cout << ans << endl;
}