import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class TastiestCommonDivisor {

	static int[][] sparse;

	public static void main(String[] args) throws IOException {
		// System.out.println(gcd(3, 5));
		new TastiestCommonDivisor().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());
		sparse = new int[17][N];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			sparse[0][i] = Integer.parseInt(inputData.nextToken());
		}
		for (int k = 1; k < 17; k++) {
			int len = 1 << (k - 1);
			for (int i = 0; i < N; i++) {
				sparse[k][i] = i + len < N ? gcd(sparse[k - 1][i], sparse[k - 1][i + len]) : sparse[k - 1][i];
			}
		}

		ArrayList<Counter> counts = new ArrayList<>();

		for (int l = 0; l < N; l++) {
			int cur = sparse[0][l];
			int ind = l;
			int lInd = l;

			while (true) {

				for (int k = 16; k >= 0; k--) {
					if (ind < N && gcd(cur, sparse[k][ind]) == cur) {
						ind += 1 << k;
					}
				}
				// ind contains first different

				if (ind < N) {
					int cnt = ind - lInd;
					counts.add(new Counter(cur, cnt));
				} else {
					int cnt = N - lInd;
					counts.add(new Counter(cur, cnt));
					break;
				}
				lInd = ind;
				cur = gcd(cur, sparse[0][ind]);
			}
		}

		counts.add(new Counter(0, 0));
		Collections.sort(counts);
		counts = merge(counts);

		long[] preSum = new long[counts.size()];

		for (int i = 1; i < counts.size(); i++) {
			preSum[i] = preSum[i - 1] + counts.get(i).cnt;
		}

		while (Q-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			long ans = preSum[binSearch(counts, r)] - preSum[binSearch(counts, l - 1)];
			printer.println(ans);
		}
		printer.close();
	}

	int binSearch(ArrayList<Counter> arr, int val) {
		int low = 0;
		int high = arr.size() - 1;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (arr.get(mid).val <= val) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	static ArrayList<Counter> merge(ArrayList<Counter> inp) {
		ArrayList<Counter> ret = new ArrayList<>();
		ret.add(inp.get(0));
		for (int i = 1; i < inp.size(); i++) {
			if (inp.get(i - 1).val == inp.get(i).val) {
				ret.get(ret.size() - 1).cnt += inp.get(i).cnt;
			} else {
				ret.add(inp.get(i));
			}
		}
		return ret;
	}

	static int gcd(int a, int b) {
		if (b < a) {
			return gcd(b, a);
		}
		if (a == 0) {
			return b;
		}
		return gcd(b % a, a);
	}

	class Counter implements Comparable<Counter> {
		int val;
		long cnt;

		Counter(int val, long cnt) {
			this.val = val;
			this.cnt = cnt;
		}

		public int compareTo(Counter o) {
			return Integer.compare(val, o.val);
		}
	}
}
