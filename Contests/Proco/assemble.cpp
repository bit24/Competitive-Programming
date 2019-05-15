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
string s;
int freq[26];
int best, letter;
int main() {
    cin>>s;
    for(int i=0; i<s.length(); i++)
    {
        freq[s[i]-'a']++;
    }
    for(int i=0; i<26; i++)
    {
        if(freq[i] >best)
        {
            letter = i;
            best = freq[i];
        }
    }
    char k = letter +'a';
    cout<<k<<endl;
    cout<<s<<endl;
}
