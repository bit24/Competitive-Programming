import java.util.ArrayList;
import java.util.LinkedList;

class PRTF {

	private int numVertices;
	private ArrayList<ArrayList<Integer>> aList;
	private int[][] residual;

	private int[] height;
	private int[] excess;

	private int source;
	private int sink;

	public PRTF(ArrayList<ArrayList<Integer>> inpAList, int[][] capacity, int source, int sink) {
		numVertices = inpAList.size();
		boolean[][] isConnected = new boolean[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			for (int j : inpAList.get(i)) {
				isConnected[i][j] = true;
				isConnected[j][i] = true;
			}
		}

		aList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
			for (int j = 0; j < numVertices; j++) {
				if (isConnected[i][j]) {
					aList.get(i).add(j);
				}
			}
		}

		residual = new int[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				residual[i][j] = capacity[i][j];
			}
		}

		this.source = source;
		this.sink = sink;
	}

	private void push(int v1, int v2) {
		int deltaFlow = Math.min(excess[v1], residual[v1][v2]);
		if (deltaFlow <= 0) {
			return;
		}
		excess[v1] -= deltaFlow;
		excess[v2] += deltaFlow;
		residual[v1][v2] -= deltaFlow;
		residual[v2][v1] += deltaFlow;
	}

	private void relabel(int vertex) {
		int min = Integer.MAX_VALUE;
		for (int neighbor : aList.get(vertex)) {
			if (residual[vertex][neighbor] == 0) {
				continue;
			}
			if (min > height[neighbor]) {
				min = height[neighbor];
			}
		}
		height[vertex] = min + 1;
	}

	private void discharge(int vertex) {
		if (excess[vertex] == 0) {
			return;
		}
		while (true) {

			for (int neighbor : aList.get(vertex)) {

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

	private void handleSource() {
		height = new int[numVertices];
		excess = new int[numVertices];

		height[source] = numVertices;

		for (int adjacentToSource : aList.get(source)) {
			excess[adjacentToSource] = residual[source][adjacentToSource];
			excess[source] -= residual[source][adjacentToSource];
			residual[adjacentToSource][source] = residual[source][adjacentToSource];
			residual[source][adjacentToSource] = 0;
		}
	}

	public int[][] getResidual() {
		computeMFResidual();
		return residual;
	}

	public int getMaxFlow() {
		computeMFResidual();
		return -excess[source];
	}

	public void computeMFResidual() {
		handleSource();
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < numVertices; i++) {
			if (i == source || i == sink) {
				continue;
			}
			list.add(i);
		}

		while (true) {

			int toUpdate = -1;
			for (int vertex : list) {
				int previousHeight = height[vertex];
				discharge(vertex);
				if (height[vertex] > previousHeight) {
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