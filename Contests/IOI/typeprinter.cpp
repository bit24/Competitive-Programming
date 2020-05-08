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

const int MAXN = 3e4;

string strs[MAXN];

int lcp(string a, string b) {
    for (int i = 0; i < a.size() && i < b.size(); i++) {
        if (a[i] != b[i]) {
            return i;
        }
    }
    return min(a.size(), b.size());
}

string longest;

// special comparator that reorders characters in a way such that longest string will go last
bool specComp(string a, string b) {
    for (int i = 0; i < a.size() && i < b.size(); i++) {
        if (a[i] == b[i]) {
            continue;
        }
        if (a[i] == longest[i]) {
            return false;
        }
        if (b[i] == longest[i]) {
            return true;
        }
        return a[i] < b[i];
    }
    return a.size() < b.size();
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int N;
    cin >> N;
    strs[0] = "";
    for (int i = 1; i <= N; i++) {
        cin >> strs[i];
    }
    sort(strs, strs + N + 1);

    longest = "";
    for (int i = 1; i <= N; i++) {
        if (strs[i].size() > longest.size()) {
            longest = strs[i];
        }
    }

    sort(strs, strs + N + 1, specComp);

    int count = 0;
    string output = "";
    for (int i = 1; i <= N; i++) {
        int cLCP = lcp(strs[i - 1], strs[i]);
        for (int j = 0; j < strs[i - 1].size() - cLCP; j++) {
            count++;
            output += "-\n";
        }
        for (int j = cLCP; j < strs[i].size(); j++) {
            count++;
            output += strs[i][j];
            output += "\n";
        }
        count++;
        output += "P\n";
    }
    cout << count << endl;
    cout << output << flush;
}