import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_388B {

	public static void main(String[] args) throws IOException {
		new Div2_388B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		Point[] points = new Point[3];

		for (int i = 0; i < 3; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}

		ArrayList<Point> newPoints = new ArrayList<Point>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == j) {
					continue;
				}
				for (int k = 0; k < 3; k++) {
					if (i == k || j == k) {
						continue;
					}
					Point newPoint = points[i].add(points[j].subtract(points[k]));
					if (!newPoints.contains(newPoint)) {
						newPoints.add(newPoint);
					}
				}
			}
		}
		printer.println(newPoints.size());
		for (Point p : newPoints) {
			printer.println(p.x + " " + p.y);
		}
		printer.close();
	}

	class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean equals(Object other) {
			return x == ((Point) other).x && y == ((Point) other).y;
		}

		Point subtract(Point subtrahend) {
			return new Point(x - subtrahend.x, y - subtrahend.y);
		}

		Point add(Point addend) {
			return new Point(x + addend.x, y + addend.y);
		}
	}

}
