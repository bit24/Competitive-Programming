import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_49E {

	static final long MOD = 998244353;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());

		long[] ans = new long[N + 1];

		long[] dp = new long[N + 1];

		for (int cMaxR = 1; cMaxR <= N; cMaxR++) {
			Arrays.fill(dp, 0);
			dp[0] = 2;
			long cSum = 2;

			for (int i = 1; i <= N; i++) {
				dp[i] = cSum;
				cSum = (cSum + dp[i]) % MOD;
				if (i - cMaxR >= 0) {
					cSum = (cSum - dp[i - cMaxR] + MOD) % MOD;
				}
			}
			ans[cMaxR] = dp[N];
		}

		long fAns = 0;
		for (int side1 = 1; side1 <= Math.min(K, N); side1++) {
			long cntWays = (ans[side1] - ans[side1 - 1] + MOD) % MOD;
			long mWays;
			if ((K + side1 - 1) / side1 > N) {
				mWays = ans[N];
			} else {
				mWays = ans[(K + side1 - 1) / side1 - 1];
			}
			fAns = (fAns + cntWays * mWays % MOD) % MOD;
		}
		fAns = (fAns * 499122177) % MOD;
		printer.println(fAns);
		printer.close();
	}
}
