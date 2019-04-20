#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

int main() {
    string str;
    cin >> str;

    int N = str.length();

    int c = 0;
    for (int i = 0; i < N; i++) {
        if (str[i] == 'a') {
            c++;
        }
    }

    cout << min(N, 2 * c - 1) << endl;
}