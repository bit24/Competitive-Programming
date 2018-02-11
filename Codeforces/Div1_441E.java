import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_441E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		minA = new int[nE * 4];
		maxA = new int[nE * 4];
		s0 = Integer.parseInt(inputData.nextToken());
		s1 = Integer.parseInt(inputData.nextToken());

		e = new int[nE + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		int low = Math.abs(s1 - s0);
		int high = 1_000_000_000;
		while (low != high) {
			int mid = (low + high) >> 1;
			build();

			int fE0 = query(s1 - mid, s1 + mid);
			int fE1 = query(s0 - mid, s0 + mid);

			for (int i = 1; i <= nE; i++) {
				int qF = query(e[i] - mid, e[i] + mid);
				if (fE1 >= i) {
					fE0 = Math.max(fE0, qF);
				}
				if (fE0 >= i) {
					fE1 = Math.max(fE1, qF);
				}
				clear(i);
			}

			if (fE0 == nE || fE1 == nE) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		printer.println(low);
		printer.close();
	}

	static int s0;
	static int s1;

	static int nE;
	static int[] e;

	static int[] minA;
	static int[] maxA;

	static void build() {
		Arrays.fill(minA, Integer.MAX_VALUE);
		Arrays.fill(maxA, Integer.MIN_VALUE);
		build(1, 1, nE);
	}

	static void build(int nI, int cL, int cR) {
		if (cL == cR) {
			minA[nI] = e[cL];
			maxA[nI] = e[cL];
		} else {
			int mid = (cL + cR) >> 1;
			build(nI * 2, cL, mid);
			build(nI * 2 + 1, mid + 1, cR);
			minA[nI] = Math.min(minA[nI * 2], minA[nI * 2 + 1]);
			maxA[nI] = Math.max(maxA[nI * 2], maxA[nI * 2 + 1]);
		}
	}

	static void clear(int cI) {
		clear(1, 1, nE, cI);
	}

	static void clear(int nI, int cL, int cR, int cI) {
		if (cL == cR) {
			minA[nI] = Integer.MAX_VALUE;
			maxA[nI] = Integer.MIN_VALUE;
		} else {
			int mid = (cL + cR) >> 1;
			if (cI <= mid) {
				clear(nI * 2, cL, mid, cI);
			} else {
				clear(nI * 2 + 1, mid + 1, cR, cI);
			}

			minA[nI] = Math.min(minA[nI * 2], minA[nI * 2 + 1]);
			maxA[nI] = Math.max(maxA[nI * 2], maxA[nI * 2 + 1]);
		}
	}

	static int query(int lB, int hB) {
		return query(1, 1, nE, lB, hB);
	}

	static int query(int nI, int cL, int cR, int lB, int hB) {
		if (cL == cR) {
			return lB <= minA[nI] && maxA[nI] <= hB ? cL : -1;
		} else {
			int mid = (cL + cR) >> 1;
			if (minA[nI * 2] < lB || hB < maxA[nI * 2]) {
				return query(nI * 2, cL, mid, lB, hB);
			}
			return Math.max(mid, query(nI * 2 + 1, mid + 1, cR, lB, hB));
		}
	}

}
