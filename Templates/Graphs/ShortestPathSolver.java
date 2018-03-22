import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class ShortestPathSolver {

	static int[] cost;
	static int[][] distance;

	static ShortestPathSolver solver = new ShortestPathSolver();

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	static int numVertices;

	public void djikstraWithPriorityQueue(int source) {
		PriorityQueue<State> queue = new PriorityQueue<State>();
		Arrays.fill(cost, Integer.MAX_VALUE);
		queue.add(solver.new State(source, 0));

		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			if (currentState.cost >= cost[currentState.vertex]) {
				continue;
			}

			for (int neighbor : aList.get(currentState.vertex)) {
				int newCost = currentState.cost + distance[currentState.vertex][neighbor];
				if (newCost < cost[neighbor]) {
					cost[neighbor] = newCost;
					queue.add(solver.new State(neighbor, newCost));
				}
			}
		}
	}

	public void djikstraWithTwoLoops(int source) {
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[source] = 0;
		boolean[] visited = new boolean[numVertices];

		while (true) {
			int vertexWithMinCost = -1;
			for (int i = 0; i < numVertices; i++) {
				if (cost[i] != Integer.MAX_VALUE && (!visited[i])
						&& (vertexWithMinCost == -1 || cost[i] < cost[vertexWithMinCost])) {
					vertexWithMinCost = i;
				}
			}
			if (vertexWithMinCost == -1) {
				break;
			}
			visited[vertexWithMinCost] = true;

			for (int neighbor : aList.get(vertexWithMinCost)) {
				int newCost = cost[vertexWithMinCost] + distance[vertexWithMinCost][neighbor];
				if (newCost < cost[neighbor]) {
					cost[neighbor] = newCost;
				}
			}
		}
	}

	class State implements Comparable<State> {
		int cost;
		int vertex;

		State(int a, int b) {
			this.vertex = a;
			this.cost = b;
		}

		public int compareTo(State o) {
			int value = Integer.compare(cost, o.cost);
			if (value == 0) {
				return Integer.compare(vertex, o.vertex);
			}
			return value;
		}

		public boolean equals(Object other) {
			State oTuple = (State) other;
			return cost == oTuple.cost && vertex == oTuple.vertex;
		}
	}

}
