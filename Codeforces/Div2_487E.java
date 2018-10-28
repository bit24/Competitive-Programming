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

public class Div2_487E {

	int N;

	public static void main(String[] args) throws IOException {
		new Div2_487E().main();
	}

	Pt[] pts;

	double[][][] pows = new double[16][][];

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());

		pts = new Pt[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			pts[i] = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()), i);
		}

		Pt[] srtd = Arrays.copyOf(pts, N);

		double[][] probs = new double[N][N];

		ArrayList<ArrayList<Integer>> sets = new ArrayList<>();

		for (int pI = 0; pI < N; pI++) {
			pivot = pts[pI];
			Arrays.sort(srtd);

			int numL = 0;
			for (int i = 1; i < N; i++) {
				if (i == 1 || srtd[i].compareTo(srtd[i - 1]) != 0) {
					numL++;
				}
			}

			for (int i = 1; i < N;) {
				ArrayList<Integer> cSet = new ArrayList<>();
				sets.add(cSet);
				cSet.add(pivot.i);

				int j = i;
				while (j + 1 < N && srtd[j + 1].compareTo(srtd[i]) == 0) {
					j++;
				}
				int numO = j - i + 2;

				probs[pI][pI] += 1.0 / numL / numO;

				while (i <= j) {
					cSet.add(srtd[i].i);
					probs[pI][srtd[i].i] = 1.0 / numL / numO;
					i++;
				}
				Collections.sort(cSet);
			}
		}

		Collections.sort(sets, lSorter);
		ArrayList<ArrayList<Integer>> nDup = new ArrayList<>();
		for (int i = 0; i < sets.size(); i++) {
			if (i == 0 || lSorter.compare(sets.get(i), sets.get(i - 1)) != 0) {
				nDup.add(sets.get(i));
			}
		}
		// sets = nDup;

		pows[0] = new double[N][N];
		for (int i = 0; i < N; i++) {
			pows[0][i][i] = 1;
		}
		pows[1] = probs;
		for (int i = 2; i < 16; i++) {
			pows[i] = mult(pows[i - 1], pows[i - 1]);
		}

		double[][] trans = new double[N][N];

		int Q = Integer.parseInt(reader.readLine());
		while (Q-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int t = Integer.parseInt(inputData.nextToken()) - 1;
			int m = Integer.parseInt(inputData.nextToken());
			double[] column = new double[N];
			column[t] = 1;
			double[] res = exp(m - 1, column);

			double max = 0;

			for (ArrayList<Integer> cSet : sets) {
				double sum = 0;

				for (int mPt : cSet) {
					sum += res[mPt] / cSet.size();
				}
				max = Math.max(max, sum);
			}
			printer.println(max);
		}
		printer.close();
	}

	Comparator<ArrayList<Integer>> lSorter = new Comparator<ArrayList<Integer>>() {
		public int compare(ArrayList<Integer> l1, ArrayList<Integer> l2) {
			if (l1.size() < l2.size()) {
				return -1;
			}
			if (l1.size() > l2.size()) {
				return 1;
			}

			for (int i = 0; i < l1.size(); i++) {
				int a = l1.get(i);
				int b = l2.get(i);
				if (a < b) {
					return -1;
				}
				if (a > b) {
					return 1;
				}
			}
			return 0;
		}
	};

	double[] exp(int exp, double[] cur) {
		int cPow = 1;
		while (exp > 0) {
			if ((exp & 1) != 0) {
				cur = mult(pows[cPow], cur);
			}
			exp >>= 1;
			cPow++;
		}
		return cur;
	}

	double[][] mult(double[][] a, double[][] b) {
		double[][] res = new double[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				double sum = 0;
				for (int k = 0; k < N; k++) {
					sum += a[i][k] * b[k][j];
				}
				res[i][j] = sum;
			}
		}
		return res;
	}

	double[] mult(double[][] a, double[] b) {
		double[] res = new double[N];

		for (int i = 0; i < N; i++) {
			double sum = 0;
			for (int j = 0; j < N; j++) {
				sum += a[i][j] * b[j];
			}
			res[i] = sum;
		}
		return res;
	}

	Pt pivot;

	class Pt implements Comparable<Pt> {
		int x, y, i;

		Pt(int x, int y, int i) {
			this.x = x;
			this.y = y;
			this.i = i;
		}

		public int compareTo(Pt o) {
			if (this == pivot) {
				return -1;
			}
			if (o == pivot) {
				return 1;
			}

			long v1x = x - pivot.x;
			long v1y = y - pivot.y;
			if (v1x < 0 || v1x == 0 && v1y > 0) {
				v1x = -v1x;
				v1y = -v1y;
			}

			long v2x = o.x - pivot.x;
			long v2y = o.y - pivot.y;
			if (v2x < 0 || v2x == 0 && v2y > 0) {
				v2x = -v2x;
				v2y = -v2y;
			}
			long i = v1x * v2y - v1y * v2x;

			// HD, Section 2-7
			return (int) ((i >> 63) | (-i >>> 63));
		}
	}

}
