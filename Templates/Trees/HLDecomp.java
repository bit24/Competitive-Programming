import java.util.ArrayList;

class HLDecomp {
	int nV;

	int root;

	ArrayList<Integer>[] aList;

	int[] par;
	int[] depth;
	int[] heavyC;
	int[] chainS;

	LPSegTree tree;
	int[] tInd;

	// returns vCount
	int initDFS(int cV) {
		int cDeg = 1;
		int mADeg = 0;
		heavyC[cV] = -1;

		for (int aV : aList[cV]) {
			if (aV != par[cV]) {
				par[aV] = cV;
				depth[aV] = depth[cV] + 1;

				int aDeg = initDFS(aV);
				if (aDeg > mADeg) {
					mADeg = aDeg;
					heavyC[cV] = aV;
				}
				cDeg += aDeg;
			}
		}
		return cDeg;
	}

	void init() {
		par = new int[nV + 1];
		depth = new int[nV + 1];
		heavyC = new int[nV + 1];
		chainS = new int[nV + 1];

		tree = new LPSegTree(nV);
		tInd = new int[nV + 1];

		par[root] = -1;
		initDFS(root);

		int nTInd = 1;
		for (int cV = 1; cV <= nV; cV++) {
			if (cV == root || heavyC[par[cV]] != cV) {
				for (int cMem = cV; cMem != -1; cMem = heavyC[cMem]) {
					chainS[cMem] = cV;
					tInd[cMem] = nTInd++;
				}
			}
		}
	}

	int query(int v1, int v2) {
		int sum = 0;
		// if they're tied, v2 will still change
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				sum += tree.query(tInd[chainS[v1]], tInd[v1]);
				v1 = par[chainS[v1]];
			} else {
				sum += tree.query(tInd[chainS[v2]], tInd[v2]);
				v2 = par[chainS[v2]];
			}
		}

		if (tInd[v1] < tInd[v2]) {
			sum += tree.query(tInd[v1], tInd[v2]);
		} else {
			sum += tree.query(tInd[v2], tInd[v1]);
		}
		return sum;
	}

	void update(int v1, int v2, int delta) {
		// if they're tied, v2 will still update
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				tree.update(tInd[chainS[v1]], tInd[v1], delta);
				v1 = par[chainS[v1]];
			} else {
				tree.update(tInd[chainS[v2]], tInd[v2], delta);
				v2 = par[chainS[v2]];
			}
		}

		if (tInd[v1] < tInd[v2]) {
			tree.update(tInd[v1], tInd[v2], delta);
		} else {
			tree.update(tInd[v2], tInd[v1], delta);
		}
	}

	class LPSegTree {
		int nE;
		int[] tree;
		int[] lazy;

		LPSegTree(int nE) {
			this.nE = nE;
			tree = new int[nE * 4];
			lazy = new int[nE * 4];
		}

		int query(int qL, int qR) {
			if (qL > qR) {
				return 0;
			}
			return query(1, 1, nE, qL, qR);
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
				update(1, 1, nE, uL, uR, delta);
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
	}
}
