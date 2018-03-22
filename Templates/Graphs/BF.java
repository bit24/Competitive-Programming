import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// runtime: O(nV * nE) but can handle negative edge weights

class BF {

	ArrayList<Edge> edges = new ArrayList<Edge>();
	int[] dist;
	int nV;

	public void bellmanFord(int source) {
		dist = new int[nV];
		Arrays.fill(dist, Integer.MAX_VALUE / 4);

		dist[source] = 0;
		Collections.sort(edges);

		for (int i = 0; i < nV - 1; i++) {
			for (Edge e : edges) {
				if (dist[e.v1] != Integer.MAX_VALUE / 4) {
					dist[e.v2] = Math.min(dist[e.v2], dist[e.v1] + e.len);
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
