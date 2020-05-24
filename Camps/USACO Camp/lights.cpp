#include <bits/stdc++.h>

using namespace std;

const int MAXN = 2010;

typedef vector<int> vi;

bitset<MAXN> bits[MAXN];

int N;

// procedure will minimize last bit first
vi elim() {
    int cR = 0;
    for (int i = 0; i < N; i++) {
//        cout << cR << endl;
        if (!bits[cR][i + 1]) {
//            cout << "inside" << endl;
            bool found = false;

            // need to look for one to swap
            for (int j = cR + 1; j < N; j++) {
                if (bits[j][i + 1]) {
//                    cout << "found" << j << endl;
                    found = true;
                    swap(bits[cR], bits[j]);
                    break;
                }
            }
//            cout << found << endl;
            if (!found) {
//                cout << "continuing" << endl;
                continue;
            }
        }

        for (int j = cR + 1; j < N; j++) {
            if (bits[j][i + 1]) {
                bits[j] ^= bits[cR];
            }
        }
        cR++;
    }
    // now should be row echelon form

    vi vals(N, 0);
    cR--;

    for (; cR >= 0; cR--) {
        int first = N;
        for (int i = 0; i < N; i++) {
            if (bits[cR][i + 1]) {
                first = i;
                break;
            }
        }
        if (first == N) continue;

        int cV = bits[cR][0];

        for (int i = first + 1; i < N; i++) {
            if (bits[cR][i + 1]) {
                cV ^= vals[i];
            }
        }
        vals[first] = cV;
    }

    return vals;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> bits[i];

        bits[i] <<= 1;
        bits[i] |= 1;
    }

    vi res = elim();
    for (int i = N - 1; i >= 0; i--) {
        cout << res[i];
    }
    cout << endl;
}