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
#define MAXN 100100
#define lc 2*node
#define rc 2*node+1
#define mid (l+r)/2
double tree[4*MAXN]; bool lazy[4*MAXN];
double a[MAXN];
int N, M;
double v, u; int x, y;
void buildtree(int node, int l, int r)
{
    if(l == r)
    {
        tree[node] = a[l];
    }
    else
    {
        buildtree(lc, l, mid); buildtree(rc, mid+1, r);
        tree[node] = tree[lc]+tree[rc];
    }
}
void propagate(int node)
{
    if(lazy[node])
    {
        lazy[lc] = lazy[rc] = true; lazy[node] = false;
        tree[lc] = tree[rc] = 0;
    }
}
void update0(int node, int l, int r, int ql, int qr)
{
    if(l != r) propagate(node);
    if(l > qr || r < ql)
    {
        return;
    }
    else if(ql <= l && r <= qr)
    {
        tree[node] = 0;
        lazy[node] = true;
    }
    else
    {
        update0(lc, l, mid, ql,  qr); update0(rc, mid+1, r, ql, qr);
        tree[node] = tree[lc] + tree[rc];
    }
}
void update1(int node, int l, int r, int q, double qq)
{
    if(l != r) propagate(node);
    if(l > q || r < q)
    {
        return;
    }
    else if(l == q && r == q)
    {
        tree[node] = qq;
    }
    else
    {
        update1(lc, l, mid, q, qq); update1(rc, mid+1, r, q, qq);
        tree[node] = tree[lc] + tree[rc];
    }
}
double query(int node, int l, int r, int ql, int qr)
{
    if(l != r) propagate(node);
    if(l > qr || r < ql)
    {
        return 0;
    }
    else if(ql <= l && r <= qr)
    {
        return tree[node];
    }
    else
    {
        return query(lc, l, mid, ql, qr) + query(rc, mid+1, r, ql, qr);
    }
}
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cin>>N>>M;
    for(int i=1; i<=N; i++)
    {
        cin>>a[i];
    }
    buildtree(1, 1, N);
    for(int i=0; i<M; i++)
    {
        cin>>x>>y;
        if(x != y) {
            u = query(1, 1, N, x, y)/2;
            update0(1, 1, N, x, y);
            update1(1, 1, N, x, u);
            update1(1, 1, N, y, u);
        }
    }
    cout<<fixed<<setprecision(7);
    for(int i=1; i<=N; i++)
    {
        cout<<query(1, 1, N, i, i)<<" ";
    }
}