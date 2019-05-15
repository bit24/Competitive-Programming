#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pii;

typedef vector<int> vi;
typedef vector <ld> vd;
typedef vector <ll> vl;

#define pb push_back
#define f first
#define s second
#define MAXN 100007

pii x[MAXN];
map<pii, vi> y;
map<pii, bool> e;
int t[MAXN];
int ans;
int q, Q, u, v;
pii k;
int main() {
    cin>>Q;
    for(int i=1; i<=Q; i++)
    {
        cin>>q>>u>>v;
        if(u >v) swap(u, v);
        k = make_pair(u, v);
        if(q == 0)
        {
            if(v != u) y[k].push_back(i);
            if(e[k])
            {
                t[i] = 1;
                ++ans;
            }
            e[make_pair(u, i)] = true;
            if(v != u) e[make_pair(v, i)] = true;
            x[i] = k;
        }
        else
        {
            if(e[k]) {
                for (int s: y[k]) {
                    ans -= t[s];
                    t[s] = 0;
                }
                e[k] = false;
                if(x[v] != make_pair(0, 0))
                {
                    if(e[x[v]])
                    {
                        --ans;
                        --t[v];
                        x[v] = make_pair(0, 0);
                    }
                }
            }
            e[make_pair(u, i)] = true;
            if(v != u) e[make_pair(v, i)] = true;
        }
        cout<<ans<<endl;
    }
}