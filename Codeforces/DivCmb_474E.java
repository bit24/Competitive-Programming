import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_474E {

	static final long MOD = 1_000_000_007;

	static long[] vals;

	static int[] ePt;
	static int[] nE;
	static int[] lSt;

	static int[] mark;
	static int[][] cnt;

	static int[] tCnt;

	static long sum = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		vals = new long[nV];
		for (int i = 0; i < nV; i++) {
			vals[i] = Integer.parseInt(inputData.nextToken());
		}

		ePt = new int[2 * nV];
		nE = new int[2 * nV];
		lSt = new int[nV];
		Arrays.fill(lSt, -1);

		int eI = 0;
		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			ePt[eI] = b;
			nE[eI] = lSt[a];
			lSt[a] = eI++;

			ePt[eI] = a;
			nE[eI] = lSt[b];
			lSt[b] = eI++;
		}

		mark = new int[nV];
		cnt = new int[2][nV];

		dfs(0, -1);

		tCnt = new int[] { cnt[0][0], cnt[1][0] };

		calculate(0, -1);
		sum = (sum + MOD) % MOD;
		printer.println(sum);
		printer.close();
	}

	static void dfs(int cV, int pV) {
		cnt[mark[cV]][cV]++;
		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV) {
				mark[aV] = mark[cV] ^ 1;
				dfs(aV, cV);
				cnt[0][cV] += cnt[0][aV];
				cnt[1][cV] += cnt[1][aV];
			}
			aI = nE[aI];
		}
	}

	static void calculate(int cV, int pV) {
		int cMark = mark[cV];

		int aI = lSt[cV];

		long[] cCnt = new long[2];
		cCnt[cMark]++;
		sum = (sum + vals[cV]) % MOD;

		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV) {
				sum = (sum + 2 * cCnt[cMark] * cnt[cMark][aV] % MOD * vals[cV]) % MOD;
				sum = (sum - 2 * cCnt[cMark ^ 1] * cnt[cMark ^ 1][aV] % MOD * vals[cV]) % MOD;
				cCnt[0] += cnt[0][aV];
				cCnt[1] += cnt[1][aV];
				calculate(aV, cV);
			}
			aI = nE[aI];
		}
		assert (cCnt[0] == cnt[0][cV] && cCnt[1] == cnt[1][cV]);
		sum = (sum + 2 * cCnt[cMark] * (tCnt[cMark] - cCnt[cMark]) % MOD * vals[cV]) % MOD;
		sum = (sum - 2 * cCnt[cMark ^ 1] * (tCnt[cMark ^ 1] - cCnt[cMark ^ 1]) % MOD * vals[cV]) % MOD;
	}
}
