import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_446E {

	static long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nI = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());

		long[] items = new long[nI];
		inputData = new StringTokenizer(reader.readLine());
		long prod = 1;
		for (int i = 0; i < nI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken()) % MOD;
			prod = prod * items[i] % MOD;
		}

		long[] z = new long[nI + 1];
		z[nI] = 1;

		long intNI = inv(nI);

		for (int i = nI - 1; i >= 0; i--) {
			z[i] = z[i + 1] * -intNI % MOD * (k + i - nI + 1) % MOD;
			if (z[i] < 0) {
				z[i] += MOD;
			}
		}

		long[] dp = new long[nI + 1];
		dp[0] = 1;
		for (int i = 0; i < nI; i++) {
			for (int j = nI; j >= 1; j--) {
				dp[j] = (dp[j] + dp[j - 1] * items[i]) % MOD;
			}
		}

		long sum = 0;
		for (int i = 0; i <= nI; i++) {
			sum = (sum + z[i] * dp[i]) % MOD;
		}
		printer.println((prod - sum + MOD) % MOD);
		printer.close();
	}

	static long inv(long num) {
		return pow(num, MOD - 2);
	}

	static long pow(long b, long e) {
		long c = 1;
		while (e > 0) {
			if ((e & 1) == 1) {
				c = c * b % MOD;
			}
			b = b * b % MOD;
			e >>= 1;
		}
		return c;
	}

}
