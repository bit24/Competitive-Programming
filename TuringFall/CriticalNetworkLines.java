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

public class CriticalNetworkLines {

	public static void main(String[] args) throws IOException {
		new CriticalNetworkLines().execute();
	}

	boolean[][] prov;

	ArrayList<Integer>[] aList;

	int nV;

	int[] disc;
	int[] low;
	int cCnt = 0;

	ArrayDeque<Edge> stack = new ArrayDeque<Edge>();

	int[] id;

	ArrayList<Integer>[] nAList;

	int[][] cnts;

	int tA = 0;
	int tB = 0;
	int ans = 0;

	int root;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		int nA = Integer.parseInt(inputData.nextToken());
		int nB = Integer.parseInt(inputData.nextToken());

		prov = new boolean[nV][2];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nA; i++) {
			prov[Integer.parseInt(inputData.nextToken()) - 1][0] = true;
		}
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nB; i++) {
			prov[Integer.parseInt(inputData.nextToken()) - 1][1] = true;
		}

		aList = new ArrayList[nV];
		nAList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
			nAList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		low = new int[nV];
		disc = new int[nV];
		id = new int[nV];
		
		// mark "not visited"
		Arrays.fill(disc, -1);
		
		dfs(root, -1);
		
		// if stack isn't empty (whole thing is biconnected)
		if (!stack.isEmpty()) {
			int cId1 = stack.getFirst().v1;
			id[cId1] = cId1;
			while (!stack.isEmpty()) {
				Edge e = stack.pop();
				id[e.v1] = cId1;
				id[e.v2] = cId1;
			}
		}

		cnts = new int[nV][2];

		for (int cV = 0; cV < nV; cV++) {
			int cId = id[cV];
			if (prov[cV][0] && cnts[cId][0] == 0) {
				tA++;
				cnts[cId][0] = 1;
			}
			if (prov[cV][1] && cnts[cId][1] == 0) {
				tB++;
				cnts[cId][1] = 1;
			}
			for (int aV : aList[cV]) {
				if (id[aV] != cId) {
					nAList[cId].add(id[aV]);
				}
			}
		}

		for (int i = 0; i < nV; i++) {
			if (nAList[i].isEmpty()) {
				continue;
			}
			Collections.sort(nAList[i]);
			ArrayList<Integer> nDup = new ArrayList<Integer>();
			nDup.add(nAList[i].get(0));
			for (int j = 1; j < nAList[i].size(); j++) {
				if (nAList[i].get(j) != nAList[i].get(j - 1)) {
					nDup.add(nAList[i].get(j));
				}
			}
			nAList[i] = nDup;
		}

		root = id[0];
		fCnts(root, -1);
		printer.println(ans);
		printer.close();
	}

	void fCnts(int cV, int pV) {
		for (int aV : nAList[cV]) {
			if (aV != pV) {
				fCnts(aV, cV);
				cnts[cV][0] += cnts[aV][0];
				cnts[cV][1] += cnts[aV][1];
			}
		}
		for (int aV : nAList[cV]) {
			if (aV != pV) {
				if (cnts[aV][0] == 0 || cnts[aV][0] == tA || cnts[aV][1] == 0 || cnts[aV][1] == tB) {
					ans++;
				}
			}
		}
	}

	void dfs(int cV, int pV) {
		disc[cV] = low[cV] = cCnt++;

		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}

			// aV not visited (child)
			if (disc[aV] == -1) {

				Edge cEdge = new Edge(cV, aV);
				stack.push(cEdge);

				dfs(aV, cV);
				// update low values
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}

				// check if the edge from aV to cV is a bridge
				if (low[aV] > disc[cV]) {
					int cId = aV;
					id[aV] = cId;

					while (!stack.peek().equals(cEdge)) {
						Edge e = stack.pop();
						id[e.v1] = cId;
						id[e.v2] = cId;
					}
					stack.pop();
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

	class Edge {
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
	}

}
