#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;

typedef pair<int, int> pi;
typedef pair<ll, ll> pl;
typedef pair<ld, ld> pd;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

vi type[26];

int main() {
    int N;
    cin >> N;

    string l;
    cin >> l;

    vi lWild;
    for (int i = 0; i < N; i++) {
        int cChar = l[i];
        if (cChar == '?') {
            lWild.pb(i);
        } else {
            type[cChar - 'a'].pb(i);
        }
    }


    string r;
    cin >> r;

    vector<pi> ans;

    vi rWild;
    for (int i = 0; i < N; i++) {
        int cChar = r[i];
        if (cChar == '?') {
            rWild.pb(i);
        } else {
            if (!type[cChar - 'a'].empty()) {
                ans.pb({type[cChar - 'a'].back(), i});
                type[cChar - 'a'].pop_back();
            } else {
                if (!lWild.empty()) {
                    ans.pb({lWild.back(), i});
                    lWild.pop_back();
                }
            }
        }
    }

    while (!lWild.empty() && !rWild.empty()) {
        ans.pb({lWild.back(), rWild.back()});
        lWild.pop_back();
        rWild.pop_back();
    }


    for (int i = 0; i < 26; i++) {
        while (!rWild.empty() && !type[i].empty()) {
            ans.pb({type[i].back(), rWild.back()});
            type[i].pop_back();
            rWild.pop_back();
        }
    }


    cout << ans.size() << endl;

    for (auto i : ans) {
        cout << (i.f + 1) << " " << (i.s + 1) << endl;
    }
}