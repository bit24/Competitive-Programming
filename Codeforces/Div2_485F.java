import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_485F implements Runnable {

	public static void main(String[] args) {
		new Thread(null, new Div2_485F(), "meep", 1 << 28).start();
	}

	int n;
	int m;
	int nV;
	int mask;

	boolean[] impt;

	boolean[] visited;

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			n = Integer.parseInt(inputData.nextToken());
			m = Integer.parseInt(inputData.nextToken());
			nV = 1 << n;
			mask = nV - 1;

			impt = new boolean[nV];

			inputData = new StringTokenizer(reader.readLine());
			for (int i = 0; i < m; i++) {
				int cV = Integer.parseInt(inputData.nextToken());
				impt[cV] = true;
			}

			visited = new boolean[nV];
			int cnt = 0;
			for (int i = 0; i < nV; i++) {
				if (impt[i] && !visited[i]) {
					cnt++;
					explore(i);
				}
			}
			printer.println(cnt);
			printer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void explore(int cV) {
		visited[cV] = true;
		int aV = mask ^ cV;
		if (!visited[aV]) {
			dfs(aV);
		}
	}

	void dfs(int cV) {
		visited[cV] = true;
		int aV = mask ^ cV;
		if (impt[cV] && !visited[aV]) {
			dfs(aV);
		}

		for (int i = 0; i < n; i++) {
			if ((cV & (1 << i)) != 0) {
				aV = cV ^ (1 << i);
				if (!visited[aV]) {
					dfs(aV);
				}
			}
		}
	}
}