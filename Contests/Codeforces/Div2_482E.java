import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_482E {

	static int nV;
	static int p;

	static int[] color;

	static long[] pow2;

	static long[][][][] dp;

	static long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		pow2 = new long[500];
		pow2[0] = 1;
		for (int i = 1; i < 500; i++) {
			pow2[i] = pow2[i - 1] * 2 % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		p = Integer.parseInt(inputData.nextToken());

		color = new int[nV];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			color[i] = Integer.parseInt(inputData.nextToken());
		}

		dp = new long[nV + 1][nV + 1][nV + 1][nV + 1];
		dp[nV][0][0][0] = 1;
		pushDP(nV, 0, 0, 0);

		for (int cV = nV - 1; cV > 0; cV--) {
			for (int i = 0; i <= nV; i++) {
				for (int j = 0; j <= nV; j++) {
					for (int k = 0; k <= nV; k++) {
						int l = nV - cV - i - j - k;
						if (l >= 0 && dp[cV][i][j][k] != 0) {
							pushDP(cV, i, j, k);
						}
					}
				}
			}
		}
		long ans = 0;
		for (int wE = 0; wE <= nV; wE++) {
			for (int wO = 0; wO <= nV; wO++) {
				for (int bE = 0; bE <= nV; bE++) {
					int bO = nV - wE - wO - bE;

					if (bO >= 0 && ((wO + bO) & 1) == p) {
						ans = (ans + dp[0][wE][wO][bE]) % MOD;
					}
				}
			}
		}
		printer.println(ans);
		printer.close();
	}

	static void pushDP(int cV, int wE, int wO, int bE) {
		int bO = nV - cV - wE - wO - bE;
		long cDP = dp[cV][wE][wO][bE];
		if (cDP == 0) {
			return;
		}

		// white
		if (color[cV - 1] == 0 || color[cV - 1] == -1) {
			if (bO != 0) {
				dp[cV - 1][wE + 1][wO][bE] = (dp[cV - 1][wE + 1][wO][bE] + pow2[wE + wO + bE + bO - 1] * cDP) % MOD;
				dp[cV - 1][wE][wO + 1][bE] = (dp[cV - 1][wE][wO + 1][bE] + pow2[wE + wO + bE + bO - 1] * cDP) % MOD;
			} else {
				dp[cV - 1][wE][wO + 1][bE] = (dp[cV - 1][wE][wO + 1][bE] + pow2[wE + wO + bE] * cDP) % MOD;
			}
		}
		// black
		if (color[cV - 1] == 1 || color[cV - 1] == -1) {
			if (wO != 0) {
				dp[cV - 1][wE][wO][bE + 1] = (dp[cV - 1][wE][wO][bE + 1] + pow2[wE + wO + bE + bO - 1] * cDP) % MOD;
				dp[cV - 1][wE][wO][bE] = (dp[cV - 1][wE][wO][bE] + pow2[wE + wO + bE + bO - 1] * cDP) % MOD;
			} else {
				dp[cV - 1][wE][wO][bE] = (dp[cV - 1][wE][wO][bE] + pow2[wE + bE + bO] * cDP) % MOD;
			}
		}
	}
}