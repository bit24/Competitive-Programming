import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_444E {

	public static void main(String[] args) throws IOException {
		new Div2_444E().main();
	}

	double x1;
	double y1;
	double x2;
	double y2;

	double[] x;
	double[] y;
	double[] r;

	int N;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		x1 = Integer.parseInt(inputData.nextToken());
		y1 = Integer.parseInt(inputData.nextToken());
		x2 = Integer.parseInt(inputData.nextToken());
		y2 = Integer.parseInt(inputData.nextToken());

		double tX = -(x1 + x2) / 2;
		double tY = -(y1 + y2) / 2;

		x1 += tX;
		y1 += tY;
		x2 += tX;
		y2 += tY;

		N = Integer.parseInt(reader.readLine());
		x = new double[N];
		y = new double[N];
		r = new double[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken()) + tX;
			y[i] = Integer.parseInt(inputData.nextToken()) + tY;
			r[i] = Integer.parseInt(inputData.nextToken());
		}

		double rot = -Math.atan2(y2, x2);
		double cos = Math.cos(rot); // can be replaced with direct conversion for more accuracy
		double sin = Math.sin(rot);

		for (int i = 0; i < N; i++) {
			double nX = x[i] * cos - y[i] * sin;
			double nY = x[i] * sin + y[i] * cos;
			x[i] = nX;
			y[i] = nY;
		}

		double nX = x1 * cos - y1 * sin;
		double nY = x1 * sin + y1 * cos;
		x1 = nX;
		y1 = nY;

		nX = x2 * cos - y2 * sin;
		nY = x2 * sin + y2 * cos;
		x2 = nX;
		y2 = nY;

		l = Math.abs(x2);

		Range[] blocked = new Range[N];
		for (int i = 0; i < N; i++) {
			calc(x[i], y[i], r[i]);
			blocked[i] = new Range(Math.min(ans1, ans2), Math.max(ans1, ans2));
		}

		Arrays.sort(blocked);
		double best = blocked[0].st;
		double furthest = Double.NEGATIVE_INFINITY;

		double sFurthest = Double.NEGATIVE_INFINITY;
		double sBlocked = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < N; i++) {
			if (furthest + 1e-4 < blocked[i].st) {
				if (furthest <= 0 && 0 <= blocked[i].st) {
					best = 0;
					sFurthest = furthest;
					sBlocked = blocked[i].st;
				} else {
					if (Math.abs(furthest) < Math.abs(best)) {
						best = furthest;
						sFurthest = furthest;
						sBlocked = blocked[i].st;
					}
					if (Math.abs(blocked[i].st) < Math.abs(best)) {
						best = blocked[i].st;
						sFurthest = furthest;
						sBlocked = blocked[i].st;
					}
				}
			}
			furthest = Math.max(furthest, blocked[i].end);
		}
		if (Math.abs(furthest) < Math.abs(best)) {
			best = furthest;
		}

		double ans = Math.sqrt(best * best + l * l);
		/*if (ans == 11630.500730841995) {
			printer.println(sFurthest + "x" + sBlocked + "x" + ans);
		}*/
		printer.println(ans);
		printer.close();
	}

	class Range implements Comparable<Range> {
		double st;
		double end;

		Range(double st, double end) {
			this.st = st;
			this.end = end;
		}

		public int compareTo(Range o) {
			if (Math.abs(st - o.st) < 1e-12) {
				return -Double.compare(end, o.end);
			}
			return Double.compare(st, o.st);
		}
	}

	double l;

	double ans1;
	double ans2;

	void calc(double a, double b, double r) {
		double E = (a * a + b * b - l * l - r * r) / (2 * r);
		double F = -b / r;

		double x = F * F - 1;
		double y = 2 * E * F;
		double z = E * E - l * l;

		double discr = y * y - 4 * x * z;
		double sqrt = Math.sqrt(discr);

		ans1 = (-y + sqrt) / (2 * x);
		ans2 = (-y - sqrt) / (2 * x);
	}

}
