import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_419D {

	static final int ADD = 0;
	static final int SUB = 1;

	static final long MOD = 1_000_000_007L;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numI = Integer.parseInt(reader.readLine());
		long[] fact = new long[numI];
		fact[0] = 1;

		for (int i = 1; i < numI; i++) {
			fact[i] = fact[i - 1] * i % MOD;
		}

		long[] items = new long[numI];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numI; i++) {
			items[i] = Long.parseLong(inputData.nextToken()) % MOD;
		}

		int nOp = ADD;
		while (numI % 4 != 1) {
			numI--;
			for (int i = 0; i < numI; i++) {
				if (nOp == ADD) {
					items[i] = (items[i] + items[i + 1]) % MOD;
					nOp = SUB;
				} else {
					items[i] = (items[i] - items[i + 1] + MOD) % MOD;
					nOp = ADD;
				}
			}
		}

		int top = (numI - 1) / 2;

		long ans = 0;
		for (int i = 0; i * 2 < numI; i++) {
			ans = (ans + items[i * 2] * divide(fact[top], fact[i] * fact[top - i] % MOD)) % MOD;
		}
		System.out.println(ans);
	}

	static long divide(long a, long b) {
		long pow = MOD - 2;

		// find b^pow
		long cur = 1;
		long sqr = b;

		while (pow > 0) {
			if ((pow & 1) == 1) {
				cur = cur * sqr % MOD;
			}
			sqr = sqr * sqr % MOD;
			pow >>= 1;
		}

		return a * cur % MOD;
	}

}
