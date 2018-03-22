import java.util.ArrayList;
import java.util.LinkedList;

class PRTF {

	static int nV;
	static ArrayList<Integer>[] aList;
	static int[][] residual;

	static int[] height;
	static int[] excess;

	static int source;
	static int sink;
	
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

	static void push(int v1, int v2) {
		int deltaFlow = Math.min(excess[v1], residual[v1][v2]);
		if (deltaFlow <= 0) {
			return;
		}
		excess[v1] -= deltaFlow;
		excess[v2] += deltaFlow;
		residual[v1][v2] -= deltaFlow;
		residual[v2][v1] += deltaFlow;
	}

	static void relabel(int cV) {
		int min = Integer.MAX_VALUE;
		for (int aV : aList[cV]) {
			if (residual[cV][aV] == 0) {
				continue;
			}
			if (min > height[aV]) {
				min = height[aV];
			}
		}
		height[cV] = min + 1;
	}

	static void discharge(int vertex) {
		if (excess[vertex] == 0) {
			return;
		}
		while (true) {

			for (int neighbor : aList[vertex]) {

				if (height[vertex] > height[neighbor]) {

					push(vertex, neighbor);

					// removed excess
					if (excess[vertex] == 0) {
						return;
					}
				}
			}
			relabel(vertex);
		}
	}

	static void handleSource() {
		height = new int[nV];
		excess = new int[nV];

		height[source] = nV;

		for (int aSource : aList[source]) {
			excess[aSource] = residual[source][aSource];
			excess[source] -= residual[source][aSource];
			residual[aSource][source] = residual[source][aSource];
			residual[source][aSource] = 0;
		}
	}

	static int[][] getResidual() {
		computeMFRes();
		return residual;
	}

	static int getMaxFlow() {
		computeMFRes();
		return -excess[source];
	}

	static void computeMFRes() {
		handleSource();
		LinkedList<Integer> list = new LinkedList<>();
		for (int i = 0; i < nV; i++) {
			if (i == source || i == sink) {
				continue;
			}
			list.add(i);
		}

		while (true) {

			int toUpdate = -1;
			for (int vertex : list) {
				int pHeight = height[vertex];
				discharge(vertex);
				if (height[vertex] > pHeight) {
					toUpdate = vertex;
					break;
				}
			}
			if (toUpdate != -1) {
				list.remove(new Integer(toUpdate));
				list.addFirst(toUpdate);
			} else {
				break;
			}
		}
	}
}
