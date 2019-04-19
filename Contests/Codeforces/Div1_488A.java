import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_488A {

	static double[][] pt1;
	static double[][] pt2;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		pt1 = new double[4][2];
		for (int i = 0; i < 4; i++) {
			pt1[i][0] = Integer.parseInt(inputData.nextToken());
			pt1[i][1] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		pt2 = new double[4][2];
		for (int i = 0; i < 4; i++) {
			pt2[i][0] = Integer.parseInt(inputData.nextToken());
			pt2[i][1] = Integer.parseInt(inputData.nextToken());
		}

		for (int i = 0; i < 4; i++) {
			if (inside(pt1[i], pt2)) {
				printer.println("YES");
				printer.close();
				return;
			}
			if (inside(pt2[i], pt1)) {
				printer.println("YES");
				printer.close();
				return;
			}
		}

		long c1X = 0;
		long c1Y = 0;
		long c2X = 0;
		long c2Y = 0;

		for (int i = 0; i < 4; i++) {
			c1X += pt1[i][0];
			c1Y += pt1[i][1];
			c2X += pt2[i][0];
			c2Y += pt2[i][1];
		}

		c1X /= 4;
		c1Y /= 4;
		c2X /= 4;
		c2Y /= 4;
		
		if (inside(new double[] { c1X, c1Y }, pt2)) {
			printer.println("YES");
			printer.close();
			return;
		}

		if (inside(new double[] { c2X, c2Y }, pt1)) {
			printer.println("YES");
			printer.close();
			return;
		}

		printer.println("NO");
		printer.close();
	}

	static boolean inside(double[] pt, double[][] pts) {
		long mag = -2;

		for (int i = 0; i < 4; i++) {
			double vX1 = pts[(i + 1) % 4][0] - pts[i][0];
			double vY1 = pts[(i + 1) % 4][1] - pts[i][1];
			double vX2 = pt[0] - pts[i][0];
			double vY2 = pt[1] - pts[i][1];
			double cCross = cross(vX1, vY1, vX2, vY2);
			if (cCross == 0) {
				continue;
			}
			if (mag == -2) {
				mag = cCross > 0 ? 1 : -1;
			} else {
				if ((cCross > 0 ? 1 : -1) != mag) {
					return false;
				}
			}
		}
		return true;
	}

	static double cross(double a, double b, double c, double d) {
		return a * d - b * c;
	}
}
