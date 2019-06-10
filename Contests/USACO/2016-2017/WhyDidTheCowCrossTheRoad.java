import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WhyDidTheCowCrossTheRoad {

	public static void main(String[] args) throws IOException {
		new WhyDidTheCowCrossTheRoad().execute();
	}

	int numE;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("mincross.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("mincross.out")));
		numE = Integer.parseInt(reader.readLine());
		int[] aEl = new int[numE + 1];
		int[] bEl = new int[numE + 1];

		int[] aLoc = new int[numE + 1];
		int[] bLoc = new int[numE + 1];

		for (int i = 1; i <= numE; i++) {
			int item = Integer.parseInt(reader.readLine());
			aEl[i] = item;
			aLoc[item] = i;
		}
		for (int i = 1; i <= numE; i++) {
			int item = Integer.parseInt(reader.readLine());
			bEl[i] = item;
			bLoc[item] = i;
		}
		reader.close();

		long ans = Math.min(minInter(aEl, bLoc), minInter(bEl, aLoc));
		printer.println(ans);
		printer.close();
	}

	long minInter(int[] aEl, int[] bLoc) {
		long cCount = countInter(aEl, bLoc);
		long ans = cCount;

		for (int i = 1; i <= numE; i++) {
			int cE = aEl[i];
			cCount -= bLoc[cE] - 1;
			cCount += numE - bLoc[cE];
			if (cCount < ans) {
				ans = cCount;
			}
		}
		return ans;
	}

	long countInter(int[] aEl, int[] bLoc) {
		BIT = new long[numE + 1];

		long count = 0;
		for (int i = 1; i <= numE; i++) {
			int cE = aEl[i];
			// if (bLoc[cE] <= i) {
			count += query(numE) - query(bLoc[cE]);
			// }
			update(bLoc[cE], 1);
		}
		return count;
	}

	long[] BIT;

	void update(int ind, long delta) {
		while (ind <= numE) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	long query(int ind) {
		long sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}

}
