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
    int N = str.length();
    vector<char> pal;

    char ext = '-';

    for (int i = 0; i < N / 2; i += 2) {
        int j = N - 1 - i - 1;

        if (i + 1 >= j) {
            ext = str[i];
            break;
        }

        if (str[i] == str[j] || str[i] == str[j + 1]) {
            pal.pb(str[i]);
        } else {
            pal.pb(str[i + 1]);
        }
    }
    for (char c : pal) {
        cout << c;
    }
    if (ext != '-') {
        cout << ext;
    }
    for (int i = pal.size() - 1; i >= 0; i--) {
        cout << pal[i];
    }
    cout << endl;
}