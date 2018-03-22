import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

// code can handle multiple edges between one pair of vertices
class TECCs {

	int nV;
	ArrayList<Integer>[] aList;

	int cCnt = 0;
	int[] disc; // cCnt at time of visitation
	int[] low; // vertex with the least disc reachable using at most one back edge

	ArrayDeque<Integer> stack = new ArrayDeque<>();

	ArrayList<ArrayList<Integer>> TECCs = new ArrayList<>();
	ArrayList<Edge> bridges = new ArrayList<>();

	void findTECCs() {
		disc = new int[nV];
		low = new int[nV];

		Arrays.fill(disc, -1); // mark "not visited"

		for(int i = 0; i < nV; i++) {
			if(disc[i] == -1) {
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

	class Edge {
		int v1, v2;

		Edge(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
	}
}