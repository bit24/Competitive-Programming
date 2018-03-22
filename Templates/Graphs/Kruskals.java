import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Kruskals {

	static int numVertices;
	static int numEdges;

	static Edge[] edges;

	static Kruskals helper = new Kruskals();

	static int[] parent;
	static long totalDistance;

	static void merge(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			assert (false);
		}

		parent[aParent] = bParent;
	}

	static boolean inSameSet(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			return true;
		} else {
			return false;
		}
	}

	static int findParent(int x) {
		if (parent[x] == x) {
			return x;
		} else {
			return parent[x] = findParent(parent[x]);
		}
	}

	static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());

		edges = new Edge[numEdges];
		parent = new int[numVertices];

		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int n = Integer.parseInt(inputData.nextToken());
			Edge e1 = helper.new Edge(a, b, n);
			edges[i] = e1;
		}
		Arrays.sort(edges);

		for (int i = 0; i < numVertices; i++) {
			parent[i] = i;
		}

		for (Edge edge : edges) {
			if (!inSameSet(edge.endPointA, edge.endPointB)) {
				merge(edge.endPointA, edge.endPointB);
				totalDistance += edge.length;
			}
		}

	}

	class Edge implements Comparable<Edge> {
		int endPointA;
		int endPointB;

		int length;

		public int compareTo(Edge o) {
			return Integer.compare(length, o.length);
		}

		Edge(int endPointA, int endPointB, int length) {
			this.endPointA = endPointA;
			this.endPointB = endPointB;
			this.length = length;
		}
	}

}
