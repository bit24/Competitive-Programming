import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LotsOfTrianglesSolver {

	public static void main(String[] args) throws IOException {
		new LotsOfTrianglesSolver().execute();
	}

	int numP;

	Point[] points;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("triangles.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("triangles.out")));

		numP = Integer.parseInt(reader.readLine());

		points = new Point[numP];

		for (int i = 0; i < numP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		reader.close();

		Arrays.sort(points);

		int[][] numB = new int[numP][numP];

		for (int i = 0; i < numP; i++) {
			for (int j = i + 1; j < numP; j++) {
				for (int k = 0; k < numP; k++) {
					if (isB(points[i], points[j], points[k])) {
						numB[i][j]++;
					}
				}
			}
		}

		int[] ans = new int[numP - 2];

		for (int i = 0; i < numP; i++) {
			for (int j = i + 1; j < numP; j++) {
				for (int k = j + 1; k < numP; k++) {
					int value = Math.abs(numB[i][j] + numB[j][k] - numB[i][k]);
					// subtract that one last point if necessary
					if (isB(points[i], points[k], points[j])) {
						value--;
					}
					if (isB(points[k], points[j], points[i])) {
						value--;
					}
					ans[value]++;
				}
			}
		}

		for (int i = 0; i <= numP - 3; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	// determines if c is between and below a and b
	// left inclusive, right EXCLUSIVE
	boolean isB(Point a, Point b, Point c) {
		if (a.x > b.x) {
			return isB(b, a, c);
		}
		if (!(a.x <= c.x && c.x < b.x)) {
			return false;
		}
		return isCW(a, b, c);
	}

	boolean isCW(Point a, Point b, Point c) {
		long v1x = b.x - a.x;
		long v1y = b.y - a.y;

		long v2x = c.x - a.x;
		long v2y = c.y - a.y;

		return v1x * v2y - v1y * v2x < 0;
	}

	class Point implements Comparable<Point> {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point o) {
			return x != o.x ? Integer.compare(x, o.x) : Integer.compare(y, o.y);
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
}