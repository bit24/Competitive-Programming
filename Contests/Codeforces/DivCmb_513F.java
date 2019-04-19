import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_513F {

	static int nV;
	static ArrayList<Integer>[] aList;

	static double[] ans;

	static double[][] comb = new double[51][51];

	static double den;

	public static void main(String[] args) throws IOException {
		comb[0][0] = 1;

		for (int i = 1; i <= 50; i++) {
			comb[i][0] = 1;
			for (int j = 1; j <= 50; j++) {
				comb[i][j] = comb[i - 1][j] + comb[i - 1][j - 1];
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());

		den = 1;
		for (int i = 2; i < nV; i++) {
			den *= i;
		}

		for (int i = 0; i < nV - 1; i++) {
			den *= 2;
		}

		aList = new ArrayList[nV];

		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		cnt = new double[nV][];
		cntPre = new double[nV][];
		tS = new int[nV];

		ans = new double[nV];
		for (int i = 0; i < nV; i++) {
			calcAll(i);
		}
		for (int i = 0; i < nV; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static void calcAll(int root) {
		Arrays.fill(cnt, null);
		Arrays.fill(cntPre, null);
		Arrays.fill(tS, 0);

		dfs(root, -1);
		ans[root] = cnt[root][nV - 1];
		ans[root] /= den;
	}

	static void dfs(int cV, int pV) {
		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}

			dfs(aV, cV);
			tS[cV] += tS[aV];
		}
		tS[cV]++;

		calc(cV, pV);
	}

	static double[][] cnt; // [vertex][#Grt]
	static double[][] cntPre;

	static int[] tS;

	static void calc(int cV, int pV) {
		double[] cCnt = new double[tS[cV]];
		int cTE = 0;

		cCnt[0] = 1;

		for (int sV : aList[cV]) {
			if (sV == pV) {
				continue;
			}

			cTE += tS[sV];

			for (int cG = tS[cV] - 1; cG >= 0; cG--) {
				int cL = cTE - cG;
				if (cL < 0) {
					continue;
				}

				double cSum = 0;

				int end1 = Math.min(tS[sV] - 1, cG - 1);

				// case: connecting is picked from G
				// times 1 because it matters that cV dominates sV
				for (int sG = 0; sG <= end1; sG++) {
					cSum += comb(cL, tS[sV] - 1 - sG) * comb(cG, sG + 1) * cntPre[sV][sG] * cCnt[cG - sG - 1];
				}

				// case: connecting is picked from L
				// times 2 because it doesn't matter which dominates
				int end2 = Math.min(tS[sV] - 1, cG);
				for (int sG = 0; sG <= end2; sG++) {
					cSum += cL * comb(cL - 1, tS[sV] - 1 - sG) * comb(cG, sG) * cnt[sV][sG] * cCnt[cG - sG] * 2;
				}
				cCnt[cG] = cSum;
			}
		}

		cnt[cV] = cCnt;
		cntPre[cV] = new double[tS[cV]];

		cntPre[cV][0] = cnt[cV][0];
		for (int i = 1; i < tS[cV]; i++) {
			cntPre[cV][i] = cntPre[cV][i - 1] + cnt[cV][i];
		}
	}

	static double comb(int n, int k) {
		if (0 <= n && 0 <= k && k <= n) {
			return comb[n][k];
		}
		return 0;
	}

}
