#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef vector<int> vi;

#define pb push_back

const int MAXN = 1e4 + 10;

string str[MAXN];

const ll MOD = 1e9 + 123;
const ll B = 97;

string toBinary(int x, int digit) {
    string res;
    for (int i = digit - 1; i >= 0; i--) {
        res += (x & (1 << i)) ? '1' : '0';
    }
    return res;
}

int pHash(vi s) {
    ll ret = 0;
    for (int i = 0; i < s.size(); i++) {
        ret = (ret * B + s[i]) % MOD;
    }
    return ret;
}

// can process uncarried additions
vi half(vi x) {
    assert(x.size() == 100);
    vi ret(100);
    bool carry = false;
    for (int i = 0; i < 100; i++) {
        int cur = x[i] + (carry ? 26 : 0);
        carry = cur & 1;
        ret[i] = cur / 2;
    }
    for (int i = 100 - 1; i > 0; i--) {
        ret[i - 1] += ret[i] / 26;
        ret[i] %= 26;
    }
    return ret;
}

vi add(vi a, vi b) {
    vi ret(100);
    for (int i = 0; i < 100; i++) {
        ret[i] = a[i] + b[i];
    }
    return ret;
}


string vToString(vi x) {
    string s;
    for (int i : x) {
        s += 'a' + i;
    }
    return s;
}

void output(string x) {
    cout << "MOO " << x << endl;
}

void recurse(vector<vi> subs, vi f, vi l) {
//    cerr << "begin" << endl;
//    cerr << vToString(f) << endl;
//    cerr << vToString(l) << endl;
    assert(subs.size());
    assert(f < l);
//
//    for (vi x :subs) {
//        cerr << vToString(x) << endl;
//        assert(f < x);
//        assert(x <= l);
//    }

    if (subs.size() == 1) {
//        cerr << "single" << endl;
        output("00");
        char o1, o2;
        cin >> o1 >> o2;

        string hash = toBinary(pHash(subs[0]) % 1024, 10);

        output(hash);

        if (o1 == '0' && o2 == '0') {
            string oHash;
            cin >> oHash;
            assert(oHash.size() == 10);
            if (hash == oHash) {
                cout << "RETURN " << vToString(subs[0]) << endl;
                exit(0);
            }
//            cerr << "Case" << endl;
        } else {
            char conf;
            cin >> conf;
            if (conf == '1') {
                cout << "RETURN " << vToString(subs[0]) << endl;
                exit(0);
            }
        }

        return;
    }

//    cerr << "dividing" << endl;
//    cerr << vToString(f) << endl;
//    cerr << vToString(l) << endl;
    vi mid = half(add(f, l));
//    cerr << vToString(mid) << endl;

    vector<vi> sub1, sub2;

    for (int i = 0; i < subs.size(); i++) {
        if (subs[i] < mid) {
            sub1.pb(subs[i]);
        } else {
            sub2.pb(subs[i]);
        }
    }

//    cerr << "divided" << endl;

    string s = "";
    s += (sub1.empty() ? "0" : "1");
    s += (sub2.empty() ? "0" : "1");

    output(s);

    char o1, o2;
    cin >> o1 >> o2;

    if (o1 == '1' && !sub1.empty()) {
        for (vi x : sub1) {
            assert(x < mid);
        }
        recurse(sub1, f, mid);
    }
    if (o2 == '1' && !sub2.empty()) {
        for (vi x : sub2) {
            assert(x < l);
        }
        recurse(sub2, mid, l);
    }

    if (o1 == '0' && o2 == '0') {
        string oHash;
        cin >> oHash;

        for (vi &x : subs) {
            string cHash = toBinary(pHash(x) % 1024, 10);
            if (cHash == oHash) {
                output("1");
                cout << "RETURN " << vToString(x) << endl;
                exit(0);
            }
        }
        output("0");
    }
}

int main() {
    int C, N;
    cin >> C >> N;

    for (int i = 0; i < N; i++) {
        cin >> str[i];
    }
    sort(str, str + N);


    vector<vi> vs;
    for (int i = 0; i < N; i++) {
        vi cV(str[i].size());
        for (int j = 0; j < str[i].size(); j++) {
            cV[j] = (str[i][j] - 'a');
        }
        vs.pb(cV);
    }
    vi f, l;

    for (int i = 0; i < 100; i++) {
        f.pb(0);
        l.pb(26);
    }

    recurse(vs, f, l);
    return 0;
}