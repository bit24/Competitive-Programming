#include <bits/stdc++.h>

typedef long long ll;

using namespace std;

int a[300000];

int main() {
    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> a[i];
    }

    int ans = 0;
    int cur = 0;

    for (int j = 0; j < 2; j++) {
        for (int i = 0; i < N; i++) {
            if (!a[i]) {
                cur = 0;
            } else {
                cur++;
                ans = max(ans, cur);
            }
        }
    }

    cout << ans;
}