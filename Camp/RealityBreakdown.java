import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RealityBreakdown {

	static int N;
	static int K;

	static int nN = 1;
	static int[][] nxt;
	static int[] pInd;

	static int[] vals;
	static ArrayList<Integer>[] aList;

	static int[] ans;

	static void add(String cStr, int cPInd) {
		int cInd = 0;
		for (int i = 0; i < cStr.length(); i++) {
			int cC = cStr.charAt(i) - 'a';
			if (nxt[cC][cInd] == -1) {
				nxt[cC][cInd] = nN++;
			}
			cInd = nxt[cC][cInd];
		}
		pInd[cInd] = cPInd;
	}

	static void dfs(int cV, int pV, int cInd) {
		cInd = nxt[vals[cV]][cInd];
		if (cInd == -1) {
			return;
		}

		if (pInd[cInd] != -1) {
			ans[pInd[cInd]]++;
		}

		for (int aV : aList[cV]) {
			if (aV != pV) {
				dfs(aV, cV, cInd);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());

		vals = new int[N];
		aList = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			aList[i] = new ArrayList<>();
		}
		for (int i = 0; i < N; i++) {
			vals[i] = reader.readLine().charAt(0) - 'a';
		}

		for (int i = 0; i < N - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		ans = new int[K];

		nxt = new int[26][20_100];
		pInd = new int[20_100];

		for (int[] a : nxt) {
			Arrays.fill(a, -1);
		}
		Arrays.fill(pInd, -1);

		for (int i = 0; i < K; i++) {
			add(reader.readLine(), i);
		}

		for (int i = 0; i < N; i++) {
			dfs(i, -1, 0);
		}

		for (int i = 0; i < K; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}
}
