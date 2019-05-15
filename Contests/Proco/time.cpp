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

int T(int num) { //number of pairs with sum of squares num
    int x = 0;
    for (long long i = 1; i <= sqrt(num); i++) {
        for (long long j = 1; j <= sqrt(num); j++) {
            if (i*i + j*j == num) {
                x++;
            }
        }
    }
    return x;
}

int I(int num) { //number of zeroes in num
    int x = 0;
    for (; num > 0; num /= 10) {
        if (num % 10 == 0) {
            x++;
        }
    }
    return x;
}

int M(int num) { //number of digits in x
    int x = 0;
    for (; num > 0; num /= 10) {
        x++;
    }
    return x;
}

int E(int num) { //sum of digits of x
    int x = 0;
    for (; num > 0; num /= 10) {
        x += num % 10;
    }
    return x;
}

int main() {
    int input;
    cin >> input;
    if (T(input) == 12 && 
        I(input) == 4 && 
        M(input) == 8 && 
        E(input) == 16 && 
        input % 2 == 1) {
        cout << "We're in the endgame now" << endl;
    } else {
        cout << "we lost :(" << endl;
    }
    return 0;
}