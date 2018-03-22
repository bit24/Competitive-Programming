import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

// runtime is min(E * maxFlow, V * E^2)
// can be extended to find the min cut
// min cut consists of edges spanning the sets of reachable via augmented path in the residual graph and the unreachable

class EdmondKarp {

	static int nV;
	static ArrayList<Integer>[] aList;
	static int[][] res; // residual

	static int source;
	static int sink;

	static int tFlow;

	static void cleanAList() {
		boolean[][] connect = new boolean[nV][nV];
		for (int i = 0; i < nV; i++) {
			for (int j : aList[i]) {
				connect[i][j] = connect[j][i] = true;
			}
		}

		for (int i = 0; i < nV; i++) {
			aList[i].clear();
			for (int j = 0; j < nV; j++) {
				if (connect[i][j]) {
					aList[i].add(j);
				}
			}
		}
	}

	static int[] FPTS() {
		int[] prev = new int[nV];
		Arrays.fill(prev, -2);
		prev[source] = -1;

		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(source);

		while (!queue.isEmpty()) {
			int cV = queue.remove();
			if (cV == sink) {
				return prev;
			}

			for (int adj : aList[cV]) {
				if (prev[adj] == -2 && res[cV][adj] > 0) {
					prev[adj] = cV;
					queue.add(adj);
				}
			}
		}

		return null;
	}

	static void computeMFRes() {
		while (true) {
			int[] prev = FPTS();
			if (prev == null) {
				break;
			}

			int cV = sink;
			int bNeck = Integer.MAX_VALUE;
			while (cV != source) {
				int pV = prev[cV];
				bNeck = Math.min(bNeck, res[pV][cV]);
				cV = pV;
			}

			cV = sink;
			while (cV != source) {
				int pV = prev[cV];
				res[pV][cV] -= bNeck;
				res[cV][pV] += bNeck;
				cV = pV;
			}
			tFlow += bNeck;
		}
	}
}