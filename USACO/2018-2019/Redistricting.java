import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Redistricting {

	public static void main(String[] args) throws IOException {
		new Redistricting().main();
	}

	void main() throws IOException {
		minC = new int[4 * MAX];
		Arrays.fill(minC, Integer.MAX_VALUE / 4);
		leaf = new TreeSet[4 * MAX];
		build(1, 1, MAX);

		BufferedReader reader = new BufferedReader(new FileReader("redistricting.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("redistricting.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int N = Integer.parseInt(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());

		String line = " " + reader.readLine();

		int[] pSum = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			pSum[i] = pSum[i - 1] + (line.charAt(i) == 'G' ? 1 : -1);
		}

		int[] dp = new int[N + 1];
		Arrays.fill(dp, Integer.MAX_VALUE / 4);
		dp[0] = 0;

		add(1, 1, MAX, OFST + 0, new State(0, 0));

		for (int i = 1; i <= N; i++) {
			if (i - (K + 1) >= 0) {
				remove(1, 1, MAX, OFST + pSum[i - (K + 1)], new State(dp[i - (K + 1)], i - (K + 1)));
			}

			int q1 = query(1, 1, MAX, 1, OFST + pSum[i]);

			int nCost = q1 + 1;
			dp[i] = Math.min(dp[i], nCost);

			int q2 = query(1, 1, MAX, OFST + pSum[i] + 1, MAX);
			nCost = q2;
			dp[i] = Math.min(dp[i], nCost);

			add(1, 1, MAX, OFST + pSum[i], new State(dp[i], i));
		}

		printer.println(dp[N]);
		printer.close();
	}

	static final int OFST = 300_010;
	static final int MAX = 2 * 300_010;

	int[] minC;

	TreeSet<State>[] leaf;

	void build(int nI, int cL, int cR) {
		if (cL == cR) {
			leaf[nI] = new TreeSet<>();
		} else {
			int mid = (cL + cR) >> 1;
			build(nI * 2, cL, mid);
			build(nI * 2 + 1, mid + 1, cR);
			minC[nI] = Math.min(minC[nI * 2], minC[nI * 2 + 1]);
		}
	}

	void add(int nI, int cL, int cR, int uI, State nState) {
		if (cL == cR) {
			leaf[nI].add(nState);
			minC[nI] = leaf[nI].first().c;
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				add(nI * 2, cL, mid, uI, nState);
			} else {
				add(nI * 2 + 1, mid + 1, cR, uI, nState);
			}
			minC[nI] = Math.min(minC[nI * 2], minC[nI * 2 + 1]);
		}
	}

	void remove(int nI, int cL, int cR, int uI, State rState) {
		if (cL == cR) {
			if (!leaf[nI].remove(rState)) {
				throw new RuntimeException();
			}
			if (!leaf[nI].isEmpty()) {
				minC[nI] = leaf[nI].first().c;
			} else {
				minC[nI] = Integer.MAX_VALUE / 4;
			}
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				remove(nI * 2, cL, mid, uI, rState);
			} else {
				remove(nI * 2 + 1, mid + 1, cR, uI, rState);
			}
			minC[nI] = Math.min(minC[nI * 2], minC[nI * 2 + 1]);
		}
	}

	int query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return Integer.MAX_VALUE / 4;
		}
		if (qL <= cL && cR <= qR) {
			return minC[nI];
		}

		int mid = (cL + cR) >> 1;
		return Math.min(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	class State implements Comparable<State> {
		int c;
		int i;

		State(int c, int i) {
			this.c = c;
			this.i = i;
		}

		public int compareTo(State o) {
			if (c != o.c) {
				return c < o.c ? -1 : 1;
			}
			if (i != o.i) {
				return i < o.i ? -1 : 1;
			}
			return 0;
		}
	}

}
