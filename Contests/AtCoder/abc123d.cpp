#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back

struct State {
    int aI;
    int bI;
    int cI;
    ll cost;

    bool operator<(State o) const {
        if (cost != o.cost) {
            return cost < o.cost;
        }
        if (aI != o.aI) {
            return aI < o.aI;
        }
        if (bI != o.bI) {
            return bI < o.bI;
        }
        return cI < o.cI;
    }
};

ll a[1005];
ll b[1005];
ll c[1005];

int main() {
    int X, Y, Z, K;
    cin >> X >> Y >> Z >> K;

    for (int i = 0; i < X; i++) {
        cin >> a[i];
    }
    for (int i = 0; i < Y; i++) {
        cin >> b[i];
    }
    for (int i = 0; i < Z; i++) {
        cin >> c[i];
    }
    sort(a, a + X, greater<ll>());
    sort(b, b + Y, greater<ll>());
    sort(c, c + Z, greater<ll>());

    priority_queue<State, vector<State>> queue;
    set<State> inQ;

    State sState = {0, 0, 0, a[0] + b[0] + c[0]};
    queue.push(sState);
    inQ.insert(sState);

    for (int i = 0; i < K; i++) {
        State cS = queue.top();
        queue.pop();
        cout << cS.cost << '\n';

        State nS;
        if (cS.aI + 1 < X) {
            nS = {cS.aI + 1, cS.bI, cS.cI, a[cS.aI + 1] + b[cS.bI] + c[cS.cI]};
            if (inQ.find(nS) == inQ.end()) {
                queue.push(nS);
                inQ.insert(nS);
            }
        }
        if (cS.bI + 1 < Y) {
            nS = {cS.aI, cS.bI + 1, cS.cI, a[cS.aI] + b[cS.bI + 1] + c[cS.cI]};
            if (inQ.find(nS) == inQ.end()) {
                queue.push(nS);
                inQ.insert(nS);
            }
        }
        if (cS.cI + 1 < Z) {
            nS = {cS.aI, cS.bI, cS.cI + 1, a[cS.aI] + b[cS.bI] + c[cS.cI + 1]};
            if (inQ.find(nS) == inQ.end()) {
                queue.push(nS);
                inQ.insert(nS);
            }
        }
    }
    cout << flush;
}