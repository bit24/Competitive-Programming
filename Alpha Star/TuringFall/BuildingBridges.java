import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class BuildingBridges {

	public static void main(String[] args) throws IOException {
		new BuildingBridges().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nE = Integer.parseInt(reader.readLine());
		long[] ht = new long[nE];
		long[] cost = new long[nE];
		long ansD = 0;

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			ht[i] = Integer.parseInt(inputData.nextToken());
		}
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			int cCost = Integer.parseInt(inputData.nextToken());
			cost[i] = -cCost;
			ansD += cCost;
		}

		add(new Line(-2 * ht[0], cost[0] + ht[0] * ht[0]));
		for (int i = 1; i < nE - 1; i++) {
			long best = query(ht[i]);
			best += cost[i] + 2 * ht[i] * ht[i];
			add(new Line(-2 * ht[i], best));
		}

		long ans = query(ht[nE - 1]);
		ans += cost[nE - 1] + ht[nE - 1] * ht[nE - 1];
		ans += ansD;
		printer.println(ans);
		printer.close();
	}

	TreeSet<Line> hull = new TreeSet<Line>();
	TreeMap<Double, Line> qSet = new TreeMap<Double, Line>();

	long query(long pt) {
		Line dLine = qSet.floorEntry((double) pt).getValue();
		return dLine.slope * pt + dLine.yInt;
	}

	void add(Line addend) {
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
				qSet.remove(left.relev);
				left = tLeft;
				tLeft = hull.lower(left);
			}
			addend.relev = xInt(left, addend);
		} else {
			addend.relev = Double.NEGATIVE_INFINITY;
		}
		if (right != null) {
			if (addend.slope == right.slope) {
				hull.remove(right);
				qSet.remove(right.relev);
				right = hull.higher(right);
			}
			if (right != null) {
				Line tRight = hull.higher(right);
				while (tRight != null && !relevant(addend, right, tRight)) {
					hull.remove(right);
					qSet.remove(right.relev);
					right = tRight;
					tRight = hull.higher(right);
				}
				qSet.remove(right.relev);
				right.relev = xInt(addend, right);
				qSet.put(right.relev, right);
			}
		}
		hull.add(addend);
		qSet.put(addend.relev, addend);
	}

	// checks if b is relevant; has to be sorted according to comparison function and slopes cannot be equal
	boolean relevant(Line a, Line b, Line c) {
		return xInt(a, b) < xInt(a, c);
	}

	double xInt(Line a, Line b) {
		return (double) (b.yInt - a.yInt) / (a.slope - b.slope);
	}

	class Line implements Comparable<Line> {
		long slope;
		long yInt;
		double relev;

		public int compareTo(Line o) {
			return slope > o.slope ? -1 : slope == o.slope ? (yInt < o.yInt ? -1 : yInt == o.yInt ? 0 : 1) : 1;
		}

		Line(long slope, long yInt) {
			this.slope = slope;
			this.yInt = yInt;
		}
	}
}
