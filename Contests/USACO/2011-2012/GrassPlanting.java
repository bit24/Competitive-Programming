import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GrassPlanting {

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		new GrassPlanting().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("grassplant.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("grassplant.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV + 1];
		for (int i = 1; i < nV + 1; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
		}

		root = 1;
		init();

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			boolean plant = inputData.nextToken().equals("P");
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			if (plant) {
				update(a, b, 1);
			} else {
				printer.println(query(a, b));
			}
		}
		reader.close();
		printer.close();
	}

	int nV;

	int root;

	boolean[] visited;

	int[] parent;
	int[] depth;
	int[] heavyC;
	int[] chainS;

	LPSegTree tree;
	int[] treeInd;

	// returns vCount
	int initDFS(int cV) {
		visited[cV] = true;
		int cV_Deg = 1;
		int mC_Deg = 0;
		heavyC[cV] = -1;

		for (int adj : aList[cV]) {
			if (!visited[adj]) {
				parent[adj] = cV;

				depth[adj] = depth[cV] + 1;

				int cDeg = initDFS(adj);
				if (cDeg > mC_Deg) {
					mC_Deg = cDeg;
					heavyC[cV] = adj;
				}
				cV_Deg += cDeg;
			}
		}
		return cV_Deg;
	}

	void init() {
		visited = new boolean[nV + 1];

		parent = new int[nV + 1];
		depth = new int[nV + 1];
		heavyC = new int[nV + 1];
		chainS = new int[nV + 1];

		tree = new LPSegTree(nV);
		treeInd = new int[nV + 1];

		parent[root] = -1;
		initDFS(root);

		int cTreeInd = 1;
		for (int cV = 1; cV <= nV; cV++) {
			if (cV == root || heavyC[parent[cV]] != cV) {
				for (int cMem = cV; cMem != -1; cMem = heavyC[cMem]) {
					chainS[cMem] = cV;
					treeInd[cMem] = cTreeInd++;
				}
			}
		}
	}

	int query(int v1, int v2) {
		int sum = 0;
		// if they're tied, v2 will still change
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				sum += tree.query(treeInd[chainS[v1]], treeInd[v1]);
				v1 = parent[chainS[v1]];
			} else {
				sum += tree.query(treeInd[chainS[v2]], treeInd[v2]);
				v2 = parent[chainS[v2]];
			}
		}

		if (treeInd[v1] < treeInd[v2]) {
			sum += tree.query(treeInd[v1] + 1, treeInd[v2]);
		} else {
			sum += tree.query(treeInd[v2] + 1, treeInd[v1]);
		}
		return sum;
	}

	void update(int v1, int v2, int delta) {
		// if they're tied, v2 will still update
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				tree.update(treeInd[chainS[v1]], treeInd[v1], delta);
				v1 = parent[chainS[v1]];
			} else {
				tree.update(treeInd[chainS[v2]], treeInd[v2], delta);
				v2 = parent[chainS[v2]];
			}
		}

		if (treeInd[v1] < treeInd[v2]) {
			tree.update(treeInd[v1] + 1, treeInd[v2], delta);
		} else {
			tree.update(treeInd[v2] + 1, treeInd[v1], delta);
		}
	}

	class LPSegTree {
		int numE;
		int[] tree;
		int[] lazy;

		LPSegTree(int numE) {
			this.numE = numE;
			tree = new int[numE * 4];
			lazy = new int[numE * 4];
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
			// current node needs to have no lazy before returning because it will be used to compute tree[parent]
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
