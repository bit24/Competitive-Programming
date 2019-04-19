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

class Main {

	public static void main(String[] args) throws IOException {
		new Main().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		String nLine = reader.readLine();
		while (nLine != null) {
			nV = Integer.parseInt(nLine);
			aList = new ArrayList[nV];
			for (int i = 0; i < nV; i++) {
				StringTokenizer inputData = new StringTokenizer(reader.readLine());
				int cV = Integer.parseInt(inputData.nextToken());
				aList[cV] = new ArrayList<>();
				inputData.nextToken();
				while (inputData.hasMoreTokens()) {
					aList[cV].add(Integer.parseInt(inputData.nextToken()));
				}
			}
			findTECCs();
			Collections.sort(bridges);

			printer.println(bridges.size() + " critical links");
			for (Edge cE : bridges) {
				printer.println(cE.v1 + " - " + cE.v2);
			}
			printer.println();
			reader.readLine();
			nLine = reader.readLine();
		}
		printer.close();
	}

	int nV;
	ArrayList<Integer>[] aList;

	int cCnt = 0;
	int[] disc; // cCnt at time of visitation
	int[] low; // vertex with the least disc reachable using at most one back edge

	ArrayDeque<Integer> stack = new ArrayDeque<>();

	ArrayList<ArrayList<Integer>> TECCs = new ArrayList<>();
	ArrayList<Edge> bridges = new ArrayList<>();

	void findTECCs() {
		cCnt = 0;
		low = new int[nV];
		disc = new int[nV];
		TECCs.clear();
		bridges.clear();

		Arrays.fill(disc, -1); // mark "not visited"

		for (int i = 0; i < nV; i++) {
			if (disc[i] == -1) {
				dfs(i, -1);
			}
		}
	}

	void dfs(int cV, int pV) {
		disc[cV] = low[cV] = cCnt++;
		stack.push(cV);

		int nCPar = 0;

		for (int aV : aList[cV]) {
			if (disc[aV] == -1) { // tree edge
				dfs(aV, cV);
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}
			} else if (aV == pV) {
				nCPar++;
			} else if (disc[aV] < low[cV]) { // back edge or forward edge
				low[cV] = disc[aV];
			}
		}

		if (nCPar > 1 && disc[pV] < low[cV]) {
			low[cV] = disc[pV];
		}

		if (low[cV] == disc[cV]) {
			ArrayList<Integer> cTECC = new ArrayList<>();
			TECCs.add(cTECC);
			while (stack.peek() != cV) {
				cTECC.add(stack.pop());
			}
			cTECC.add(stack.pop());
			if (pV != -1) {
				bridges.add(new Edge(pV, cV));
			}
		}
	}

	class Edge implements Comparable<Edge> {
		int v1, v2;

		Edge(int v1, int v2) {
			if (v1 < v2) {
				this.v1 = v1;
				this.v2 = v2;
			} else {
				this.v1 = v2;
				this.v2 = v1;
			}
		}

		public boolean equals(Object oth) {
			return ((Edge) oth).v1 == v1 && ((Edge) oth).v2 == v2;
		}

		public int compareTo(Edge o) {
			return v1 < o.v1 ? -1 : v1 == o.v1 ? (v2 < o.v2 ? -1 : v2 == o.v2 ? 0 : 1) : 1;
		}
	}
}