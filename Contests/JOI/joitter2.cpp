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

const int MAXN = 1e5 + 10;

int anc[MAXN];

void initDSU() {
    for (int i = 0; i < MAXN; i++) {
        anc[i] = i;
    }
}

int fRt(int i) {
    return anc[i] == i ? i : anc[i] = fRt(anc[i]);
}

bool merge(int a, int b) {
    a = fRt(a), b = fRt(b);
    if (a == b) {
        return false;
    }
    anc[a] = b;
    return true;
}

int N, M;

ll size[MAXN];

set<int> out[MAXN];
set<int> in[MAXN];
set<int> din[MAXN];

int qd[MAXN];

int cT = 1;

ll sum = 0;

void mergeGroups(int gA, int gB) {
    queue<int> q;
    q.push(gB);
    qd[gA] = cT;
    qd[gB] = cT;

    while (q.size()) {
        gB = q.front();
        q.pop();

//        ps("merging", gA, gB);
//        ps(fRt(gA), fRt(gB));
        assert(gA == fRt(gA) && gB == fRt(gB));

//        ps(q.size());

        if (size[gA] > size[gB]) {
            swap(gA, gB);
        }

        for (int x : out[gA]) {
            if (qd[x] != cT && in[gB].count(x)) {
                q.push(x);
                qd[x] = cT;
            }
        }

        for (int x : in[gA]) {
            if (qd[x] != cT && out[gB].count(x)) {
                q.push(x);
                qd[x] = cT;
            }
        }

//        ps("new size", q.size());

        // actual merging
        sum -= din[gA].size() * size[gA] + din[gB].size() * size[gB];

        size[gB] += size[gA];

        // rename gA into gB, do not for distinct
        for (int x : out[gA]) {
            in[x].erase(gA);
            in[x].insert(gB);
        }

        for (int x : in[gA]) {
            out[x].erase(gA);
            out[x].insert(gB);
        }

//        if (gA == 3 && gB == 4) {
//            ps("flag");
//            ps(in[3]);
//            ps(out[3]);
//            ps(in[4]);
//            ps(out[4]);
//        }

//        ps("cleaned");

        // update gB lists
        out[gB].insert(out[gA].begin(), out[gA].end());
        in[gB].insert(in[gA].begin(), in[gA].end());

//        ps(in[gB]);

//        ps("added");

        in[gB].erase(gA);
        out[gB].erase(gA);

        in[gB].erase(gB);
        out[gB].erase(gB);

//        ps("erased");

        din[gB].insert(din[gA].begin(), din[gA].end());

//        ps("added 2");
        sum += din[gB].size() * size[gB];

//        ps("pre merge");
        merge(gA, gB);

        out[gA].clear();
        in[gA].clear();
        din[gA].clear();
        size[gA] = 0;

//        ps("merged", gA, gB);

//        for (int i = 0; i < N; i++) {
//            ps("in", i, in[i]);
//            ps("out", i, out[i]);
//        }

        gA = gB;
    }
    cT++;
}

void addCheck(int a, int b) {
    int gA = fRt(a);
    int gB = fRt(b);

//    if (gA == 4 && gB == 2) {
//        for (int i = 0; i < N; i++) {
//            ps("in", i, in[i]);
//            ps("out", i, out[i]);
//        }
//    }

    if (gA == gB) {
        return;
    }

    out[gA].insert(gB);
    in[gB].insert(gA);

    if (!din[gB].count(a)) {
        sum += size[gB];
        din[gB].insert(a);
    }

    if (out[gB].count(gA)) {
//        ps("change");
//        ps(gA, gB);
        assert(in[gA].count(gB));
        mergeGroups(gA, gB);
    } else {
//        ps("no change");
        assert(!in[gA].count(gB));
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> N >> M;

    initDSU();

    sum = N;

    memset(qd, 0, sizeof(qd));
    fill(size, size + N, 1);

    for (int i = 0; i < N; i++) {
        din[i].insert(i); // this simplifies intra-group counting, we'll undo over counting later
    }

    for (int i = 0; i < M; i++) {
//        ps(i);
        int a, b;
        cin >> a >> b;
        a--, b--;

        addCheck(a, b);
        ll ans = sum - N;
        cout << ans << endl;
    }
}