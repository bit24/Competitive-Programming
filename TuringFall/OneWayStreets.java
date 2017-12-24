import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class OneWayStreets {

	public static void main(String[] args) throws IOException {
		new OneWayStreets().execute();
	}

	ArrayList<Integer>[] cAList;
	ArrayList<Edge>[] orig;
	int[] depth;
	int[][] anc;

	int[] uArray;
	int[] dArray;

	TreeSet<Edge> dEdges = new TreeSet<Edge>();

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		cAList = new ArrayList[nV];
		orig = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
			cAList[i] = new ArrayList<Integer>();
			orig[i] = new ArrayList<Edge>();
		}

		Edge[] edges = new Edge[nE];
		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			edges[i] = new Edge(Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()) - 1);
			aList[edges[i].v1].add(edges[i].v2);
			aList[edges[i].v2].add(edges[i].v1);
		}
		findTECCs();

		depth = new int[nV];

		int root = id[0];
		anc = new int[nV][21];
		anc[root][0] = root;

		dfsComp(0, new boolean[nV]);
		// assertion: cAList will not contain duplicates

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}

		uArray = new int[nV];
		dArray = new int[nV];
		int nI = Integer.parseInt(reader.readLine());
		for (int i = 0; i < nI; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = id[Integer.parseInt(inputData.nextToken()) - 1];
			int b = id[Integer.parseInt(inputData.nextToken()) - 1];
			int lca = findLCA(a, b);
			uArray[a]++;
			dArray[b]++;
			uArray[lca]--;
			dArray[lca]--;
		}

		dfsAnalyze(root);

		for (Edge oEdge : edges) {
			if (dEdges.contains(oEdge)) {
				printer.print('R');
				assert (!dEdges.contains(new Edge(oEdge.v2, oEdge.v1)));
			} else if (dEdges.contains(new Edge(oEdge.v2, oEdge.v1))) {
				printer.print('L');
			} else {
				printer.print('B');
			}
		}
		printer.println();
		printer.close();
	}

	void dfsAnalyze(int cV) {
		for (int aI = 0; aI < cAList[cV].size(); aI++) {
			int aV = cAList[cV].get(aI);
			dfsAnalyze(aV);
			uArray[cV] += uArray[aV];
			dArray[cV] += dArray[aV];
			Edge oEdge = orig[cV].get(aI);
			if (uArray[aV] != 0) {
				if (id[oEdge.v1] == cV) {
					dEdges.add(new Edge(oEdge.v2, oEdge.v1));
				} else {
					dEdges.add(new Edge(oEdge.v1, oEdge.v2));
				}
			}
			if (dArray[aV] != 0) {
				if (id[oEdge.v1] == cV) {
					dEdges.add(new Edge(oEdge.v1, oEdge.v2));
				} else {
					dEdges.add(new Edge(oEdge.v2, oEdge.v1));
				}
			}
		}
	}

	void dfsComp(int cV, boolean[] visited) {
		visited[cV] = true;
		for (int aV : aList[cV]) {
			if (!visited[aV]) {
				if (id[cV] != id[aV]) {
					cAList[id[cV]].add(id[aV]);
					anc[id[aV]][0] = id[cV];
					depth[id[aV]] = depth[id[cV]] + 1;
					orig[id[cV]].add(new Edge(cV, aV));
				}
				dfsComp(aV, visited);
			}
		}
	}

	int findLCA(int a, int b) {
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

	int nV;
	ArrayList<Integer>[] aList;

	int[] disc;
	int[] low;
	int cCnt = 0;

	int[] parent;

	ArrayDeque<Edge> stack = new ArrayDeque<Edge>();

	ArrayList<ArrayList<Integer>> TECCS;

	int[] id;

	void findTECCs() {
		low = new int[nV];
		disc = new int[nV];
		parent = new int[nV];
		TECCS = new ArrayList<ArrayList<Integer>>();
		id = new int[nV];

		// mark "not visited"
		Arrays.fill(disc, -1);

		dfs(0);

		// if stack isn't empty (whole thing is 2-edge connected)
		if (!stack.isEmpty()) {
			ArrayList<Integer> cTECC = new ArrayList<Integer>();
			TECCS.add(cTECC);
			int cID = stack.peek().v1;
			while (!stack.isEmpty()) {
				Edge e = stack.pop();
				cTECC.add(e.v1);
				cTECC.add(e.v2);
				id[e.v1] = cID;
				id[e.v2] = cID;
			}
		}

		for (int i = 0; i < TECCS.size(); i++) {
			ArrayList<Integer> cTECC = TECCS.get(i);
			Collections.sort(cTECC);
			ArrayList<Integer> pTECC = new ArrayList<Integer>();
			pTECC.add(cTECC.get(0));
			for (int j : cTECC) {
				if (pTECC.get(pTECC.size() - 1) != j) {
					pTECC.add(j);
				}
			}
		}
	}

	void dfs(int cV) {
		disc[cV] = low[cV] = cCnt++;

		int nCPar = 0;
		for (int aV : aList[cV]) {

			// aV not visited (child)
			if (disc[aV] == -1) {
				parent[aV] = cV;

				Edge cEdge = new Edge(cV, aV);
				stack.push(cEdge);

				dfs(aV);
				// update low values
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}

				// check if there edge from aV to cV is a bridge
				if (low[aV] > disc[cV]) {
					ArrayList<Integer> cTECC = new ArrayList<Integer>();
					TECCS.add(cTECC);

					int cID = aV;
					while (!stack.peek().equals(cEdge)) {
						Edge e = stack.pop();
						cTECC.add(e.v1);
						cTECC.add(e.v2);
						id[e.v1] = cID;
						id[e.v2] = cID;
					}
					stack.pop();
					cTECC.add(aV);
					id[aV] = cID;
				}
			} else if (aV == parent[cV]) {
				nCPar++;
				if (nCPar > 1 && low[cV] > disc[parent[cV]]) {
					low[cV] = disc[parent[cV]];
				}
			}
			// back edge
			else if (disc[aV] < disc[cV]) {
				stack.push(new Edge(cV, aV));
				if (low[cV] > disc[aV]) {
					low[cV] = disc[aV];
				}
			}
		}
	}

	class Edge implements Comparable<Edge> {
		int v1;
		int v2;

		Edge(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public boolean equals(Object obj) {
			Edge other = (Edge) (obj);
			return other.v1 == v1 && other.v2 == v2;
		}

		public int compareTo(Edge o) {
			return v1 < o.v1 ? -1 : v1 == o.v1 ? Integer.compare(v2, o.v2) : 1;
		}
	}
}
