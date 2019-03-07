import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BDCowDating {

	final Custom ZERO = new Custom(0);
	final Custom ONE = new Custom(1);
	final Custom MILLION = new Custom(1_000_000);

	public static void main(String[] args) throws IOException {
		new BDCowDating().main();
	}

	int N;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowdate.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cowdate.out")));
		N = Integer.parseInt(reader.readLine());
		Custom[] p = new Custom[N + 1];

		for (int i = 1; i <= N; i++) {
			p[i] = new Custom(Integer.parseInt(reader.readLine())).divide(MILLION);
		}

		Custom[] pSum = new Custom[N + 1];
		Custom[] pProd = new Custom[N + 1];

		pSum[0] = ZERO;
		pProd[0] = ONE;
		for (int i = 1; i <= N; i++) {
			Custom opp = ONE.sub(p[i]);
			pSum[i] = pSum[i - 1].add((p[i].divide(opp)));
			pProd[i] = pProd[i - 1].multiply(opp);
		}

		hull = new Line[N + 1];
		xInsect = new Custom[N + 1];
		add(new Line(ONE, ZERO));

		Custom ans = ZERO;
		for (int i = 1; i <= N; i++) {
			Custom rPProd = ONE.divide(pProd[i]);
			Custom max = query(pSum[i]).multiply(pProd[i]);
			ans = ans.max(max);
			add(new Line(rPProd, pSum[i].negate().multiply(rPProd)));
		}

		Custom t10 = ans.multiply(MILLION);

		printer.println((int) t10.toDouble());
		printer.close();
	}

	Line[] hull;
	Custom[] xInsect;
	int hSize;

	Custom xInsect(Line a, Line b) {
		return b.yIncpt.sub(a.yIncpt).divide(a.slope.sub(b.slope));
	}

	boolean relevant(Line newLine) {
		if (hSize <= 2) {
			return true;
		}
		return xInsect[hSize - 2].compareTo(xInsect(hull[hSize - 2], newLine)) < 0;
	}

	void add(Line newLine) {
		assert (hSize == 0 || newLine.slope.compareTo(hull[hSize - 1].slope) > 0);

		while (!relevant(newLine)) {
			hSize--;
		}

		if (hSize >= 1) {
			xInsect[hSize - 1] = xInsect(hull[hSize - 1], newLine);
		}

		hull[hSize++] = newLine;
	}

	Custom query(Custom xVal) {
		int low = 0;
		int high = hSize - 1;
		while (low != high) {
			int mid = (low + high) >> 1;

			if (mid != 0 && xVal.compareTo(xInsect[mid - 1]) < 0) {
				high = mid - 1;
			} else if (mid == hSize - 1 || xVal.compareTo(xInsect[mid]) <= 0) {
				Line mLine = hull[mid];
				return mLine.slope.multiply(xVal).add(mLine.yIncpt);
			} else {
				low = mid + 1;
			}
		}
		Line mLine = hull[low];

		return mLine.slope.multiply(xVal).add(mLine.yIncpt);
	}

	class Line {
		Custom slope;
		Custom yIncpt;

		Line(Custom slope, Custom yIncpt) {
			this.slope = slope;
			this.yIncpt = yIncpt;
		}
	}

	class Custom implements Comparable<Custom> {

		double val;
		int scale;

		Custom add(Custom o) {
			Custom a = this;
			if (a.scale < o.scale) { // a has larger scale
				Custom t = a;
				a = o;
				o = t;
			}
			if (a.scale - o.scale > 63) {
				return a;
			}

			double nVal = a.val + Math.scalb(o.val, o.scale - a.scale);
			int nScale = a.scale;

			int cScale = Math.getExponent(nVal);
			nScale += cScale;
			nVal = Math.scalb(nVal, -cScale);

			return new Custom(nVal, nScale);
		}

		Custom sub(Custom o) {
			Custom a = this;
			double nVal;
			int nScale;

			if (a.scale < o.scale) { // a has larger scale
				Custom t = a;
				a = o;
				o = t;

				nVal = -a.val + Math.scalb(o.val, o.scale - a.scale);
				nScale = a.scale;
			} else {
				if (a.scale - o.scale > 63) {
					return a;
				}

				nVal = a.val - Math.scalb(o.val, o.scale - a.scale);
				nScale = a.scale;
			}

			int cScale = Math.getExponent(nVal);
			nScale += cScale;
			nVal = Math.scalb(nVal, -cScale);

			return new Custom(nVal, nScale);
		}

		Custom negate() {
			return new Custom(-val, scale);
		}

		Custom divide(Custom o) {
			double nVal = val / o.val;
			int nScale = scale - o.scale;

			int cScale = Math.getExponent(nVal);
			nScale += cScale;
			nVal = Math.scalb(nVal, -cScale);

			return new Custom(nVal, nScale);
		}

		Custom multiply(Custom o) {
			double nVal = val * o.val;
			int nScale = scale + o.scale;

			int cScale = Math.getExponent(nVal);
			nScale += cScale;
			nVal = Math.scalb(nVal, -cScale);

			return new Custom(nVal, nScale);
		}

		Custom(int i) {
			if (i == 0) {
				val = 0;
				scale = 0;
				return;
			}

			int cScale = Math.getExponent(i);
			scale = cScale;
			val = Math.scalb(i, -cScale);
		}

		Custom(double val, int scale) {
			this.val = val;
			this.scale = scale;
		}

		public int compareTo(Custom o) {
			if (val < 0) {
				if (o.val >= 0) {
					return -1;
				}
			} else if (val == 0) {
				if (o.val < 0) {
					return 1;
				} else if (o.val == 0) {
					return 0;
				} else {
					return -1;
				}
			} else {
				if (o.val <= 0) {
					return 1;
				}
			}

			if (scale != o.scale) {
				return scale < o.scale ? -1 : 1;
			}

			if (val < o.val) {
				return -1;
			} else if (val == o.val) {
				return 0;
			} else {
				return 1;
			}
		}

		Custom max(Custom o) {
			if (this.compareTo(o) >= 0) {
				return this;
			}
			return o;
		}

		double toDouble() {
			return Math.scalb(val, scale);
		}
	}
}