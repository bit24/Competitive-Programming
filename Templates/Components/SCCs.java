import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

class SCCs {

	int nV;
	ArrayList<Integer>[] aList;

	int cCnt = 0;
	int[] disc; // cCnt at time of visitation
	int[] low; // vertex with the least disc reachable using at most one back edge

	ArrayDeque<Integer> stack = new ArrayDeque<>();

	ArrayList<ArrayList<Integer>> SCCs = new ArrayList<>();

	void findSCCs() {
		disc = new int[nV];
		low = new int[nV];

		Arrays.fill(disc, -1); // mark "not visited"

		for (int i = 0; i < nV; i++) {
			if (disc[i] == -1) {
				dfs(i);
			}
		}
	}

	void dfs(int cV) {
		disc[cV] = low[cV] = cCnt++;
		stack.push(cV);

		for (int aV : aList[cV]) {
			if (disc[aV] == -1) { // tree edge
				dfs(aV);
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}
			} else if (disc[aV] < low[cV]) { // back edge, forward edge, or cross edge; only vertices in stack have disc
				low[cV] = disc[aV];
			}
		}

		if (low[cV] == disc[cV]) {
			ArrayList<Integer> cSCC = new ArrayList<Integer>();
			SCCs.add(cSCC);
			while (stack.peek() != cV) {
				disc[stack.peek()] = Integer.MAX_VALUE; // so that cross edges will not interfere
				cSCC.add(stack.pop());
			}
			disc[stack.peek()] = Integer.MAX_VALUE;
			cSCC.add(stack.pop());
		}
	}
}
