
public class SegTree {

	static int numE;
	static int[] tree;

	static int query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, numE, qL, qR);
	}

	static int query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int midPt = (cL + cR) / 2;

		int leftQ = query(nI * 2, cL, midPt, qL, qR);
		int rightQ = query(nI * 2 + 1, midPt + 1, cR, qL, qR);

		return leftQ + rightQ;
	}

	static void update(int uI, int delta) {
		update(1, 1, numE, uI, delta);
	}

	static void update(int nI, int cL, int cR, int uI, int delta) {
		if (cL == cR) {
			tree[nI] += delta;
			return;
		}

		int mid = (cL + cR) / 2;

		if (uI <= mid) {
			update(nI * 2, cL, mid, uI, delta);
		} else {
			update(nI * 2 + 1, mid + 1, cR, uI, delta);
		}
		tree[nI] = tree[nI * 2] + tree[nI * 2 + 1];
	}

}
