import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class SeeingTheBoundary {

	public static void main(String[] args) throws IOException {
		new SeeingTheBoundary().execute();
	}

	static Pt fP;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int r = Integer.parseInt(inputData.nextToken());
		inputData = new StringTokenizer(reader.readLine());
		fP = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));

		ArrayList<Line> lines = new ArrayList<Line>();

		for (int i = 0; i < r; i++) {
			int nP = Integer.parseInt(reader.readLine());
			inputData = new StringTokenizer(reader.readLine());
			Pt pP = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
			Pt p1 = pP;

			for (int j = 1; j < nP; j++) {
				inputData = new StringTokenizer(reader.readLine());
				Pt cP = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
				if (orient(fP, pP, cP) > 0) {
					lines.add(new Line(pP, cP));
				} else {
					lines.add(new Line(cP, pP));
				}
				pP = cP;
			}
			if (orient(fP, pP, p1) > 0) {
				lines.add(new Line(pP, p1));
			} else {
				lines.add(new Line(p1, pP));
			}
		}

		Line[] posts = new Line[4 * n];
		int nPInd = 0;
		for (int i = 0; i < n; i++) {
			posts[nPInd++] = new Line(new Pt(i, 0));
			posts[nPInd++] = new Line(new Pt(n, i));

			posts[nPInd++] = new Line(new Pt(n - i, n));
			posts[nPInd++] = new Line(new Pt(0, n - i));
		}
		Arrays.sort(posts, ByA);

		Collections.sort(lines, ByA);
		int nLInd = 0;

		TreeSet<Line> active = new TreeSet<>();

		int ans = 0;
		for (int cRep = 0; cRep < 2; cRep++) {
			// System.out.println("fRep");
			for (Line cPost : posts) {
				while (lines.get(nLInd).aA <= cPost.aA) {
					lines.get(nLInd).aA += 2 * Math.PI;
					active.add(lines.get(nLInd));
					nLInd++;
					if (nLInd == lines.size()) {
						nLInd = 0;
					}
				}
				while (!active.isEmpty() && active.first().aB < cPost.aA) {
					Line tR = active.first();
					active.remove(tR);
					tR.aB += 2 * Math.PI;
				}

				if (cRep != 0) {
					if (active.isEmpty()) {
						ans++;
					}
				}
			}
			for (int i = 0; i < posts.length; i++) {
				posts[i].aA += 2 * Math.PI;
			}
		}
		printer.println(ans);
		printer.close();
	}

	// orient < 0 : cw
	// orient > 0: ccw
	public static long orient(Pt a, Pt b, Pt c) {
		return (long) (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
	}

	class Pt {
		long x;
		long y;

		Pt(long x, long y) {
			this.x = x;
			this.y = y;
		}
	}

	class Line implements Comparable<Line> {
		Pt a;
		Pt b;
		double aA;
		double aB;

		Line(Pt a, Pt b) {
			this.a = a;
			this.b = b;
			aA = Math.atan2(a.y - fP.y, a.x - fP.x);
			aB = Math.atan2(b.y - fP.y, b.x - fP.x);
			if (aA < 0) {
				aA += 2 * Math.PI;
			}
			while (aB < aA) {
				aB += 2 * Math.PI;
			}
		}

		Line(Pt a) {
			this.a = a;
			aA = Math.atan2(a.y - fP.y, a.x - fP.x);
			if (aA < 0) {
				aA += 2 * Math.PI;
			}
		}

		public int compareTo(Line o) {
			int cVal = Double.compare(aB, o.aB);
			return cVal != 0 ? cVal : Double.compare(aA, o.aA);
		}
	}

	Comparator<Line> ByA = new Comparator<Line>() {
		public int compare(Line l1, Line l2) {
			return Double.compare(l1.aA, l2.aA);
		}
	};
}
