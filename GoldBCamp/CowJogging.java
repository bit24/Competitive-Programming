import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CowJogging {

	static int numVertices;
	static int numEdges;
	static int numRoutes;

	static ArrayList<ArrayList<Edge>> adjacencyList = new ArrayList<ArrayList<Edge>>();
	static boolean[] visited;

	static CowJogging solver = new CowJogging();

	static LinkedList<Integer> topologicalOrdering = new LinkedList<Integer>();

	static PriorityQueue<Integer>[] shortestPaths;
	static int[] count;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());
		numRoutes = Integer.parseInt(inputData.nextToken());
		for (int i = 0; i < numVertices; i++) {
			adjacencyList.add(new ArrayList<Edge>());
		}
		visited = new boolean[numVertices];

		shortestPaths = new PriorityQueue[numVertices];
		for (int i = 0; i < numVertices; i++) {
			shortestPaths[i] = new PriorityQueue<Integer>(numRoutes, MAXFIRST);
		}
		count = new int[numVertices];

		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int start = Integer.parseInt(inputData.nextToken()) - 1;
			int end = Integer.parseInt(inputData.nextToken()) - 1;
			int distance = Integer.parseInt(inputData.nextToken());
			adjacencyList.get(start).add(solver.new Edge(end, distance));
		}
		reader.close();

		DFS(numVertices - 1);
		shortestPaths[numVertices-1].add(0);

		for (int currentVertex : topologicalOrdering) {
			if(currentVertex == 0){
				continue;
			}
			int[] currentDistance = new int[shortestPaths[currentVertex].size()];
			for (int i = shortestPaths[currentVertex].size() - 1; i >= 0; i--) {
				currentDistance[i] = shortestPaths[currentVertex].remove();
			}

			for (Edge childEdge : adjacencyList.get(currentVertex)) {
				int child = childEdge.endVertex;
				int edgeLength = childEdge.distance;

				for (int distanceSoFar : currentDistance) {
					int newDistance = distanceSoFar + edgeLength;
					shortestPaths[child].add(newDistance);
					count[child]++;
					if (count[child] > numRoutes) {
						count[child]--;
						if (newDistance == shortestPaths[child].remove()) {
							break;
						}
					}
				}
			}
		}

		LinkedList<Integer> best = new LinkedList<Integer>();
		while (!shortestPaths[0].isEmpty()) {
			best.addFirst(shortestPaths[0].remove());
		}

		for (int i = 0; i < numRoutes; i++) {
			if(!best.isEmpty()){
				System.out.println(best.removeFirst());
			}
			else{
				System.out.println(-1);
			}
		}
	}

	public static void DFS(int current) {
		for (Edge childEdge : adjacencyList.get(current)) {
			if (!visited[childEdge.endVertex]) {
				DFS(childEdge.endVertex);
			}
		}
		visited[current] = true;
		topologicalOrdering.addFirst(current);
	}

	class Edge {
		int endVertex;
		int distance;

		Edge() {
		};

		Edge(int endVertex, int distance) {
			this.endVertex = endVertex;
			this.distance = distance;
		}
	}

	static Comparator<Integer> MAXFIRST = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return -Integer.compare(o1, o2);
		}
	};

}
