import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// runtime: O(nV * nE) but can handle negative edge weights

class BF {

	ArrayList<Edge> edges = new ArrayList<Edge>();
	int[] distance;
	int nV;

	public void bellmanFord(int source) {
		distance = new int[nV];
		Arrays.fill(distance, Integer.MAX_VALUE / 4);

		distance[source] = 0;
		Collections.sort(edges);

		for (int i = 0; i < nV - 1; i++) {
			for (Edge e : edges) {
				if (distance[e.v1] != Integer.MAX_VALUE / 4) {
					distance[e.v2] = Math.min(distance[e.v2], distance[e.v1] + e.len);
				}
			}
		}
		// if edges continue to be relaxed after this, there is a negative cycle
	}

	class Edge implements Comparable<Edge> {
		int v1;
		int v2;
		int len;

		public int compareTo(Edge o) {
			return Integer.compare(len, o.len);
		}

		public Edge(int v1, int v2, int len) {
			this.v1 = v1;
			this.v2 = v2;
			this.len = len;
		}
	}
}
