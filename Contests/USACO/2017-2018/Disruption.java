import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Disruption {

	public static void main(String[] args) throws IOException {
		new Disruption().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("disrupt.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("disrupt.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nU = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV + 1];
		iList = new ArrayList[nV + 1];

		for (int i = 1; i <= nV; i++) {
			aList[i] = new ArrayList<>();
			iList[i] = new ArrayList<>();
		}

		for (int i = 1; i < nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
			iList[a].add(i);
			iList[b].add(i);
		}

		root = 1;
		init();

		for (int i = 1; i <= nU; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			int c = Integer.parseInt(inputData.nextToken());
			update(a, b, c);
		}

		ass(tree.query(tInd[root], tInd[root]) == Integer.MAX_VALUE);

		int[] ans = new int[nV + 1];
		for (int cV = 2; cV <= nV; cV++) {
			ans[parEI[cV]] = tree.query(tInd[cV], tInd[cV]);
		}

		for (int i = 1; i < nV; i++) {
			if (ans[i] == Integer.MAX_VALUE) {
				printer.println(-1);
			} else {
				printer.println(ans[i]);
			}
		}
		printer.close();
	}

	int nV;

	int root;

	ArrayList<Integer>[] aList;
	ArrayList<Integer>[] iList;

	int[] par;
	int[] parEI;
	int[] depth;
	int[] heavyC;
	int[] chainS;

	LPSegTree tree;
	int[] tInd;

	// returns vCount
	int initDFS(int cV) {
		int cSize = 1;
		int mASize = 0;
		heavyC[cV] = -1;

		for (int aI = 0; aI < aList[cV].size(); aI++) {
			int aV = aList[cV].get(aI);
			if (aV != par[cV]) {
				par[aV] = cV;
				parEI[aV] = iList[cV].get(aI);
				depth[aV] = depth[cV] + 1;

				int aSize = initDFS(aV);
				if (aSize > mASize) {
					mASize = aSize;
					heavyC[cV] = aV;
				}
				cSize += aSize;
			}
		}
		return cSize;
	}

	void init() {
		par = new int[nV + 1];
		parEI = new int[nV + 1];
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
		ass(nTInd == nV + 1);
	}

	// note: do not update the LCA
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
			tree.update(tInd[v1] + 1, tInd[v2], delta);
		} else {
			tree.update(tInd[v2] + 1, tInd[v1], delta);
		}
	}

	class LPSegTree {
		int nE;
		int[] tree;
		int[] lazy;

		LPSegTree(int nE) {
			this.nE = nE;
			tree = new int[nE * 4];
			Arrays.fill(tree, Integer.MAX_VALUE);
			lazy = new int[nE * 4];
			Arrays.fill(lazy, Integer.MAX_VALUE);
		}

		int query(int qL, int qR) {
			if (qL > qR) {
				return Integer.MAX_VALUE;
			}
			return query(1, 1, nE, qL, qR);
		}

		int query(int nI, int cL, int cR, int qL, int qR) {
			if (cR < qL || qR < cL) {
				return Integer.MAX_VALUE;
			}

			if (lazy[nI] != Integer.MAX_VALUE) {
				tree[nI] = Math.min(tree[nI], lazy[nI]);
				if (cL != cR) {
					// internal node
					lazy[nI * 2] = Math.min(lazy[nI * 2], lazy[nI]);
					lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], lazy[nI]);
				}
				lazy[nI] = Integer.MAX_VALUE;
			}

			if (qL <= cL && cR <= qR) {
				return tree[nI];
			}

			int mid = (cL + cR) >> 1;

			int leftQ = query(nI * 2, cL, mid, qL, qR);
			int rightQ = query(nI * 2 + 1, mid + 1, cR, qL, qR);

			return Math.min(leftQ, rightQ);
		}

		void update(int uL, int uR, int delta) {
			if (uL <= uR) {
				update(1, 1, nE, uL, uR, delta);
			}
		}

		void update(int nI, int cL, int cR, int uL, int uR, int delta) {
			// current node needs to have no lazy before returning because it will be used to compute tree[par]
			if (lazy[nI] != Integer.MAX_VALUE) {
				tree[nI] = Math.min(tree[nI], lazy[nI]);
				if (cL != cR) {
					lazy[nI * 2] = Math.min(lazy[nI * 2], lazy[nI]);
					lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], lazy[nI]);
				}
				lazy[nI] = Integer.MAX_VALUE;
			}
			if (cR < uL || uR < cL) {
				return;
			}

			if (uL <= cL && cR <= uR) {
				tree[nI] = Math.min(tree[nI], delta);
				if (cL != cR) {
					lazy[nI * 2] = Math.min(lazy[nI * 2], delta);
					lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], delta);
				}
				return;
			}

			int mid = (cL + cR) >> 1;

			update(nI * 2, cL, mid, uL, uR, delta);
			update(nI * 2 + 1, mid + 1, cR, uL, uR, delta);

			tree[nI] = Math.min(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	static void ass(boolean inp) {
		if (!inp) {
			throw new RuntimeException();
		}
	}
}