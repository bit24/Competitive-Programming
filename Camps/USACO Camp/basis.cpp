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

ll toBinary(string s) {
    ll ret;
    for (int i = 0; i < s.size(); i++) {
        ret <<= 1;
        ret += (s[i] == '1');
    }
    return ret;
}

int main() {
    int N;
    cin >> N;
    vector<ll> x(N);

    for (int i = 0; i < N; i++) {
        cin >> x[i];
    }

    vector<ll> basis; // list of basis elements, initially empty

    for (ll a: x) {
        ll A = a;
        for (ll b: basis) {
            A = min(A, A ^ b);
        }
        if (A) {
            int ind = 0;
            while (ind < basis.size() && basis[ind] > A) {
                ind++;
            }
            basis.insert(basis.begin() + ind, A);
        }
    }

    ll res = (1ll << basis.size()) - N;
    cout << res << endl;
}