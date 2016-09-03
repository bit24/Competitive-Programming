import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: telecow
*/

public class telecow {

	static telecow yee = new telecow();

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static int[][] capacity;

	static int source;
	static int sink;

	static int LRVertex;

	static final int INF = Integer.MAX_VALUE / 2;

	static int currentMaxFlow;

	static ArrayList<Integer> cut = new ArrayList<Integer>();

	public static void main(String[] args) throws IOException {
		input();
		ArrayList<Integer> splitVertices = findMCEdges();
		Collections.sort(splitVertices);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("telecow.out")));
		printer.println(splitVertices.size());

		printer.print(splitVertices.get(0));
		for (int i = 1; i < splitVertices.size(); i++) {
			printer.print(" " + splitVertices.get(i));
		}

		printer.println();
		printer.close();
	}

	public static ArrayList<Integer> findMCEdges() {
		currentMaxFlow = yee.new FFImp(aList, capacity, source, sink).getMaxFlow();
		ArrayList<Integer> edges = new ArrayList<Integer>();
		for (int i = 1; i <= LRVertex; i++) {
			if (i != source && i != sink) {
				if (testVC(i)) {
					edges.add(i);
				}
			}
		}
		return edges;
	}

	public static boolean testVC(int i) {
		capacity[i][LRVertex + i] = 0;
		FFImp newTest = yee.new FFImp(aList, capacity, source, sink);
		if (newTest.getMaxFlow() == currentMaxFlow - 1) {
			currentMaxFlow = currentMaxFlow - 1;
			return true;
		} else {
			capacity[i][LRVertex + i] = 1;
			return false;
		}
	}

	public static void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("telecow.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		LRVertex = Integer.parseInt(inputData.nextToken());
		int numInputEdges = Integer.parseInt(inputData.nextToken());
		source = Integer.parseInt(inputData.nextToken());
		sink = Integer.parseInt(inputData.nextToken());

		capacity = new int[LRVertex * 2 + 1][LRVertex * 2 + 1];

		for (int i = 0; i <= LRVertex * 2; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int i = 1; i <= LRVertex; i++) {
			if (i != source && i != sink) {
				aList.get(i).add(LRVertex + i);
				capacity[i][LRVertex + i] = 1;
			}
		}

		for (int i = 0; i < numInputEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());

			if (a == source || a == sink) {
				aList.get(a).add(b);
				capacity[a][b] = INF;
			} else {
				aList.get(LRVertex + a).add(b);
				capacity[LRVertex + a][b] = INF;
			}
			if (b == source || b == sink) {
				aList.get(b).add(a);
				capacity[b][a] = INF;
			} else {
				aList.get(LRVertex + b).add(a);
				capacity[LRVertex + b][a] = INF;
			}
		}
		reader.close();
	}

	class FFImp {

		private int numVertices;
		private ArrayList<ArrayList<Integer>> aList;
		private int[][] residual;

		private int source;
		private int sink;

		private int currentFlow;

		public FFImp(ArrayList<ArrayList<Integer>> inpAList, int[][] capacity, int source, int sink) {
			numVertices = inpAList.size();
			boolean[][] isConnected = new boolean[numVertices][numVertices];
			for (int i = 0; i < numVertices; i++) {
				for (int j : inpAList.get(i)) {
					isConnected[i][j] = true;
					isConnected[j][i] = true;
				}
			}

			aList = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < numVertices; i++) {
				aList.add(new ArrayList<Integer>());
				for (int j = 0; j < numVertices; j++) {
					if (isConnected[i][j]) {
						aList.get(i).add(j);
					}
				}
			}

			residual = new int[numVertices][numVertices];
			for (int i = 0; i < numVertices; i++) {
				for (int j = 0; j < numVertices; j++) {
					residual[i][j] = capacity[i][j];
				}
			}

			this.source = source;
			this.sink = sink;
		}

		public int[][] getResidual() {
			computeMFResidual();
			return residual;
		}

		public int getMaxFlow() {
			computeMFResidual();
			return currentFlow;
		}

		private ArrayList<Integer> FPTS(int current, boolean[] visited) {
			visited[current] = true;
			if (current == sink) {
				ArrayList<Integer> path = new ArrayList<Integer>();
				path.add(sink);
				return path;
			}

			for (int neighbor : aList.get(current)) {
				if (!visited[neighbor] && residual[current][neighbor] > 0) {
					ArrayList<Integer> path = FPTS(neighbor, visited);
					if (path != null) {
						path.add(current);
						return path;
					}
				}
			}
			return null;
		}

		public void computeMFResidual() {
			ArrayList<Integer> nextPath = FPTS(source, new boolean[numVertices]);

			while (nextPath != null) {
				Collections.reverse(nextPath);

				int bottleNeck = Integer.MAX_VALUE;
				for (int i = 0; i + 1 < nextPath.size(); i++) {
					if (residual[nextPath.get(i)][nextPath.get(i + 1)] < bottleNeck) {
						bottleNeck = residual[nextPath.get(i)][nextPath.get(i + 1)];
					}
				}

				for (int i = 0; i + 1 < nextPath.size(); i++) {
					residual[nextPath.get(i)][nextPath.get(i + 1)] -= bottleNeck;
					residual[nextPath.get(i + 1)][nextPath.get(i)] += bottleNeck;
				}
				currentFlow += bottleNeck;
				nextPath = FPTS(source, new boolean[numVertices]);
			}
		}
	}
}
