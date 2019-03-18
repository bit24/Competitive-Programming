import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class MainI {

	public static void main(String[] args) throws IOException {
		new MainI().main();
	}

	int nV;
	ArrayList<Integer>[] aList;

	@SuppressWarnings("unchecked")
	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int M = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		int[] v = new int[nV];
		inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < nV; i++) {
			v[i] = Integer.parseInt(inputData.nextToken());
		}

		for (int i = 0; i < M; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int d = Integer.parseInt(inputData.nextToken());
			if (d <= v[a]) {
				aList[a].add(b);
			}
			if (d <= v[b]) {
				aList[b].add(a);
			}
		}

		vis = new boolean[nV];

		int ans = 0;
		for (int i = 0; i < nV; i++) {
			cnt = 0;
			Arrays.fill(vis, false);
			dfs(i);
			ans = Math.max(ans, cnt);
		}
		printer.println(ans);
		printer.close();
	}

	int cnt = 0;
	boolean[] vis;
	
	ArrayList<Integer> undo = new ArrayList<Integer>();

	void dfs(int cV) {
		cnt++;
		vis[cV] = true;
		for (int aV : aList[cV]) {
			if (!vis[aV]) {
				dfs(aV);
			}
		}
	}

}