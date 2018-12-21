import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RedAndBlue {

	static int N;
	static int K;

	static long[] preA;
	static long[] preB;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		// BufferedReader reader = new BufferedReader(new FileReader("rb.in"));
		// PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("rb.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());
		String type = reader.readLine();

		preA = new long[N + 1];
		preB = new long[N + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= N; i++) {
			preA[i] = preA[i - 1];
			preB[i] = preB[i - 1];

			if (type.charAt(i - 1) == 'r') {
				preA[i] += Integer.parseInt(inputData.nextToken());
			} else {
				preB[i] += Integer.parseInt(inputData.nextToken());
			}
		}

		cost = new long[N + 1];
		cnt = new int[N + 1];

		long ans = search();
		if (ans < 0) {
			throw new RuntimeException();
		}

		printer.println(ans);
		printer.close();
	}

	static long search() {
		long low = 0;
		long high = cost(1, N) + 1000;

		while (low != high) {
			long mid = (low + high) >> 1;
			calc(mid);

			if (tCnt > K) {
				low = mid + 1;
			} else if (tCnt == K) {
				return tCost - tCnt * mid;
			} else {
				high = mid;
			}
		}

		calc(low);
		if (tCnt > K) {
			throw new RuntimeException();
		}
		long ans = tCost - tCnt * low;

		calc(low + 1);

		if (tCnt >= K) {
			throw new RuntimeException();
		}

		return ans;
	}

	static long tCost;
	static int tCnt;

	static long[] cost;
	static int[] cnt;

	static ArrayDeque<Integer> trans = new ArrayDeque<>();
	static ArrayDeque<Integer> intPt = new ArrayDeque<>();

	static void calc(long pen) {
		Arrays.fill(cost, 0);
		Arrays.fill(cnt, 0);

		trans.clear();
		intPt.clear();

		trans.addLast(0);

		for (int i = 1; i <= N; i++) {
			while (!intPt.isEmpty() && intPt.peek() <= i) {
				trans.removeFirst();
				intPt.removeFirst();
			}

			int preI = trans.peekFirst();

			cost[i] = cost[preI] + cost(preI + 1, i) + pen;

			if (cost[i] < 0) {
				throw new RuntimeException();
			}
			cnt[i] = cnt[preI] + 1;

			if (i + 1 <= N) {

				while (!intPt.isEmpty() && (cost[trans.peekLast()]
						+ cost(trans.peekLast() + 1, intPt.peekLast()) > cost[i] + cost(i + 1, intPt.peekLast())
						|| (cost[trans.peekLast()] + cost(trans.peekLast() + 1, intPt.peekLast()) == cost[i]
								+ cost(i + 1, intPt.peekLast()) && cnt[trans.peekLast()] > cnt[i]))) {
					trans.removeLast();
					intPt.removeLast();
				}

				if (intPt.isEmpty() && (cost[trans.peekLast()] + cost(trans.peekLast() + 1, i + 1) > cost[i]
						|| (cost[trans.peekLast()] + cost(trans.peekLast() + 1, i + 1) == cost[i]
								&& cnt[trans.peekLast()] > cnt[i]))) {

					if (trans.size() != 1) {
						throw new RuntimeException();
					}
					trans.removeLast();
					trans.addLast(i);
				} else {
					int tPt = fInt(trans.peekLast(), i);
					if (tPt != N + 1) {
						trans.addLast(i);
						intPt.addLast(tPt);
					}
				}
			}
		}

		tCost = cost[N];
		tCnt = cnt[N];
	}

	static int fInt(int preT, int newT) {
		int low = newT;
		int high = N + 1;

		while (low != high) {
			int mid = (low + high) >> 1;
			if (mid == N + 1 || cost[preT] + cost(preT + 1, mid) > cost[newT] + cost(newT + 1, mid)
					|| (cost[preT] + cost(preT + 1, mid) == cost[newT] + cost(newT + 1, mid)
							&& cnt[preT] > cnt[newT])) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

	static long cost(int i, int j) {
		if (i > j) {
			throw new RuntimeException();
		}
		return (preA[j] - preA[i - 1]) * (preB[j] - preB[i - 1]);
	}
}
