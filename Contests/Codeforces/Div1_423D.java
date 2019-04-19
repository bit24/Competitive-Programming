import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_423D {

	public static void main(String[] args) throws IOException {
		new Div1_423D().main();
	}

	int nV;
	int nE;

	ArrayList<Integer>[] aList;
	ArrayList<Integer>[] cList;

	int[] anc;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		cList = new ArrayList[nV];
		tAList = new ArrayList[nV];

		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			cList[i] = new ArrayList<>();
			tAList[i] = new ArrayList<>();
		}

		Edge[] edges = new Edge[nE];

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
			cList[a].add(c);
			cList[b].add(c);

			edges[i] = new Edge(a, b, c, i);
		}
		Arrays.sort(edges);

		anc = new int[nV];
		for (int i = 0; i < nV; i++) {
			anc[i] = i;
		}

		uTree = new int[4 * nV];
		Arrays.fill(uTree, Integer.MAX_VALUE / 2);

		qTree = new int[4 * nV];

		pRes = new int[nV + 1];

		init = new int[nV + 1];

		for (Edge cE : edges) {
			int aR = fRoot(cE.a);
			int bR = fRoot(cE.b);
			if (aR != bR) {
				anc[aR] = bR;
				tAList[cE.a].add(cE.b);
				tAList[cE.b].add(cE.a);
				cE.inMST = true;
			}
		}

		init();

		edgeI = new int[nV];
		Arrays.fill(edgeI, -1);

		for (Edge cE : edges) {
			if (cE.inMST) {
				if (depth[cE.a] > depth[cE.b]) {
					edgeI[cE.a] = cE.i;
					init[tInd[cE.a]] = cE.c;
				} else {
					edgeI[cE.b] = cE.i;
					init[tInd[cE.b]] = cE.c;
				}
			}
		}

		buildAll(1, 1, nV);

		int[] ans = new int[nE];

		for (Edge cE : edges) {
			if (!cE.inMST) {
				ans[cE.i] = query(cE.a, cE.b) - 1;
				update(cE.a, cE.b, cE.c - 1);
			}
		}

		pushAll(1, 1, nV);

		for (int i = 0; i < nV; i++) {
			if (edgeI[i] != -1) {
				ans[edgeI[i]] = pRes[tInd[i]];
			}
		}

		for (int i = 0; i < nE; i++) {
			if (ans[i] >= Integer.MAX_VALUE / 2) {
				printer.print(-1 + " ");
			} else {
				printer.print(ans[i] + " ");
			}
		}
		printer.println();
		printer.close();
	}

	int[] edgeI;

	int fRoot(int cV) {
		return (anc[cV] == cV) ? cV : (anc[cV] = fRoot(anc[cV]));
	}

	ArrayList<Integer>[] tAList;

	int root = 0;

	int[] par;
	int[] depth;
	int[] heavyC;
	int[] chainS;

	int[] tInd;

	// returns vCount
	int initDFS(int cV) {
		int cDeg = 1;
		int mADeg = 0;
		heavyC[cV] = -1;

		for (int aV : tAList[cV]) {
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
		par = new int[nV];
		depth = new int[nV];
		heavyC = new int[nV];
		chainS = new int[nV];

		tInd = new int[nV];

		par[root] = -1;
		initDFS(root);

		int nTInd = 1;
		for (int cV = 0; cV < nV; cV++) {
			if (cV == root || heavyC[par[cV]] != cV) {
				for (int cMem = cV; cMem != -1; cMem = heavyC[cMem]) {
					chainS[cMem] = cV;
					tInd[cMem] = nTInd++;
				}
			}
		}
	}

	int query(int v1, int v2) {
		int max = 0;
		// if they're tied, v2 will still change
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				max = Math.max(max, query(1, 1, nV, tInd[chainS[v1]], tInd[v1]));
				v1 = par[chainS[v1]];
			} else {
				max = Math.max(max, query(1, 1, nV, tInd[chainS[v2]], tInd[v2]));
				v2 = par[chainS[v2]];
			}
		}

		if (tInd[v1] < tInd[v2]) {
			max = Math.max(max, query(1, 1, nV, tInd[v1] + 1, tInd[v2]));
		} else if (tInd[v1] > tInd[v2]) {
			max = Math.max(max, query(1, 1, nV, tInd[v2] + 1, tInd[v1]));
		}
		return max;
	}

	void update(int v1, int v2, int bnd) {
		// if they're tied, v2 will still update
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				update(1, 1, nV, tInd[chainS[v1]], tInd[v1], bnd);
				v1 = par[chainS[v1]];
			} else {
				update(1, 1, nV, tInd[chainS[v2]], tInd[v2], bnd);
				v2 = par[chainS[v2]];
			}
		}

		if (tInd[v1] < tInd[v2]) {
			update(1, 1, nV, tInd[v1] + 1, tInd[v2], bnd);
		} else if (tInd[v1] > tInd[v2]) {
			update(1, 1, nV, tInd[v2] + 1, tInd[v1], bnd);
		}
	}

	int[] uTree;
	int[] qTree;

	int[] pRes;
	int[] init;

	void update(int nI, int cL, int cR, int uL, int uR, int bnd) {
		if (cR < uL || uR < cL) {
			return;
		}
		if (uL <= cL && cR <= uR) {
			uTree[nI] = Math.min(uTree[nI], bnd);
			return;
		}
		int mid = (cL + cR) >> 1;
		update(nI * 2, cL, mid, uL, uR, bnd);
		update(nI * 2 + 1, mid + 1, cR, uL, uR, bnd);
	}

	int query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}
		if (qL <= cL && cR <= qR) {
			return qTree[nI];
		}
		int mid = (cL + cR) >> 1;
		return Math.max(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	void pushAll(int nI, int cL, int cR) {
		if (cL == cR) {
			pRes[cL] = uTree[nI];
		} else {
			int mid = (cL + cR) >> 1;
			uTree[nI * 2] = Math.min(uTree[nI * 2], uTree[nI]);
			uTree[nI * 2 + 1] = Math.min(uTree[nI * 2 + 1], uTree[nI]);
			pushAll(nI * 2, cL, mid);
			pushAll(nI * 2 + 1, mid + 1, cR);
		}
	}

	void buildAll(int nI, int cL, int cR) {
		if (cL == cR) {
			qTree[nI] = init[cL];
		} else {
			int mid = (cL + cR) >> 1;
			buildAll(nI * 2, cL, mid);
			buildAll(nI * 2 + 1, mid + 1, cR);
			qTree[nI] = Math.max(qTree[nI * 2], qTree[nI * 2 + 1]);
		}
	}

	class Edge implements Comparable<Edge> {
		int a;
		int b;
		int c;
		int i;
		boolean inMST;

		Edge(int a, int b, int c, int i) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.i = i;
		}

		public int compareTo(Edge o) {
			return Integer.compare(c, o.c);
		}
	}
}