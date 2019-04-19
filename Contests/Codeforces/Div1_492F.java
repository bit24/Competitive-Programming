import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div1_492F {

	static final long MOD = 1_000_000_007;

	static long[] fact = new long[6001];
	static long[] iFact = new long[6001];

	static int nV;
	static ArrayList<Integer>[] aList;

	static long Q;

	static int[] size;

	static long[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		Q = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 1; i < nV; i++) {
			aList[Integer.parseInt(reader.readLine()) - 1].add(i);
		}

		dp = new long[nV][nV + 1];

		dfs(0, -1);

		// interpolate a degree nV polynomial using nV pts

		long[] x = new long[nV + 1];
		long[] y = new long[nV + 1];

		for (int i = 0; i <= nV; i++) {
			x[i] = i;
			y[i] = dp[0][i];
		}

		if (Q <= nV) {
			printer.println(y[(int) Q]);
			printer.close();
		}

		long ans = 0;

		for (int i = 0; i <= nV; i++) {
			long num = y[i];
			long den = 1;
			for (int j = 0; j <= nV; j++) {
				if (i != j) {
					num = num * (MOD + Q - x[j]) % MOD;
					den = den * (MOD + x[i] - x[j]) % MOD;
				}
			}
			ans = (ans + num * inv(den)) % MOD;
		}
		printer.println(ans);
		printer.close();
	}

	static long inv(long number) {
		long dend = number, dendA = 1;

		long dsor = MOD, dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor, rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		return (dendA % MOD + MOD) % MOD;
	}

	static void dfs(int cV, int pV) {
		for (int aV : aList[cV]) {
			if (aV != pV) {
				dfs(aV, cV);
			}
		}

		for (int cB = 1; cB <= nV; cB++) {
			long prod = 1;

			for (int aV : aList[cV]) {
				if (aV != pV) {
					prod = prod * dp[aV][cB] % MOD;
				}
			}

			dp[cV][cB] = (dp[cV][cB - 1] + prod) % MOD;
		}
	}

}
