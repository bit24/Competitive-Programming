#include <bits/stdc++.h>
#define ll long long
#define N 500001
using namespace std;

struct AWrap {
    ll a[4][2][2];
    
    AWrap()
    {
        memset(a, 0, sizeof(a));
    }
};

int ept[N * 2];
int nxt[N * 2];
int head[N];
int cnt = 0;

AWrap s[N];

void add(int a, int b)
{
    ept[cnt] = b;
    nxt[cnt] = head[a];
    head[a] = cnt;
    cnt++;
}

void dfs(int cV, int pV)
{
    s[cV].a[0][0][0] = 1;
    s[cV].a[1][0][1] = 1;
    s[cV].a[2][0][0] = 1;
    
    int eI = head[cV];
    while (eI != -1) {
        int aV = ept[eI];
        if (aV == pV) {
            eI = nxt[eI];
            continue;
        }
        dfs(aV, cV);
        ll ret[4][2][2];
        memset(ret, 0, sizeof(ret));
        
        for (int aCut = 0; aCut <= 1; aCut++) {
            for (int bCut = 0; aCut + bCut <= 1; bCut++) {
                for (int aM = 0; aM <= 1; aM++) {
                    for (int bM = aM; bM <= 1; bM++) {
                        int rCut = aCut + bCut;
                        int rM = (aM == bM) ? 1 : 0;
                        ret[0][rCut][rM] += s[cV].a[0][aCut][aM] * s[aV].a[0][bCut][bM];
                        
                        ret[1][rCut][rM] += s[cV].a[1][aCut][aM] * s[aV].a[0][bCut][bM];
                        ret[1][rCut][rM] += s[cV].a[0][aCut][aM] * s[aV].a[1][bCut][bM];
                        
                        ret[2][rCut][rM] += s[cV].a[2][aCut][aM] * s[aV].a[0][bCut][bM];
                        ret[2][rCut][rM] += s[cV].a[0][aCut][aM] * s[aV].a[2][bCut][bM];
                        
                        ret[3][rCut][rM] += s[cV].a[3][aCut][aM] * s[aV].a[0][bCut][bM];
                        ret[3][rCut][rM] += s[cV].a[0][aCut][aM] * s[aV].a[3][bCut][bM];
                        
                        ret[3][rCut][rM] += s[cV].a[1][aCut][aM] * s[aV].a[1][bCut][bM];
                        ret[3][rCut][rM] += s[cV].a[2][aCut][aM] * s[aV].a[2][bCut][bM];
                    }
                }
            }
        }
        
        for(int i = 0; i < 4; i++){
            for(int j = 0;j < 2; j++){
                for(int k = 0; k < 2; k++){
                    s[cV].a[i][j][k] = ret[i][j][k];
                }
            }
        }
        eI = nxt[eI];
    }
    // cut end from cV to pV
    s[cV].a[1][1][1] += s[cV].a[1][0][1];
    s[cV].a[2][1][1] += s[cV].a[2][0][1];
}

int main()
{
    int nV;
    
    memset(head, -1, sizeof(head));
    
    scanf("%d", &nV);
    for (int i = 0; i < nV - 1; i++) {
        int a, b;
        scanf("%d%d", &a, &b);
        a--;
        b--;
        add(a, b);
        add(b, a);
    }
    
    dfs(0, -1);
    
    printf("%I64d\n", s[0].a[3][1][1]);
}
