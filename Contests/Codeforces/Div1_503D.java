import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_503D {

	public static void main(String[] args) throws IOException {
		new Div1_503D().execute();
	}

	int N;
	Pt[] pts;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		long tar = Long.parseLong(inputData.nextToken()) * 2;

		pts = new Pt[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			pts[i] = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()), i);
		}

		Arrays.sort(pts);

		int[] pos = new int[N];
		for (int i = 0; i < N; i++) {
			pos[pts[i].i] = i;
		}

		Line[] lines = new Line[N * (N - 1) / 2];

		int nLine = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				lines[nLine++] = new Line(pts[i], pts[j]);
			}
		}
		Arrays.sort(lines);

		for (Line cLine : lines) {
			Pt ptA = cLine.a;
			Pt ptB = cLine.b;

			int posA = pos[ptA.i];
			int posB = pos[ptB.i];

			pts[posA] = ptB;
			pts[posB] = ptA;

			pos[ptA.i] = posB;
			pos[ptB.i] = posA;

			Pt ret = search(cLine, tar);
			if (ret != null) {
				printer.println("Yes");
				printer.println(cLine.a.x + " " + cLine.a.y);
				printer.println(cLine.b.x + " " + cLine.b.y);
				printer.println(ret.x + " " + ret.y);
				printer.close();
				return;
			}

			ret = search(cLine, -tar);
			if (ret != null) {
				printer.println("Yes");
				printer.println(cLine.a.x + " " + cLine.a.y);
				printer.println(cLine.b.x + " " + cLine.b.y);
				printer.println(ret.x + " " + ret.y);
				printer.close();
				return;
			}
		}
		printer.println("No");
		printer.close();
	}

	Pt search(Line base, long tar) {
		int low = 0;
		int high = N - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			long cArea = tArea(base, pts[mid]);
			if (cArea == tar) {
				return pts[mid];
			} else if (cArea < tar) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return null;
	}

	long tArea(Line base, Pt ext) {
		int v1x = base.b.x - base.a.x;
		int v1y = base.b.y - base.a.y;
		int v2x = ext.x - base.a.x;
		int v2y = ext.y - base.a.y;

		return (long) v1x * v2y - (long) v1y * v2x;
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
			if (y != o.y) {
				return Integer.compare(y, o.y);
			} else {
				return Integer.compare(x, o.x);
			}
		}
	}

	class Line implements Comparable<Line> {
		Pt a;
		Pt b;
		double angle;

		Line(Pt a, Pt b) {
			this.a = a;
			this.b = b;

			if (a.y > b.y || (a.y == b.y && a.x > b.x)) {
				Pt t = a;
				a = b;
				b = t;
			}

			angle = Math.atan2(b.y - a.y, b.x - a.x);
		}

		public int compareTo(Line o) {
			return Double.compare(angle, o.angle);
		}
	}
}
