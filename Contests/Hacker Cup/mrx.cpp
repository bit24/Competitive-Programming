#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<pi, int> ppii;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

string line;

int i;

pi process() {
    // cout << i << endl;
    if (line[i] != '(') {
        if (line[i] == 'x') {
            i++;
            return {0, 1};
        }
        if (line[i] == 'X') {
            i++;
            return {1, 0};
        }
        if (line[i] == '0') {
            i++;
            return {0, 0};
        }
        if (line[i] == '1') {
            i++;
            return {1, 1};
        }
        cout << "WHAT???" << endl;
        return {-1, -1};
    }
    i++;

    pi left = process();

    char op = line[i];
    // cout << "op:" << op << endl;

    i++;
    pi right = process();

    if (line[i] != ')') {
        cout << "???WHAT" << endl;
    }
    i++;

    if (op == '|') {
        return {(left.f | right.f), (left.s | right.s)};
    }
    if (op == '&') {
        return {(left.f & right.f), (left.s & right.s)};
    }
    if (op == '^') {
        return {(left.f ^ right.f), (left.s ^ right.s)};
    }
    cout << "??WHAT??" << endl;
    return {-1, -1};
}

int main() {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        cin >> line;
        // cout << "line: " << line << endl;
        i = 0;
        pi res = process();
        cout << "Case #" << cT << ": ";
        if (res.f == res.s) {
            cout << 0 << endl;
        } else {
            cout << 1 << endl;
        }
    }
}