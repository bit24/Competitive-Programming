import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CowCurling {

	public static void main(String[] args) throws IOException {
		new CowCurling().execute();
	}

	int numPoints;

	Point[] aTeam;
	Point[] bTeam;

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("curling.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("curling.out")));
		numPoints = Integer.parseInt(reader.readLine());

		aTeam = new Point[numPoints];
		bTeam = new Point[numPoints];

		for (int i = 0; i < numPoints; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			aTeam[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		for (int i = 0; i < numPoints; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			bTeam[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		reader.close();

		printer.print(computePoints(aTeam, bTeam) + " ");
		printer.println(computePoints(bTeam, aTeam));
		printer.close();
	}

	public int computePoints(Point[] points, Point[] testPoints) {

		Arrays.sort(points);

		Stack<Point> lowerHull = new Stack<Point>();

		for (int i = 0; i < points.length; i++) {
			while (lowerHull.size() >= 2
					&& !isCCW(lowerHull.get(lowerHull.size() - 2), lowerHull.get(lowerHull.size() - 1), points[i])) {
				lowerHull.pop();
			}
			lowerHull.push(points[i]);
		}
		// lowerHull.pop();

		Stack<Point> upperHull = new Stack<Point>();
		for (int i = points.length - 1; i >= 0; i--) {
			while (upperHull.size() >= 2
					&& !isCCW(upperHull.get(upperHull.size() - 2), upperHull.get(upperHull.size() - 1), points[i])) {
				upperHull.pop();
			}
			upperHull.push(points[i]);
		}
		// upperHull.pop();

		TreeSet<Point> lowerHullSet = new TreeSet<Point>(lowerHull);
		TreeSet<Point> upperHullSet = new TreeSet<Point>(upperHull);

		int result = 0;
		for (Point testPoint : testPoints) {
			Point bottomLeft = lowerHullSet.floor(testPoint);
			Point bottomRight = lowerHullSet.ceiling(testPoint);

			if (bottomLeft == null || bottomRight == null) {
				continue;
			}

			if (isCW(bottomLeft, bottomRight, testPoint)) {
				continue;
			}

			Point topLeft = upperHullSet.floor(testPoint);
			Point topRight = upperHullSet.ceiling(testPoint);

			if (topLeft == null || topRight == null) {
				continue;
			}

			if (isCCW(topLeft, topRight, testPoint)) {
				continue;
			}
			result++;
		}
		return result;
	}

	public static boolean isCCW(Point a, Point b, Point c) {
		long area = 1L * ((long) a.x - b.x) * ((long) c.y - b.y) - 1L * ((long) a.y - b.y) * ((long) c.x - b.x);
		return area < 0L;
	}

	public static boolean isCW(Point a, Point b, Point c) {
		long area = 1L * ((long) a.x - b.x) * ((long) c.y - b.y) - 1L * ((long) a.y - b.y) * ((long) c.x - b.x);
		return area > 0L;
	}

	class Point implements Comparable<Point> {
		long x;
		long y;

		Point(long x, long y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point o) {
			int comparisonResult = Long.compare(x, o.x);
			if (comparisonResult == 0) {
				comparisonResult = Long.compare(y, o.y);
			}
			return comparisonResult;
		}
	}
}