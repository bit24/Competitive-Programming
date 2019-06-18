#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

int main() {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int T;
    cin >> T;

    for (int cT = 1; cT <= T; cT++) {
        string line;
        cin >> line;

        int spaces = line.size() - 1;
        int bCnt = 0;

        for (int i = 1; i < line.size(); i++) {
            if (line[i] == 'B') {
                bCnt++;
            }
        }
        cout << "Case #" << cT << ": ";
        if (bCnt == spaces) {
            cout << "N" << endl;
            continue;
        }
        if (bCnt * 2 >= spaces) {
            cout << "Y" << endl;
        } else {
            cout << "N" << endl;
        }
    }
}