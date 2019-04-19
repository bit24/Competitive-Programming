#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

char ans[400000];

int main() {
    ios_base::sync_with_stdio(false);
    int N;
    cin >> N;
    string s;
    cin >> s;

    int b, o;

    for (int i = 0; i < N; i++) {
        if (s[i] == '(') {
            b++;
        } else if (s[i] == ')') {
            b--;
        } else {
            o++;
        }
    }

    int r = 0;
    for (int i = 0; i < N; i++) {
        if (s[i] != '?') {
            ans[i] = s[i];
            if (s[i] == '(') {
                r++;
                b--;
            } else {
                r--;
                b++;
                if (r < 0 || (r == 0 && i != N - 1)) {
                    cout << ":(" << endl;
                    return 0;
                }
            }
        } else {
            o--;
            if (r + b - o >= 0) {
                ans[i] = ')';
                r--;
                if (r < 0 || (r == 0 && i != N - 1)) {
                    cout << ":(" << endl;
                    return 0;
                }
            } else {
                ans[i] = '(';
                r++;
            }
        }
    }

    if (r != 0) {
        cout << ":(" << endl;
        return 0;
    }

    for (int i = 0; i < N; i++) {
        cout << ans[i];
    }
    cout << endl;
}