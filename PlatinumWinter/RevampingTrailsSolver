import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class RevampingTrailsSolver {

	public static void main(String[] args) throws IOException {
		new RevampingTrailsSolver().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numVertices = Integer.parseInt(inputData.nextToken());
		int numEdges = Integer.parseInt(inputData.nextToken());
		int revampMax = Integer.parseInt(inputData.nextToken());

		ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();

		for (int i = 0; i < numVertices; i++) {
			edges.add(new ArrayList<Edge>());
		}

		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			edges.get(a).add(new Edge(b, c));
			edges.get(b).add(new Edge(a, c));
		}

		PriorityQueue<State> queue = new PriorityQueue<State>();
		queue.add(new State(0, 0, 0));

		int[][] cost = new int[numVertices][revampMax + 1];
		for (int i = 0; i < numVertices; i++) {
			Arrays.fill(cost[i], Integer.MAX_VALUE / 2);
		}

		while (!queue.isEmpty()) {
			State cS = queue.remove();
			if (cS.cost > cost[cS.index][cS.numR]) {
				continue;
			}
			if (cS.index == numVertices - 1) {
				System.out.println(cS.cost);
				return;
			}
			for (Edge outE : edges.get(cS.index)) {
				int nCost = cS.cost + outE.cost;
				if (nCost < cost[outE.end][cS.numR]) {
					cost[outE.end][cS.numR] = nCost;
					queue.add(new State(outE.end, nCost, cS.numR));
				}

				if (cS.numR < revampMax) {
					if (cS.cost < cost[outE.end][cS.numR + 1]) {
						cost[outE.end][cS.numR + 1] = cS.cost;
						queue.add(new State(outE.end, cS.cost, cS.numR + 1));
					}
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i <= revampMax; i++) {
			if (cost[numVertices - 1][i] < ans) {
				ans = cost[numVertices - 1][i];
			}
		}
		System.out.println(ans);
	}

	class Edge {
		int end;
		int cost;

		Edge(int a, int b) {
			end = a;
			cost = b;
		}
	}

	class State implements Comparable<State> {
		int index;
		int cost;
		int numR;

		State(int a, int b, int c) {
			index = a;
			cost = b;
			numR = c;
		}

		public int compareTo(State o) {
			if (cost != o.cost) {
				return Integer.compare(cost, o.cost);
			}
			if (numR != o.numR) {
				return Integer.compare(numR, o.numR);
			}
			return Integer.compare(index, o.index);
		}
	}

}
