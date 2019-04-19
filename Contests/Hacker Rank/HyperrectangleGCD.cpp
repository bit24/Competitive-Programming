//
//  HyperrectangleGCD.cpp
//  QuickBuild
//
//

#include <iostream>
using namespace std;


const int MAX = 100000;
const long long MOD = 1000000007;

int main(){
    ios::sync_with_stdio(false);
    int phi[MAX + 1];
    
    for(int i = 1; i <= MAX; i++){
        phi[i] = i;
    }
    for(int i = 1; i <= MAX; i++){
        for(int j = i << 1; j <= MAX; j += i){
            phi[j] -= phi[i];
        }
    }
    
    int lengths[500];
    
    int numT;
    cin >> numT;
    
    while(numT--){
        int numD;
        cin >> numD;
        for(int i = 0; i < numD; i++){
            cin >> lengths[i];
        }
        
        long long ans = 0;
        for(int d = 1; d <= MAX; d++){
            long long divC = 1;
            
            for(int i = 0; i < numD && divC; i++){
                divC = (divC * (lengths[i] / d)) % 1000000007;
            }
            ans = (ans + phi[d] * divC) % MOD;
        }
        cout << ans << endl;
    }
    return 0;
}
