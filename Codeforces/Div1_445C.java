import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_445C {

	static final long MOD = 1_000_000_007;

	static long[] fact;
	static long[] iFact;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());

		if (k >= n - 1) {
			printer.println(0);
			printer.close();
			return;
		}

		fact = new long[n + 1];
		fact[0] = 1;
		for (int i = 1; i <= n; i++) {
			fact[i] = i * fact[i - 1] % MOD;
		}

		iFact = new long[n + 1];

		iFact[n] = pow(fact[n], MOD - 2);
		for (int i = n - 1; i >= 1; i--) {
			iFact[i] = (i + 1) * iFact[i + 1] % MOD;
		}

		long[] f = new long[n + 1];
		long[] preG = new long[n + 1];

		for (int i = k + 1; i <= n; i++) {
			f[i] = ((i - k - 1) * fact[i - 2]
					+ fact[i - 2] * (preG[i - 1] - (i - k - 1 >= 0 ? preG[i - k - 1] : 0) + MOD)) % MOD;
			preG[i] = (preG[i - 1] + f[i] * iFact[i - 1]) % MOD;
		}

		long ans = 0;
		// i is position where we put the greatest element
		for (int i = 1; i <= n; i++) {
			ans = (ans + f[i] * fact[n - 1] % MOD * iFact[i - 1]) % MOD;
		}
		printer.println(ans);
		printer.close();
	}

	static long pow(long base, long exp) {
		base %= MOD;
		long ans = 1;
		while (exp > 0) {
			if ((exp & 1) == 1) {
				ans = ans * base % MOD;
			}
			base = base * base % MOD;
			exp >>= 1;
		}
		return ans;
	}
}
