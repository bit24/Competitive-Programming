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

    pt ins(pt x, int k) { // alternate implementations that are short but slow
        auto a = split(x, k), b = split(a.s, k + 1);
        return merge(a.f, merge(new tnode(k), b.s));
    }

    pt del(pt x, int k) {
        auto a = split(x, k), b = split(a.s, k + 1);
        return merge(a.f, b.s);
    }

    pt findKth(pt x, int k) { // 0-indexed
        if (!x) return x;
        int lSz = sz(x->c[0]);
        if (lSz == k) return x;
        if (lSz < k) {
            return findKth(x->c[1], k - lSz - 1);
        } else {
            return findKth(x->c[0], k);
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
