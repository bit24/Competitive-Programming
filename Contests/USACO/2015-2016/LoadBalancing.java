import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class LoadBalancing {

	public static void main(String[] args) throws IOException {
		new LoadBalancing().execute();
	}

	int numP;
	Point[] p;

	BIT leftSide;
	BIT rightSide;

	int finalY;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("balancing.in"));
		numP = Integer.parseInt(reader.readLine());
		p = new Point[numP];
		for (int i = 0; i < numP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			p[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		reader.close();

		Arrays.sort(p, new BY_Y());

		int lastY = p[0].y;
		finalY = 1;
		for (int i = 0; i < numP; i++) {
			if (p[i].y == lastY) {
				p[i].y = finalY;
			} else {
				lastY = p[i].y;
				p[i].y = ++finalY;
			}
		}

		Arrays.sort(p);

		leftSide = new BIT(finalY);
		rightSide = new BIT(finalY);

		int ans = Integer.MAX_VALUE;

		for (int i = 0; i < numP; i++) {
			leftSide.update(p[i].y, 1);
		}

		for (int i = 0; i < numP; i++) {
			int cX = p[i].x;

			leftSide.update(p[i].y, -1);
			rightSide.update(p[i].y, 1);
			while (i + 1 < numP && p[i + 1].x == cX) {
				leftSide.update(p[i + 1].y, -1);
				rightSide.update(p[i + 1].y, 1);
				i++;
			}

			int cost = ternarySearch(1, finalY);
			if (cost < ans) {
				ans = cost;
			}
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("balancing.out")));
		printer.println(ans);
		printer.close();
	}

	// find min in range
	int ternarySearch(int rangeL, int rangeR) {
		if (rangeR - rangeL <= 1) {
			return Math.min(function(rangeL), function(rangeR));
		}

		int ind1 = (2 * rangeL + rangeR) / 3;
		int ind2 = (rangeL + 2 * rangeR) / 3;

		assert (ind1 != rangeL || ind2 != rangeR);

		int value1 = function(ind1);
		int value2 = function(ind2);
		if (value1 < value2) {
			return ternarySearch(rangeL, ind2 - 1);
		}
		if (value1 > value2) {
			return ternarySearch(ind1 + 1, rangeR);
		}
		return ternarySearch(ind1, ind2);
	}

	int function(int maxY) {
		return Math.max(Math.max(leftSide.query(maxY), leftSide.query(maxY + 1, finalY)),
				Math.max(rightSide.query(maxY), rightSide.query(maxY + 1, finalY)));
	}

	class BIT {
		// one indexed as operations on index 0 will not work

		int[] BIT;
		int numElements;

		BIT(int numElements) {
			this.numElements = numElements;
			BIT = new int[numElements + 1];
		}

		void update(int index, int delta) {
			while (index <= numElements) {
				BIT[index] += delta;
				index += (index & -index);
			}
		}

		int query(int index) {
			int result = 0;
			while (index > 0) {
				result += BIT[index];
				index -= (index & -index);
			}
			return result;
		}

		int query(int left, int right) {
			if (left < 1 || right > numElements || left > right) {
				return 0;
			}
			return left != 1 ? query(right) - query(left - 1) : query(right);
		}
	}

	class BY_Y implements Comparator<Point> {

		public int compare(Point p1, Point p2) {
			if (p1.y != p2.y) {
				return p1.y < p2.y ? -1 : 1;
			}
			return Integer.compare(p1.x, p2.x);
		}
	}

	class Point implements Comparable<Point> {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point o) {
			if (x != o.x) {
				return x < o.x ? -1 : 1;
			}
			return Integer.compare(y, o.y);
		}
	}
}
