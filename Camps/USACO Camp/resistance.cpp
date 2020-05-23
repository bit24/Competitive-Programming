#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
typedef unordered_map<ll, int> MP;

#include <ext/pb_ds/assoc_container.hpp>

using namespace __gnu_pbds;

// set
typedef tree<
        ll,
        null_type,
        less_equal<ll>,
        rb_tree_tag,
        tree_order_statistics_node_update>
        ordered_set;

#define pb push_back
#define f first
#define s second

const int MAXN = 5e4 + 10;

vector<pi> aL[MAXN];

int v[MAXN];

int vis[MAXN];
int cnt[MAXN];

void fCnt(int cV, int pV) {
    cnt[cV] = 1;
    for (pi aE : aL[cV]) {
        int aV = aE.f;
        if (aV != pV && !vis[aV]) {
            fCnt(aV, cV);
            cnt[cV] += cnt[aV];
        }
    }
}

const ll BOUND = 2e15;
const ll ADD = 1e15;

MP bitA;
MP bitB;

void update0(MP &mp, ll i, int d) {
//    cout << "upd " << i << " " << d << endl;
    while (i < BOUND) {
        mp[i] += d;
        i += (i & -i);
    }
}

int query0(MP &mp, ll i) {
    int sum = 0;
    while (i > 0) {
        sum += mp[i];
        i -= (i & -i);
    }
    return sum;
}

void update(MP &mp, ll i, int d) {
    update0(mp, i + ADD, d);
}

int query(MP &mp, ll i) {
    return query0(mp, i + ADD);
}

int CSZ;

int fCent(int cV, int pV) {
    for (pi aE :aL[cV]) {
        int aV = aE.f;
        if (aV != pV && !vis[aV] && cnt[aV] > CSZ / 2) {
            return fCent(aV, cV);
        }
    }
    return cV;
}

ordered_set set1, set2;

ll ans = 0;

void process(int cV, int pV, ll rSum, int mode) {
    if (mode == 0) {
//        ans += query(bitA, v[cV] - rSum);
//        ans += query(bitB, -v[cV] - rSum);
        ans += set1.order_of_key(v[cV] - rSum+1);
        ans += set2.order_of_key(-v[cV] - rSum+1);
    } else {
//        update(bitA, v[cV] + rSum, mode);
//        update(bitB, -v[cV] + rSum, mode);
        set1.insert(v[cV] + rSum);
        set2.insert(-v[cV] + rSum);
    }

    for (pi aE : aL[cV]) {
        int aV = aE.f;
        int eC = aE.s;
        if (aV != pV && !vis[aV]) {
            process(aV, cV, rSum + eC, mode);
        }
    }
}

void decompose(int cV) {
    fCnt(cV, -1);
    CSZ = cnt[cV];
    int cent = fCent(cV, -1);

//    bitA.clear();
//    bitB.clear();
    set1.clear();
    set2.clear();

//    cout << cV << " " << cent << endl;

//    update(bitA, v[cent], 1);
//    update(bitB, -v[cent], 1);
    set1.insert(v[cent]);
    set2.insert(-v[cent]);

    for (pi aE : aL[cent]) {
        int aV = aE.f;
        int eC = aE.s;
        if (!vis[aV]) {
            process(aV, cent, eC, 0);
            process(aV, cent, eC, 1);
        }
    }

    vis[cent] = true;

    for (pi aE : aL[cent]) {
        int aV = aE.f;
        if (!vis[aV]) {
            decompose(aV);
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

//    set1.insert(3);
//    set1.insert(4);
//    set1.insert(5);
//    set1.insert(5);
//    cout << set1.order_of_key(6) << endl;

    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> v[i];
    }

    for (int i = 0; i < N - 1; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        a--, b--;
        aL[a].pb({b, c});
        aL[b].pb({a, c});
    }
    memset(vis, 0, sizeof(vis));
    decompose(0);
    cout << ans << endl;
}