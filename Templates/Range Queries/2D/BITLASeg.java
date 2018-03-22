
// note: very high constant factor
// for N ~ 100_000, 6 seconds might be needed

// make sure everything is 1-indexed

public class BITLASeg {

	Node[] BIT;

	void update(int arg1, int arg2, int delta) {
		while (arg1 <= max1) {
			BIT[arg1].update(arg2, delta);
			arg1 += (arg1 & -arg1);
		}
	}

	int query(int qL1, int qR1, int qL2, int qR2) {
		if (qL1 > qR1 || qL2 > qR2) {
			return 0;
		}

		int exclude = 0;
		qL1--;
		while (qL1 > 0) {
			exclude += BIT[qL1].query(qL2, qR2);
			qL1 -= (qL1 & -qL1);
		}

		int include = 0;
		while (qR1 > 0) {
			include += BIT[qR1].query(qL2, qR2);
			qR1 -= (qR1 & -qR1);
		}

		return include - exclude;
	}

	static int max1;
	static int max2;

	int nN; // diagnostic purposes

	class Node {

		Node() {
			nN++;
		}

		int value = 0;
		Node left;
		Node right;

		void update(int target, int delta) {
			update(1, max2, target, delta);
		}

		void update(int cL, int cR, int target, int delta) {
			value += delta;
			if (cL != cR) {
				int mid = (cL + cR) / 2;
				if (target <= mid) {
					if (left == null) {
						left = new Node();
					}
					left.update(cL, mid, target, delta);
				} else {
					if (right == null) {
						right = new Node();
					}
					right.update(mid + 1, cR, target, delta);
				}
			}
		}

		int query(int qL, int qR) {
			return query(1, max2, qL, qR);
		}

		int query(int cL, int cR, int qL, int qR) {
			if (cR < qL || qR < cL) {
				return 0;
			}
			if (qL <= cL && cR <= qR) {
				return value;
			}
			int mid = (cL + cR) / 2;
			int sum = 0;
			if (left != null) {
				sum += left.query(cL, mid, qL, qR);
			}
			if (right != null) {
				sum += right.query(mid + 1, cR, qL, qR);
			}
			return sum;
		}
	}
}
