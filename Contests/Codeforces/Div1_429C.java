import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_429C {

	static final long MOD = 1_000_000_007;
	static long[] fact = new long[305];
	static long[] iFact = new long[305];
	static final long I304 = 904487323;

	public static void main(String[] args) throws IOException {
		fact[0] = 1;
		for (int i = 1; i < 305; i++) {
			fact[i] = fact[i - 1] * i % MOD;
		}
		iFact[304] = I304;
		for (int i = 303; i >= 0; i--) {
			iFact[i] = iFact[i + 1] * (i + 1) % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());
		long[] groups = new long[len + 1];
		int[] gSizes = new int[len + 1];
		int nG = 0;

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		iLoop:
		for (int i = 0; i < len; i++) {
			long nxt = Integer.parseInt(inputData.nextToken());
			for (int j = 1; j <= nG; j++) {
				if (isSquare(nxt * groups[j])) {
					gSizes[j]++;
					continue iLoop;
				}
			}
			groups[++nG] = nxt;
			gSizes[nG] = 1;
		}

		long[][] dp = new long[nG + 1][len];

		dp[0][0] = 1;

		int fTotal = 0;
		for (int fG = 0; fG < nG; fG++) {
			for (int fB = 0; fB < len; fB++) {
				if (dp[fG][fB] == 0) {
					continue;
				}
				int nGSize = gSizes[fG + 1];
				for (int nS = 1; nS <= Math.min(nGSize, fTotal + 1); nS++) {
					for (int nBR = 0; nBR <= Math.min(fB, nS); nBR++) {
						long nW = dp[fG][fB] * fact[nGSize] % MOD * comb(nGSize - 1, nS - 1) % MOD * comb(fB, nBR) % MOD
								* comb(fTotal + 1 - fB, nS - nBR) % MOD;
						dp[fG + 1][fB - nBR + nGSize - nS] = (dp[fG + 1][fB - nBR + nGSize - nS] + nW) % MOD;
					}
				}
			}
			fTotal += gSizes[fG + 1];
		}
		printer.println(dp[nG][0]);
		printer.close();
	}

	static long comb(int a, int b) {
		if(b > a) {
			return 0;
		}
		return fact[a] * iFact[a - b] % MOD * iFact[b] % MOD;
	}

	static boolean isSquare(long inp) {
		long sqrt = (long) Math.sqrt(inp);
		return inp == sqrt * sqrt;
	}

}
