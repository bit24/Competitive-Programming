import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TruthTelling {

	static int M;
	static int N;
	static int S;

	static int[] a;
	static int[] b;
	static int[] t;
	static long[] k;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int T = Integer.parseInt(reader.readLine());

		while (T-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			M = Integer.parseInt(inputData.nextToken());
			N = Integer.parseInt(inputData.nextToken());
			S = Integer.parseInt(inputData.nextToken());

			a = new int[S];
			b = new int[S];
			t = new int[S];
			k = new long[S];

			for (int i = 0; i < S; i++) {
				inputData = new StringTokenizer(reader.readLine());
				a[i] = Integer.parseInt(inputData.nextToken()) - 1;
				b[i] = Integer.parseInt(inputData.nextToken()) - 1;
				t[i] = Integer.parseInt(inputData.nextToken());
				k[i] = Integer.parseInt(inputData.nextToken());
			}
			printer.println(binSearch());
		}
		printer.close();
	}

	static int binSearch() {
		int low = 1;
		int high = S;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (cons(mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	static boolean cons(int S) {
		long[][] lGrid = new long[M][N];
		long[][] gGrid = new long[M][N];

		for (long[] a : lGrid) {
			Arrays.fill(a, Long.MAX_VALUE / 4);
		}
		for (long[] a : gGrid) {
			Arrays.fill(a, Long.MIN_VALUE / 4);
		}

		long[][] tGrid1 = new long[M][M];
		long[][] tGrid2 = new long[N][N];

		for (long[] a : tGrid1) {
			Arrays.fill(a, Long.MIN_VALUE / 4);
		}
		for (long[] a : tGrid2) {
			Arrays.fill(a, Long.MIN_VALUE / 4);
		}

		for (int i = 0; i < S; i++) {
			int cA = a[i];
			int cB = b[i];
			int cT = t[i];
			long cK = k[i];

			for (int c = 0; c < M; c++) {
				if (cT == -1) {
					long d = gGrid[c][cB] - cK;
					tGrid1[cA][c] = Math.max(tGrid1[cA][c], d);
				} else {
					long d = cK - lGrid[c][cB];
					tGrid1[c][cA] = Math.max(tGrid1[c][cA], d);
				}
			}

			for (int c = 0; c < N; c++) {
				if (cT == -1) {
					long d = gGrid[cA][c] - cK;
					tGrid2[cB][c] = Math.max(tGrid2[cB][c], d);
				} else {
					long d = cK - lGrid[cA][c];
					tGrid2[c][cB] = Math.max(tGrid2[c][cB], d);
				}
			}

			if (cT == -1) {
				lGrid[cA][cB] = Math.min(lGrid[cA][cB], cK);
			} else {
				gGrid[cA][cB] = Math.max(gGrid[cA][cB], cK);
			}
		}

		long[] time1 = new long[M];
		long[] time2 = new long[N];

		boolean upd = false;
		for (int i = 0; i < M; i++) {
			upd = false;

			for (int j = 0; j < M; j++) {
				for (int k = 0; k < M; k++) {
					if (time1[j] + tGrid1[j][k] > time1[k]) {
						time1[k] = time1[j] + tGrid1[j][k];
						upd = true;
					}
				}
			}

			if (!upd) {
				break;
			}
		}
		if (upd) {
			return false;
		}

		for (int i = 0; i < N; i++) {
			upd = false;

			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					if (time2[j] + tGrid2[j][k] > time2[k]) {
						time2[k] = time2[j] + tGrid2[j][k];
						upd = true;
					}
				}
			}

			if (!upd) {
				break;
			}
		}
		if (upd) {
			return false;
		}

		return true;
	}

}
