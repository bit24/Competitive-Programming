import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class TriangleCountingSolver {

	public static void main(String[] args) throws IOException {
		new TriangleCountingSolver().execute();
	}

	int numP;
	Point[] points;

	Point origin = new Point(0, 0);

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		numP = Integer.parseInt(reader.readLine());
		points = new Point[numP];

		for (int i = 0; i < numP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		reader.close();
		Arrays.sort(points, new By_Angle());

		// System.out.println(Arrays.toString(points));

		// System.out.println(isCW(origin, new Point(5, 0), new Point(5, 5)));

		// System.out.println(((long) numP) * (numP - 1) * (numP - 2) / 6);
		long ans = ((long) numP) * (numP - 1) * (numP - 2) / 6 - sweepAngles();
		System.out.println(ans);
	}

	long count;

	long sweepAngles() {
		long sum = 0;

		for (int right = 0, left = 1; right < numP; right++) {
			while (isCW(points[right], origin, points[(left + 1) % numP])) {
				left = (left + 1) % numP;
			}

			long numInRange = (left - right + numP) % numP;
			sum += numInRange * (numInRange - 1) / 2;
		}
		return sum;
	}

	boolean isCW(Point a, Point b, Point c) {
		Point v1 = b.subtract(a);
		Point v2 = c.subtract(b);

		return v1.x * v2.y - v1.y * v2.x < 0;
	}

	class Point {
		long x;
		long y;

		Point(long x, long y) {
			this.x = x;
			this.y = y;
		}

		Point subtract(Point subtrahend) {
			return new Point(x - subtrahend.x, y - subtrahend.y);
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}

	}

	class By_Angle implements Comparator<Point> {
		public int compare(Point c1, Point c2) {
			double angle1 = (Math.atan2(c1.y, c1.x) + 16 * Math.PI) % (2 * Math.PI);
			double angle2 = (Math.atan2(c2.y, c2.x) + 16 * Math.PI) % (2 * Math.PI);
			return Double.compare(angle1, angle2);
		}
	}
}
