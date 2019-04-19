import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_404D {

	static long MOD = 1_000_000_007;
	static long[] fact;

	public static void main(String[] args) throws IOException {
		fact = new long[400_001];
		fact[0] = 1;
		for (int i = 1; i <= 400_000; i++) {
			fact[i] = fact[i - 1] * i % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		char[] items = (" " + reader.readLine()).toCharArray();
		int numI = items.length - 1;
		int[] numA = new int[numI + 2];

		for (int i = numI; i >= 1; i--) {
			numA[i] = items[i] == ')' ? numA[i + 1] + 1 : numA[i + 1];
		}

		int numB = 0;
		long ans = 0;
		for (int i = 1; i <= numI; i++) {
			if (items[i] == ')' && numB != 0) {
				ans = (ans + compute(numB, numA[i + 1])) % MOD;
			}
			if (items[i] == '(') {
				numB++;
			}
		}
		System.out.println(ans);
	}

	static long compute(int a, int b) {
		if (a + b - 1 < 0) {
			return 0;
		}
		return (combination(a + b, b + 1));
	}

	static long combination(int a, int b) {
		if (b > a) {
			return 0;
		}
		return div(fact[a], fact[b] * fact[a - b] % MOD);
	}

	static long div(long num, long den) {
		// "a" = den
		// "b" = mod

		long dend = den;
		long dendA = 1;

		long dsor = MOD;
		long dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor;
			long rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		long gcd = dend;
		long pRes = dendA;
		return (num / gcd * pRes % MOD + MOD) % MOD;
	}

}
