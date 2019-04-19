import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Mail18_2E {

	public static void main(String[] args) throws IOException {
		new Mail18_2E().main();
	}

	int N;
	int nS;
	int M;
	int K;

	int[] a;

	ArrayList<Seg> segs;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		nS = Integer.parseInt(inputData.nextToken());
		M = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());

		TreeSet<Integer> numS = new TreeSet<>();
		a = new int[N];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
			numS.add(a[i]);
		}
		ArrayList<Integer> nums = new ArrayList<>(numS);

		Seg[] segI = new Seg[nS];
		for (int i = 0; i < nS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			segI[i] = new Seg(Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()) - 1);
		}

		Arrays.sort(segI);

		segs = new ArrayList<>();

		for (int i = 0; i < nS; i++) {
			while (!segs.isEmpty() && segs.get(segs.size() - 1).l >= segI[i].l) {
				segs.remove(segs.size() - 1);
			}
			segs.add(segI[i]);
		}
		nS = segs.size();

		pCnt = new int[N];
		dp = new int[M + 1][N];

		nums.add(1_000_000_001);

		int low = 0;
		int high = nums.size() - 1;
		while (low != high) {
			int mid = (low + high) >> 1;
			int tSearch = nums.get(mid);

			if (search(tSearch)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		if (low == nums.size() - 1) {
			printer.println(-1);
		} else {
			printer.println(nums.get(low));
		}
		printer.close();
	}

	int[] pCnt;
	int[][] dp;

	boolean search(int X) {
		pCnt[0] = a[0] <= X ? 1 : 0;
		for (int i = 1; i < N; i++) {
			pCnt[i] = pCnt[i - 1] + (a[i] <= X ? 1 : 0);
		}

		int fOver = 0;
		int nxtS = 0;

		for (int[] a : dp) {
			Arrays.fill(a, 0);
		}

		for (int i = 0; i < N; i++) {
			if (nxtS < nS && segs.get(nxtS).r == i) {
				while (segs.get(fOver).r < segs.get(nxtS).l) {
					fOver++;
				}

				for (int j = 1; j <= M; j++) {
					// no overlap
					if (segs.get(nxtS).l != 0) {
						dp[j][i] = Math.max(dp[j][i],
								pCnt[segs.get(nxtS).r] - pCnt[segs.get(nxtS).l - 1] + dp[j - 1][segs.get(nxtS).l - 1]);
					} else {
						dp[j][i] = Math.max(dp[j][i], pCnt[segs.get(nxtS).r]);
					}

					// overlap
					dp[j][i] = Math.max(dp[j][i],
							pCnt[segs.get(nxtS).r] - pCnt[segs.get(fOver).r] + dp[j - 1][segs.get(fOver).r]);
				}
				nxtS++;
			}

			for (int j = 0; j <= M; j++) {
				if (j >= 1 && dp[j - 1][i] > dp[j][i]) {
					dp[j][i] = dp[j - 1][i];

				}
				if (i >= 1 && dp[j][i - 1] > dp[j][i]) {
					dp[j][i] = dp[j][i - 1];
				}
			}

		}
		return dp[M][N - 1] >= K;
	}

	class Seg implements Comparable<Seg> {
		int l;
		int r;

		Seg(int l, int r) {
			this.l = l;
			this.r = r;
		}

		public int compareTo(Seg o) {
			if (r != o.r) {
				return Integer.compare(r, o.r);
			}
			return -Integer.compare(l, o.l);
		}
	}
}
