#include <bits/stdc++.h>

using namespace std;
typedef long long ll;
typedef pair<int, int> pi;
typedef vector<int> vi;

#define pb push_back
#define f first
#define s second

int N, K;
ll M;

struct state {
    int inert = 0;
    vi half;
    vector<pi> full;

    constexpr bool operator<(const state &o) {
        if (inert != o.inert) {
            return inert < o.inert;
        }
        if (half.size() != o.half.size()) {
            return half.size() < o.half.size();
        }
        if (full.size() != o.full.size()) {
            return full.size() < o.full.size();
        }
        if (half != o.half) {
            return half < o.half;
        }
        return full < o.full;
    }

    void shift() {
        vi nH;
        for (int x : half) {
            x++;
            if (x <= K) {
                nH.pb(x);
            } else {
                inert++;
            }
        }
        half = nH;

        vector<pi> nFull;
        for (pi x : full) {
            assert(x.f != x.s);
            assert(x.f >= 0 && x.s >= 0);
            assert(x.f <= K && x.s <= K);

            x.f++;
            x.s++;
            if (x.f > K) {
                half.pb(x.s);
            } else if (x.s > K) {
                half.pb(x.f);
            } else {
                nFull.pb(x);
            }
        }
        full = nFull;

        sort(half.begin(), half.end());
        sort(full.begin(), full.end());

//        assert(false);
        assert(inert <= 100);
        assert(full.size() <= 4);
        assert(half.size() <= 10);

        for (pi x : full) {
            assert(x.f < x.s);
        }
    }
};

void eraseFirst(vi &a, int x) {
    for (int i = 0; i < a.size(); i++) {
        if (a[i] == x) {
            a.erase(a.begin() + i);
            break;
        }
    }
}

void eraseFirst(vector<pi> &a, pi x) {
    for (int i = 0; i < a.size(); i++) {
        if (a[i] == x) {
            a.erase(a.begin() + i);
            break;
        }
    }
}

int NS = 1;
map<state, int> stateMap;

state states[100000];

ll cDP[100000];
ll nDP[100000];

int match = 0;

int getSI(state &s) {
    int &loc = stateMap[s];

    if(loc == 0){
        states[NS] = s;
        loc = NS++;
        return NS-1;
    }
    else{
        match++;
        return loc;
    }
}

int main() {
    cin >> N >> K >> M;

    states[NS] = state();
    stateMap.insert({states[0], NS++});

    cDP[0] = 1;

    for (int c = 1; c <= N; c++) {
//        pr("c");
//        pr(c);
        for (int cSI = 0; cSI < NS; cSI++) {
            ll cCnt = cDP[cSI];
            if (cCnt == 0) continue;

//            pr("cSI");
//            pr(cSI);

            state &cS = states[cSI];

            // case 1: insert into inert hazard
            if (cS.inert > 0) {
                state nS = cS;
                nS.inert--;

                nS.shift();

                int nSI = getSI(nS);
                nDP[nSI] = (nDP[nSI] + cCnt * cS.inert) % M;
            }

            // case 2: insert into half hazard
            for (int cH : cS.half) {
                state nS = cS;
                eraseFirst(nS.half, cH);
                assert(nS.half.size() == cS.half.size() - 1);

                // becomes a full
                nS.full.pb({0, cH});

                nS.shift();

                int nSI = getSI(nS);
                nDP[nSI] = (nDP[nSI] + cCnt) % M;
            }

            // case 3: insert into full hazard
            for (pi &cF : cS.full) {
                state nS = cS;
                eraseFirst(nS.full, cF);
                assert(nS.full.size() == cS.full.size() - 1);

                // becomes two fulls
                nS.full.pb({0, cF.f});
                nS.full.pb({0, cF.s});

                nS.shift();

                int nSI = getSI(nS);
                nDP[nSI] = (nDP[nSI] + cCnt) % M;
            }

            int ACT = min(K, c - 1);

            // case 4: insert to the side of a number without a prexisting hazard, creating a hazard
            int free[6];
            for (int i = 1; i <= ACT; i++) free[i] = 2;

            for (pi &x : cS.full) {
                free[x.f]--;
                free[x.s]--;
            }
            for (int x : cS.half) {
                free[x]--;
            }

            for (int i = 1; i <= ACT; i++) {
                if (free[i] == 0) continue;
                state nS = cS;

                //becomes one full
                nS.full.pb({0, i});

                nS.shift();

                int nSI = getSI(nS);
                nDP[nSI] = (nDP[nSI] + cCnt * free[i]) % M;
            }

            // case 4: insert away from an existing number and is not an inert hazard
            int rem = (c - 1) + 1 - (ACT * 2 - cS.full.size()) - cS.inert;
//            pr("rem");
//            pr(rem);
//            pr(cS.full.size());
//            pr(cS.inert);
            assert(rem >= 0);

            state nS = cS;
            nS.shift();

            int nSI = getSI(nS);
            nDP[nSI] = (nDP[nSI] + cCnt * rem) % M;
        }

        for (int i = 0; i < NS; i++) {
            cDP[i] = nDP[i];
            nDP[i] = 0;

//            if (cDP[i] > 0) {
//                cout << "dp: " << i << " " << cDP[i] << endl;
//            }
        }
    }

    state fState;
    int fSI = getSI(fState);
    assert(fSI == 1);

    ll ans = cDP[fSI];
    cout << ans << endl;
//    cout << NS << endl;
//    cout << match << endl;
}

//int main(){
//    while(true){
//        main0();
//    }
//}