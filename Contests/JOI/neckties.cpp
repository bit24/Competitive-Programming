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

const int MAXN = 2e5 + 10;

pi fr[MAXN];
int orig[MAXN];

int st[MAXN][2];
int odd[MAXN][2];

int ans[MAXN];

int main() {
    int N;
    cin >> N;

    for (int i = 1; i <= N + 1; i++) {
        int x;
        cin >> x;
        fr[i] = {x, i};
    }
    sort(fr + 1, fr + N + 2);

    for (int i = 1; i <= N; i++) {
        cin >> orig[i];
    }
    sort(&orig[1], &orig[N + 1]);

    st[0][0] = st[0][1] = 0;
    st[N + 1][0] = st[N + 1][1] = 0;

    for (int i = 1; i <= N; i++) {
        st[i][0] = max(fr[i].f - orig[i], 0);
        st[i][1] = max(fr[i + 1].f - orig[i], 0);
    }

    for (int i = 1; i <= N; i++) {
        odd[i][0] = max(st[i][0], odd[i - 1][0]);
    }

    for (int i = N; i >= 1; i--) {
        odd[i][1] = max(st[i][1], odd[i + 1][1]);
    }

    for (int i = 1; i <= N + 1; i++) {
        int k = fr[i].s;
        ans[k] = max(odd[i - 1][0], odd[i][1]);
    }

    cout << ans[1];
    for (int i = 2; i <= N + 1; i++) {
        cout << " " << ans[i];
    }
    cout << endl;
}