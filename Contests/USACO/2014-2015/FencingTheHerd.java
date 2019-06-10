import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class FencingTheHerd {

	public static void main(String[] args) throws IOException {
		new FencingTheHerd().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fencing.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fencing.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nO = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());
		long lM = Long.MAX_VALUE;
		long rM = Long.MIN_VALUE;
		for (int i = 0; i < nO; i++) {
			inputData = new StringTokenizer(reader.readLine());
			long x = Integer.parseInt(inputData.nextToken());
			long y = Integer.parseInt(inputData.nextToken());
			add(minHull, new Line(-x, y));
			add(maxHull, new Line(-x, y));
			lM = Math.min(lM, x);
			rM = Math.max(rM, x);
		}

		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("1")) {
				long x = Integer.parseInt(inputData.nextToken());
				long y = Integer.parseInt(inputData.nextToken());
				add(minHull, new Line(-x, y));
				add(maxHull, new Line(-x, y));
				lM = Math.min(lM, x);
				rM = Math.max(rM, x);
			} else {
				long A = Integer.parseInt(inputData.nextToken());
				long B = Integer.parseInt(inputData.nextToken());
				long C = Long.parseLong(inputData.nextToken());
				if (B == 0) {
					double x = (double) C / A;
					if (lM <= x && x <= rM) {
						printer.println("NO");
					} else {
						printer.println("YES");
					}
				} else {
					Frac slope = new Frac(-A, B);
					Line minQ = query(minHull, slope);
					Line maxQ = query(maxHull, slope);
					if (Long.signum(-minQ.slope * A + minQ.yInt * B - C)
							* Long.signum(-maxQ.slope * A + maxQ.yInt * B - C) <= 0) {
						printer.println("NO");
					} else {
						printer.println("YES");
					}
				}
			}
		}
		reader.close();
		printer.close();
	}

	TreeSet<Line> minHull = new TreeSet<>(MINLINE);

	TreeSet<Line> maxHull = new TreeSet<>(MAXLINE);

	Line query(TreeSet<Line> hull, Frac pt) {
		return hull.floor(new Line(pt));
	}

	void add(TreeSet<Line> hull, Line addend) {
		Line left = hull.floor(addend);
		Line right = hull.higher(addend);
		if (left != null) {
			if (left.slope == addend.slope
					|| (right != null && addend.slope != right.slope && !relevant(left, addend, right))) {
				return;
			}
			Line tLeft = hull.lower(left);
			while (tLeft != null && !relevant(tLeft, left, addend)) {
				hull.remove(left);
				left = tLeft;
				tLeft = hull.lower(left);
			}
			addend.relev = xInt(left, addend);
		} else {
			addend.relev = new Frac(Long.MIN_VALUE, 1);
		}
		if (right != null) {
			if (addend.slope == right.slope) {
				hull.remove(right);
				right = hull.higher(right);
			}
			if (right != null) {
				Line tRight = hull.higher(right);
				while (tRight != null && !relevant(addend, right, tRight)) {
					hull.remove(right);
					right = tRight;
					tRight = hull.higher(right);
				}
				right.relev = xInt(addend, right);
			}
		}
		hull.add(addend);
	}

	boolean relevant(Line a, Line b, Line c) {
		return xInt(a, b).compareTo(xInt(a, c)) == -1;
	}

	Frac xInt(Line a, Line b) {
		return new Frac(b.yInt - a.yInt, a.slope - b.slope);
	}

	static final Comparator<Line> MAXLINE = new Comparator<Line>() {
		public int compare(Line l1, Line l2) {
			if (l1.rOnly || l2.rOnly) {
				return l1.relev.compareTo(l2.relev);
			}
			return l1.slope < l2.slope ? -1
					: l1.slope == l2.slope ? (l1.yInt > l2.yInt ? -1 : l1.yInt == l2.yInt ? 0 : 1) : 1;
		}
	};

	static final Comparator<Line> MINLINE = new Comparator<Line>() {
		public int compare(Line l1, Line l2) {
			if (l1.rOnly || l2.rOnly) {
				return l1.relev.compareTo(l2.relev);
			}
			return l1.slope > l2.slope ? -1
					: l1.slope == l2.slope ? (l1.yInt < l2.yInt ? -1 : l1.yInt == l2.yInt ? 0 : 1) : 1;
		}
	};

	class Line {
		long slope;
		long yInt;
		Frac relev;
		boolean rOnly;

		Line(long slope, long yInt) {
			this.slope = slope;
			this.yInt = yInt;
		}

		Line(Frac relev) {
			this.relev = relev;
			rOnly = true;
		}
	}

	class Frac implements Comparable<Frac> {
		long num;
		long den;

		Frac(long num, long den) {
			this.num = num;
			this.den = den;
		}

		public int compareTo(Frac o) {
			if (num == Long.MIN_VALUE) {
				return o.num == Long.MIN_VALUE ? 0 : -1;
			}
			if (o.num == Long.MIN_VALUE) {
				return 1;
			}
			long a = num * o.den;
			long b = den * o.num;
			if (den > 0 ? (o.den > 0) : (o.den < 0)) {
				return (a < b) ? -1 : ((a == b) ? 0 : 1);
			} else {
				return (a > b) ? -1 : ((a == b) ? 0 : 1);
			}
		}
	}
}
