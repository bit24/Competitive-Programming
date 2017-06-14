import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div2_411F {

	static ArrayList<Integer>[] aList;
	static boolean[] visited;

	static int[] comp;
	static int[] dLen;
	static int[] pre;

	static int[] mLen;

	static int[] compS;

	static int[] compD;

	static int[][] compBnd;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numE = Integer.parseInt(inputData.nextToken());
		int numQ = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[numV];
		for (int i = 0; i < numV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		visited = new boolean[numV];

		comp = new int[numV];
		compS = new int[numV];

		dLen = new int[numV];
		pre = new int[numV];
		Arrays.fill(pre, -1);

		compD = new int[numV];
		Arrays.fill(compD, -1);
		// keeps the two boundary points of the component's diameter
		compBnd = new int[numV][2];

		ArrayList<Integer> comps = new ArrayList<Integer>();

		for (int i = 0; i < numV; i++) {
			if (!visited[i]) {
				comp[i] = i;
				comps.add(i);
				dfs1(i);
			}
		}

		visited = new boolean[numV];
		mLen = new int[numV];

		int[][] lens = new int[2][numV];

		for (int i : comps) {
			for (int j = 0; j < 2; j++) {
				if (compBnd[i][j] == -1) {
					dfsLen(i, lens[j]);
					break;
				}
				while (pre[compBnd[i][j]] != -1) {
					compBnd[i][j] = pre[compBnd[i][j]];
				}

				dfsLen(compBnd[i][j], lens[j]);
			}
		}

		for (int i = 0; i < numV; i++) {
			mLen[i] = Math.max(lens[0][i], lens[1][i]);
		}

		int[][] cLenCt = new int[numV][];
		int[][] preCt = new int[numV][];
		long[][] preSum = new long[numV][];

		for (int i : comps) {
			cLenCt[i] = new int[compS[i] + 1];
			preCt[i] = new int[compS[i] + 1];
			preSum[i] = new long[compS[i] + 1];
		}

		for (int i = 0; i < numV; i++) {
			cLenCt[comp[i]][mLen[i]]++;
		}

		for (int i : comps) {
			preCt[i][0] = cLenCt[i][0];
			for (int j = 1; j <= compS[i]; j++) {
				preCt[i][j] = preCt[i][j - 1] + cLenCt[i][j];
				preSum[i][j] = preSum[i][j - 1] + (long) j * cLenCt[i][j];
			}
		}

		TreeMap<Integer, Double>[] processed = new TreeMap[numV];
		for (int i = 0; i < numV; i++) {
			processed[i] = new TreeMap<Integer, Double>();
		}

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = comp[Integer.parseInt(inputData.nextToken()) - 1];
			int b = comp[Integer.parseInt(inputData.nextToken()) - 1];
			if (a == b) {
				printer.println(-1);
				continue;
			}
			if (compS[a] > compS[b] || (compS[a] == compS[b] && a > b)) {
				int temp = a;
				a = b;
				b = temp;
			}

			if (processed[a].containsKey(b)) {
				printer.printf("%.10f", processed[a].get(b));
				printer.println();
				continue;
			}

			int interM = Math.max(compD[a], compD[b]);

			long posSum = 0;

			for (int aLen = 0; aLen < cLenCt[a].length; aLen++) {
				if (cLenCt[a][aLen] == 0) {
					continue;
				}

				int cut = Math.min(interM - 1 - aLen, compS[b]);
				if (cut >= 0) {
					posSum += (long) interM * cLenCt[a][aLen] * preCt[b][cut];
					posSum += (aLen + 1L) * cLenCt[a][aLen] * (preCt[b][compS[b]] - preCt[b][cut]);
					posSum += (long) cLenCt[a][aLen] * (preSum[b][compS[b]] - preSum[b][cut]);
				} else {
					posSum += (aLen + 1L) * cLenCt[a][aLen] * preCt[b][compS[b]];
					posSum += (long) cLenCt[a][aLen] * preSum[b][compS[b]];
				}
			}
			double ans = (double) posSum / ((long) compS[a] * compS[b]);
			processed[a].put(b, ans);
			printer.printf("%.10f", ans);
			printer.println();
		}
		printer.close();
	}

	// computes dLen, pre, comp, compS, compD, and compB
	static void dfs1(int cV) {
		int cComp = comp[cV];

		visited[cV] = true;
		compS[cComp]++;

		int[] mChDLen = new int[] { -1, -1 };
		int[] mChldn = new int[] { -1, -1 };

		for (int adj : aList[cV]) {
			if (!visited[adj]) {
				comp[adj] = cComp;
				dfs1(adj);
				int aDLen = dLen[adj];

				if (aDLen >= mChDLen[0]) {
					mChDLen[1] = mChDLen[0];
					mChDLen[0] = aDLen;
					mChldn[1] = mChldn[0];
					mChldn[0] = adj;
				} else if (aDLen > mChDLen[1]) {
					mChDLen[1] = aDLen;
					mChldn[1] = adj;
				}
			}
		}
		dLen[cV] = mChDLen[0] + 1;
		pre[cV] = mChldn[0];

		int sum = mChDLen[0] + mChDLen[1] + 2;
		if (sum > compD[cComp]) {
			compD[cComp] = sum;
			// initialize compBnd to the children, they will be expanded later
			compBnd[cComp] = mChldn;
		}
	}

	static void dfsLen(int cV, int[] len) {
		visited[cV] = true;
		for (int adj : aList[cV]) {
			if (!visited[adj]) {
				len[adj] = len[cV] + 1;
				dfsLen(adj, len);
			}
		}
		visited[cV] = false;
	}

}
