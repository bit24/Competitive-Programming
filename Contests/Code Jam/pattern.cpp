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


string p[200];

bool matchSuf(string a, string b) {
    for (int i = 0; i < a.size() && i < b.size(); i++) {
        if (a[a.size() - i - 1] != b[b.size() - i - 1]) {
            return false;
        }
    }
    return true;
}

bool matchPre(string a, string b) {
    for (int i = 0; i < a.size() && i < b.size(); i++) {
        if (a[i] != b[i]) {
            return false;
        }
    }
    return true;
}

int lastIndex(string s, char c) {
    for (int i = s.size() - 1; i >= 0; i--) {
        if (s[i] == c) {
            return i;
        }
    }
    return -1;
}

int firstIndex(string s, char c) {
    for (int i = 0; i < s.size(); i++) {
        if (s[i] == c) {
            return i;
        }
    }
    return s.size();
}

int main() {
    int T;
    cin >> T;
    for (int cT = 1; cT <= T; cT++) {
        int N;
        cin >> N;

        string suf = "";

        for (int i = 0; i < N; i++) {
            cin >> p[i];
        }

        cout << "Case #" << cT << ": ";

        bool pos = true;

        for (int i = 0; i < N; i++) {
            int li = lastIndex(p[i], '*');

            string last = p[i].substr(li + 1);
            if (!matchSuf(suf, last)) {
                pos = false;
                break;
            }
            p[i] = p[i].substr(0, li);

            if (last.size() > suf.size()) {
                suf = last;
            }
        }

        if (!pos) {
            cout << '*' << endl;
            continue;
        }

//        for (int i = 0; i < N; i++) {
//            ps(p[i]);
//        }

        string pre = "";

        for (int i = 0; i < N; i++) {
            int fi = firstIndex(p[i], '*');

            string first = p[i].substr(0, fi);
            if (!matchPre(pre, first)) {
                pos = false;
                break;
            }
            p[i] = p[i].substr(fi);
            if (first.size() > pre.size()) {
                pre = first;
            }
        }

        if (!pos) {
            cout << '*' << endl;
            continue;
        }


//        for (int i = 0; i < N; i++) {
//            cout << i << " " << p[i] << endl;
//        }

        string cur = pre;

        for (int i = 0; i < N; i++) {
            p[i].erase(remove(p[i].begin(), p[i].end(), '*'), p[i].end());
            cur += p[i];
        }
        cur += suf;

        cout << cur << endl;
    }
}