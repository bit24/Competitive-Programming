import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RCC_14D {

	static int nV;

	static ArrayList<Integer>[] aList;

	static PrintWriter printer;

	static int cQ = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}
		depth = new int[nV];
		maxSDist = new int[nV];

		maxDDist = new int[nV][3];
		maxDDistV = new int[nV][3];

		anc = new int[nV][21];
		maxRDist = new int[nV][21];
		maxRDistFR = new int[nV][21];

		dfs(0, -1);
		preprocess();

		int nQ = Integer.parseInt(reader.readLine());
		for (cQ = 1; cQ <= nQ; cQ++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			printer.println(query(a, b));
		}
		printer.close();
	}

	static int[] depth;
	static int[] maxSDist;

	static int[][] anc;
	static int[][] maxRDist;
	static int[][] maxRDistFR;

	static int[][] maxDDist;
	static int[][] maxDDistV;

	static void dfs(int cV, int pV) {
		maxDDist[cV][0] = 0;
		maxDDistV[cV][0] = cV;
		maxDDist[cV][1] = maxDDist[cV][2] = -1;

		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}
			depth[aV] = depth[cV] + 1;
			anc[aV][0] = cV;
			dfs(aV, cV);

			if (maxDDist[cV][0] < maxDDist[aV][0] + 1) {
				maxDDist[cV][2] = maxDDist[cV][1];
				maxDDistV[cV][2] = maxDDistV[cV][1];

				maxDDist[cV][1] = maxDDist[cV][0];
				maxDDistV[cV][1] = maxDDistV[cV][0];

				maxDDist[cV][0] = maxDDist[aV][0] + 1;
				maxDDistV[cV][0] = aV;
			} else if (maxDDist[cV][1] < maxDDist[aV][0] + 1) {
				maxDDist[cV][2] = maxDDist[cV][1];
				maxDDistV[cV][2] = maxDDistV[cV][1];

				maxDDist[cV][1] = maxDDist[aV][0] + 1;
				maxDDistV[cV][1] = aV;
			} else if (maxDDist[cV][2] < maxDDist[aV][0] + 1) {
				maxDDist[cV][2] = maxDDist[aV][0] + 1;
				maxDDistV[cV][2] = aV;
			}
		}

		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}

			if (maxDDistV[cV][0] == aV) {
				maxSDist[aV] = maxDDist[cV][1] + 1;
			} else {
				maxSDist[aV] = maxDDist[cV][0] + 1;
			}
		}
	}

	static int query(int a, int b) {
		if (depth[a] > depth[b]) {
			return query(b, a);
		}

		int lca = fLCA(a, b);

		int max = 0;

		// case: root
		max = Math.max(max, depth[a]);
		if (cQ == 136) {
			// printer.print("a: " + max + " ");
		}

		// case: sibling of an ancestor (note root will be larger than any other ancestor)
		int cD = depth[lca];
		int cV = lca;
		int dist = depth[a] - depth[lca];

		for (int i = 0; i <= 20; i++) {
			if ((cD & (1 << i)) != 0) {
				max = Math.max(max, dist + maxRDist[cV][i]);
				cV = anc[cV][i];
				dist += (1 << i);
			}
		}

		if (cQ == 136) {
			// printer.print("b: " + max + " ");
		}

		int dDif = depth[b] - depth[a];
		int depthMid = depth[lca] + (dDif + 1) / 2;

		int mid = jump(b, depth[b] - depthMid);

		// case: mid
		max = Math.max(max, depth[b] - depth[mid]);

		if (cQ == 136) {
			// printer.print("c: " + max + " ");
		}

		if (mid != lca) { // if depth[a] != depth[b]
			// case right: siblings up to children of mid (note mid will be further than any other ancestor)
			if (depth[b] >= depth[mid] + 1) {
				cD = depth[b] - depth[mid];
				cV = b;
				dist = 0;
				for (int i = 0; i <= 20; i++) {
					if ((cD & (1 << i)) != 0) {
						max = Math.max(max, dist + maxRDist[cV][i]);
						cV = anc[cV][i];
						dist += (1 << i);
					}
				}

				if (cQ == 136) {
					// printer.print("d: " + max + " ");
				}
			}
		} else if (depth[b] >= depth[mid] + 2) { // mid == lca
			// case right: complete section
			cD = depth[b] - depth[mid] - 1;
			cV = b;
			dist = 0;
			for (int i = 0; i <= 20; i++) {
				if ((cD & (1 << i)) != 0) {
					max = Math.max(max, dist + maxRDist[cV][i]);
					cV = anc[cV][i];
					dist += (1 << i);
				}
			}

			if (cQ == 136) {
				// printer.print("e: " + max + " ");
			}
		}

		if (depth[mid] >= depth[lca] + 2) {
			// case right: siblings up to grandchildren of lca (note siblings of children of lca includes other branch)

			cD = depth[mid] - depth[lca] - 1;
			cV = mid;
			for (int i = 0; i <= 20; i++) {
				if ((cD & (1 << i)) != 0) {
					max = Math.max(max, maxRDistFR[cV][i] - 2 * depth[lca] + depth[a]);
					cV = anc[cV][i];
				}
			}

			if (cQ == 136) {
				// printer.print("f: " + max + " ");
			}
		}

		if (a == lca) {
			int bB = jump(b, depth[b] - depth[lca] - 1);
			if (maxDDistV[lca][0] != bB) {
				max = Math.max(max, maxDDist[lca][0]);
			} else {
				max = Math.max(max, maxDDist[lca][1]);
			}

			max = Math.max(max, maxDDist[b][0]);

			if (cQ == 136) {
				// printer.print("g: " + max + " ");
			}
			return max;
		}

		if (depth[a] >= depth[lca] + 2) {
			// case left: siblings up to grandchildren of lca (note siblings of children of lca includes other branch)
			cD = depth[a] - depth[lca] - 1;
			cV = a;
			dist = 0;
			for (int i = 0; i <= 20; i++) {
				if ((cD & (1 << i)) != 0) {
					max = Math.max(max, dist + maxRDist[cV][i]);
					cV = anc[cV][i];
					dist += (1 << i);
				}
			}

			if (cQ == 136) {
				// printer.print("h: " + max + " ");
			}
		}

		max = Math.max(max, maxDDist[a][0]);
		max = Math.max(max, maxDDist[b][0]);

		if (cQ == 136) {
			// printer.print("i: " + max + " ");
		}

		int aB = jump(a, depth[a] - depth[lca] - 1);
		int bB = jump(b, depth[b] - depth[lca] - 1);

		if (maxDDistV[lca][0] != aB && maxDDistV[lca][0] != bB) {
			max = Math.max(max, depth[a] - depth[lca] + maxDDist[lca][0]);
		} else if (maxDDistV[lca][1] != aB && maxDDistV[lca][1] != bB) {
			max = Math.max(max, depth[a] - depth[lca] + maxDDist[lca][1]);
		} else {
			max = Math.max(max, depth[a] - depth[lca] + maxDDist[lca][2]);
		}

		if (cQ == 136) {
			// printer.print("j: " + max + " ");
		}

		return max;
	}

	static void preprocess() {
		anc[0][0] = 0;
		for (int i = 0; i < nV; i++) {
			maxRDist[i][0] = maxSDist[i];
			maxRDistFR[i][0] = maxSDist[i] + depth[i] - 2;
		}

		for (int exp = 1, dist = 1; exp <= 20; exp++, dist <<= 1) {
			for (int cV = 0; cV < nV; cV++) {
				int step = anc[cV][exp - 1];
				anc[cV][exp] = anc[step][exp - 1];
				maxRDist[cV][exp] = Math.max(maxRDist[cV][exp - 1], dist + maxRDist[step][exp - 1]);
				maxRDistFR[cV][exp] = Math.max(maxRDistFR[cV][exp - 1], maxRDistFR[step][exp - 1]);
			}
		}
	}

	static int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int dif = depth[b] - depth[a];
		for (int i = 0; i <= 20; i++) {
			if ((dif & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}
		if (a == b) {
			return a;
		}
		for (int i = 20; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}

	static int jump(int cV, int d) {
		for (int i = 0; i <= 20; i++) {
			if ((d & (1 << i)) != 0) {
				cV = anc[cV][i];
			}
		}
		return cV;
	}
}