import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_231E {

	static int M;
	static int N;

	static double[] a;
	static double[] b;
	static double[] x;
	static double[] y;

	static double aSS;
	static double aS;
	static double bSS;
	static double bS;

	static double[] aCf;
	static double[] bCf;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		M = Integer.parseInt(reader.readLine());
		a = new double[M];
		b = new double[M];

		for (int i = 0; i < M; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			a[i] = Integer.parseInt(inputData.nextToken());
			b[i] = Integer.parseInt(inputData.nextToken());
			aSS += a[i] * a[i];
			aS += a[i];
			bSS += b[i] * b[i];
			bS += b[i];
		}

		N = Integer.parseInt(reader.readLine());
		x = new double[N];
		y = new double[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
		}

		aCf = new double[] { M, -2 * aS, aSS };
		bCf = new double[] { M, -2 * bS, bSS };

		double optX = -aCf[1] / (2 * aCf[0]);
		double optY = -bCf[1] / (2 * bCf[0]);

		if (inside(optX, optY)) {
			printer.println(calcSum(optX, optY));
			printer.close();
			return;
		}

		double ans = Double.POSITIVE_INFINITY;
		for (int i = 0; i < N; i++) {
			ans = Math.min(ans, process(i));
		}

		printer.println(ans);
		printer.close();
	}

	static double process(int i) {
		int nxt = (i + 1) % N;

		if (x[i] == x[nxt]) {
			return processVertical(i);
		}

		double m = (y[nxt] - y[i]) / (x[nxt] - x[i]);
		double b = y[i] - m * x[i];

		double[] lCf = { aCf[0], aCf[1], aCf[2] };
		lCf[0] += bCf[0] * m * m;
		lCf[1] += bCf[0] * m * b * 2;
		lCf[2] += bCf[0] * b * b;

		lCf[1] += bCf[1] * m;
		lCf[2] += bCf[1] * b;

		lCf[2] += bCf[2];

		double optX = -lCf[1] / (2 * lCf[0]);

		if ((x[i] <= optX && optX <= x[nxt]) || (x[nxt] <= optX && optX <= x[i])) {
			double optY = m * optX + b;
			return calcSum(optX, optY);
		}

		return Math.min(calcSum(x[i], y[i]), calcSum(x[nxt], y[nxt]));
	}

	static double processVertical(int i) {
		int nxt = (i + 1) % N;

		double optY = -bCf[1] / (2 * bCf[0]);

		if ((y[i] <= optY && optY <= y[nxt]) || (y[nxt] <= optY && optY <= y[i])) {
			return calcSum(x[i], y[i]);
		}

		return Math.min(calcSum(x[i], y[i]), calcSum(x[nxt], y[nxt]));
	}

	static double calcSum(double x, double y) {
		return aCf[0] * x * x + aCf[1] * x + aCf[2] + bCf[0] * y * y + bCf[1] * y + bCf[2];
	}

	static boolean inside(double pX, double pY) {
		for (int i = 0; i < N; i++) {
			int nxt = (i + 1) % N;
			double v1x = x[nxt] - x[i];
			double v1y = y[nxt] - y[i];
			double v2x = pX - x[i];
			double v2y = pY - y[i];
			double prod = v1x * v2y - v1y * v2x;

			if (prod > 0) {
				return false;
			}
		}
		return true;
	}
}
