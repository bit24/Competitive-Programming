
/*ID: eric.ca1
LANG: JAVA
TASK: ditch
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ditch {

	static int numEdges;
	static int numVertices;

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	static int[][] capacity;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("ditch.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("ditch.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numEdges = Integer.parseInt(inputData.nextToken());
		numVertices = Integer.parseInt(inputData.nextToken());

		capacity = new int[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			if(capacity[a][b] == 0){
				aList.get(a).add(b);
				aList.get(b).add(a);
			}
			int flowCap = Integer.parseInt(inputData.nextToken());
			capacity[a][b] += flowCap;
			//capacity[b][a] = flowCap;
			
		}
		MaxFlow flow = new ditch().new MaxFlow(aList, capacity, 0, numVertices-1);
		
		printer.println(flow.pushToFront());
		
		reader.close();
		printer.close();
	}
	
	class MaxFlow {

		public int numVertices;
		ArrayList<ArrayList<Integer>> aList;
		int[][] residual;

		int[] height;
		int[] excess;

		int source;
		int sink;

		public MaxFlow(ArrayList<ArrayList<Integer>> aList, int[][] capacity, int source, int sink) {
			this.aList = aList;
			this.residual = capacity;
			this.source = source;
			this.sink = sink;
			numVertices = aList.size();
		}

		public void push(int v1, int v2) {
			int deltaFlow = Math.min(excess[v1], residual[v1][v2]);
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
				if(residual[vertex][neighbor] == 0){
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
			excess = new int[numVertices];

			height[source] = numVertices;

			for (int adjacentToSource : aList.get(source)) {
				excess[adjacentToSource] = residual[source][adjacentToSource];
				excess[source] -= residual[source][adjacentToSource];
				residual[adjacentToSource][source] = residual[source][adjacentToSource];
				residual[source][adjacentToSource] = 0;
			}
		}

		public int pushToFront() {
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
