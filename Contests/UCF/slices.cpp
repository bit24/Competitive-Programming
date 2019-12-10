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
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        int N, K;
        cin >> N >> K;
        string name;
        int l;

        int a = 0, b = 0;

        bool aH = false, bH = false;

        for (int i = 0; i < N; i++) {
            cin >> name >> l;
            if (name == "Kelly") {
                a++;
            } else {
                b++;
            }

            if (a - b > K) {
                bH = true;
            }
            if (b - a > K) {
                aH = true;
            }
        }

        if (aH) {
            if (bH) {
                cout << "Their friendship is doomed" << endl;
            } else {
                cout << "Kelly hates Jim" << endl;
            }
        } else {
            if (bH) {
                cout << "Jim hates Kelly" << endl;
            } else {
                cout << "Everything is good" << endl;
            }
        }
    }
}