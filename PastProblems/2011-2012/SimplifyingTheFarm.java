import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class SimplifyingTheFarm {

	static int numVertices;
	static int numEdges;

	static Edge[] edges;

	static SimplifyingTheFarm helper = new SimplifyingTheFarm();

	static int[] parent;
	static long distance;

	static long numWays = 1;

	public static boolean merge(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			assert (false);
		}

		parent[aParent] = bParent;

		return true;
	}

	public static boolean connectSameComponents(Edge a, Edge b) {
		if (inSameSet(a.endPointA, b.endPointA) && inSameSet(a.endPointB, b.endPointB)) {
			return true;
		} else if (inSameSet(a.endPointA, b.endPointB) && inSameSet(a.endPointB, b.endPointA)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean inSameSet(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			return true;
		} else {
			return false;
		}
	}

	public static int findParent(int x) {
		if (parent[x] == x) {
			return x;
		} else {
			return parent[x] = findParent(parent[x]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("simplify.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("simplify.out")));
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
		reader.close();
		Arrays.sort(edges);

		for (int i = 0; i < numVertices; i++) {
			parent[i] = i;
		}

		int edgeNum = 0;
		while (edgeNum < edges.length) {
			Edge[] SLE = new Edge[3];
			int i = 0;

			while (edgeNum < edges.length && (i == 0 || edges[edgeNum].length == SLE[0].length)) {
				if (inSameSet(edges[edgeNum].endPointA, edges[edgeNum].endPointB)) {
					edgeNum++;
					continue;
				}
				SLE[i++] = edges[edgeNum++];
			}

			if (i == 0) {
				continue;
			}
			int length = SLE[0].length;

			if (i == 1) {
				merge(SLE[0].endPointA, SLE[0].endPointB);
				distance += length;
			} else if (i == 2) {
				if (connectSameComponents(SLE[0], SLE[1])) {
					merge(SLE[0].endPointA, SLE[0].endPointB);
					numWays *= 2;
					distance += length;
				} else {
					merge(SLE[0].endPointA, SLE[0].endPointB);
					merge(SLE[1].endPointA, SLE[1].endPointB);
					distance += 2 * length;
				}
			} else {
				HashSet<Integer> components = new HashSet<Integer>();
				for (int CLE = 0; CLE < 3; CLE++) {
					int componentNum = findParent(SLE[CLE].endPointA);
					if (!components.contains(componentNum)) {
						components.add(componentNum);
					}
					componentNum = findParent(SLE[CLE].endPointB);
					if (!components.contains(componentNum)) {
						components.add(componentNum);
					}
				}

				int numComponents = components.size();

				if (numComponents == 2) {
					// two components, three connections
					merge(SLE[0].endPointA, SLE[0].endPointB);
					numWays *= 3;
					distance += length;
				} else if (numComponents == 3 && (!connectSameComponents(SLE[0], SLE[1]))
						&& (!connectSameComponents(SLE[1], SLE[2])) && (!connectSameComponents(SLE[0], SLE[2]))) {
					// triangle shape
					merge(SLE[0].endPointA, SLE[0].endPointB);
					merge(SLE[1].endPointA, SLE[1].endPointB);
					numWays *= 3;
					distance += 2 * length;
				} else if (connectSameComponents(SLE[0], SLE[1])) {
					merge(SLE[0].endPointA, SLE[0].endPointB);
					merge(SLE[2].endPointA, SLE[2].endPointB);
					numWays *= 2;
					distance += 2 * length;
				} else if (connectSameComponents(SLE[1], SLE[2])) {
					merge(SLE[0].endPointA, SLE[0].endPointB);
					merge(SLE[1].endPointA, SLE[1].endPointB);
					numWays *= 2;
					distance += 2 * length;
				} else if (connectSameComponents(SLE[0], SLE[2])) {
					merge(SLE[0].endPointA, SLE[0].endPointB);
					merge(SLE[1].endPointA, SLE[1].endPointB);
					numWays *= 2;
					distance += 2 * length;
				} else {
					merge(SLE[0].endPointA, SLE[0].endPointB);
					merge(SLE[1].endPointA, SLE[1].endPointB);
					merge(SLE[2].endPointA, SLE[2].endPointB);
					distance += 3 * length;
				}

			}
			assert (numWays > 0);
			numWays = numWays % 1_000_000_007;
		}

		printer.println(distance + " " + numWays);
		printer.close();
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