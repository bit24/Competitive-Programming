#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;

typedef vector<int> vi;
typedef vector <ld> vd;
typedef vector <ll> vl;

#define pb push_back
#define f first
#define s second
int Q;
ll c;
bool flag;
int main() {
    cin>>Q;
    for(int i=0; i<Q; i++)
    {
        cin>>c;
        flag = false;
        for(ll j = 10000; j>=2; j--)
        {
            if(c%(j*j*j) == 0)
            {
                cout<<c/(j*j)<<" "<<c/(j*j*j)<<endl;
                flag = true;
                break;
            }
        }
        if(!flag)
        {
            cout<<4*c<<" "<<8*c<<endl;
        }
    }
}