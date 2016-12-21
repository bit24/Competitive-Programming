import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class RoadsAndPlanesSolver {

	public static void main(String[] args) throws IOException {
		new RoadsAndPlanesSolver().execute();
	}

	ArrayList<ArrayList<Edge>> regEdge = new ArrayList<ArrayList<Edge>>();
	ArrayList<ArrayList<Edge>> jumpEdge = new ArrayList<ArrayList<Edge>>();

	ArrayList<ArrayList<Integer>> members = new ArrayList<ArrayList<Integer>>();

	int numV;
	int numReg;
	int numJump;
	int start;

	boolean[] visited;

	int[] compID;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numV = Integer.parseInt(inputData.nextToken());
		numReg = Integer.parseInt(inputData.nextToken());
		numJump = Integer.parseInt(inputData.nextToken());
		start = Integer.parseInt(inputData.nextToken()) - 1;

		ArrayList<ArrayList<Integer>> sources = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < numV; i++) {
			regEdge.add(new ArrayList<Edge>());
			jumpEdge.add(new ArrayList<Edge>());
			members.add(new ArrayList<Integer>());
			sources.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numReg; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			regEdge.get(a).add(new Edge(b, c));
			regEdge.get(b).add(new Edge(a, c));
		}

		for (int i = 0; i < numJump; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			jumpEdge.get(a).add(new Edge(b, c));
		}
		reader.close();

		visited = new boolean[numV];
		compID = new int[numV];
		for (int i = 0; i < numV; i++) {
			if (!visited[i]) {
				compID[i] = i;
				compress(i);
			}
		}

		visited = new boolean[numV];
		for (int i = 0; i < numV; i++) {
			if (!visited[compID[i]]) {
				topSort(compID[i]);
			}
		}
		ArrayList<Integer> ordering = new ArrayList<Integer>();

		while (!stack.isEmpty()) {
			ordering.add(stack.removeLast());
		}

		int[] cost = new int[numV];
		Arrays.fill(cost, Integer.MAX_VALUE / 2);
		cost[start] = 0;

		sources.get(compID[start]).add(start);

		for (int cComponent : ordering) {
			assert (cComponent == compID[cComponent]);
			PriorityQueue<State> sQueue = new PriorityQueue<State>();
			for (int source : sources.get(cComponent)) {
				sQueue.add(new State(source, cost[source]));
			}

			while (!sQueue.isEmpty()) {
				State cState = sQueue.remove();
				if (cost[cState.vertex] < cState.cost) {
					continue;
				}

				for (Edge outEdge : regEdge.get(cState.vertex)) {
					int newCost = cState.cost + outEdge.cost;
					if (newCost < cost[outEdge.vertex]) {
						cost[outEdge.vertex] = newCost;
						sQueue.add(new State(outEdge.vertex, newCost));
					}
				}

				for (Edge outEdge : jumpEdge.get(cState.vertex)) {
					int newCost = cState.cost + outEdge.cost;
					if (newCost < cost[outEdge.vertex]) {
						cost[outEdge.vertex] = newCost;
						sources.get(compID[outEdge.vertex]).add(outEdge.vertex);
					}
				}
			}
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		for (int i = 0; i < numV; i++) {
			if (cost[i] != Integer.MAX_VALUE / 2) {
				printer.println(cost[i]);
			} else {
				printer.println("NO PATH");
			}
		}
		printer.close();
	}

	class State implements Comparable<State> {
		int vertex;
		int cost;

		State(int a, int b) {
			vertex = a;
			cost = b;
		}

		public int compareTo(State o) {
			return Integer.compare(cost, o.cost);
		}
	}

	ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

	void topSort(int index) {
		assert (index == compID[index]);
		visited[index] = true;

		for (int member : members.get(index)) {
			for (Edge previous : jumpEdge.get(member)) {
				int previousV = compID[previous.vertex];
				if (!visited[previousV]) {
					topSort(previousV);
				}
			}
		}
		stack.addLast((index));
	}

	void compress(int vertex) {
		visited[vertex] = true;
		members.get(compID[vertex]).add(vertex);
		for (Edge adjacent : regEdge.get(vertex)) {
			if (!visited[adjacent.vertex]) {
				compID[adjacent.vertex] = compID[vertex];
				compress(adjacent.vertex);
			}
		}
	}

	class Edge {
		int vertex;
		int cost;

		Edge(int a, int b) {
			vertex = a;
			cost = b;
		}
	}
}
