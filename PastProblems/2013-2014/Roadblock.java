import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Roadblock {

	public static void main(String[] args) throws IOException {
		new Roadblock().execute();
	}

	int[][] cost;
	int[] parent;

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	int numVertices;
	int numEdges;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("rblock.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("rblock.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}

		cost = new int[numVertices][numVertices];
		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			aList.get(a).add(b);
			aList.get(b).add(a);
			cost[a][b] = c;
			cost[b][a] = c;
		}
		reader.close();

		parent = new int[numVertices];
		parent[0] = -1;
		int originalDistance = djikstra();

		ArrayList<Integer> originalPath = new ArrayList<Integer>();

		int current = numVertices - 1;
		while (current != -1) {
			originalPath.add(current);
			current = parent[current];
		}

		Collections.reverse(originalPath);

		int updatedMaxDistance = 0;

		for (int i = 0; i + 1 < originalPath.size(); i++) {
			int endPointA = originalPath.get(i);
			int endPointB = originalPath.get(i + 1);
			int originalCost = cost[endPointA][endPointB];
			assert (cost[endPointB][endPointA] == originalCost);

			cost[endPointA][endPointB] = 2 * originalCost;
			cost[endPointB][endPointA] = 2 * originalCost;

			updatedMaxDistance = Math.max(updatedMaxDistance, djikstra());

			cost[endPointA][endPointB] = originalCost;
			cost[endPointB][endPointA] = originalCost;
		}
		printer.println(updatedMaxDistance - originalDistance);
		printer.close();
	}

	int djikstra() {
		PriorityQueue<State> queue = new PriorityQueue<State>();
		int[] distance = new int[numVertices];
		Arrays.fill(distance, Integer.MAX_VALUE);
		queue.add(new State(0, 0));
		distance[0] = 0;

		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			if (currentState.cost > distance[currentState.vertex]) {
				continue;
			}

			for (int neighbor : aList.get(currentState.vertex)) {
				int newCost = currentState.cost + cost[currentState.vertex][neighbor];
				if (newCost < distance[neighbor]) {
					parent[neighbor] = currentState.vertex;
					distance[neighbor] = newCost;
					queue.add(new State(neighbor, newCost));
				}
			}
		}
		return distance[numVertices - 1];
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