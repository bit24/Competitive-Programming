import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_487D {

	static int nE;
	static int L;
	static int M;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		L = Integer.parseInt(inputData.nextToken());
		M = Integer.parseInt(inputData.nextToken());

		sig = new int[nE + 1];
		int[] tLeft = new int[nE + 1];
		int[] tRight = new int[nE + 1];
		int lCnt = 0;
		int rCnt = 0;

		for (int i = 1; i <= nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int ind = Integer.parseInt(inputData.nextToken());
			int v = Integer.parseInt(inputData.nextToken());
			sig[i] = ind;
			if (v == -1) {
				tLeft[lCnt++] = ind;
			} else {
				tRight[rCnt++] = ind;
			}
		}

		Arrays.sort(sig, 1, sig.length);
		sig[0] = Integer.MIN_VALUE;
		Arrays.sort(tLeft, 0, lCnt);
		Arrays.sort(tRight, 0, rCnt);

		int j = lCnt - 1;

		long ans = 0;
		for (int i = 0; i < rCnt; i++) {
			int cX = tRight[i];
			while (j >= 0 && cX + tLeft[j] + L >= 0 && -cX + tLeft[j] + L >= 0) {
				struct_upd(tLeft[j], 1);
				j--;
			}

			while (j + 1 < lCnt && -cX + tLeft[j + 1] + L < 0) {
				struct_upd(tLeft[j + 1], -1);
				j++;
			}

			long num = cX * (1L + M) + L * (1L - M);
			long den = M - 1;

			if (den == 0) {
				if (cX < 0) {
					ans += bit_query(BIT.length - 1);
				}
				continue;
			}

			long val;

			if (num >= 0) {
				val = num / den;
			} else {
				val = (num - den + 1) / den;
			}

			ass(Math.abs(val) < Integer.MAX_VALUE);
			int iVal = (int) val;
			ans += struct_queryGreater(iVal);
		}

		Arrays.fill(BIT, 0);

		j = 0;
		for (int i = lCnt - 1; i >= 0; i--) {
			int cX = tLeft[i];
			while (j < rCnt && cX + tRight[j] + L < 0 && cX - tRight[j] + L >= 0) {
				struct_upd(tRight[j], 1);
				j++;
			}

			while (j - 1 >= 0 && cX - tRight[j - 1] + L < 0) {
				struct_upd(tRight[j - 1], -1);
				j--;
			}

			long num = cX * (M + 1L) + L * (M + 1L);
			long den = M - 1;

			if (den == 0) {
				if (cX > -L) {
					ans += bit_query(BIT.length - 1);
				}
				continue;
			}

			long val;

			if (num >= 0) {
				val = (num + den - 1) / den;
			} else {
				val = num / den;
			}

			ass(Math.abs(val) < Integer.MAX_VALUE);
			int iVal = (int) val;
			ans += struct_queryLesser(iVal);
		}
		printer.println(ans);
		printer.close();
	}

	static void ass(boolean inp) {
		if (!inp) {
			throw new RuntimeException();
		}
	}

	static int struct_queryLesser(int i) {
		return bit_query(lesser(i));
	}

	static int struct_queryGreater(int i) {
		int ans = bit_query(BIT.length - 1);
		ans -= bit_query(lesserEqual(i));
		return ans;
	}

	static void struct_upd(int i, int d) {
		bit_upd(lesserEqual(i), d);
	}

	static int bit_query(int i) {
		int sum = 0;
		while (i > 0) {
			sum += BIT[i];
			i -= (i & -i);
		}
		return sum;
	}

	static void bit_upd(int i, int d) {
		if (i == 0) {
			return;
		}
		while (i < BIT.length) {
			BIT[i] += d;
			i += (i & -i);
		}
	}

	static int[] BIT = new int[100_001];

	static int[] sig;

	static int lesserEqual(int i) {
		int ind = lesser(i);
		if (ind + 1 < sig.length && sig[ind + 1] == i) {
			return ind + 1;
		}
		return ind;
	}

	// find last index lesser
	static int lesser(int i) {
		int low = 0;
		int high = sig.length - 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (sig[mid] < i) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}
}
