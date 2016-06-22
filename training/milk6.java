import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: milk6
*/

public class milk6 {

	static int numVertices;
	static ArrayList<ArrayList<Integer>> aList;
	static long[][] capacity;
	static ArrayList<Integer>[][] routeNumbers;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("milk6.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("milk6.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		int numInputs = Integer.parseInt(inputData.nextToken());

		capacity = new long[numVertices][numVertices];

		aList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}

		routeNumbers = new ArrayList[numVertices][numVertices];

		for (int i = 0; i < numInputs; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int v1 = Integer.parseInt(inputData.nextToken()) - 1;
			int v2 = Integer.parseInt(inputData.nextToken()) - 1;
			if (routeNumbers[v1][v2] == null) {
				routeNumbers[v1][v2] = new ArrayList<Integer>();
			}
			routeNumbers[v1][v2].add(i);
			int currentCap = Integer.parseInt(inputData.nextToken()) * (numVertices*numVertices + 1) + numVertices;
			currentCap += i;
			
			if (capacity[v1][v2] == 0) {
				aList.get(v1).add(v2);
			}
			capacity[v1][v2] += currentCap;
		}
		reader.close();

		MaxFlow flowNetwork = new milk6().new MaxFlow(aList, capacity, 0, numVertices - 1);
		long preResult = flowNetwork.pushToFront();

		boolean[] visited = new boolean[numVertices];
		dfs(0, visited);

		ArrayList<Tuple> edgeList = new ArrayList<Tuple>();

		for (int i = 0; i < numVertices; i++) {
			if (!visited[i]) {
				continue;
			}
			for (int j : aList.get(i)) {
				if (!visited[j]) {
					edgeList.add(new milk6().new Tuple(i, j));
				}
			}
		}

		ArrayList<Integer> routes = new ArrayList<Integer>();
		
		int sum = 0;
		for (Tuple currentEdge : edgeList) {
			if(routeNumbers[currentEdge.a][currentEdge.b] == null){
				continue;
			}
			for (int i : routeNumbers[currentEdge.a][currentEdge.b]) {
				routes.add(i+1);
				sum += i;
			}
		}

		Collections.sort(routes);

		long totalRoutes = routes.size();
		
		preResult -= sum;
		
		long totalCost = (preResult - totalRoutes*numVertices) / (numVertices*numVertices + 1);

		printer.println(totalCost + " " + totalRoutes);

		for (int i : routes) {
			printer.println(i);
		}

		printer.close();

	}

	class Tuple{
		int a;
		int b;

		Tuple() {
		}

		Tuple(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}

	public static void dfs(int index, boolean[] visited) {
		visited[index] = true;
		for (int neighborIndex : aList.get(index)) {
			if (capacity[index][neighborIndex] == 0) {
				continue;
			}
			if(visited[neighborIndex]){
				continue;
			}
			dfs(neighborIndex, visited);
		}
	}

	class MaxFlow {

		public int numVertices;
		ArrayList<ArrayList<Integer>> aList;
		long[][] residual;

		int[] height;
		long[] excess;

		int source;
		int sink;

		public MaxFlow(ArrayList<ArrayList<Integer>> aList, long[][] capacity, int source, int sink) {
			numVertices = aList.size();
			this.aList = aList;
			boolean[][] isConnected = new boolean[numVertices][numVertices];
			for (int i = 0; i < numVertices; i++) {
				for (int j : aList.get(i)) {
					isConnected[i][j] = true;
				}
			}

			for (int i = 0; i < numVertices; i++) {
				for (int j : aList.get(i)) {
					if (!isConnected[j][i]) {
						aList.get(j).add(i);
					}
				}
			}

			this.residual = capacity;
			this.source = source;
			this.sink = sink;
		}

		public void push(int v1, int v2) {
			long deltaFlow = Math.min(excess[v1], residual[v1][v2]);
			if (deltaFlow <= 0) {
				return;
			}
			excess[v1] -= deltaFlow;
			excess[v2] += deltaFlow;
			residual[v1][v2] -= deltaFlow;
			residual[v2][v1] += deltaFlow;
		}

		public void relabel(int vertex) {
			int min = Integer.MAX_VALUE;
			for (int neighbor : aList.get(vertex)) {
				if (residual[vertex][neighbor] == 0) {
					continue;
				}
				if (min > height[neighbor]) {
					min = height[neighbor];
				}
			}
			height[vertex] = min + 1;
		}

		public void discharge(int vertex) {
			if (excess[vertex] == 0) {
				return;
			}
			while (true) {

				for (int neighbor : aList.get(vertex)) {

					if (height[vertex] > height[neighbor]) {

						push(vertex, neighbor);

						// removed excess
						if (excess[vertex] == 0) {
							return;
						}
					}
				}
				relabel(vertex);
			}
		}

		public void init() {
			height = new int[numVertices];
			excess = new long[numVertices];

			height[source] = numVertices;

			for (int adjacentToSource : aList.get(source)) {
				if(adjacentToSource == source){
					continue;
				}
				excess[adjacentToSource] = residual[source][adjacentToSource];
				excess[source] -= residual[source][adjacentToSource];
				residual[adjacentToSource][source] = residual[source][adjacentToSource];
				residual[source][adjacentToSource] = 0;
			}
		}

		public long pushToFront() {
			init();
			LinkedList<Integer> list = new LinkedList<Integer>();
			for (int i = 0; i < numVertices; i++) {
				if (i == source || i == sink) {
					continue;
				}
				list.add(i);
			}

			while (true) {

				int toUpdate = -1;
				for (int vertex : list) {
					int previousHeight = height[vertex];
					discharge(vertex);
					if (height[vertex] > previousHeight) {
						toUpdate = vertex;
						break;
					}
				}
				if (toUpdate != -1) {
					list.remove(new Integer(toUpdate));
					list.addFirst(toUpdate);
				} else {
					break;
				}
			}
			return -excess[source];
		}

	}

}
