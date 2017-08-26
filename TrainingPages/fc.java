import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: fc
*/

public class fc {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("fc.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fc.out")));

		int numPoints = Integer.parseInt(reader.readLine());
		Point[] points = new Point[numPoints];

		for (int i = 0; i < numPoints; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			points[i] = new fc().new Point(Double.parseDouble(inputData.nextToken()),
					Double.parseDouble(inputData.nextToken()));
		}
		reader.close();
		
		
		ConvexHull hullFinder = new fc().new ConvexHull(points);
		@SuppressWarnings("unchecked")
		ArrayList<Point> hull = new ArrayList(hullFinder.GrahamScan());

		double totalDistance = 0.0;

		for (int i = 0; i < hull.size(); i++) {
			int next = (i + 1) % hull.size();
			totalDistance += Math.sqrt(
					Math.pow(hull.get(i).x - hull.get(next).x, 2) + Math.pow(hull.get(i).y - hull.get(next).y, 2));
		}
		DecimalFormat formatter = new DecimalFormat("0.00");
		printer.println(formatter.format(totalDistance));
		printer.close();

	}

	class ConvexHull {
		Point[] points;
		int numPoints;

		public ConvexHull(Point[] points) {
			this.points = points;
			numPoints = points.length;
		}

		public LinkedList<Point> GrahamScan() {
			Point pivot = null;
			for (Point current : points) {
				if (pivot == null || current.y < pivot.y || (current.y == pivot.y && current.x < pivot.x)) {
					pivot = current;
				}
			}
			Arrays.sort(points, pivot.PolarOrder());

			LinkedList<Point> currentHull = new LinkedList<Point>();
			currentHull.push(pivot);

			int evalPoint = 2;
			for (; evalPoint < numPoints; evalPoint++) {
				if (!pivot.isCollinear(pivot, points[1], points[evalPoint])) {
					break;
				}
			}
			// point before it is collinear and will serve as a nice anchor
			// point
			currentHull.push(points[evalPoint - 1]);

			for (; evalPoint < numPoints; evalPoint++) {
				Point currentTop = currentHull.pop();
				while (!pivot.isCCW(currentHull.peek(), currentTop, points[evalPoint])) {
					currentTop = currentHull.pop();
				}
				// return currentTop
				currentHull.push(currentTop);

				// add new point
				currentHull.push(points[evalPoint]);
			}
			return currentHull;
		}
	}

	class Point implements Comparable<Point> {

		double x;
		double y;

		public Point() {
		}

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public boolean isCollinear(Point a, Point b, Point c) {
			double slope1 = (a.y - b.y) / (a.x - b.x);
			double slope2 = (b.y - c.y) / (b.x - c.x);
			if (Math.abs(slope1 - slope2) < 0.00000000001) {
				return true;
			}
			return false;
		}

		public int compareTo(Point o) {
			if (this.y < o.y) {
				return -1;
			}
			if (this.y > o.y) {
				return 1;
			}
			if (this.x < o.x) {
				return -1;
			}
			if (this.x > o.x) {
				return 1;
			}
			return 0;
		}

		public boolean equals(Object o) {
			if (this.getClass() != o.getClass()) {
				return false;
			}
			Point other = (Point) o;
			if (this.x == other.x && this.y == other.y) {
				return true;
			}
			return false;
		}

		public double distance(Point other) {
			return Math.sqrt(Math.abs(this.x - other.x) + Math.abs(this.y - other.y));
		}

		public boolean isCCW(Point a, Point b, Point c) {
			double IDK = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
			if (IDK < 0) {
				return false;
			} else if (IDK > 0) {
				return true;
			} else {
				// straight -> false
				return false;
			}
		}

		public PolarOrder PolarOrder() {
			return new PolarOrder();
		}

		class PolarOrder implements Comparator<Point> {

			public int compare(Point q1, Point q2) {
				if (q1.equals(Point.this)) {
					return -1;
				}
				if (q2.equals(Point.this)) {
					return 1;
				}

				double dx1 = q1.x - x;
				double dy1 = q1.y - y;
				double dx2 = q2.x - x;
				double dy2 = q2.y - y;

				// q1 above; q2 below
				if (dy1 >= 0 && dy2 < 0) {
					return -1;
				}

				// q1 below; q2 above
				else if (dy2 >= 0 && dy1 < 0) {
					return +1;
				}

				// 3-collinear and horizontal
				else if (dy1 == 0 && dy2 == 0) {
					if (dx1 >= 0 && dx2 < 0) {
						return -1;
					} else if (dx2 >= 0 && dx1 < 0) {
						return +1;
					} else {
						return 0;
					}
				}

				else {
					return !isCCW(Point.this, q1, q2) ? 1 : -1;
				}
			}

		}

	}

}
