#include <bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef long double ld;
typedef pair<int, int> pi;
typedef pair<ll, ll> pl;

typedef vector<int> vi;
typedef vector<ld> vd;
typedef vector<ll> vl;

#define pb push_back
#define f first
#define s second

namespace treap {

    mt19937 rng(rand());

    typedef struct tnode *pt;

    struct tnode {
        int pr, val;
        pt c[2];
        int sz = 1;
        ll sum;

        tnode(int iV) {
            pr = rng();
            sum = val = iV;
            c[0] = c[1] = NULL;
        }
    };

    int sz(pt x) { return x ? x->sz : 0; }

    ll sum(pt x) { return x ? x->sum : 0; }

    pt calc(pt x) { // maintain metadata
        x->sz = 1 + sz(x->c[0]) + sz(x->c[1]);
        x->sum = sum(x->c[0]) + sum(x->c[1]);
        return x;
    }

    pair<pt, pt> split(pt t, int k) {
        if (!t) {
            return {t, t};
        } else if (k <= t->val) {
            auto sub = split(t->c[0], k);
            t->c[0] = sub.s;
            return {sub.f, calc(t)};
        } else {
            auto sub = split(t->c[1], k);
            t->c[1] = sub.f;
            return {calc(t), sub.s};
        }
    }

    pt merge(pt l, pt r) {
        if (!l || !r) return l ? l : r;
        if (l->pr > r->pr) {
            l->c[1] = merge(l->c[1], r);
            return calc(l);
        } else {
            r->c[0] = merge(l, r->c[0]);
            return calc(r);
        }
    }

    bool contains(pt x, int v) {
        if (!x) return false;
        if (x->val == v) return true;
        return contains(v < x->val ? x->c[0] : x->c[1], v);
    }

    pt insF(pt x, pt nw) {
        if (!x) return nw;
        if (nw->pr > x->pr) {
            auto sub = split(x, nw->val);
            nw->c[0] = sub.f, nw->c[1] = sub.s;
            return calc(nw);
        } else {
            pt &sub = nw->val < x->val ? x->c[0] : x->c[1];
            sub = insF(sub, nw);
            return calc(x);
        }
    }

    pt delF(pt x, int v) {
        if (!x) return x;
        if (x->val == v) {
            return merge(x->c[0], x->c[1]);
        } else {
            pt &sub = v < x->val ? x->c[0] : x->c[1];
            sub = delF(sub, v);
            return calc(x);
        }
    }

    pt insND(pt x, int v) {
        if (contains(x, v)) return x;
        return insF(x, new tnode(v));
    }

    pt findKth(pt x, int k) { // 1-indexed
        if (!x) return x;
        int lSz = sz(x->c[0]);
        if (lSz == k - 1) return x;
        if (lSz >= k) {
            return findKth(x->c[0], k);
        } else {
            return findKth(x->c[1], k - lSz - 1);
        }
    }

    int cntLess(pt x, int v) {
        if (!x) return 0;

        if (x->val < v) {
            return 1 + sz(x->c[0]) + cntLess(x->c[1], v);
        } else {
            return cntLess(x->c[0], v);
        }
    }
}
using namespace treap;

const int MAXN = 3e5 + 10;

struct SegTr {
    int tr[4 * MAXN];

    void b(int i, int l, int r, int o[]) {
        if (l == r) {
            tr[i] = o[l];
            return;
        }
        int mid = (l + r) / 2;
        b(i * 2, l, mid, o);
        b(i * 2 + 1, mid + 1, r, o);
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    int q(int i, int l, int r, int s, int e) {
        if (e < l || r < s) {
            return 0;
        }
        if (s <= l && r <= e) {
            return tr[i];
        }
        int mid = (l + r) / 2;
        return max(q(i * 2, l, mid, s, e), q(i * 2 + 1, mid + 1, r, s, e));
    }

    void u(int i, int l, int r, int x, int d) {
        if (l == r) {
            tr[i] = d;
            return;
        }
        int mid = (l + r) / 2;
        if (x <= mid) {
            u(i * 2, l, mid, x, d);
        } else {
            u(i * 2 + 1, mid + 1, r, x, d);
        }
        tr[i] = max(tr[i * 2], tr[i * 2 + 1]);
    }

    int lLarger(int i, int l, int r, int e, int v) {
        if (tr[i] <= v) {
            return -1;
        }
        if (l == r) return l;
        int mid = (l + r) / 2;

        int rRes = e <= mid ? -1 : lLarger(i * 2 + 1, mid + 1, r, e, v);

        if (rRes == -1) {
            return lLarger(i * 2, l, mid, e, v);
        }
        return rRes;
    }
} segTr;

int h[MAXN], l[MAXN];

pt treaps[MAXN];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int N;
    cin >> N;

    h[0] = 1e9;
    for (int i = 1; i <= N; i++) {
        cin >> h[i];
        l[h[i]] = i;
    }

    segTr.b(1, 0, N, h);

    for (int i = 0; i <= N; i++) {
        treaps[i] = NULL;
    }

    for (int i = 1; i <= N; i++) {
        int lG = segTr.lLarger(1, 0, N, i, h[i]);
        treaps[lG] = insND(treaps[lG], h[i]);
    }

    int Q;
    cin >> Q;

    for (int cQ = 0; cQ < Q; cQ++) {
        int t;
        cin >> t;
        if (t == 1) {
            int i;
            cin >> i;

            if (h[i] < h[i + 1]) {
                auto rSplit = split(treaps[i + 1], h[i]);
                assert(!treaps[i]);
                treaps[i] = merge(new tnode(h[i]), rSplit.s);
                treaps[i + 1] = rSplit.f;

                int oPar = segTr.lLarger(1, 0, N, i - 1, h[i]);
                treaps[oPar] = delF(treaps[oPar], h[i]);
            } else {
                treaps[i + 1] = merge(treaps[i + 1], treaps[i]);
                treaps[i + 1] = delF(treaps[i + 1], h[i + 1]);
                treaps[i] = NULL;

                int nPar = segTr.lLarger(1, 0, N, i - 1, h[i + 1]);
                treaps[nPar] = insND(treaps[nPar], h[i + 1]);
            }


            swap(h[i], h[i + 1]);
            l[h[i]] = i;
            l[h[i + 1]] = i + 1;

            segTr.u(1, 0, N, i, h[i]);
            segTr.u(1, 0, N, i + 1, h[i + 1]);
        } else {
            int i, k;
            cin >> i >> k;
            pt res = findKth(treaps[i], k);
            if (!res) {
                cout << -1 << '\n';
            } else {
                cout << l[res->val] - i << '\n';
            }
        }
    }
    cout << flush;
}