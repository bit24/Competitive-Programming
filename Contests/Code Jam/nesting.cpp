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

string txt, ans;

int main() {
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        cin >> txt;
        ans = "";

        int depth = 0;

        for (int i = 0; i < txt.size(); i++) {
            int cD = txt[i] - '0';

            while (depth < cD) {
                ans += '(';
                depth++;
            }
            while (depth > cD) {
                ans += ')';
                depth--;
            }
            ans += txt[i];
        }

        while (depth > 0) {
            ans += ')';
            depth--;
        }
        cout << "Case #" << cT << ": " << ans << endl;
    }
}