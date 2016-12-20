import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BovineAllianceSolver {

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
		reader.close();

		long count = 1;

		boolean[] visited = new boolean[numVertices];
		for (int i = 0; i < numVertices; i++) {
			if (!visited[i]) {
				edgeCount = 0;
				int size = countSize(i, visited);
				edgeCount /= 2;
				if (size == 1) {
					continue;
				}
				if (edgeCount == size - 1) {
					count *= size;
				}
				if (edgeCount == size) {
					count *= 2;
				}

				count %= 1_000_000_007;
			}
		}
		System.out.println(count);
	}

	static int edgeCount = 0;

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	static int countSize(int v, boolean[] visited) {
		edgeCount += aList.get(v).size();
		visited[v] = true;
		int count = 1;
		for (int n : aList.get(v)) {
			if (!visited[n]) {
				count += countSize(n, visited);
			}
		}
		return count;
	}

}
