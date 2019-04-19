import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class OutOfSorts {

	public static void main(String[] args) throws IOException {
		new OutOfSorts().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("sort.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
		int N = Integer.parseInt(reader.readLine());
		int[] orig = new int[N];

		for (int i = 0; i < N; i++) {
			orig[i] = Integer.parseInt(reader.readLine());
		}

		for (int i = 0; i < N - 1; i++) {
			if (orig[i] > orig[i + 1]) {
				int t = orig[i];
				orig[i] = orig[i + 1];
				orig[i + 1] = t;
			}
		}

		Item[] items = new Item[N];

		for (int i = 0; i < N; i++) {
			items[i] = new Item(orig[i], i);
		}
		Arrays.sort(items);

		int[] sInd = new int[N];
		for (int i = 0; i < N; i++) {
			sInd[items[i].oInd] = i;
		}

		// stores nOp's needed to clear gap between i and i+1
		int[] nOp = new int[N - 1];

		int mOI = 0;
		for (int gI = 0; gI < N - 1; gI++) {
			mOI = Math.max(mOI, items[gI].oInd);
			nOp[gI] = mOI - gI;
		}

		long ans = N;
		for (int i = 0; i < N; i++) {
			int cDepth = 0;
			if (i - 1 >= 0) {
				cDepth = Math.max(cDepth, nOp[i - 1]);
			}
			if (i + 1 < N) {
				cDepth = Math.max(cDepth, nOp[i]);
			}
			ans += cDepth;
		}
		printer.println(ans);
		printer.close();
	}

	class Item implements Comparable<Item> {
		int val;
		int oInd;

		Item(int val, int oInd) {
			this.val = val;
			this.oInd = oInd;
		}

		public int compareTo(Item o) {
			if (val != o.val) {
				return Integer.compare(val, o.val);
			}
			return Integer.compare(oInd, o.oInd);
		}
	}
}
