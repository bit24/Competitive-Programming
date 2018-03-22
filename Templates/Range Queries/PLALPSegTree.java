import java.util.Arrays;
import java.util.Random;

public class PLALPSegTree {

	int nE;

	RetVal query(Node root, int qL, int qR) {
		if (qL > qR) {
			return new RetVal(root, 0);
		}
		return query(root, 1, nE, qL, qR);
	}

	// can modify the persistence by pushing down lazy
	RetVal query(Node cNode, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return new RetVal(cNode, 0);
		}
		if (cL != cR) {
			if (cNode.left == null) {
				cNode.left = new Node();
			}
			if (cNode.right == null) {
				cNode.right = new Node();
			}
		}

		if (cNode.lazy != 0) {
			cNode = new Node(cNode);
			cNode.value += cNode.lazy * (cR - cL + 1);
			if (cL != cR) {
				cNode.left = new Node(cNode.left);
				cNode.right = new Node(cNode.right);
				cNode.left.lazy += cNode.lazy;
				cNode.right.lazy += cNode.lazy;
			}
			cNode.lazy = 0;
		}

		if (qL <= cL && cR <= qR) {
			return new RetVal(cNode, cNode.value);
		}

		int mid = (cL + cR) / 2;

		RetVal leftQ = query(cNode.left, cL, mid, qL, qR);
		cNode.left = leftQ.nNode;
		RetVal rightQ = query(cNode.right, mid + 1, cR, qL, qR);
		cNode.right = rightQ.nNode;

		return new RetVal(cNode, leftQ.qRes + rightQ.qRes);
	}

	Node update(Node root, int uL, int uR, int delta) {
		if (uL <= uR) {
			return update(root, 1, nE, uL, uR, delta);
		}
		return null;
	}

	Node update(Node cNode, int cL, int cR, int uL, int uR, int delta) {
		if (cL != cR) {
			if (cNode.left == null) {
				cNode.left = new Node();
			}
			if (cNode.right == null) {
				cNode.right = new Node();
			}
		}

		// current node needs to have no lazy before returning because it will be used to compute tree[par]
		if (cNode.lazy != 0) {
			cNode = new Node(cNode);
			cNode.value += (cR - cL + 1) * cNode.lazy;

			if (cL != cR) {
				cNode.left = new Node(cNode.left);
				cNode.right = new Node(cNode.right);
				cNode.left.lazy += cNode.lazy;
				cNode.right.lazy += cNode.lazy;
			}
			cNode.lazy = 0;
		}
		if (cR < uL || uR < cL) {
			return cNode; // no modifications needed
		}

		if (uL <= cL && cR <= uR) {
			cNode = new Node(cNode);
			cNode.value += (cR - cL + 1) * delta;
			if (cL != cR) {
				cNode.left = new Node(cNode.left);
				cNode.right = new Node(cNode.right);
				cNode.left.lazy += delta;
				cNode.right.lazy += delta;
			}
			return cNode;
		}

		int mid = (cL + cR) / 2;

		cNode = new Node(cNode);
		cNode.left = update(cNode.left, cL, mid, uL, uR, delta);
		cNode.right = update(cNode.right, mid + 1, cR, uL, uR, delta);

		cNode.value = cNode.left.value + cNode.right.value;
		return cNode;
	}

	class Node {
		Node left;
		Node right;
		int value;
		int lazy;

		Node() {}

		Node(Node orig) {
			left = orig.left;
			right = orig.right;
			value = orig.value;
			lazy = orig.lazy;
		}

		Node(Node left, Node right, int value) {
			this.left = left;
			this.right = right;
			this.value = value;
		}
	}

	class RetVal {
		Node nNode;
		int qRes;

		RetVal(Node nNode, int qRes) {
			this.nNode = nNode;
			this.qRes = qRes;
		}
	}
	
	public static void main(String[] args) {
		new PLALPSegTree().test();
	}
	
	void test() {
		Random rng = new Random();
		nE = 1000;

		int[][] arrays = new int[1000][];
		arrays[0] = new int[1001];
		Node[] trees = new Node[1000];
		trees[0] = new Node();

		for (int i = 1; i < 1000; i++) {
			int uL = 1 + rng.nextInt(1000);
			int uR = 1 + rng.nextInt(1000);
			if (uR < uL) {
				int temp = uL;
				uL = uR;
				uR = temp;
			}
			int uV = 1 + rng.nextInt(1000);
			arrays[i] = dUpdate(arrays[i - 1], uL, uR, uV);
			trees[i] = update(trees[i - 1], uL, uR, uV);
		}

		int nT = 100000;
		while (nT-- > 0) {
			int i = rng.nextInt(1000);
			int l = 1 + rng.nextInt(1000);
			int r = 1 + rng.nextInt(1000);
			if (r < l) {
				int temp = l;
				l = r;
				r = temp;
			}
			if (rng.nextBoolean()) {
				int uV = 1 + rng.nextInt(1000);
				arrays[i] = dUpdate(arrays[i], l, r, uV);
				trees[i] = update(trees[i], l, r, uV);
			} else {
				RetVal sQ = query(trees[i], l, r);
				trees[i] = sQ.nNode;
				if (dQuery(arrays[i], l, r) != sQ.qRes) {
					System.out.println("Error");
					assert (false);
				}
			}
		}
	}

	int dQuery(int[] orig, int qL, int qR) {
		int sum = 0;
		for (int i = qL; i <= qR; i++) {
			sum += orig[i];
		}
		return sum;
	}

	int[] dUpdate(int[] orig, int uL, int uR, int delta) {
		int[] copy = Arrays.copyOf(orig, orig.length);
		for (int i = uL; i <= uR; i++) {
			copy[i] += delta;
		}
		return copy;
	}
}