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
        if (str[i] != 'a') {
            c++;
        }
    }

    if (c & 1) {
        cout << ":(" << endl;
        return 0;
    }

    c /= 2;

    string noA = str.substr(N - c, c);

    int j = 0;
    for (int i = 0; i < N - c; i++) {
        if (str[i] != 'a') {
            if (j >= noA.length() || str[i] != noA[j++]) {
                cout << ":(" << endl;
                return 0;
            }
        }
    }

    cout << str.substr(0, N - c) << endl;
}