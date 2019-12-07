#include <bits/stdc++.h>
#include "rail.h"

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

int N;

vi getFrom(int src) {
    vi dist(N);

    for (int i = 0; i < N; i++) {
       if(i == src){
           dist[i] = 0;
       }
       else{
           dist[i] = getDistance(src, i);
       }
    }
    return dist;
}

void findLocation(int n, int first, int location[], int stype[]) {
    N = n;

    vi dist0 = getFrom(0);


    int minI = -1;
    for (int i = 1; i < N; i++) {
        if (minI == -1 || dist0[i] < dist0[minI]) {
            minI = i;
        }
    }

    int r = minI; // r is going up
    vi distR = getFrom(r);

    minI = -1;
    for (int i = 0; i < N; i++) {
        if (i == r) continue;
        if (minI == -1 || distR[i] < distR[minI]) {
            minI = i;
        }
    }

    int l = minI;

    vi distL(N);
    distL[l] = 0;

    for (int i = 0; i < N; i++) {
        if (i != l) {
            distL[i] = getDistance(i, l);
        }
    }

    vi side(N);

    for (int i = 0; i < N; i++) {
        if (i != l && i != r) {
            if (distL[i] < distR[i]) {
                side[i] = 1;
            } else {
                side[i] = 0;
            }
        }
    }
    side[l] = -1;
    side[r] = -1;
    stype[l] = 1; //down
    stype[r] = 2; //up
    location[r] = first + dist0[r];
    location[l] = location[r] + distR[l];

    vi nextO;

    for (int i = 0; i < N; i++) {
        nextO.pb((distL[i] - (dist));
    }

    vector<pi> lDists, rDists;

    for (int i = 0; i < N; i++) {
        if (side[i] == 0) {
            lDists.pb({distR[i], i});
        } else {
            rDists.pb({distL[i], i});
        }
    }

    assert(l == 0);

    for (int i = 0; i < N; i++) {
        if (side[i] == 0) {
            stype[i] = 1;
            location[i] = first + distL[r] - distR[i];
        } else {
            stype[i] = 2;
            location[i] = first + distL[i];
        }
    }
}