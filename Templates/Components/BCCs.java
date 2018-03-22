import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class BCCs {

	int nV;
	ArrayList<Integer>[] aList;

	int cCnt = 0;
	int[] disc;
	int[] low;

	ArrayDeque<Edge> stack = new ArrayDeque<>();

	ArrayList<ArrayList<Integer>> BCCs = new ArrayList<>();
	ArrayList<Integer> AP = new ArrayList<>();

	void findBCCs() {
		disc = new int[nV];
		low = new int[nV];

		Arrays.fill(disc, -1); // mark "not visited"

		for (int i = 0; i < nV; i++) {
			if (disc[i] == -1) {
				dfs(i, -1);
				
				if (!stack.isEmpty()) { // root is part of another biconnected component
					ArrayList<Integer> cBCC = new ArrayList<>();
					BCCs.add(cBCC);
					while (!stack.isEmpty()) {
						Edge e = stack.pop();
						cBCC.add(e.v1);
						cBCC.add(e.v2);
					}
				}
			}
		}

		
		for (int i = 0; i < BCCs.size(); i++) {
			ArrayList<Integer> cBCC = BCCs.get(i);
			Collections.sort(cBCC);
			ArrayList<Integer> pBCC = new ArrayList<>();
			pBCC.add(cBCC.get(0));
			for (int j : cBCC) {
				if (pBCC.get(pBCC.size() - 1) != j) {
					pBCC.add(j);
				}
			}
			BCCs.set(i, pBCC);
		}
		Collections.sort(AP);
		ArrayList<Integer> pAP = new ArrayList<>();
		pAP.add(AP.get(0));
		for (int i : AP) {
			if (pAP.get(pAP.size() - 1) != i) {
				pAP.add(i);
			}
		}
		AP = pAP;
	}

	void dfs(int cV, int pV) {
		disc[cV] = low[cV] = cCnt++;

		int nChldn = 0;
		for (int aV : aList[cV]) {

			if (disc[aV] == -1) { // tree edge
				nChldn++;

				Edge cEdge = new Edge(cV, aV);
				stack.push(cEdge);

				dfs(aV, cV);
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}

				// check if cV is an articulation point for this subtree
				if (pV != -1 ? (low[aV] >= disc[cV]) : (nChldn > 1)) {
					AP.add(cV);
					ArrayList<Integer> cBCC = new ArrayList<>();
					BCCs.add(cBCC);

					while (!stack.peek().equals(cEdge)) {
						Edge e = stack.pop();
						cBCC.add(e.v1);
						cBCC.add(e.v2);
					}
					Edge e = stack.pop();
					cBCC.add(e.v1);
					cBCC.add(e.v2);
				}
			} else if (low[cV] > disc[aV]) { // back edge or front edge
				low[cV] = disc[aV];
			}
		}
	}

	class Edge {
		int v1, v2;

		Edge(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public boolean equals(Object oth) {
			return ((Edge) oth).v1 == v1 && ((Edge) oth).v2 == v2;
		}
	}
}