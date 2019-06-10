import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class SkiCourseRating {

	boolean merge(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			assert (false);
		}

		parent[aParent] = bParent;
		size[bParent] += size[aParent];
		numStarting[bParent] += numStarting[aParent];
		return true;
	}

	boolean inSameSet(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			return true;
		} else {
			return false;
		}
	}

	int findParent(int x) {
		if (parent[x] == x) {
			return x;
		} else {
			return parent[x] = findParent(parent[x]);
		}
	}

	int getNumStarting(int x) {
		return numStarting[findParent(x)];
	}

	void removeStarting(int x) {
		numStarting[findParent(x)] = 0;
	}

	int getSize(int x) {
		return size[findParent(x)];
	}

	int numRows;
	int numColumns;
	int sizeRequirement;

	int numVertices;
	int numEdges;

	int[] elevation;
	int[] parent;
	int[] size;
	int[] numStarting;

	ArrayList<Edge> edges = new ArrayList<Edge>();

	void init() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("skilevel.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numRows = Integer.parseInt(inputData.nextToken());
		numColumns = Integer.parseInt(inputData.nextToken());
		sizeRequirement = Integer.parseInt(inputData.nextToken());

		numVertices = numRows * numColumns;
		elevation = new int[numVertices];

		for (int i = 0; i < numRows; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numColumns; j++) {
				int currentIndex = i * numColumns + j;
				elevation[currentIndex] = Integer.parseInt(inputData.nextToken());
			}
		}

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				int currentIndex = i * numColumns + j;

				if (i - 1 >= 0) {
					int neighborIndex = currentIndex - numColumns;
					edges.add(new Edge(currentIndex, neighborIndex,
							Math.abs(elevation[currentIndex] - elevation[neighborIndex])));
				}

				if (j - 1 >= 0) {
					int neighborIndex = currentIndex - 1;
					edges.add(new Edge(currentIndex, neighborIndex,
							Math.abs(elevation[currentIndex] - elevation[neighborIndex])));
				}
			}
		}
		numEdges = edges.size();

		numStarting = new int[numVertices];
		for (int i = 0; i < numRows; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numColumns; j++) {
				if (inputData.nextToken().equals("1")) {
					numStarting[i * numColumns + j] = 1;
				}
			}
		}
		reader.close();
		Collections.sort(edges);

		parent = new int[numVertices];
		size = new int[numVertices];
		for (int i = 0; i < numVertices; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}

	void execute() throws IOException {
		init();

		long answer = 0;
		for (int i = 0; i < numEdges;) {
			int currentLength = edges.get(i).length;

			while (i < numEdges && edges.get(i).length == currentLength) {
				Edge currentEdge = edges.get(i++);
				if (!inSameSet(currentEdge.endPointA, currentEdge.endPointB)) {
					merge(currentEdge.endPointA, currentEdge.endPointB);

					if (getNumStarting(currentEdge.endPointA) != 0
							&& getSize(currentEdge.endPointA) >= sizeRequirement) {

						answer += ((long) getNumStarting(currentEdge.endPointA)) * currentLength;
						removeStarting(currentEdge.endPointA);
					}
				}
			}
		}
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("skilevel.out")));
		printer.println(answer);
		printer.close();
	}

	public static void main(String[] args) throws IOException {
		new SkiCourseRating().execute();
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