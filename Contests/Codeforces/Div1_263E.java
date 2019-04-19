import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Div1_263E {

	static final long MOD = 1_000_000_123;

	static final long B1 = 1_343_317;
	static final long B2 = 3_943_673;

	static long[] P1 = new long[200_000];
	static long[] P2 = new long[200_000];

	public static void main(String[] args) throws IOException {
		P1[0] = 1;
		P2[0] = 1;

		for (int i = 1; i < 200_000; i++) {
			P1[i] = P1[i - 1] * B1 % MOD;
			P2[i] = P2[i - 1] * B2 % MOD;
		}

		new Div1_263E().main();
	}

	String patt;
	long[] h1;
	long[] h2;

	long M;
	int N;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		M = Long.parseLong(reader.readLine());

		patt = reader.readLine();
		N = patt.length();
		patt = " " + patt + " ";

		int[][] nxt = new int[4][N + 1];
		int[] last = { N + 1, N + 1, N + 1, N + 1 };

		for (int i = N; i >= 1; i--) {
			for (int j = 0; j < 4; j++) {
				nxt[j][i] = last[j];
			}
			last[patt.charAt(i) - 'A'] = i;
		}

		h1 = new long[N + 1];
		h2 = new long[N + 1];

		for (int i = 1; i <= N; i++) {
			h1[i] = (h1[i - 1] * B1 + patt.charAt(i)) % MOD;
			h2[i] = (h2[i - 1] * B2 + patt.charAt(i)) % MOD;
		}

		Suffix[] sufs = new Suffix[N + 1];
		for (int i = 1; i <= N; i++) {
			sufs[i] = new Suffix(i);
		}

		Arrays.sort(sufs, 1, N + 1);

		int[][] minBreak = new int[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				minBreak[i][j] = N + 1;
			}
		}

		for (int i = 1; i + 1 <= N; i++) {
			Suffix a = sufs[i];
			Suffix b = sufs[i + 1];
			int lcp = fLCP(a.i, b.i);

			int aNxt = patt.charAt(a.i + lcp) - 'A';
			int bNxt = patt.charAt(b.i + lcp) - 'A';

			if (bNxt == ' ') {
				throw new RuntimeException();
			}

			int sCI = -1;

			if (lcp != 0) { // no gap is possible if lcp == 0
				sCI = patt.charAt(a.i) - 'A';
				// first dif matches neither
				boolean gap = false;
				for (int j = 0; j < 4; j++) {
					if (aNxt < j && j < bNxt) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], lcp + 1);
						gap = true;
					}
				}
				if (gap) {
					for (int j = 0; j < 4; j++) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], lcp + 2);
					}
				}
			}

			// first dif matches a
			if (a.i + lcp <= N) {
				if (lcp == 0) {
					sCI = patt.charAt(a.i) - 'A';
				}

				int notD = Math.min(nxt[0][a.i + lcp], Math.min(nxt[1][a.i + lcp], nxt[2][a.i + lcp]));
				if (notD == N + 1) {
					for (int j = 0; j < 4; j++) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], notD - a.i + 1);
					}
				} else {
					int notDC = patt.charAt(notD) - 'A';
					for (int j = notDC + 1; j < 4; j++) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], notD - a.i + 1);
					}
					for (int j = 0; j < 4; j++) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], notD - a.i + 2);
					}
				}
			}

			// first dif matches b
			if (b.i + lcp <= N) {
				if (lcp == 0) {
					sCI = patt.charAt(b.i) - 'A';
				}
				int notA = Math.min(nxt[1][b.i + lcp], Math.min(nxt[2][b.i + lcp], nxt[3][b.i + lcp]));
				if (notA == N + 1) {

				} else {
					int notAC = patt.charAt(notA) - 'A';
					for (int j = 0; j < notAC; j++) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], notA - b.i + 1);
					}
					for (int j = 0; j < 4; j++) {
						minBreak[sCI][j] = Math.min(minBreak[sCI][j], notA - b.i + 2);
					}
				}
			}
		}

		Suffix b = sufs[1];
		int notA = Math.min(nxt[1][b.i], Math.min(nxt[2][b.i], nxt[3][b.i]));
		if (notA == N + 1) {
			
		} else {
			int notAC = patt.charAt(notA) - 'A';
			for (int j = 0; j < notAC; j++) {
				minBreak[0][j] = Math.min(minBreak[0][j], notA - b.i + 1);
			}
			for (int j = 0; j < 4; j++) {
				minBreak[0][j] = Math.min(minBreak[0][j], notA - b.i + 2);
			}
		}

		Suffix a = sufs[N];
		int notD = Math.min(nxt[0][a.i], Math.min(nxt[1][a.i], nxt[2][a.i]));
		if (notD == N + 1) {
			for (int j = 0; j < 4; j++) {
				minBreak[3][j] = Math.min(minBreak[3][j], notD - a.i + 1);
			}
		} else {
			int notDC = patt.charAt(notD) - 'A';
			for (int j = notDC + 1; j < 4; j++) {
				minBreak[3][j] = Math.min(minBreak[3][j], notD - a.i + 1);
			}
			for (int j = 0; j < 4; j++) {
				minBreak[3][j] = Math.min(minBreak[3][j], notD - a.i + 2);
			}
		}

		long[][] unit = new long[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				unit[i][j] = minBreak[i][j];
			}
		}

		long[][][] pows = new long[60][][];
		pows[0] = unit;

		for (int i = 1; i < 60; i++) {
			pows[i] = mult(pows[i - 1], pows[i - 1]);
		}

		long[][] cur = new long[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cur[i][j] = Long.MAX_VALUE / 4;
			}
			cur[i][i] = 1;
		}

		long ans = 0;
		for (int i = 59; i >= 0; i--) {
			long[][] pos = mult(cur, pows[i]);
			if (min(pos) <= M) {
				cur = pos;
				ans += 1L << i;
			}
		}

		printer.println(ans + 1);
		printer.close();
	}

	long min(long[][] a) {
		long min = Long.MAX_VALUE / 4;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				min = Math.min(min, a[i][j]);
			}
		}
		return min;
	}

	long[][] mult(long[][] a, long[][] b) {
		long[][] res = new long[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res[i][j] = Long.MAX_VALUE / 4;
				for (int k = 0; k < 4; k++) {
					res[i][j] = Math.min(res[i][j], a[i][k] + b[k][j] - 1);
				}
			}
		}
		return res;
	}

	class Suffix implements Comparable<Suffix> {
		int i;

		Suffix(int i) {
			this.i = i;
		}

		public int compareTo(Suffix o) {
			int lcp = fLCP(i, o.i);
			return patt.charAt(i + lcp) - patt.charAt(o.i + lcp);
		}
	}

	int fLCP(int i, int j) {
		int low = 0;
		int high = N - Math.max(i, j) + 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if ((h1[i + mid - 1] - h1[j + mid - 1] + (h1[j - 1] - h1[i - 1]) * P1[mid]) % MOD == 0
					&& (h2[i + mid - 1] - h2[j + mid - 1] + (h2[j - 1] - h2[i - 1]) * P2[mid]) % MOD == 0) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}
}
