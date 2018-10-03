import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Div1_462D {

	static final long MOD = 1_000_000_007;

	static final long INV6 = 166_666_668;
	static final long INV30 = 233_333_335;
	static final long INV42 = 23_809_524;

	static long[] polyC;

	static final long[][] comb = { { 1 }, { 1, 1 }, { 1, 2, 1 }, { 1, 3, 3, 1 } };

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		long N = Long.parseLong(reader.readLine());
		long SQRTN = (long) Math.sqrt(N);

		long nModded = N % MOD;

		polyC = new long[] { nModded * (nModded + 1) % MOD * (nModded + 2) % MOD * INV6 % MOD,
				(3 * nModded % MOD + 4) % MOD * INV6 % MOD, (-3 * nModded % MOD - 6 + 4 * MOD) % MOD * INV6 % MOD,
				2 * INV6 % MOD };

		long[] powC = new long[4];

		long[] powXSQ = new long[4];

		long sum = 0;
		for (long x = -SQRTN; x <= SQRTN; x++) {
			Arrays.fill(powC, 0);
			long XSQ = x * x % MOD;

			powXSQ[0] = 1;
			powXSQ[1] = XSQ;
			powXSQ[2] = powXSQ[1] * XSQ % MOD;
			powXSQ[3] = powXSQ[2] * XSQ % MOD;

			for (int term = 0; term <= 3; term++) {
				for (int subT = 0; subT <= term; subT++) {
					// subT is power of y^2
					long cCoeff = powXSQ[term - subT] * comb[term][subT] * polyC[term] % MOD;
					powC[subT] = (powC[subT] + cCoeff) % MOD;
				}
			}

			long YMAX = (long) Math.sqrt(N - x * x);
			sum = (sum + powC[3] * 2 * sumSixths(YMAX)) % MOD;
			sum = (sum + powC[2] * 2 * sumFourths(YMAX)) % MOD;
			sum = (sum + powC[1] * 2 * sumSquares(YMAX)) % MOD;
			sum = (sum + powC[0] * ((2 * YMAX + 1) % MOD)) % MOD;
		}
		printer.println(sum);
		printer.close();
	}

	static long sumSquares(long N) {
		return N * (N + 1) % MOD * (2 * N + 1) % MOD * INV6 % MOD;
	}

	static long sumFourths(long N) {
		return N * (N + 1) % MOD * (2 * N + 1) % MOD * ((3 * N * (N + 1) - 1) % MOD) % MOD * INV30 % MOD;
	}

	static long sumSixths(long N) {
		return N * (N + 1) % MOD * (2 * N + 1) % MOD * ((3 * N * (N + 1) % MOD * ((N * N + N - 1) % MOD) + 1) % MOD)
				% MOD * INV42 % MOD;
	}
}
