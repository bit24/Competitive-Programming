import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Toys {

	static int N;
	static long C;
	static int sT;
	static int lT;
	static long sC;
	static long lC;

	static int[] a;

	static int sum = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		sC = Integer.parseInt(inputData.nextToken());
		sT = Integer.parseInt(inputData.nextToken());
		lC = Integer.parseInt(inputData.nextToken());
		lT = Integer.parseInt(inputData.nextToken());
		C = Integer.parseInt(inputData.nextToken());

		if (sT > lT) {
			int t = sT;
			sT = lT;
			lT = t;

			long t1 = sC;
			sC = lC;
			lC = t1;
		}

		lC = Math.min(lC, sC);

		a = new int[N];
		for (int i = 0; i < N; i++) {
			sum += a[i] = Integer.parseInt(reader.readLine());
		}

		int nT = search();

		/*
		for (int i = 1; i <= sum; i++) {
			printer.println(test(i));
		}*/

		printer.println(test(nT));
		printer.close();
	}

	static int search() {
		int low = 1;
		int high = sum + 5;

		while (low < high) {
			int mid = (low + high) >> 1;
			long cost = test(mid);
			if (cost == Long.MAX_VALUE / 4) {
				low = mid + nExt;
			} else {
				long costN = test(mid + 1);
				if (cost < costN) {
					high = mid;
				} else {
					low = mid + 1;
				}
			}
		}
		return low;
	}

	static int[] cQueue = new int[12_000_000];
	static int[] fQueue = new int[20_000_000];

	static int cS;
	static int cE ;

	static int fS ;
	static int fE;

	static int nExt;

	static long test(int nT) {
		nExt = 0;

		cS = 1;
		cE = 1 - 1;
		fS = 10_000_000;
		fE = 10_000_000 - 1;

		for (int i = 0; i < nT; i++) {
			cQueue[++cE] = Integer.MIN_VALUE / 4;
		}

		long ans = 0;
		for (int i = 0; i < N; i++) {
			while (cS <= cE && cQueue[cS] + sT <= i) {
				fQueue[++fE] = cQueue[cS++];
			}
			for (int j = 0; j < a[i]; j++) {
				if (fS > fE) {
					nExt++;
					cQueue[++cE] = i;
				} else {
					int f = fQueue[fS];
					if (f + lT <= i) {
						fS++;
						cQueue[++cE] = i;
						ans += lC;
					} else {
						fE--;
						cQueue[++cE] = i;
						ans += sC;
					}
				}
			}
		}
		if (nExt > 0) {
			return Long.MAX_VALUE / 4;
		}

		return ans + nT * C - Math.min(sum, nT) * lC;
	}
}
