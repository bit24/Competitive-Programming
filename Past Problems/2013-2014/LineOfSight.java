import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class LineOfSight {

	public static void main(String[] args) throws IOException {
		new LineOfSight().execute();
	}

	int nP;
	int R;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("sight.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("sight.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nP = Integer.parseInt(inputData.nextToken());
		R = Integer.parseInt(inputData.nextToken());

		Point[] points = new Point[nP];
		for (int i = 0; i < nP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		reader.close();
		Arrays.sort(points, cBAngle);

		PriorityQueue<Double> remQ = new PriorityQueue<Double>();
		for (int i = 0; i < nP; i++) {
			while (!remQ.isEmpty() && remQ.peek() < points[i].bAngle) {
				remQ.remove();
			}
			remQ.add(points[i].fAngle);
			points[i].fAngle += Math.PI * 2;
			points[i].bAngle += Math.PI * 2;
		}

		long ans = 0;
		for (int i = 0; i < nP; i++) {
			while (!remQ.isEmpty() && remQ.peek() < points[i].bAngle) {
				remQ.remove();
			}

			ans += remQ.size();
			remQ.add(points[i].fAngle);
		}

		printer.println(ans);
		printer.close();
	}

	class Point {
		double x;
		double y;

		double bAngle;
		double fAngle;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
			double pAngle = Math.atan2(y, x);
			if (pAngle < 0) {
				pAngle += Math.PI * 2;
			}
			double dAngle = Math.acos(R / Math.sqrt(x * x + y * y));
			bAngle = pAngle - dAngle;
			if (bAngle < 0) {
				bAngle += Math.PI * 2;
			}
			fAngle = bAngle + 2 * dAngle;
		}
	}

	Comparator<Point> cBAngle = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			return Double.compare(p1.bAngle, p2.bAngle);
		}
	};
}
