import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div1_393A {

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static boolean[] visited;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numE = Integer.parseInt(reader.readLine());

		StringTokenizer inp1 = new StringTokenizer(reader.readLine());
		StringTokenizer inp2 = new StringTokenizer(reader.readLine());

		for (int i = 0; i <= numE; i++) {
			aList.add(new ArrayList<Integer>());
		}

		int flipSum = 0;
		for (int i = 1; i <= numE; i++) {
			int next = Integer.parseInt(inp1.nextToken());
			aList.get(i).add(next);
			flipSum += Integer.parseInt(inp2.nextToken());
		}

		int count = 0;
		visited = new boolean[numE + 1];
		for (int i = 1; i <= numE; i++) {
			if (!visited[i]) {
				count++;
				dfs(i);
			}
		}
		if (count == 1) {
			count = 0;
		}
		System.out.println((flipSum & 1) == 1 ? count : count + 1);
	}

	static void dfs(int index) {
		visited[index] = true;
		for (int neigbor : aList.get(index)) {
			if (!visited[neigbor]) {
				dfs(neigbor);
			}
		}
	}

}
