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

int main() {
    string str;
    cin >> str;

    ll s0 = 0;
    ll s1 = 0;
    ll s2 = 0;

    for (int i = 0; i < str.length(); i++) {
        if (str[i] == 'v') {
            if (i > 0 && str[i - 1] == 'v') {
                s2 += s1;
                s0++;
            }
        }
        else{
            s1 += s0;
        }
    }
    cout << s2 << endl;
}