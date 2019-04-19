import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CowIQTest {

	static int N;
	static int MOD;

	static int nPF;
	static int[] pF = new int[10];

	static int[][] fPFC;

	static long[] rem;
	static long[] fR;
	static long[] iFR;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken()) - 1;
		int nE = N + 1;
		MOD = Integer.parseInt(inputData.nextToken());
		int test = MOD;

		for (int i = 2; i * i <= test; i++) {
			if (test % i == 0) {
				pF[nPF++] = i;
				do {
					test /= i;
				} while (test % i == 0);
			}
		}
		if (test != 1) {
			pF[nPF++] = test;
		}

		fPFC = new int[N + 1][nPF];

		rem = new long[N + 1];
		fR = new long[N + 1];
		fR[0] = 1;

		for (int i = 1; i <= N; i++) {
			int cR = i;

			for (int j = 0; j < nPF; j++) {
				fPFC[i][j] = fPFC[i - 1][j];

				while (cR % pF[j] == 0) {
					fPFC[i][j]++;
					cR /= pF[j];
				}
			}
			rem[i] = cR;
			fR[i] = fR[i - 1] * cR % MOD;
		}

		iFR = new long[N + 1];
		iFR[N] = inv(fR[N]);

		for (int i = N - 1; i >= 0; i--) {
			iFR[i] = (rem[i + 1]) * iFR[i + 1] % MOD;
		}

		long ans = 0;

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i <= N; i++) {
			ans = (ans + compute(i) * Integer.parseInt(inputData.nextToken())) % MOD;
		}
		printer.println(ans);
		printer.close();
	}

	static long compute(int k) {
		long res = fR[N] * iFR[k] % MOD * iFR[N - k] % MOD;

		for (int i = 0; i < nPF; i++) {
			res = res * exp(pF[i], fPFC[N][i] - fPFC[k][i] - fPFC[N - k][i]) % MOD;
		}
		return res;
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

	static long exp(long b, long p) {
		long res = 1;
		long cur = b;

		while (p > 0) {
			if ((p & 1) == 1) {
				res = res * cur % MOD;
			}
			cur = cur * cur % MOD;
			p >>= 1;
		}
		return res;
	}
}