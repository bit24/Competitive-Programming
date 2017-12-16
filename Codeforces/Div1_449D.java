import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_449D {

	static int n;
	static long mod;
	static long[] pF;
	static int nP;
	static long[] fact;
	static int[][] cnt;
	static long[] rmvp;
	static long[] inv;
	static long[][] pow;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		n = Integer.parseInt(inputData.nextToken());
		mod = Integer.parseInt(inputData.nextToken());
		int l = Integer.parseInt(inputData.nextToken());
		int r = Integer.parseInt(inputData.nextToken());

		pF = new long[9];
		nP = 0;
		long rem = mod;
		for (long cN = 2; cN * cN <= rem; cN++) {
			if (rem % cN == 0) {
				while (rem % cN == 0) {
					rem /= cN;
				}
				pF[nP++] = cN;
			}
		}

		if (rem > 1) {
			pF[nP++] = rem;
		}

		fact = new long[n + 1];
		fact[0] = 1;
		cnt = new int[n + 1][9];

		rmvp = new long[n + 1];
		for (int cN = 1; cN <= n; cN++) {
			rmvp[cN] = cN;

			for (int cPI = 0; cPI < nP; cPI++) {
				cnt[cN][cPI] = cnt[cN - 1][cPI];
				while (rmvp[cN] % pF[cPI] == 0) {
					rmvp[cN] /= pF[cPI];
					cnt[cN][cPI]++;
				}
			}
			fact[cN] = fact[cN - 1] * rmvp[cN] % mod;
		}

		inv = new long[n + 1];
		inv[n] = inv(fact[n], mod);

		for (int cN = n - 1; cN >= 0; cN--) {
			inv[cN] = rmvp[cN + 1] * inv[cN + 1] % mod;
		}

		pow = new long[nP][];

		for (int cPI = 0; cPI < nP; cPI++) {
			pow[cPI] = new long[cnt[n][cPI] + 1];
			pow[cPI][0] = 1;
			for (int exp = 1; exp <= cnt[n][cPI]; exp++) {
				pow[cPI][exp] = pow[cPI][exp - 1] * pF[cPI] % mod;
			}
		}

		long ans = 0;
		for (int nVIP = 0; nVIP <= n; nVIP++) {
			ans = (ans + choose(n, nVIP)
					* (choose(n - nVIP, (l + n - nVIP + 1) / 2) - choose(n - nVIP, (r + n - nVIP) / 2 + 1))) % mod;
		}
		printer.println(ans);
		printer.close();
	}

	static long choose(int a, int b) {
		if (b > a || a < 0) {
			return 0;
		}
		long prod = fact[a] * inv[b] % mod * inv[a - b] % mod;
		for (int cPI = 0; cPI < nP; cPI++) {
			prod = (prod * pow[cPI][cnt[a][cPI] - cnt[b][cPI] - cnt[a - b][cPI]]) % mod;
		}
		return prod;
	}

	static long inv(long number, long mod) {
		long dend = number, dendA = 1;

		long dsor = mod, dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor, rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		assert (dend == 1);
		return (dendA % mod + mod) % mod;
	}
}
