import java.util.Random;

class LPSegTree {

	int numE;
	int[] tree;
	int[] lazy;

	LPSegTree(int[] initialData) {
		numE = initialData.length - 1;
		tree = new int[initialData.length * 4];
		lazy = new int[initialData.length * 4];
		buildTree(initialData, 1, 1, numE);
	}

	void buildTree(int[] initialData, int nI, int rL, int rR) {
		if (rL == rR) {
			// leaf node
			tree[nI] = initialData[rL];
		} else {
			// internal node
			int mid = (rL + rR) / 2;
			buildTree(initialData, nI * 2, rL, mid);
			buildTree(initialData, nI * 2 + 1, mid + 1, rR);
			tree[nI] = tree[nI * 2] + tree[nI * 2 + 1];
		}
	}

	int query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, numE, qL, qR);
	}

	int query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (lazy[nI] != 0) {
			tree[nI] += lazy[nI] * (cR - cL + 1);
			if (cL != cR) {
				// internal node
				lazy[nI * 2] += lazy[nI];
				lazy[nI * 2 + 1] += lazy[nI];
			}
			lazy[nI] = 0;
		}

		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int mid = (cL + cR) / 2;

		int leftQ = query(nI * 2, cL, mid, qL, qR);
		int rightQ = query(nI * 2 + 1, mid + 1, cR, qL, qR);

		return leftQ + rightQ;
	}

	void update(int uL, int uR, int delta) {
		if (uL <= uR) {
			update(1, 1, numE, uL, uR, delta);
		}
	}

	void update(int nI, int cL, int cR, int uL, int uR, int delta) {
		// current node needs to have no lazy before returning because it will be used to compute tree[par]
		if (lazy[nI] != 0) {
			tree[nI] += (cR - cL + 1) * lazy[nI];

			if (cL != cR) {
				lazy[nI * 2] += lazy[nI];
				lazy[nI * 2 + 1] += lazy[nI];
			}
			lazy[nI] = 0;
		}
		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			tree[nI] += (cR - cL + 1) * delta;
			if (cL != cR) {
				lazy[nI * 2] += delta;
				lazy[nI * 2 + 1] += delta;
			}
			return;
		}

		int mid = (cL + cR) / 2;

		update(nI * 2, cL, mid, uL, uR, delta);
		update(nI * 2 + 1, mid + 1, cR, uL, uR, delta);

		tree[nI] = tree[nI * 2] + tree[nI * 2 + 1];
	}

	public static void main(String[] args) {

		Random rng = new Random();
		int dataLength = 1005;

		int[] data = new int[dataLength + 1];
		for (int i = 1; i <= dataLength; i++) {
			data[i] = rng.nextInt(100);
		}

		LPSegTree tree = new LPSegTree(data);
		dumbElements = data;

		// System.out.println(Arrays.toString(dumbElements));

		for (int i = 0; i < 10000; i++) {
			if (rng.nextBoolean()) {
				int left = 1 + rng.nextInt(dataLength);
				int right = 1 + rng.nextInt(dataLength);
				int delta = rng.nextInt(1_000);
				// System.out.println(left + " " + right + " " + delta);
				dumbUpdate(left, right, delta);
				tree.update(left, right, delta);
			} else {
				int left = 1 + rng.nextInt(dataLength);
				int right = 1 + rng.nextInt(dataLength);
				if (dumbQuery(left, right) != tree.query(left, right)) {
					System.out.println(left + " " + right + " error");
				}
			}
		}
	}

	static int[] dumbElements;

	static void dumbUpdate(int uL, int uR, int delta) {
		for (int i = uL; i <= uR; i++) {
			dumbElements[i] += delta;
		}
	}

	static int dumbQuery(int qL, int qR) {
		int sum = 0;
		for (int i = qL; i <= qR; i++) {
			sum += dumbElements[i];
		}
		return sum;
	}

}