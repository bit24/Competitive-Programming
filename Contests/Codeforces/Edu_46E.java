import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_46E {

	public static void main(String[] args) throws IOException {
		new Edu_46E().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		findTECCs();

		cAList = new ArrayList[nV];

		for (int i = 0; i < nV; i++) {
			cAList[i] = new ArrayList<>();
		}

		for (Edge cBridge : bridges) {
			cAList[id[cBridge.v1]].add(id[cBridge.v2]);
			cAList[id[cBridge.v2]].add(id[cBridge.v1]);
		}

		fDiameter(id[0], -1);
		printer.println(ans);
		printer.close();
	}

	int ans = 0;

	int fDiameter(int cV, int pV) {
		int longest = 0;
		for (int aV : cAList[cV]) {
			if (aV != pV) {
				int aDFS = fDiameter(aV, cV);
				ans = Math.max(ans, longest + aDFS);
				if (aDFS > longest) {
					longest = aDFS;
				}
			}
		}
		return longest + 1;
	}

	int nV;
	int nE;
	ArrayList<Integer>[] aList;

	int cCnt = 0;
	int[] disc; // cCnt at time of visitation
	int[] low; // vertex with the least disc reachable using at most one back edge

	ArrayDeque<Integer> stack = new ArrayDeque<>();

	ArrayList<Edge> bridges = new ArrayList<>();

	int[] id;

	ArrayList<Integer>[] cAList;

	void findTECCs() {
		disc = new int[nV];
		low = new int[nV];
		id = new int[nV];

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
			while (stack.peek() != cV) {
				id[stack.pop()] = cV;
			}
			id[stack.pop()] = cV;
			if (pV != -1) {
				bridges.add(new Edge(pV, cV));
			}
		}
	}

	class Edge {
		int v1, v2;

		Edge(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
	}
}
