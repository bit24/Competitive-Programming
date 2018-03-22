import java.util.Arrays;

class MCConvexHull {

	// hull is calculated ccw bottom first

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
		return (long) (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
	}

	class Point implements Comparable<Point> {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point o) {
			return x < o.x ? -1 : x == o.x ? (y < o.y ? -1 : y == o.y ? 0 : 1) : 1;
		}
	}
}