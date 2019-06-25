#include <bits/stdc++.h>
#include "trilib.h"

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

//
//int get_n();
//
//bool is_clockwise(int a, int b, int c);
//
//void give_answer(int s);

int N;
int REFA;
int REFB;

vector<int> above;

bool cmpLess(int x, int y) {
    if(x == REFA){
        return !above[y];
    }
    if(y == REFA){
        return above[x];
    }

    if (above[x] & !above[y]) {
        return true;
    }
    if (!above[x] & above[y]) {
        return false;
    }
    return is_clockwise(x, REFA, y);
}

int main() {
    N = get_n();

    REFA = 1;
    REFB = 2;
    above.resize(N + 1);

    above[REFB] = true;
    for (int i = 3; i <= N; i++) {
        above[i] = is_clockwise(REFB, REFA, i);
    }
    // ps(above);

    vi pts;
    for (int i = 1; i <= N; i++) {
        pts.pb(i);
    }

    random_shuffle(pts.begin(), pts.end());

    // by definition REFA is first, and REFB is second
    sort(pts.begin(), pts.end(), cmpLess);
    // ps(pts);

    vi stk;
    for (int i = 0; i < N; i++) {
        while (stk.size() >= 2 && is_clockwise(stk[stk.size() - 2], stk[stk.size() - 1], pts[i])) {
            stk.pop_back();
        }
        stk.pb(pts[i]);
    }

    vi fHull(stk);
    // ps(fHull);

    vector<bool> removed(N + 1, false);
    for (int i = 0; i < N; i++) {
        while (stk.size() >= 2 && is_clockwise(stk[stk.size() - 2], stk[stk.size() - 1], pts[i])) {
            removed[stk[stk.size() - 1]] = true;
            stk.pop_back();
        }
        stk.pb(pts[i]);
    }

    int cnt = 0;
    for (int hMem : fHull) {
        if (!removed[hMem]) {
            cnt++;
        }
    }
    give_answer(cnt);
}