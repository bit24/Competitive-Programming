import java.util.ArrayDeque;
import java.util.Arrays;

// runtime: O(V^2*E)
// unit capacities: O(min{V^2/3, E^1/2}*E)
// bipartite matching: O(E*V^1/2), Hopcroft-Karp

public class Dinics {

	static int nV;
	static int nE;

	static int[] lSt;
	static int[] nLSt;

	static int[] nxtE; // double number of input edges
	static int[] end;
	static int[] cap;
	static int[] flow;

	static int source;
	static int sink;

	static int[] level;

	static void addEdge(int a, int b, int c) { // must create a back edge so flow can be undone
		nxtE[nE] = lSt[a];
		lSt[a] = nE;
		end[nE] = b;
		cap[nE++] = c;

		nxtE[nE] = lSt[b];
		lSt[b] = nE;
		end[nE] = a;
		cap[nE++] = 0;
	}

	// constructs layered network
	static boolean bfs() {
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(source);

		while (!queue.isEmpty()) {
			int cV = queue.remove();
			for (int eI = lSt[cV]; eI != -1; eI = nxtE[eI]) {
				int aV = end[eI];
				if (cap[eI] == flow[eI]) { // note flows can be negative
					continue;
				}
				if (level[aV] != -1) {
					continue;
				}
				level[aV] = level[cV] + 1;
				queue.add(aV);
			}
		}
		return level[sink] != -1;
	}

	// finds augmenting flow
	static long dfs(int cV, long pushed) {
		if (pushed == 0) {
			return 0;
		}
		if (cV == sink) {
			return pushed;
		}
		for (int eI = nLSt[cV]; eI != -1; nLSt[cV] = eI = nxtE[eI]) {
			int aV = end[eI];
			if (level[aV] != level[cV] + 1 || cap[eI] == flow[eI]) {
				continue;
			}
			long aPushed = dfs(aV, Math.min(pushed, cap[eI] - flow[eI]));
			if (aPushed > 0) {
				flow[eI] += aPushed;
				flow[eI ^ 1] -= aPushed; // inverse edge
				return aPushed;
			}
		}
		return 0;
	}

	static long cFlow() {
		level = new int[nV];
		long tFlow = 0;
		while (true) {
			Arrays.fill(level, -1);
			level[source] = 0;
			if (!bfs()) {
				break;
			}
			System.arraycopy(lSt, 0, nLSt, 0, nV);
			long pushed = dfs(source, Long.MAX_VALUE / 4);
			while (pushed > 0) {
				tFlow += pushed;
				pushed = dfs(source, Long.MAX_VALUE / 4);
			}
		}
		return tFlow;
	}
}
