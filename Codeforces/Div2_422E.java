import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Div2_422E {

	static int tL;
	static char[] text;

	static int pL;
	static char[] pat;

	static final long MOD = 1_000_000_007;

	static long[] pow;

	static long[] tPH;
	static long[] pPH;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		tL = Integer.parseInt(reader.readLine());
		text = (" " + reader.readLine()).toCharArray();
		pL = Integer.parseInt(reader.readLine());
		pat = (" " + reader.readLine()).toCharArray();
		int mJ = Integer.parseInt(reader.readLine());

		pow = new long[tL + 1];
		pow[0] = 1;
		for (int i = 1; i <= tL; i++) {
			pow[i] = pow[i - 1] * 31 % MOD;
		}

		tPH = new long[tL + 1];
		for (int i = 1; i <= tL; i++) {
			tPH[i] = (tPH[i - 1] * 31 + text[i]) % MOD;
		}

		pPH = new long[pL + 1];
		for (int i = 1; i <= pL; i++) {
			pPH[i] = (pPH[i - 1] * 31 + pat[i]) % MOD;
		}

		int[][] mExt = new int[tL + 2][mJ + 1];
		Arrays.fill(mExt[1], 1);

		for (int tI = 1; tI <= tL; tI++) {
			for (int cJ = 0; cJ <= mJ; cJ++) {
				if (mExt[tI + 1][cJ] < mExt[tI][cJ]) {
					mExt[tI + 1][cJ] = mExt[tI][cJ];
				}
				int cExt = mExt[tI][cJ];
				int lcp = lcp(tI, cExt);
				if (cJ < mJ && mExt[tI + lcp][cJ + 1] < cExt + lcp) {
					mExt[tI + lcp][cJ + 1] = cExt + lcp;
				}
			}
		}

		for (int tI = 1; tI <= tL + 1; tI++) {
			for (int i = 0; i <= mJ; i++) {
				if (mExt[tI][i] == pL + 1) {
					System.out.println("YES");
					return;
				}
			}
		}

		System.out.println("NO");
	}

	static int lcp(int tS, int pS) {
		int low = 0;
		int high = Math.min(pL - pS + 1, tL - tS + 1);

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if ((tPH[tS + mid - 1] - tPH[tS - 1] * pow[mid] - pPH[pS + mid - 1] + pPH[pS - 1] * pow[mid]) % MOD == 0) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

}
