import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lifeguards {

	public static void main(String[] args) throws IOException {
		new Lifeguards().execute();
	}

	Seg[] nShifts;
	int nN;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("lifeguards.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("lifeguards.out")));;
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());
		Seg[] shifts = new Seg[n + 1];

		for (int i = 1; i <= n; i++) {
			inputData = new StringTokenizer(reader.readLine());
			shifts[i] = new Seg(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		Arrays.sort(shifts, 1, n + 1);

		nShifts = new Seg[n + 1];
		nN = 0;
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			if (i == 1 || nShifts[nN].e < shifts[i].e) {
				sum += i == 1 ? shifts[i].e - shifts[i].s : shifts[i].e - nShifts[nN].e;
				nShifts[++nN] = shifts[i];
			} else {
				k--;
			}
		}
		reader.close();

		if (k <= 0) {
			printer.println(sum);
			printer.close();
			return;
		}

		long[][] mCover = new long[nN + 1][k + 1];
		for (int i = 0; i <= nN; i++) {
			Arrays.fill(mCover[i], Long.MIN_VALUE / 8);
		}

		mCover[0][0] = 0;

		nShifts[0] = new Seg(-1000, -1000);

		for (int i = 1; i <= nN; i++) {
			mCover[i][0] = mCover[i - 1][0] + nShifts[i].e - Math.max(nShifts[i - 1].e, nShifts[i].s);
			for (int cR = 1; cR <= k; cR++) {
				// don't take this guard
				mCover[i][cR] = Math.max(mCover[i][cR], mCover[i - 1][cR - 1]);

				// take this guard
				int pNO = bSearch(nShifts[i].s);
				int btwn = i - pNO - 1;

				mCover[i][cR] = Math.max(mCover[i][cR],
						mCover[pNO][Math.max(cR - btwn, 0)] + nShifts[i].e - nShifts[i].s);

				int pO = pNO + 1;
				btwn--;
				if (pO != i) {
					mCover[i][cR] = Math.max(mCover[i][cR],
							mCover[pO][Math.max(cR - btwn, 0)] + nShifts[i].e - nShifts[pO].e);
				}
			}
		}
		printer.println(mCover[nN][k]);
		printer.close();
	}

	int bSearch(int ind) {
		int l = 0;
		int h = nN;

		while (l != h) {
			int m = (l + h + 1) / 2;
			if (nShifts[m].e < ind) {
				l = m;
			} else {
				h = m - 1;
			}
		}
		return l;
	}

	class Seg implements Comparable<Seg> {
		int s;
		int e;

		Seg(int s, int e) {
			this.s = s;
			this.e = e;
		}

		public int compareTo(Seg o) {
			return Integer.compare(s, o.s);
		}
	}

}
