import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_407D {

	static int[][] aList;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numE = Integer.parseInt(inputData.nextToken());
		if (numE < 2) {
			System.out.println(0);
			return;
		}

		// stupid constant pruning
		int[] deg = new int[numV];
		int[] endA = new int[numE];
		int[] endB = new int[numE];
		for (int i = 0; i < numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			endA[i] = a;
			endB[i] = b;
			if (a != b) {
				deg[a]++;
				deg[b]++;
			}
		}

		aList = new int[numV][];
		for (int i = 0; i < numV; i++) {
			aList[i] = new int[deg[i]];
		}

		int regCnt = 0;
		int selfCnt = 0;

		int notFloating = -1;
		int[] nxt = new int[numV];
		
		boolean[] selfLoop = new boolean[numV];
		for (int i = 0; i < numE; i++) {
			int a = endA[i];
			int b = endB[i];
			if (b != a) {
				regCnt++;
				aList[a][nxt[a]++] = b;
				aList[b][nxt[b]++] = a;
				notFloating = a;
			} else {
				selfCnt++;
				selfLoop[a] = true;
			}
		}

		if (notFloating == -1) {
			System.out.println(0);
			return;
		}

		visited = new boolean[numV];
		dfs(notFloating);

		for (int i = 0; i < numV; i++) {
			if (!visited[i] && (deg[i] > 0 || selfLoop[i])) {
				System.out.println(0);
				return;
			}
		}

		long ans = 0;

		// 2 regular edges
		for (int i = 0; i < numV; i++) {
			int numA = deg[i];
			ans += (long) numA * (numA - 1) >> 1;
		}

		// regular edge and a self-loop
		ans += (long) regCnt * selfCnt;

		// 2 self-loops
		ans += (long) selfCnt * (selfCnt - 1) >> 1;
		System.out.println(ans);
	}

	static void dfs(int cV) {
		visited[cV] = true;
		for (int adj : aList[cV]) {
			if (!visited[adj]) {
				dfs(adj);
			}
		}
	}
}
