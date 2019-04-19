import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_49D {

	static int[] nxt;

	static long ans = 0;

	static int[] costs;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());
		nxt = new int[nV];

		costs = new int[nV];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			costs[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			nxt[i] = Integer.parseInt(inputData.nextToken()) - 1;
		}

		visited = new int[nV];
		Arrays.fill(visited, -1);

		int cM = 0;
		for (int i = 0; i < nV; i++) {
			if (visited[i] == -1) {
				search(i, cM++);
				stack.clear();
			}
		}
		printer.println(ans);
		printer.close();
	}

	static int[] visited;
	static ArrayList<Integer> stack = new ArrayList<>();

	static void search(int cV, int cM) {
		visited[cV] = cM;
		stack.add(cV);

		if (visited[nxt[cV]] == cM) {
			int minC = costs[nxt[cV]];
			for (int i = stack.size() - 1; stack.get(i) != nxt[cV]; i--) {
				minC = Math.min(minC, costs[stack.get(i)]);
			}
			ans += minC;
		} else if (visited[nxt[cV]] == -1) {
			search(nxt[cV], cM);
		}
	}
}