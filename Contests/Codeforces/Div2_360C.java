import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_360C {

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numVertices = Integer.parseInt(inputData.nextToken());
		int numEdges = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a).add(b);
			aList.get(b).add(a);
		}

		boolean[] visited = new boolean[numVertices];
		boolean[] marking = new boolean[numVertices];
		boolean isPossible = true;

		for (int i = 0; i < numVertices; i++) {
			if (!visited[i]) {
				isPossible = isPossible && dfs(i, marking, visited);
			}
		}
		if (!isPossible) {
			System.out.println(-1);
		} else {
			int numMarked = 0;
			for (boolean i : marking) {
				if (i) {
					numMarked++;
				}
			}

			System.out.println(numMarked);
			for (int i = 0; i < numVertices; i++) {
				if (marking[i]) {
					System.out.print(i + 1 + " ");
				}
			}
			System.out.println();

			System.out.println(numVertices - numMarked);
			for (int i = 0; i < numVertices; i++) {
				if (!marking[i]) {
					System.out.print(i + 1 + " ");
				}
			}
			System.out.println();

		}

	}

	public static boolean dfs(int index, boolean[] marking, boolean[] visited) {
		visited[index] = true;
		boolean isMarked = marking[index];

		for (int neighbor : aList.get(index)) {
			if (visited[neighbor]) {
				if (isMarked == marking[neighbor]) {
					return false;
				}
			} else {
				marking[neighbor] = !isMarked;
				if (!dfs(neighbor, marking, visited)) {
					return false;
				}
			}
		}
		return true;
	}

}
