import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class DivCmb_505F {

	public static void main(String[] args) throws IOException {
		new DivCmb_505F().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nP = Integer.parseInt(reader.readLine());
		Pt[] pts = new Pt[nP];

		for (int i = 0; i < nP; i++) {
			String nLine = reader.readLine();
			int space = nLine.indexOf(' ');
			pts[i] = new Pt(Integer.parseInt(nLine.substring(0, space)), Integer.parseInt(nLine.substring(space + 1)),
					i);
		}

		Line[] lines = new Line[nP * (nP - 1) / 2];

		Arrays.sort(pts);

		int nL = 0;
		for (int i = 0; i < nP; i++) {
			for (int j = i + 1; j < nP; j++) {
				lines[nL++] = new Line(pts[i], pts[j]);
			}
		}

		Arrays.sort(lines);
		int[] pos = new int[nP];
		for (int i = 0; i < nP; i++) {
			pos[pts[i].i] = i;
		}

		long ans = 0;

		for (Line cLine : lines) {
			Pt p1 = cLine.p1;
			Pt p2 = cLine.p2;

			int p1p = pos[p1.i];
			int p2p = pos[p2.i];

			pts[p1p] = p2;
			pts[p2p] = p1;

			pos[p1.i] = p2p;
			pos[p2.i] = p1p;

			int min = p1p < p2p ? p1p : p2p;
			long below = min;
			long above = nP - 2 - min;

			if (below >= 2 && above >= 2) {
				ans += below * (below - 1) * above * (above - 1) >> 2;
			}
		}
		printer.println(ans);
		printer.close();
	}

	class Pt implements Comparable<Pt> {
		int x;
		int y;
		int i;

		Pt(int x, int y, int i) {
			this.x = x;
			this.y = y;
			this.i = i;
		}

		public int compareTo(Pt o) {
			int c1 = Integer.compare(y, o.y);
			if (c1 == 0) {
				return Integer.compare(x, o.x);
			}
			return c1;
		}
	}

	class Line implements Comparable<Line> {
		Pt p1;
		Pt p2;
		double ang;

		Line(Pt p1, Pt p2) {
			this.p1 = p1;
			this.p2 = p2;
			int dY = p2.y - p1.y;
			int dX = p2.x - p1.x;
			if (dY < 0) {
				dY = -dY;
				dX = -dX;
			} else if (dY == 0 && dX < 0) {
				dX = -dX;
			}
			ang = Math.atan2(dY, dX);
			if (ang < 0) {
				ang += Math.PI;
			}
		}

		public int compareTo(Line o) {
			double ang2 = o.ang;
			if (ang < ang2) {
				return -1;
			}
			if (ang > ang2) {
				return 1;
			}
			return 0;
		}
	}
}