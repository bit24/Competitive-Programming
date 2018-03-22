import java.util.Random;

//lazy allocating and lazy propagating segment tree
class LALPSegTree {

	public static void main(String[] args) {

		Random rng = new Random();
		int dataLength = 1_000_000;

		int[] data = new int[dataLength + 1];

		LALPSegTree tree = new LALPSegTree(dataLength);
		dumbElements = data;

		// System.out.println(Arrays.toString(dumbElements));

		for (int i = 0; i < 100_000; i++) {
			if (rng.nextBoolean()) {
				int left = 1 + rng.nextInt(dataLength);
				int right = 1 + rng.nextInt(dataLength);
				int delta = rng.nextInt(1_000);
				// System.out.println(left + " " + right + " " + delta);
				// dumbUpdate(left, right, delta);
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

	int numE;
	Node root = new Node();

	LALPSegTree(int numE) {
		this.numE = numE;
	}

	int query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(root, 1, numE, qL, qR);
	}

	int query(Node cNode, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (cNode.left == null) {
			cNode.left = new Node();
		}
		if (cNode.right == null) {
			cNode.right = new Node();
		}

		if (cNode.lazy != 0) {
			cNode.value += cNode.lazy * (cR - cL + 1);
			if (cL != cR) {
				// internal node
				cNode.left.lazy += cNode.lazy;
				cNode.right.lazy += cNode.lazy;
			}
			cNode.lazy = 0;
		}

		if (qL <= cL && cR <= qR) {
			return cNode.value;
		}

		int midPt = cL + (cR - cL) / 2;

		int leftQ = query(cNode.left, cL, midPt, qL, qR);
		int rightQ = query(cNode.right, midPt + 1, cR, qL, qR);

		return leftQ + rightQ;
	}

	void update(int uL, int uR, int delta) {
		if (uL <= uR) {
			update(root, 1, numE, uL, uR, delta);
		}
	}

	void update(Node cNode, int cL, int cR, int uL, int uR, int delta) {
		if (cNode.left == null) {
			cNode.left = new Node();
		}
		if (cNode.right == null) {
			cNode.right = new Node();
		}

		// current node needs to have no lazy before returning because it will be used to compute tree[parent]
		if (cNode.lazy != 0) {
			cNode.value += (cR - cL + 1) * cNode.lazy;

			if (cL != cR) {
				cNode.left.lazy += cNode.lazy;
				cNode.right.lazy += cNode.lazy;
			}
			cNode.lazy = 0;
		}
		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			cNode.value += (cR - cL + 1) * delta;
			if (cL != cR) {
				cNode.left.lazy += delta;
				cNode.right.lazy += delta;
			}
			return;
		}

		int midPt = cL + (cR - cL) / 2;

		update(cNode.left, cL, midPt, uL, uR, delta);
		update(cNode.right, midPt + 1, cR, uL, uR, delta);

		cNode.value = cNode.left.value + cNode.right.value;
	}

	class Node {
		Node left;
		Node right;
		int value;
		int lazy;

		Node() {}

		Node(Node left, Node right, int value) {
			this.left = left;
			this.right = right;
			this.value = value;
		}
	}
}