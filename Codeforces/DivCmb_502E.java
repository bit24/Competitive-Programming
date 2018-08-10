import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_502E {

	public static void main(String[] args) throws IOException {
		new DivCmb_502E().execute();
	}

	static long BASE = 101323;

	static long MOD = 1_000_000_123;

	static long[] POW = new long[200_001];

	void execute() throws IOException {
		POW[0] = 1;
		for (int i = 1; i <= 200_000; i++) {
			POW[i] = POW[i - 1] * BASE % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int M = Integer.parseInt(inputData.nextToken());

		Point[] set1 = new Point[N];
		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			set1[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}

		Point[] hull1 = solve(set1);

		Point[] set2 = new Point[M];
		for (int i = 0; i < M; i++) {
			inputData = new StringTokenizer(reader.readLine());
			set2[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}

		Point[] hull2 = solve(set2);

		if (hull1.length != hull2.length) {
			printer.println("NO");
			printer.close();
			return;
		}
		int hSize = hull1.length;

		long[] vals1 = new long[hSize * 2];
		for (int i = 0; i < hSize; i++) {
			Point cP = hull1[i];
			Point pP = hull1[(i - 1 + hSize) % hSize];
			Point nP = hull1[(i + 1) % hSize];
			vals1[2 * i] = (pP.x - cP.x) * (nP.x - cP.x) + (pP.y - cP.y) * (nP.y - cP.y);
			vals1[2 * i + 1] = (nP.x - cP.x) * (nP.x - cP.x) + (nP.y - cP.y) * (nP.y - cP.y);
		}

		long[] vals2 = new long[hSize * 2];
		for (int i = 0; i < hSize; i++) {
			Point cP = hull2[i];
			Point pP = hull2[(i - 1 + hSize) % hSize];
			Point nP = hull2[(i + 1) % hSize];
			vals2[2 * i] = (pP.x - cP.x) * (nP.x - cP.x) + (pP.y - cP.y) * (nP.y - cP.y);
			vals2[2 * i + 1] = (nP.x - cP.x) * (nP.x - cP.x) + (nP.y - cP.y) * (nP.y - cP.y);
		}

		long h1 = 0;
		long h2 = 0;

		for (int i = 0; i < 2 * hSize; i++) {
			h1 = (h1 * BASE + vals1[i] % MOD + MOD) % MOD;
			h2 = (h2 * BASE + vals2[i] % MOD + MOD) % MOD;
		}

		if (h1 == h2 && Arrays.equals(vals1, vals2)) {
			printer.println("YES");
			printer.close();
			return;
		}

		for (int i = 0; i < hSize * 2; i += 2) {
			h1 = (h1 - (POW[hSize * 2 - 1] * ((vals1[i] % MOD) + MOD)) % MOD + MOD) % MOD;
			h1 = h1 * BASE % MOD;
			h1 = (h1 + vals1[i] % MOD + MOD) % MOD;

			h1 = (h1 - (POW[hSize * 2 - 1] * ((vals1[i + 1] % MOD) + MOD)) % MOD + MOD) % MOD;
			h1 = h1 * BASE % MOD;
			h1 = (h1 + vals1[i + 1] % MOD + MOD) % MOD;

			if (h1 == h2 && equals(vals1, vals2, i + 2)) {
				printer.println("YES");
				printer.close();
				return;
			}
		}

		printer.println("NO");
		printer.close();
	}

	static boolean equals(long[] a, long[] b, int off) {
		for (int i = 0; i < a.length; i++) {
			if (a[(i + off) % a.length] != b[i]) {
				return false;
			}
		}
		return true;
	}

	Point[] solve(Point[] points) {
		Arrays.sort(points);

		Point[] cHull = new Point[2 * points.length];
		int hSize = 0;

		for (int i = 0; i < points.length; i++) {
			while (hSize >= 2 && orient(cHull[hSize - 2], cHull[hSize - 1], points[i]) <= 0) {
				hSize--;
			}
			cHull[hSize++] = points[i];
		}
		int lHullSize = hSize;

		for (int i = points.length - 2; i >= 0; i--) {
			while (hSize > lHullSize && orient(cHull[hSize - 2], cHull[hSize - 1], points[i]) <= 0) {
				hSize--;
			}
			cHull[hSize++] = points[i];
		}

		Point[] aCHull = new Point[hSize - 1];

		for (int i = 0; i < hSize - 1; i++) {
			aCHull[i] = cHull[i];
		}
		return aCHull;
	}

	public static long orient(Point a, Point b, Point c) {
		return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
	}

	class Point implements Comparable<Point> {
		long x;
		long y;

		Point(long x, long y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point o) {
			return x < o.x ? -1 : x == o.x ? (y < o.y ? -1 : y == o.y ? 0 : 1) : 1;
		}
	}
}