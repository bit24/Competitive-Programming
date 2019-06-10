import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CowRectangles {

	public static void main(String[] args) throws IOException {
		new CowRectangles().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowrect.in"));

		int numP = Integer.parseInt(reader.readLine());

		Point[] points = new Point[numP];
		for (int i = 0; i < numP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					inputData.nextToken().equals("H"));
		}
		reader.close();
		Arrays.sort(points, ByY);

		int maxP = 1;
		int minA = 0;

		for (int bottom = 0; bottom < numP; bottom++) {

			if (points[bottom].good) {
				// Exclusive
				int lBound = Integer.MIN_VALUE;
				int rBound = Integer.MAX_VALUE;

				TreeSet<Point> currentWindow = new TreeSet<Point>(ByX);
				currentWindow.add(points[bottom]);

				for (int next = 0; next < numP;) {
					if (points[next].y < points[bottom].y || next == bottom) {
						next++;
						continue;
					}

					int currentY = points[next].y;
					while (next < numP && points[next].y == currentY) {

						if (points[next].good && lBound < points[next].x && points[next].x < rBound) {
							currentWindow.add(points[next]);
						} else {
							// left bound
							if (points[next].x <= points[bottom].x && lBound < points[next].x) {
								lBound = points[next].x;

								while (!currentWindow.isEmpty() && currentWindow.first().x <= lBound) {
									currentWindow.remove(currentWindow.first());
								}
							}
							// right bound
							else if (points[bottom].x <= points[next].x && points[next].x < rBound) {
								rBound = points[next].x;

								while (!currentWindow.isEmpty() && rBound <= currentWindow.last().x) {
									currentWindow.remove(currentWindow.last());
								}
							}
						}
						next++;
					}

					int cPoints = currentWindow.size();
					if (cPoints == 0) {
						continue;
					}

					int area = (currentWindow.last().x - currentWindow.first().x) * (currentY - points[bottom].y);

					if (cPoints > maxP) {
						maxP = cPoints;
						minA = area;
					} else if (cPoints == maxP && area < minA) {
						minA = area;
					}
				}

			}
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cowrect.out")));
		printer.println(maxP);
		printer.println(minA);
		printer.close();
	}

	Comparator<Point> ByX = new Comparator<Point>() {
		public int compare(Point o1, Point o2) {
			return o1.x != o2.x ? Integer.compare(o1.x, o2.x) : Integer.compare(o1.y, o2.y);
		}
	};

	Comparator<Point> ByY = new Comparator<Point>() {
		public int compare(Point o1, Point o2) {
			return o1.y != o2.y ? Integer.compare(o1.y, o2.y) : Integer.compare(o1.x, o2.x);
		}
	};

	class Point {
		int x;
		int y;
		boolean good;

		Point(int x, int y, boolean good) {
			this.x = x;
			this.y = y;
			this.good = good;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
}