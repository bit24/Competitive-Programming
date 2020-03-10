#include "bits/stdc++.h"

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

namespace debug {
    const int DEBUG = true;

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x);

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x);

    template<class T>
    void pr(const vector<T> &x);

    template<class T>
    void pr(const set<T> &x);

    template<class T1, class T2>
    void pr(const map<T1, T2> &x);

    template<class T>
    void pr(const T &x) { if (DEBUG) cout << x; }

    template<class T, class... Ts>
    void pr(const T &first, const Ts &... rest) { pr(first), pr(rest...); }

    template<class T1, class T2>
    void pr(const pair<T1, T2> &x) { pr("{", x.f, ", ", x.s, "}"); }

    template<class T>
    void prIn(const T &x) {
        pr("{");
        bool fst = 1;
        for (auto &a : x) {
            pr(fst ? "" : ", ", a), fst = 0;
        }
        pr("}");
    }

    template<class T, size_t SZ>
    void pr(const array<T, SZ> &x) { prIn(x); }

    template<class T>
    void pr(const vector<T> &x) { prIn(x); }

    template<class T>
    void pr(const set<T> &x) { prIn(x); }

    template<class T1, class T2>
    void pr(const map<T1, T2> &x) { prIn(x); }

    void ps() { pr("\n"), cout << flush; }

    template<class Arg, class... Args>
    void ps(const Arg &first, const Args &... rest) {
        pr(first, " ");
        ps(rest...);
    }
}
using namespace debug;


const ll INF = 1e15;

int H, W;
ll A, B, C;
int N;

vector<pi> pts;

pi st;
pi ed;

ll distP[600][600];

int dI[] = {-1, 0, 1, 0};
int dJ[] = {0, -1, 0, 1};

bool valid(int i, int j) {
    return 0 <= i && i < H && 0 <= j && j < W;
}

void bfs() {
    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            distP[i][j] = INF;
        }
    }

    queue<pi> q;

    for (pi x :pts) {
        distP[x.f][x.s] = 0;
        q.push(x);
    }

    while (q.size()) {
        pi x = q.front();
        q.pop();

        ll cDist = distP[x.f][x.s];

        for (int d = 0; d < 4; d++) {
            int nI = x.f + dI[d], nJ = x.s + dJ[d];
            if (valid(nI, nJ)) {
                if (distP[nI][nJ] == INF) {
                    distP[nI][nJ] = cDist + 1;
                    q.push({nI, nJ});
                }
            }
        }
    }
}

inline int lin(int i, int j, int t) {
    return W * i + j + H * W * t;
}

const int MAXV = 501 * 501 * 3 + 10;
vector<pl> aL[MAXV];
ll dist[MAXV];

ll dijkstra(int sV, int eV) {
    fill(dist, dist + MAXV, INF);

    priority_queue<pl, vector<pl>, greater<pl>> q;

    q.push({0, sV});
    dist[sV] = 0;

    while (q.size()) {
        pl x = q.top();
        q.pop();

        ll cD = x.f;
        int cV = x.s;

        if (cV == eV) {
            return cD;
        }

        if (cD > dist[cV]) {
            continue;
        }


        for (pl aE : aL[cV]) {
            int aV = aE.f;
            ll eC = aE.s;

            if (cD + eC < dist[aV]) {
                dist[aV] = cD + eC;
                q.push({cD + eC, aV});
            }
        }
    }
//    ps(dist[eV]);
    assert(false);
    return -1;
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    cin >> H >> W;
    H++, W++;
    cin >> A >> B >> C;
    cin >> N;

//    ps('h');

    for (int i = 0; i < N; i++) {
        int x, y;
        cin >> x >> y;
        pts.pb({x, y});
    }
    st = pts[0];
    ed = pts[N - 1];

//    ps("read");

    sort(pts.begin(), pts.end());
    pts.erase(unique(pts.begin(), pts.end()), pts.end());

    N = pts.size();

    bfs();

//    ps("fin bfs");

    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            for (int d = 0; d < 4; d++) {
                int nI = i + dI[d], nJ = j + dJ[d];
                if (valid(nI, nJ)) {
                    // kick
                    int t = d & 1;
                    assert(t != 2);
                    aL[lin(i, j, t)].pb({lin(nI, nJ, t), A});
                }
            }

            for (int t = 0; t < 2; t++) {
                // kick the ball
                aL[lin(i, j, 2)].pb({lin(i, j, t), B});

                // ball stop, person run to it
                aL[lin(i, j, t)].pb({lin(i, j, 2), distP[i][j] * C});
            }


            // run with ball
            for (int d = 0; d < 4; d++) {
                int nI = i + dI[d], nJ = j + dJ[d];

                if (valid(nI, nJ)) {
                    aL[lin(i, j, 2)].pb({lin(nI, nJ, 2), C});
                }
            }
//            if (i % 10 && j == 0) ps(i, j);
        }
    }

//    ps("added");

    int sV = lin(st.f, st.s, 2);
    int eV = lin(ed.f, ed.s, 2);

    ll ans = dijkstra(sV, eV);

    cout << ans << endl;
}