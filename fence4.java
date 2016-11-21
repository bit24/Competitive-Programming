import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*ID: eric.ca1
LANG: JAVA
TASK: fence4
*/

public class fence4 {

	public static void main(String[] args) throws IOException {
		new fence4().execute();
	}

	void execute() throws IOException {
		input();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fence4.out")));
		if (containsIntersection()) {
			printer.println("NOFENCE");
		} else {
			TreeSet<Integer> visIndices = scanVisible();

			printer.println(visIndices.size());
			if (visIndices.contains(numSegments - 1) && visIndices.contains(numSegments - 2)) {
				for (int i = 0; i < numSegments - 2; i++) {
					if (visIndices.contains(i)) {
						printer.println(segments[i]);
					}
				}
				printer.println(segments[numSegments - 1]);
				printer.println(segments[numSegments - 2]);
			} else {
				for (int i : visIndices) {
					printer.println(segments[i]);
				}
			}
		}
		printer.close();
	}

	Point[] points;
	Segment[] segments;
	Point obsPoint;
	int numPoints, numSegments;

	void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fence4.in"));
		numPoints = numSegments = Integer.parseInt(reader.readLine());

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		obsPoint = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));

		points = new Point[numPoints];
		for (int i = 0; i < numPoints; i++) {
			inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		segments = new Segment[numSegments];
		for (int i = 0; i < numSegments - 1; i++) {
			segments[i] = new Segment(points[i], points[i + 1]);
		}
		segments[numSegments - 1] = new Segment(points[0], points[numSegments - 1]);

		reader.close();
	}

	TreeSet<Integer> scanVisible() {
		Point[] directions = new Point[numPoints * 3];
		for (int i = 0; i < numSegments; i++) {
			Segment cSeg = segments[i];
			directions[0] = cSeg.p1;
			directions[1] = new Point((cSeg.p1.x + cSeg.p2.x) / 2, (cSeg.p1.y + cSeg.p2.y) / 2);
			directions[2] = cSeg.p2;
		}

		TreeSet<Integer> visibleSegI = new TreeSet<Integer>();
		for (int segmentI = 0; segmentI < numSegments; segmentI++) {
			Segment cSeg = segments[segmentI];

			// see if midpoint is directly visible
			Point midPT = new Point((cSeg.p1.x + cSeg.p2.x) / 2, (cSeg.p1.y + cSeg.p2.y) / 2);
			Segment visionSeg = new Segment(obsPoint, midPT);
			double mpDistance = Math.sqrt(Math.pow(midPT.x - obsPoint.x, 2) + Math.pow(midPT.y - obsPoint.y, 2));
			boolean isVisible = true;

			for (int i = 0; i < numSegments; i++) {
				if (i != segmentI) {
					Segment cSegment = segments[i];
					Point interPoint = findIntersection(visionSeg, cSegment);
					if (interPoint != null) {
						double ipDistance = Math
								.sqrt(Math.pow(interPoint.x - obsPoint.x, 2) + Math.pow(interPoint.y - obsPoint.y, 2));
						if (ipDistance < mpDistance) {
							isVisible = false;
							break;
						}
					}
				}
			}
			if (isVisible) {
				// midpoint is visible
				visibleSegI.add(segmentI);
			}

			Point[] grazed = new Point[] { cSeg.p1, cSeg.p2 };
			for (int i = 0; i < 2; i++) {
				Ray visionRay = new Ray(obsPoint, grazed[i]);
				int grazedDirection = findDirection(visionRay, cSeg.otherPoint(grazed[i]));

				double minDistance = Double.POSITIVE_INFINITY;
				int closestInd = -1;

				for (int interSegI = 0; interSegI < numSegments; interSegI++) {

					Segment interSeg = segments[interSegI];
					Point interPoint = findIntersection(visionRay, interSeg);
					// intersections at endpoints don't count
					if (interPoint == null) {
						continue;
					}
					double cDistance = Math
							.sqrt(Math.pow(interPoint.x - obsPoint.x, 2) + Math.pow(interPoint.y - obsPoint.y, 2));

					if (interSeg.hasEndpoint(interPoint)) {
						if (findDirection(visionRay, interSeg.otherPoint(interPoint)) != grazedDirection) {
							if (cDistance < minDistance) {
								minDistance = cDistance;
								closestInd = -1;
							}
						}
						continue;
					}

					// directly intersects
					if (cDistance < minDistance) {
						minDistance = cDistance;
						closestInd = interSegI;
					}
				}
				if (closestInd != -1) {
					visibleSegI.add(closestInd);
				}
			}
		}
		return visibleSegI;
	}

	/*
	 * boolean areParallel(Segment a, Segment b) { // vertical check if (a.p1.x == a.p2.x || b.p1.x == b.p2.x) { if
	 * (a.p1.x == a.p2.x && b.p1.x == b.p2.x) { return true; } else { return false; } } return (a.p1.y - a.p2.y) /
	 * (a.p1.x - a.p2.x) == (b.p1.y - b.p2.y) / (b.p1.x - b.p2.x); }
	 */

	boolean containsIntersection() {
		for (int i = 0; i < numSegments; i++) {
			for (int j = i + 1; j < numSegments; j++) {
				if (j - i > 1 && (i != 0 || j != numSegments - 1)) {
					Point interPoint = findIntersection(segments[i], segments[j]);
					if (interPoint != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// according to Topcoder tutorial
	// utilize equations of lines
	Point findIntersection(Segment seg1, Segment seg2) {
		// standard form: Ax + By = C
		// slope is -A/B
		double A1 = (seg1.p1.y - seg1.p2.y);
		double B1 = (seg1.p2.x - seg1.p1.x);
		double C1 = A1 * seg1.p1.x + B1 * seg1.p1.y;
		assert (C1 == A1 * seg1.p2.x + B1 * seg1.p2.y);

		double A2 = (seg2.p1.y - seg2.p2.y);
		double B2 = (seg2.p2.x - seg2.p1.x);
		double C2 = A2 * seg2.p1.x + B2 * seg2.p1.y;
		assert (C2 == A2 * seg2.p2.x + B2 * seg2.p2.y);

		double det = A1 * B2 - A2 * B1;

		if (det == 0) {
			return null;
		}

		double x = (B2 * C1 - B1 * C2) / det;
		double y = (A1 * C2 - A2 * C1) / det;

		// bounding box
		if (!(Math.min(seg1.p1.x, seg1.p2.x) <= x && x <= Math.max(seg1.p1.x, seg1.p2.x))) {
			return null;
		}
		if (!(Math.min(seg1.p1.y, seg1.p2.y) <= y && y <= Math.max(seg1.p1.y, seg1.p2.y))) {
			return null;
		}
		if (!(Math.min(seg2.p1.x, seg2.p2.x) <= x && x <= Math.max(seg2.p1.x, seg2.p2.x))) {
			return null;
		}
		if (!(Math.min(seg2.p1.y, seg2.p2.y) <= y && y <= Math.max(seg2.p1.y, seg2.p2.y))) {
			return null;
		}

		// all good
		return new Point(x, y);
	}

	Point findIntersection(Ray ray, Segment seg) {

		// standard form: Ax + By = C
		// slope is -A/B
		double A1 = (ray.otherPT.y - ray.endPT.y);
		double B1 = -(ray.otherPT.x - ray.endPT.x);
		double C1 = A1 * ray.endPT.x + B1 * ray.endPT.y;
		// System.out.println(A1 * ray.otherPT.x + B1 * ray.otherPT.y);
		assert (C1 == A1 * ray.otherPT.x + B1 * ray.otherPT.y);

		double A2 = (seg.p1.y - seg.p2.y);
		double B2 = (seg.p2.x - seg.p1.x);
		double C2 = A2 * seg.p1.x + B2 * seg.p1.y;
		assert (C2 == A2 * seg.p2.x + B2 * seg.p2.y);

		double det = A1 * B2 - A2 * B1;

		if (det == 0) {
			return null;
		}

		double x = (B2 * C1 - B1 * C2) / det;
		double y = (A1 * C2 - A2 * C1) / det;

		// bounding box
		if (!(Math.min(seg.p1.x, seg.p2.x) <= x && x <= Math.max(seg.p1.x, seg.p2.x))) {
			return null;
		}
		if (!(Math.min(seg.p1.y, seg.p2.y) <= y && y <= Math.max(seg.p1.y, seg.p2.y))) {
			return null;
		}

		if (!((ray.otherPT.x - ray.endPT.x) * (x - ray.endPT.x) >= 0)) {
			return null;
		}
		if (!((ray.otherPT.y - ray.endPT.y) * (y - ray.endPT.y) >= 0)) {
			return null;
		}
		return new Point(x, y);
	}

	// intersection direction
	int findDirection(Ray visionRay, Point other) {
		double crossProductZComponent = (visionRay.otherPT.x - visionRay.endPT.x) * (other.y - visionRay.otherPT.y)
				- (visionRay.otherPT.y - visionRay.endPT.y) * (other.x - visionRay.otherPT.x);
		if (crossProductZComponent == 0) {
			return 0;
		}
		return crossProductZComponent > 0 ? 1 : -1;
	}

	class Ray {
		Point endPT;
		Point otherPT;

		Ray(Point endPT, Point otherPT) {
			this.endPT = endPT;
			this.otherPT = otherPT;
		}

		public String toString() {
			return endPT.x + " " + endPT.y + " " + otherPT.x + " " + otherPT.y;
		}
	}

	class Segment/* implements Comparable<Segment> */ {
		Point p1;
		Point p2;

		Segment(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		Point otherPoint(Point p) {
			if (p.equals(p1)) {
				return p2;
			} else {
				return p1;
			}
		}

		boolean hasEndpoint(Point p) {
			return p1.equals(p) || p2.equals(p);
		}

		public boolean equals(Object other) {
			return ((Segment) other).p1.equals(p1) && ((Segment) other).p2.equals(p2);
		}

		public String toString() {
			return (int) p1.x + " " + (int) p1.y + " " + (int) p2.x + " " + (int) p2.y;
		}
	}

	// for simplicitiy's sake
	class Point/* implements Comparable<Point> */ {
		double x;
		double y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public boolean equals(Object other) {
			return ((Point) other).x == x && ((Point) other).y == y;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}
		/*
		 * public int compareTo(Point o) { if (x != o.x) { return x < o.x ? -1 : 1; } return Double.compare(y, o.y); }
		 */
	}
}
