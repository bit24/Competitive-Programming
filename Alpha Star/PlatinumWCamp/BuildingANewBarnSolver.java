import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class BuildingANewBarnSolver {

	public static void main(String[] args) throws IOException {
		new BuildingANewBarnSolver().execute();
	}

	Point[] points;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numP = Integer.parseInt(reader.readLine());
		Point[] pointsBY_X = new Point[numP];
		points = pointsBY_X;
		for (int i = 0; i < numP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			pointsBY_X[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		reader.close();

		// even
		if ((numP & 1) == 0) {
			Arrays.sort(pointsBY_X, new BY_X());
			int x = pointsBY_X[numP / 2].x;

			Point[] pointsBY_Y = Arrays.copyOf(pointsBY_X, numP);
			Arrays.sort(pointsBY_Y, new BY_Y());
			int y = pointsBY_Y[numP / 2].y;
			int distance = 0;

			for (Point p : pointsBY_X) {
				distance += Math.abs(x - p.x) + Math.abs(y - p.y);
			}

			// System.out.println(Math.abs(pointsBY_X[numP / 2].x - pointsBY_X[numP / 2 - 1].x));
			// System.out.println(Math.abs(pointsBY_Y[numP / 2].y - pointsBY_Y[numP / 2 - 1].y));

			int left = pointsBY_X[numP / 2 - 1].x;
			int right = pointsBY_X[numP / 2].x;
			int top = pointsBY_Y[numP / 2].y;
			int bottom = pointsBY_Y[numP / 2 - 1].y;

			int count = (right - left + 1) * (top - bottom + 1);

			for (Point p : pointsBY_X) {
				if (left <= p.x && p.x <= right && bottom <= p.y && p.y <= top) {
					count--;
				}
			}

			System.out.println(distance + " " + count);
		} else {
			Arrays.sort(pointsBY_X, new BY_X());
			int x = pointsBY_X[numP / 2].x;

			Point[] pointsBY_Y = Arrays.copyOf(pointsBY_X, numP);
			Arrays.sort(pointsBY_Y, new BY_Y());
			int y = pointsBY_Y[numP / 2].y;

			int min = distance(new Point(x - 1, y));
			int count = 1;

			int cost = distance(new Point(x, y - 1));
			if (cost < min) {
				min = cost;
				count = 1;
			} else if (cost == min) {
				count++;
			}

			cost = distance(new Point(x + 1, y));
			if (cost < min) {
				min = cost;
				count = 1;
			} else if (cost == min) {
				count++;
			}

			cost = distance(new Point(x, y + 1));
			if (cost < min) {
				min = cost;
				count = 1;
			} else if (cost == min) {
				count++;
			}
			System.out.println(min + " " + count);
		}
	}

	int distance(Point o) {
		int sum = 0;
		for (Point p : points) {
			sum += Math.abs(p.x - o.x) + Math.abs(p.y - o.y);
		}
		return sum;
	}

	class BY_X implements Comparator<Point> {
		public int compare(Point c1, Point c2) {
			return Integer.compare(c1.x, c2.x);
		}
	}

	class BY_Y implements Comparator<Point> {
		public int compare(Point c1, Point c2) {
			return Integer.compare(c1.y, c2.y);
		}
	}

	class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

}
