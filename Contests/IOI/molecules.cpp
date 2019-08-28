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

vi find_subset(int L, int U, vi w) {
    int N = w.size();
    vector<pi> tags;
    for (int i = 0; i < N; i++) {
        tags.pb({w[i], i});
    }
    sort(tags.begin(), tags.end());

    sort(w.begin(), w.end());

    if (U < w[0]) {
        return {};
    }

    ll sum = 0;

    int nA;
    for (nA = 0; nA < N; nA++) {
        sum += w[nA];
        if (sum > U) {
            sum -= w[nA];
            break;
        }
    }
    assert(sum <= U);

    if (sum >= L) {
        vi ans;
        for (int i = 0; i < nA; i++) {
            ans.pb(tags[i].s);
        }
        return ans;
    }

    assert(sum < L);

    if (nA == N) {
        return {};
    }

    int last = N - 1;

    for (int i = nA - 1; i >= 0; i--) {
        assert(sum - w[i] + w[last] <= U);
        sum -= w[i];
        sum += w[last];

        if (sum >= L) {
            assert(sum <= U);
            vi ans;
            for (int j = 0; j < i; j++) {
                ans.pb(tags[j].s);
            }
            for (int j = last; j < N; j++) {
                ans.pb(tags[j].s);
            }
            return ans;
        }
        last--;
    }
    return {};
}