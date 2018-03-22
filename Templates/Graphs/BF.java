import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class BF {

	ArrayList<Edge> edges = new ArrayList<Edge>();
	int[] distance;
	int numVertices;

	public void bellmanFord(int source) {
		distance = new int[numVertices];
		Arrays.fill(distance, Integer.MAX_VALUE / 4);

		distance[source] = 0;
		Collections.sort(edges);

		for (int i = 0; i < numVertices - 1; i++) {
			for (Edge e : edges) {
				if (distance[e.endPointA] != Integer.MAX_VALUE / 4) {
					distance[e.endPointB] = Math.min(distance[e.endPointB], distance[e.endPointA] + e.length);
				}
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

		public Edge(int endPointA, int endPointB, int length) {
			this.endPointA = endPointA;
			this.endPointB = endPointB;
			this.length = length;
		}
	}

}
