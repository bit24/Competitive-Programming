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
#define MAXN 1010

double n[MAXN];
double e[MAXN];
double ans;
double x[MAXN], y[MAXN], z[MAXN];
char c; double cur;
double angle;
double dist[MAXN][MAXN];
int N;
bool vis[MAXN];
void dfs(int node, double test)
{
    vis[node] = true;
    for(int i=0; i<N; i++)
    {
        if(!vis[i] && dist[node][i] != -1 && dist[node][i] <= test)
        {
            dfs(i, test);
        }
    }
}
bool judge(double kk)
{
    for(int i=0; i<MAXN; i++)
    {
        vis[i] = false;
    }
    dfs(0, kk);
    for(int i=0; i<N; i++) {
        if(!vis[i])
        {
            return false;
        }
    }
    return true;
}
int main() {
    cin>>N;
    for(int i=0; i<N; i++)
    {
        cin>>cur>>c;
        if(c == 'N')
        {
            n[i] = cur;
        }
        else
        {
            n[i] = -cur;
        }
        cin>>cur>>c;
        if(c == 'E')
        {
            e[i] = cur;
        }
        else
        {
            e[i] = -cur;
        }
        n[i] = n[i]*M_PI/180.0;
        e[i] = e[i]*M_PI/180.0;
        x[i] = sin(n[i]);
        y[i] = cos(n[i])*sin(e[i]);
        z[i] = cos(n[i])*cos(e[i]);
    }
    for(int i=0; i<N; i++)
    {
        for(int j=0; j<N; j++)
        {
            dist[i][j] = -1;
        }
    }
    for(int i=0; i<N; i++)
    {
        for(int j=i+1; j<N; j++)
        {
            angle = acos(x[i]*x[j]+y[i]*y[j]+z[i]*z[j]);
            if(angle < M_PI)
            {
                dist[i][j] = dist[j][i] = 1/cos(angle/2)-1;
            }
        }
    }
    double lbound = 0; double hbound = 1e10; double ans;
    while(hbound - lbound > 1e-7)
    {
        ans = (lbound + hbound)/2;
        if(judge(ans))
        {
            hbound = ans;
        }
        else
        {
            lbound = ans;
        }
    }
    cout<<fixed<<setprecision(7)<<ans<<endl;
}