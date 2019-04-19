import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class SweetGrass {

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
		}

		pre = new int[nV];
		Arrays.fill(pre, -1);
		pre[0] = -2;
		dfs(0);

		int cV = nV - 1;
		ArrayList<Integer> path = new ArrayList<>();

		while (cV != -2) {
			path.add(cV);
			cV = pre[cV];
		}
		Collections.reverse(path);

		pInd = new int[nV];
		Arrays.fill(pInd, -1);
		for (int i = 0; i < path.size(); i++) {
			pInd[path.get(i)] = i;
		}

		Arrays.fill(pre, -1);
		for (int i : path) {
			if (i != nV - 1) {
				pre[i] = -2;
			}
		}
		dfs(0);

		if (pre[nV - 1] != -1) {
			printer.println(0);
			printer.close();
			return;
		}

		v = new boolean[nV];

		ArrayList<Integer> ans = new ArrayList<>();
		for (int i = 0; i < path.size() - 2; i++) {
			maxR = Math.max(maxR, i);
			dfs2(path.get(i));

			if (maxR <= i + 1) {
				ans.add(path.get(i + 1));
			}
		}
		Collections.sort(ans);
		printer.println(ans.size());
		for (int i : ans) {
			printer.println(i + 1);
		}
		printer.close();
	}
	
	static int[] pInd;

	static boolean[] v;
	static int maxR;

	static void dfs2(int cV) {
		v[cV] = true;
		for (int aV : aList[cV]) {
			if (!v[aV]) {
				if (pInd[aV] != -1) {
					maxR = Math.max(maxR, pInd[aV]);
				} else {
					dfs2(aV);
				}
			}
		}
	}

	static int[] pre;

	static void dfs(int cV) {
		for (int aV : aList[cV]) {
			if (pre[aV] == -1) {
				pre[aV] = cV;
				dfs(aV);
			}
		}
	}
}
