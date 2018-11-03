import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div1_253E {

	static final double EPS = 1e-11;

	long W;
	long H;
	int N;

	long[] x;
	long[] y;

	public static void main(String[] args) throws IOException {
		new Div1_253E().main();
	}

	double ans = 0;

	double[][] angles;
	double[][] dist;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		W = Integer.parseInt(inputData.nextToken());
		H = Integer.parseInt(inputData.nextToken());
		N = Integer.parseInt(inputData.nextToken());
		x = new long[N];
		y = new long[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
		}

		angles = new double[N][N];
		dist = new double[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dist[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
				angles[i][j] = Math.atan2(y[j] - y[i], x[j] - x[i]);
			}
		}

		ArrayList<Integer> order = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			order.add(i);
		}
		Collections.shuffle(order);

		for (int i : order) {
			binSearch(i);
		}
		printer.println(ans);
		printer.close();
	}

	void binSearch(int pv) {
		fInts(pv, ans + 2e-9);

		if (!gap()) {
			return;
		}

		double low = ans + 2e-9;
		double high = 1_414_214;

		int i = 50;

		while (i-- > 0) {
			double mid = (low + high) / 2;
			fInts(pv, mid);
			if (gap()) {
				low = mid;
			} else {
				high = mid;
			}
		}

		ans = Math.max(ans, low);
	}

	Int[] ints = new Int[2010];
	int iS = 0;

	void fInts(int pv, double r) {
		iS = 0;

		double r2 = 2 * r;
		double inv2r = 1 / (2 * r);
		double[] cDist = dist[pv];
		double[] cAng = angles[pv];
		for (int i = 0; i < N; i++) {
			double d = cDist[i];

			if (d < EPS) {
				continue;
			}

			// no contact
			if (d > r2) {
				continue;
			}

			double angle = cAng[i];

			double theta = Math.acos(d * inv2r);
			double st = angle - theta;
			double end = angle + theta;

			if (st < -Math.PI) {
				ints[iS++] = new Int(st + PI2, Math.PI);
				ints[iS++] = new Int(-Math.PI, end);
			} else if (end > Math.PI) {
				ints[iS++] = new Int(st, Math.PI);
				ints[iS++] = new Int(-Math.PI, end - PI2);
			} else {
				ints[iS++] = new Int(st, end);
			}
		}

		// down
		double d = y[pv];
		if (d < 2 * r) {
			double theta = Math.acos(d / r);
			addInts(-HALF_PI - theta, -HALF_PI + theta);// twice because they're not ignorable
			addInts(-HALF_PI - theta, -HALF_PI + theta);
		}

		// right
		d = W - x[pv];
		if (d < 2 * r) {
			double theta = Math.acos(d / r);
			addInts(-theta, theta);
			addInts(-theta, theta);
		}

		// up
		d = H - y[pv];
		if (d < 2 * r) {
			double theta = Math.acos(d / r);
			addInts(HALF_PI - theta, HALF_PI + theta);
			addInts(HALF_PI - theta, HALF_PI + theta);
		}

		// left
		d = x[pv];
		if (d < 2 * r) {
			double theta = Math.acos(d / r);
			addInts(Math.PI - theta, Math.PI + theta);
			addInts(Math.PI - theta, Math.PI + theta);
		}
	}

	static final double PI2 = 2 * Math.PI;
	static final double HALF_PI = Math.PI / 2;

	// assuming end-st < 2pi
	void addInts(double st, double end) {
		if (st < -Math.PI) {
			ints[iS++] = new Int(st + PI2, Math.PI);
			ints[iS++] = new Int(-Math.PI, end);
		} else if (end > Math.PI) {
			ints[iS++] = new Int(st, Math.PI);
			ints[iS++] = new Int(-Math.PI, end - PI2);
		} else {
			ints[iS++] = new Int(st, end);
		}
	}

	// an Int can span anywhere from -pi to pi
	// error on the side of returning true
	boolean gap() {
		Arrays.sort(ints, 0, iS);

		if (iS < 2 || EPS < ints[1].st) {
			return true;
		}

		double f1 = Double.NEGATIVE_INFINITY;
		double f2 = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < iS; i++) {
			if (i != 0) {
				if (f2 + EPS < ints[i].st) {
					return true;
				}
			}

			if (ints[i].end > f1) {
				f2 = f1;
				f1 = ints[i].end;
			} else if (ints[i].end > f2) {
				f2 = ints[i].end;
			}

			while (i + 1 < iS && ints[i + 1].st < ints[i].st + EPS) {
				i++;
				if (ints[i].end > f1) {
					f2 = f1;
					f1 = ints[i].end;
				} else if (ints[i].end > f2) {
					f2 = ints[i].end;
				}
			}
		}

		if (f2 + EPS < Math.PI) {
			return true;
		}
		return false;
	}

	class Int implements Comparable<Int> {
		double st;
		double end;

		Int(double st, double end) {
			this.st = st;
			this.end = end;
		}

		public int compareTo(Int o) {
			double d2 = o.st;
			if (st < d2)
				return -1;
			if (st > d2)
				return 1;

			// Cannot use doubleToRawLongBits because of possibility of NaNs.
			long thisBits = Double.doubleToLongBits(st);
			long anotherBits = Double.doubleToLongBits(d2);

			return (thisBits == anotherBits ? 0 : // Values are equal
					(thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
							1)); // (0.0, -0.0) or (NaN, !NaN)
		}
	}
}
