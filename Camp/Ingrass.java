import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class Ingrass {

	public static void main(String[] args) throws IOException {
		new Ingrass().main();
	}

	Random rng = new Random();

	int N;
	Pt[] pts;

	PrintWriter printer;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());
		pts = new Pt[N];

		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			pts[i] = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()), i);
		}

		Pt[] cHull = solve(pts);

		boolean[] onHull = new boolean[N];
		for (Pt p : cHull) {
			onHull[p.i] = true;
		}

		double[] angles = new double[cHull.length - 2];

		for (int i = 1; i < cHull.length - 1; i++) {
			angles[i - 1] = Math.atan2(cHull[i].y - cHull[0].y, cHull[i].x - cHull[0].x);
		}

		ArrayList<Pt>[] sectors = new ArrayList[angles.length];
		for (int i = 0; i < sectors.length; i++) {
			sectors[i] = new ArrayList<>();
		}

		for (Pt p : pts) {
			if (!onHull[p.i]) {
				double angle = Math.atan2(p.y - cHull[0].y, p.x - cHull[0].x);
				int cSec = binSearch(angle, angles);
				sectors[cSec].add(p);
			}
		}

		printer.println((3 * N - 2 * cHull.length - 2) + " " + (3 * N - cHull.length - 3));

		for (int i = 0; i < cHull.length; i++) {
			printer.println((cHull[i].i + 1) + " " + (cHull[(i + 1) % cHull.length].i + 1));
		}

		for (int i = 2; i < cHull.length - 1; i++) {
			printer.println((cHull[0].i + 1) + " " + (cHull[i].i + 1));
		}

		for (int i = 0; i < sectors.length; i++) {
			recurse(cHull[0], cHull[i + 1], cHull[i + 2], sectors[i]);
		}
		printer.close();
	}

	void recurse(Pt a, Pt b, Pt c, ArrayList<Pt> pts) {
		if (pts.isEmpty()) {
			return;
		}

		int sI = rng.nextInt(pts.size());
		Pt split = pts.get(sI);

		printer.println((split.i + 1) + " " + (a.i + 1));
		printer.println((split.i + 1) + " " + (b.i + 1));
		printer.println((split.i + 1) + " " + (c.i + 1));

		ArrayList<Pt> g1 = new ArrayList<>();
		ArrayList<Pt> g2 = new ArrayList<>();
		ArrayList<Pt> g3 = new ArrayList<>();

		for (int i = 0; i < pts.size(); i++) {
			if (i == sI) {
				continue;
			}
			Pt cP = pts.get(i);
			if (inside(a, b, split, cP)) {
				g1.add(cP);
			} else if (inside(b, c, split, cP)) {
				g2.add(cP);
			} else {
				g3.add(cP);
			}
		}
		recurse(a, b, split, g1);
		recurse(b, c, split, g2);
		recurse(c, a, split, g3);
	}

	boolean inside(Pt a, Pt b, Pt c, Pt t) {
		if (orient(a, b, t) != orient(a, b, c)) {
			return false;
		}
		if (orient(b, c, t) != orient(b, c, a)) {
			return false;
		}
		if (orient(c, a, t) != orient(c, a, b)) {
			return false;
		}
		return true;
	}

	static int binSearch(double ang, double[] angles) {
		int low = 0;
		int high = angles.length - 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (ang > angles[mid]) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	Pt[] solve(Pt[] pts) {
		Arrays.sort(pts);

		Pt[] cHull = new Pt[2 * pts.length];
		int hS = 0;

		for (int i = 0; i < pts.length; i++) {
			while (hS >= 2 && orient(cHull[hS - 2], cHull[hS - 1], pts[i]) <= 0) {
				hS--;
			}
			cHull[hS++] = pts[i];
		}
		int lHS = hS;

		for (int i = pts.length - 2; i >= 0; i--) {
			while (hS > lHS && orient(cHull[hS - 2], cHull[hS - 1], pts[i]) <= 0) {
				hS--;
			}
			cHull[hS++] = pts[i];
		}

		Pt[] aCHull = new Pt[hS - 1];

		for (int i = 0; i < hS - 1; i++) {
			aCHull[i] = cHull[i];
		}
		return aCHull;
	}

	public static long orient(Pt a, Pt b, Pt c) {
		return Long.signum((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x));
	}

	class Pt implements Comparable<Pt> {
		long x;
		long y;
		int i;

		Pt(int x, int y, int i) {
			this.x = x;
			this.y = y;
			this.i = i;
		}

		public int compareTo(Pt o) {
			return x < o.x ? -1 : x == o.x ? (y < o.y ? -1 : y == o.y ? 0 : 1) : 1;
		}
	}
}
