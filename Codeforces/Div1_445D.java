import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_445D {

	public static void main(String[] args) throws IOException {
		new Div1_445D().execute();
	}

	int nP;
	Pair[] pts;
	Pair[] mPts;

	long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nP = Integer.parseInt(reader.readLine());
		pts = new Pair[nP];

		long sX = 0;
		long sY = 0;

		for (int i = 0; i < nP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			pts[i] = new Pair(Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()) - 1);
			sX += pts[i].x;
			sY += pts[i].y;
		}

		mPts = new Pair[nP];

		TreeSet<Pair> ptSet = new TreeSet<>();

		for (int i = 0; i < nP; i++) {
			mPts[i] = new Pair(pts[i].x * nP - sX, pts[i].y * nP - sY);
			ptSet.add(mPts[i]);
		}
		// center is now at (0, 0) for mPts

		int nRef = -1;
		for (int i = 0; i < nP; i++) {
			if (!ptSet.contains(new Pair(-mPts[i].x, -mPts[i].y))) {
				nRef = i;
				break;
			}
		}

		if (nRef == -1) {
			printer.println(-1);
			printer.close();
			return;
		}

		TreeSet<Pair> ans = new TreeSet<>();
		for (int i = 0; i < nP; i++) {
			long mX = mPts[nRef].x + mPts[i].x;
			long mY = mPts[nRef].y + mPts[i].y;

			if (isSym(-mY, mX)) {
				long gcd = gcd(mX, mY);
				mX /= gcd;
				mY /= gcd;
				if (mY < 0 || (mY == 0 && mX < 0)) {
					mX = -mX;
					mY = -mY;
				}
				ans.add(new Pair(-mY, mX));
			}
		}
		printer.println(ans.size());
		printer.close();
	}

	boolean isSym(long xM, long yM) {
		long[] vals = new long[nP];
		for (int i = 0; i < nP; i++) {
			vals[i] = mPts[i].x * xM + mPts[i].y * yM;
		}
		Arrays.sort(vals);
		long sum = vals[0] + vals[nP - 1];

		for (int i = 0; i < nP / 2; i++) {
			if (vals[i] + vals[nP - 1 - i] != sum) {
				return false;
			}
		}
		return true;
	}

	class Pair implements Comparable<Pair> {
		long x;
		long y;

		Pair(long x, long y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Pair o) {
			return x < o.x ? -1 : x == o.x ? (y < o.y ? -1 : y == o.y ? 0 : 1) : 1;
		}
	}

}
