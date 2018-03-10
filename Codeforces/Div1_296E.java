import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_296E {

	public static void main(String[] args) throws IOException {
		new Div1_296E().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nL = Integer.parseInt(reader.readLine());
		Line[] lines = new Line[nL];
		for (int i = 0; i < nL; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			lines[i] = new Line(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()));
		}

		Arrays.sort(lines);

		double res = 0;
		for (int bI = 0; bI < nL; bI++) {
			Vector pSum = new Vector(0, 0);
			for (int dI = 1; dI < nL; dI++) {
				Vector cInter = inter(lines[bI], lines[(bI + dI) % nL]);
				res += crossMag(pSum, cInter);
				pSum.pE(cInter);
			}
		}
		res *= 3.0 / ((long) nL * (nL - 1) * (nL - 2));
		printer.println(res);
		printer.close();
	}

	class Line implements Comparable<Line> {
		double A;
		double B;
		double C;
		double ang;

		Line(int A, int B, int C) {
			if (A < 0) {
				A = -A;
				B = -B;
				C = -C;
			}
			this.A = A;
			this.B = B;
			this.C = C;
			ang = Math.atan2(-B, A);
		}

		public int compareTo(Line o) {
			return -Double.compare(ang, o.ang);
		}
	}

	// Cramer's rule
	Vector inter(Line l1, Line l2) {
		double det = l1.A * l2.B - l1.B * l2.A;
		return new Vector((l1.C * l2.B - l1.B * l2.C) / det, (l1.A * l2.C - l1.C * l2.A) / det);
	}

	double crossMag(Vector v1, Vector v2) {
		return v1.x * v2.y - v1.y * v2.x;
	}

	class Vector {
		double x;
		double y;

		Vector(double x, double y) {
			this.x = x;
			this.y = y;
		}

		void pE(Vector addend) {
			x += addend.x;
			y += addend.y;
		}
	}
}
