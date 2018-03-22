
class RedBlackTree {

	class Vertex {
		int k;
		int v;
		private boolean isRed = true;

		private Vertex left;
		private Vertex right;

		Vertex(int k, int v) {
			this.k = k;
			this.v = v;
		}
	}

	Vertex root;

	RedBlackTree() {
		root = new Vertex(Integer.MIN_VALUE, -1);
		root.isRed = false;
	}

	// returns the vertex that is mapped to key
	// very, very dangerous if used incorrectly but allows free manipulation of value
	Vertex get(int k) {
		Vertex cV = root;
		while (k != cV.k) {
			cV = k < cV.k ? cV.left : cV.right;
			if (cV == null) {
				return null;
			}
		}
		return cV;
	}

	private Vertex rotateLeft(Vertex cV) {
		Vertex nPar = cV.right;
		cV.right = nPar.left;
		nPar.left = cV;
		cV.isRed = true;
		nPar.isRed = false;
		return nPar;
	}

	private Vertex rotateRight(Vertex cV) {
		Vertex nPar = cV.left;
		cV.left = nPar.right;
		nPar.right = cV;
		cV.isRed = true;
		nPar.isRed = false;
		return nPar;
	}

	private Vertex doubleLeft(Vertex cV) {
		cV.right = rotateRight(cV.right);
		return rotateLeft(cV);
	}

	private Vertex doubleRight(Vertex cV) {
		cV.left = rotateLeft(cV.left);
		return rotateRight(cV);
	}

	void put(int k, int v) {
		root = put(root, k, v);
		root.isRed = false;
	}

	// returns root of updated subtree
	private Vertex put(Vertex cV, int k, int v) {
		if (cV.k == k) {
			cV.v = v;
		} else if (k < cV.k) {
			cV.left = cV.left == null ? new Vertex(k, v) : put(cV.left, k, v);

			// the flip code works as often as possible; fixes violations two vertices down
			// the rotation code works only when needed; fixes violations two vertices down
			if (cV.left.isRed) {
				if (cV.right != null && cV.right.isRed) {
					assert (!cV.isRed);
					cV.left.isRed = cV.right.isRed = false;

					cV.isRed = true;
				} else {
					if (cV.left.left != null && cV.left.left.isRed) {
						return rotateRight(cV);
					} else if (cV.left.right != null && cV.left.right.isRed) {
						return doubleRight(cV);
					}
				}
			}
		} else {
			cV.right = cV.right == null ? new Vertex(k, v) : put(cV.right, k, v);

			if (cV.right.isRed) {
				if (cV.left != null && cV.left.isRed) {
					assert (!cV.isRed);
					cV.left.isRed = cV.right.isRed = false;

					cV.isRed = true;
				} else {
					if (cV.right.right != null && cV.right.right.isRed) {
						return rotateLeft(cV);
					} else if (cV.right.left != null && cV.right.left.isRed) {
						return doubleLeft(cV);
					}
				}
			}
		}
		return cV;
	}

	// used to express whether the the tree is balanced (only used during removal)
	// default state is true
	private boolean isBal = true;

	void remove(int k) {
		isBal = false;
		root = remove(root, k);
		isBal = true;
		root.isRed = false;
	}

	// actual deletion only happens when the vertex is a leaf or the vertex has only one child, otherwise a vertex
	// fulfilling the requirements is copied up
	private Vertex remove(Vertex cV, int k) {
		if (cV.k == k) {
			if (cV.left == null) {
				Vertex saved = cV.right;

				if (cV.isRed) {
					isBal = true;
				} else if (saved != null && saved.isRed) {
					saved.isRed = false;
					isBal = true;
				}

				return saved;
			} else if (cV.right == null) {
				Vertex saved = cV.left;
				assert (saved != null);

				if (cV.isRed) {
					isBal = true;
				} else if (saved.isRed) {
					saved.isRed = false;
					isBal = true;
				}

				return saved;
			} else {
				Vertex replacement = cV.left;
				while (replacement.right != null) {
					replacement = replacement.right;
				}

				// copy all valuable information
				cV.k = replacement.k;
				cV.v = replacement.v;

				// now we're going to continue down the tree to delete the old vertex
				k = replacement.k;
			}
		}

		if (k <= cV.k) {
			if (cV.left != null) {
				cV.left = remove(cV.left, k);
				if (!isBal) {
					cV = balRemovalLeft(cV);
				}
			} else {
				isBal = true;
			}
		} else {
			if (cV.right != null) {
				cV.right = remove(cV.right, k);
				if (!isBal) {
					cV = balRemovalRight(cV);
				}
			} else {
				isBal = true;
			}
		}
		return cV;
	}

	// rV.left is has one less black layer than rV.right

	// rV will always be updated to be the root of the subtree (eventually)
	// opV is the vertex we are rotating on, which may or may not be rV
	private Vertex balRemovalLeft(Vertex rV) {
		Vertex opV = rV;
		Vertex saved = rV.right;

		if (saved != null && saved.isRed) {
			rV = rotateLeft(rV);
			saved = opV.right;
		}

		if (saved != null) {
			if ((saved.left == null || !saved.left.isRed) && (saved.right == null || !saved.right.isRed)) {
				if (opV.isRed) {
					isBal = true;
					opV.isRed = false;
				}
				saved.isRed = true;
			} else {
				boolean wasRed = opV.isRed;
				boolean isRoot = rV == opV;

				if (saved.right != null && saved.right.isRed) {
					opV = rotateLeft(opV);
				} else {
					opV = doubleLeft(opV);
				}

				opV.isRed = wasRed;
				opV.left.isRed = opV.right.isRed = false;

				if (isRoot) {
					rV = opV;
				} else {
					rV.left = opV;
				}
				isBal = true;
			}
		}
		return rV;
	}

	private Vertex balRemovalRight(Vertex rV) {
		Vertex opV = rV;
		Vertex saved = rV.left;

		if (saved != null && saved.isRed) {
			rV = rotateRight(rV);
			saved = opV.left;
		}

		if (saved != null) {
			if ((saved.left == null || !saved.left.isRed) && (saved.right == null || !saved.right.isRed)) {
				if (opV.isRed) {
					isBal = true;
					opV.isRed = false;
				}
				saved.isRed = true;
			} else {
				boolean wasRed = opV.isRed;
				boolean isRoot = rV == opV;

				if (saved.left != null && saved.left.isRed) {
					opV = rotateRight(opV);
				} else {
					opV = doubleRight(opV);
				}

				opV.isRed = wasRed;
				opV.left.isRed = opV.right.isRed = false;

				if (isRoot) {
					rV = opV;
				} else {
					rV.right = opV;
				}
				isBal = true;
			}
		}
		return rV;
	}
}