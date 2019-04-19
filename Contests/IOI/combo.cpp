#include <bits/stdc++.h>
 
using namespace std;
 
typedef long long ll;
typedef long double ld;
 
typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;
 
#define pb push_back
#define f first
#define s second
 
/*
int press(string p) {
    cout << p << endl;
    int res;
    cin >> res;
    return res;
}*/
 
int press(string p);
 
string keys[] = {"A", "B", "X", "Y"};
 
string guess_sequence(int N) {
    if (N == 1) {
        for (int i = 0; i < 3; i++) {
            if (press(keys[i]) == 1) {
                return keys[i];
            }
        }
        return keys[3];
    }
    string seq = "";
    int f = -1;
 
    int res = press("AB");
    if (res != 0) {
        res = press("A");
        if (res == 1) {
            seq.append("A");
            f = 0;
        } else {
            seq.append("B");
            f = 1;
        }
    } else {
        res = press("X");
        if (res == 1) {
            seq.append("X");
            f = 2;
        } else {
            seq.append("Y");
            f = 3;
        }
    }
 
    vector<string> cand(keys, keys + 4);
 
    cand.erase(cand.begin() + f);
 
    for (int i = 1; i < N - 1; i++) {
        string q = "";
        for (int j = 0; j < 3; j++) {
            q.append(seq);
            q.append(cand[0]);
            q.append(cand[j]);
        }
        q.append(seq);
        q.append(cand[1]);
 
        int res = press(q);
 
        if (res == seq.length() + 2) {
            seq.append(cand[0]);
        } else if (res == seq.length() + 1) {
            seq.append(cand[1]);
        } else {
            seq.append(cand[2]);
        }
    }
 
    string q = "";
    q.append(seq);
    q.append(cand[0]);
    q.append(seq);
    q.append(cand[1]);
    res = press(q);
 
    if (res == seq.length() + 1) {
        q = "";
        q.append(seq);
        q.append(cand[0]);
        int res = press(q);
        if (res == seq.length() + 1) {
            seq.append(cand[0]);
        } else {
            seq.append(cand[1]);
        }
    } else {
        seq.append(cand[2]);
    }
    return seq;
}
 
/*
int main() {
    cout << guess_sequence(5);
}*/
