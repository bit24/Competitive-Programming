import java.util.TreeSet;

public class ConvexHullT {

	public static void main(String[] args) {
		String a = new String();
		String b = "";
		assert (a != b);
		TreeSet<String> tSet = new TreeSet<String>();
		tSet.add(a);
		assert (tSet.floor(b) == a);
	}

	// used for minimization

	TreeSet<Line> hull = new TreeSet<Line>();

	long query(long pt) {
		Line dLine = hull.floor(new Line((double) pt));
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
		boolean rOnly;

		public int compareTo(Line o) {
			if (rOnly || o.rOnly) {
				return Double.compare(relev, o.relev);
			}
			return slope > o.slope ? -1 : slope == o.slope ? (yInt < o.yInt ? -1 : yInt == o.yInt ? 0 : 1) : 1;
		}

		Line(long slope, long yInt) {
			this.slope = slope;
			this.yInt = yInt;
		}

		Line(double relev) {
			this.relev = relev;
			rOnly = true;
		}
	}
}
