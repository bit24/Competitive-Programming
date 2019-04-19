import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_449C {

	public static void main(String[] args) throws IOException {
		new Div1_449C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());
		seed = Integer.parseInt(inputData.nextToken());
		vmax = Integer.parseInt(inputData.nextToken());

		vals = new TreeSet<>();

		int first = nxt() % vmax + 1;
		vals.add(new Range(0, first));
		for (int i = 1; i < nE; i++) {
			int cur = nxt() % vmax + 1;
			Range pR = vals.last();
			if (pR.v != cur) {
				vals.add(new Range(i, cur));
			}
		}

		for (int i = 1; i <= nQ; i++) {
			int op = nxt() % 4 + 1;
			int l = nxt() % nE;
			int r = nxt() % nE;
			if (l > r) {
				int temp = l;
				l = r;
				r = temp;
			}
			if (op == 1) {
				op1(l, r, nxt() % vmax + 1);
			} else if (op == 2) {
				op2(l, r, nxt() % vmax + 1);
			} else if (op == 3) {
				printer.println(op3(l, r, nxt() % (r - l + 1) + 1));
			} else {
				printer.println(op4(l, r, nxt() % vmax + 1, nxt() % vmax + 1));
			}
		}
		printer.close();
	}

	int seed;
	int vmax;

	int nxt() {
		int ret = seed;
		seed = (int) (((long) seed * 7 + 13) % 1000000007);
		return ret;
	}

	int nE;
	TreeSet<Range> vals;

	void op1(int uL, int uR, int d) {
		Range cR = vals.floor(new Range(uL));
		if (cR.l < uL) {
			cR = new Range(uL, cR.v);
			vals.add(cR);
		}

		while (cR != null && cR.l <= uR) {
			Range higher = vals.higher(cR);
			if (higher == null ? uR < nE - 1 : uR < higher.l - 1) {
				higher = new Range(uR + 1, cR.v);
				vals.add(higher);
			}

			cR.v += d;
			cR = higher;
		}

		Range lMost = vals.floor(new Range(uL));
		Range lower = vals.lower(lMost);
		if (lower != null && lower.v == lMost.v) {
			vals.remove(lMost);
		}

		Range tRight = vals.ceiling(new Range(uR + 1));
		if (tRight != null) {
			lower = vals.lower(tRight);
			if (lower != null && lower.v == tRight.v) {
				vals.remove(tRight);
			}
		}
	}

	void op2(int uL, int uR, int nV) {
		Range cR = vals.floor(new Range(uL));
		if (cR.l < uL) {
			cR = new Range(uL, cR.v);
			vals.add(cR);
		}

		while (cR != null && cR.l <= uR) {
			Range higher = vals.higher(cR);
			if (higher == null ? uR < nE - 1 : uR < higher.l - 1) {
				higher = new Range(uR + 1, cR.v);
				vals.add(higher);
			}

			vals.remove(cR);
			cR = higher;
		}
		vals.add(new Range(uL, nV));

		Range lMost = vals.floor(new Range(uL));
		Range lower = vals.lower(lMost);
		if (lower != null && lower.v == lMost.v) {
			vals.remove(lMost);
		}

		Range tRight = vals.ceiling(new Range(uR + 1));
		if (tRight != null) {
			lower = vals.lower(tRight);
			if (lower != null && lower.v == tRight.v) {
				vals.remove(tRight);
			}
		}
	}

	long op3(int qL, int qR, int qI) {
		ArrayList<Num> numbers = new ArrayList<>();
		Range cR = vals.floor(new Range(qL));
		Range higher = vals.higher(cR);
		numbers.add(new Num(cR.v, Math.min(qR, higher != null ? higher.l - 1 : nE - 1) - qL + 1));

		cR = higher;

		while (cR != null && cR.l <= qR) {
			higher = vals.higher(cR);
			numbers.add(new Num(cR.v, Math.min(qR, higher != null ? higher.l - 1 : nE - 1) - cR.l + 1));
			cR = higher;
		}
		Collections.sort(numbers);
		for (Num cNum : numbers) {
			if (cNum.c >= qI) {
				return cNum.n;
			}
			qI -= cNum.c;
		}
		return -1;
	}

	long exp(long b, int p, int m) {
		b %= m;
		long c = 1;
		while (p > 0) {
			if ((p & 1) != 0) {
				c = c * b % m;
			}
			b = b * b % m;
			p >>= 1;
		}
		return c;
	}

	long op4(int qL, int qR, int qP, int qM) {
		long sum = 0;
		Range cR = vals.floor(new Range(qL));
		Range higher = vals.higher(cR);
		sum = exp(cR.v, qP, qM) * (Math.min(qR, higher != null ? higher.l - 1 : nE - 1) - qL + 1) % qM;

		cR = higher;

		while (cR != null && cR.l <= qR) {
			higher = vals.higher(cR);
			sum = (sum + exp(cR.v, qP, qM) * (Math.min(qR, higher != null ? higher.l - 1 : nE - 1) - cR.l + 1)) % qM;
			cR = higher;
		}
		return sum;
	}

	class Num implements Comparable<Num> {
		long n;
		int c;

		Num(long n, int c) {
			this.n = n;
			this.c = c;
		}

		public int compareTo(Num o) {
			return Long.compare(n, o.n);
		}
	}

	class Range implements Comparable<Range> {
		int l;
		long v;

		Range(int l) {
			this.l = l;
		}

		Range(int l, long v) {
			this.l = l;
			this.v = v;
		}

		public int compareTo(Range o) {
			return Integer.compare(l, o.l);
		}
	}
}
