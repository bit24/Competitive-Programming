import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_489E {

	static int n;
	static int q;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		n = Integer.parseInt(inputData.nextToken());
		q = Integer.parseInt(inputData.nextToken());

		int[] a = new int[n + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= n; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		sum = new long[4 * n];
		max = new int[4 * n];
		build(1, 1, n, a);

		qLoop:
		for (int cQ = 0; cQ < q; cQ++) {
			inputData = new StringTokenizer(reader.readLine());
			int uI = Integer.parseInt(inputData.nextToken());
			int uV = Integer.parseInt(inputData.nextToken());
			update(1, 1, n, uI, uV);
			a[uI] = uV;

			long cSum = 0;
			int l = 1;

			while (true) {
				int cI = qGE(1, 1, n, l, cSum);
				if (cI == -1) {
					break;
				}

				long pSum = qSum(1, 1, n, 1, cI - 1);
				if (pSum == a[cI]) {
					printer.println(cI);
					continue qLoop;
				}
				cSum = pSum + a[cI];
				l = cI + 1;
			}
			printer.println(-1);
		}
		printer.close();
	}

	static long[] sum;
	static int[] max;

	static void build(int nI, int cL, int cR, int[] inp) {
		if (cL == cR) {
			sum[nI] = max[nI] = inp[cL];
		} else {
			int mid = (cL + cR) >> 1;
			build(nI * 2, cL, mid, inp);
			build(nI * 2 + 1, mid + 1, cR, inp);
			sum[nI] = sum[nI * 2] + sum[nI * 2 + 1];
			max[nI] = Math.max(max[nI * 2], max[nI * 2 + 1]);
		}
	}

	static void update(int nI, int cL, int cR, int uI, int uV) {
		if (cL == cR) {
			sum[nI] = max[nI] = uV;
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI, uV);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI, uV);
			}
			sum[nI] = sum[nI * 2] + sum[nI * 2 + 1];
			max[nI] = Math.max(max[nI * 2], max[nI * 2 + 1]);
		}
	}

	static long qSum(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return 0;
		}
		if (qL <= cL && cR <= qR) {
			return sum[nI];
		}
		int mid = (cL + cR) >> 1;
		return qSum(nI * 2, cL, mid, qL, qR) + qSum(nI * 2 + 1, mid + 1, cR, qL, qR);
	}

	static int qGE(int nI, int cL, int cR, int qL, long tar) {
		if (cL == cR) {
			return max[nI] >= tar ? cL : -1;
		}

		int mid = (cL + cR) >> 1;

		if (qL <= mid && max[nI * 2] >= tar) {
			int v = qGE(nI * 2, cL, mid, qL, tar);
			if (v != -1) {
				return v;
			}
		}
		if (max[nI * 2 + 1] != -1) {
			return qGE(nI * 2 + 1, mid + 1, cR, qL, tar);
		}
		return -1;
	}
}
