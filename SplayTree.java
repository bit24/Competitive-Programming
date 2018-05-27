public class SplayTree {

	static Node r_rotate(Node oTop) {
		Node nTop = oTop.l;
		oTop.l = nTop.r;
		nTop.r = oTop;
		return nTop;
	}

	static Node l_rotate(Node oTop) {
		Node nTop = oTop.r;
		oTop.r = nTop.l;
		nTop.l = oTop;
		return nTop;
	}

	// see diagrams in http://digital.cs.usu.edu/~allan/DS/Notes/Ch22.pdf
	Node splay(int k, Node cur) {
		if (cur == null) {
			return null;
		}

		Node header = new Node(); // temporary node for holding stuff

		Node lMax = header;
		Node rMin = header;

		while (true) {
			if (k < cur.k) {
				if (cur.l == null) {
					break;
				}

				if (k < cur.l.k) {
					cur = r_rotate(cur);
					if (cur.l == null) {
						break;
					}
				}

				rMin.l = cur;
				rMin = rMin.l;
				cur = cur.l;
				rMin.l = null;
			} else if (k > cur.k) {
				if (cur.r == null) {
					break;
				}

				if (k > cur.r.k) {
					cur = l_rotate(cur);
					if (cur.r == null) {
						break;
					}
				}

				lMax.r = cur;
				lMax = lMax.r;
				cur = cur.r;
				lMax.r = null;
			}
		}

		lMax.r = cur.l;
		rMin.l = cur.r;
		cur.l = header.r; // based on how the code is written the header ends up
		cur.r = header.l; // with inverted references to the left/right subtrees
		return cur;
	}

	Node pNode = null;

	Node insert(int k, Node cur) {
		if (pNode == null) {
			pNode = new Node(k);
		} else {
			pNode.k = k;
		}
		if (cur != null) {

		}
	}

	class Node {
		int k;
		Node l;
		Node r;

		// Node p;
		Node() {
		};

		Node(int k) {
			this.k = k;
		}
	}
}
