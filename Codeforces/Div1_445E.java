import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_445E {

	static int N;
	static long[] a;

	static long[] max;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());
		a = new long[N + 2];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			a[i] = Long.parseLong(inputData.nextToken());
			if (i != 0 && a[i - 1] < a[i]) {
				a[i] = a[i - 1];
			}
		}
		a[N] = 1;
		a[N + 1] = 0;

		max = new long[N + 1];
		for (int i = N; i >= 0; i--) {
			calcMax(i);
		}

		printer.println(max[0]);
		printer.close();
	}

	static void calcMax(int cI) {
		if (cI == N) {
			max[cI] = 0;
			return;
		}

		long cPer = a[cI];
		long nPer = a[cI + 1];
		long complete = cPer / nPer;

		long rem = cPer % nPer;

		long case1 = (complete - 1) * nPer * (cI + 1) + max[cI + 1];

		long case2 = rem != 0 ? complete * nPer * (cI + 1) + calcMax(cI + 1, rem) : 0;
		max[cI] = Math.max(case1, case2);
	}

	static long calcMax(int oI, long cPer) {
		if (cPer == 0) {
			return 0;
		}
		// search for last index whose period >= pLen
		int low = oI;
		int high = N + 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (a[mid] >= cPer) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}

		int cI = low;
		
		if(cI == N) {
			return 0;
		}

		long nPer = a[cI + 1];
		long complete = cPer / nPer;

		long rem = cPer % nPer;

		long case1 = (complete - 1) * nPer * (cI + 1) + max[cI + 1];

		long case2 = rem != 0 ? complete * nPer * (cI + 1) + calcMax(cI + 1, rem) : 0;
		return Math.max(case1, case2);
	}
}
