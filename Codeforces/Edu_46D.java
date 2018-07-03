import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Edu_46D {

	static final long MOD = 998244353;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nE = Integer.parseInt(reader.readLine());
		int[] e = new int[nE + 1];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		long[] dp = new long[nE + 1];
		dp[nE] = 1;

		long[][] choose = new long[1001][1001];

		choose[0][0] = 1;

		for (int i = 1; i <= 1000; i++) {
			choose[i][0] = 1;
			for (int j = 1; j <= 1000; j++) {
				choose[i][j] = (choose[i - 1][j] + choose[i - 1][j - 1]) % MOD;
			}
		}

		for (int i = nE - 1; i >= 0; i--) {
			int skip = e[i];
			if (skip <= 0) {
				continue;
			}

			long cCnt = 0;
			for (int j = i + 1; j <= nE; j++) {
				if (skip <= j - i - 1) {
					cCnt = (cCnt + choose[j - i - 1][skip] * dp[j]) % MOD;
				}
			}
			dp[i] = cCnt;
		}

		long ans = 0;
		for (int i = 0; i < nE; i++) {
			ans = (ans + dp[i]) % MOD;
		}
		printer.println(ans);
		printer.close();
	}
}
