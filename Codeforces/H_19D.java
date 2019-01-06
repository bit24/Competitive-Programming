import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class H_19D {

	static final long MOD = 1_000_000_007;

	static int nPF;
	static long[] pF = new long[20];
	static int[] exp = new int[20];

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long N = Long.parseLong(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());

		long test = N;
		for (long i = 2; i * i <= test; i++) {
			if (test % i == 0) {
				pF[nPF] = i;
				exp[nPF] = 1;
				test /= i;

				while (test % i == 0) {
					exp[nPF]++;
					test /= i;
				}
				nPF++;
			}
		}
		if (test != 1) {
			pF[nPF] = test;
			exp[nPF] = 1;
			nPF++;
		}

		long[] inv = new long[61];
		for (int i = 1; i <= 60; i++) {
			inv[i] = inv(i);
		}

		long ans = 1;

		for (int i = 0; i < nPF; i++) {
			long[] cDP = new long[exp[i] + 1];
			long[] nDP = new long[exp[i] + 1];

			cDP[exp[i]] = 1;

			for (int j = 1; j <= K; j++) {
				for (int l = 0; l <= exp[i]; l++) {
					for (int m = 0; m <= l; m++) {
						nDP[m] = (nDP[m] + cDP[l] * inv[l + 1]) % MOD;
					}
				}
				long[] temp = cDP;
				cDP = nDP;
				nDP = temp;
				Arrays.fill(nDP, 0);
			}

			long exV = 0;
			long cV = 1;
			for (int j = 0; j <= exp[i]; j++) {
				exV = (exV + cV * cDP[j]) % MOD;
				cV = cV * pF[i] % MOD;
			}
			ans = ans * exV % MOD;
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
		assert (dend == 1);
		return (dendA % MOD + MOD) % MOD;
	}
}
