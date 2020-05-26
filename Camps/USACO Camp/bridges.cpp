#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef vector<int> vi;
typedef pair<int, int> pi;

#define pb push_back
#define f first
#define s second

const int MAXN = 1e5 + 10;

ll h[MAXN];
vi ch[MAXN];

int N;

vector<pair<ll, pi>> edges;

map<int, int> anc;

void dfs(int cV) {
    // find closest above and below

    auto ge = anc.lower_bound(h[cV]);
    if (ge != anc.end()) {
        pi above = *ge;
        edges.pb({above.f - h[cV], {cV, above.s}});
    }

    auto le = anc.upper_bound(h[cV]);
    if (le != anc.begin()) {
        --le;
        pi below = *le;
        edges.pb({h[cV] - below.f, {cV, below.s}});
    }

    bool added = false;
    if (anc.count(h[cV]) == 0) {
        anc.insert({h[cV], cV});
        added = true;
    }

    for (int aV : ch[cV]) {
        dfs(aV);
    }

    if (added) {
        anc.erase(h[cV]);
    }
}

int par[MAXN];

int fRoot(int x) {
    return x == par[x] ? x : (par[x] = fRoot(par[x]));
}

bool merge(int a, int b) {
    a = fRoot(a);
    b = fRoot(b);
    if (a == b) return false;
    par[a] = b;
    return true;
}

int main() {
    cin >> N;
    for (int i = 0; i < N; i++) {
        cin >> h[i];
    }

    for (int i = 1; i < N; i++) {
        int p;
        cin >> p;
        p--;
        ch[p].pb(i);
    }

    dfs(0);

    for (int i = 0; i < N; i++) {
        par[i] = i;
    }

    sort(edges.begin(), edges.end());

    ll cost = 0;
    for (auto e : edges) {
        assert(e.f >= 0);
        if (merge(e.s.f, e.s.s)) {
            cost += e.f;
        }
    }
    cout << cost << endl;
}