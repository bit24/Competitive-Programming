#include <bits/stdc++.h>

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

struct it {
    int f, s, oI;
};

bool cmp(it x, it y) {
    return x.s < y.s;
}

vector<it> a;
vector<it> b;

vi process(vector<it> a) {
    sort(a.begin(), a.end(), cmp);
    reverse(a.begin(), a.end());

    int cS = 1e9;
    vi ord;
    for(int i = 0; i < a.size(); i++){
        if(cS > a[i].f){
            ord.pb(a[i].oI);
            cS = a[i].s;
        }
    }

    return ord;
}

int main() {
    int N;
    cin >> N;

    for (int i = 1; i <= N; i++) {
        int x, y;
        cin >> x >> y;
        if (x < y) {
            a.pb({x, y, i});
        } else if (x > y) {
            b.pb({x, y, i});
        }
    }

    vi ord1 = process(a);

    a.clear();
    for (it x : b) {
        a.pb({-x.f, -x.s, x.oI});
    }

    vi ord2 = process(a);

    if (ord1.size() >= ord2.size()) {
        cout << ord1.size() << '\n';
        for (int i : ord1) {
            cout << i << " ";
        }
    } else {
        cout << ord2.size() << '\n';
        for (int i : ord2) {
            cout << i << " ";
        }
    }
    cout << endl;
}