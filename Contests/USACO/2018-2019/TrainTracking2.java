import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TrainTracking2 {

	public static void main(String[] args) throws IOException {
		new TrainTracking2().main();
	}

	static final long MOD = 1_000_000_007;
	static final long MAX = 1_000_000_000;

	static int K;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tracking2.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("tracking2.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());

		int[] a = new int[N - K + 1];

		for (int i = 0; i < N - K + 1; i++) {
			a[i] = Integer.parseInt(reader.readLine());
		}

		ArrayList<Group> groups = new ArrayList<>();

		int cMin = -1;
		int cLen = 0;

		for (int i = 0; i < a.length; i++) {
			if (cMin == a[i]) {
				cLen++;
			} else {
				if (cLen > 0) {
					groups.add(new Group(cMin, cLen));
				}
				cMin = a[i];
				cLen = 1;
			}
		}
		if (cLen > 0) {
			groups.add(new Group(cMin, cLen));
		}

		if (groups.size() == 1) {
			printer.println(count(groups.get(0).min, N));
			printer.close();
			return;
		}

		long ans = 1;

		for (int i = 0; i < groups.size(); i++) {
			cMin = groups.get(i).min;
			cLen = -1;

			if (i == 0) {
				if (groups.get(i + 1).min > groups.get(i).min) {
					cLen = groups.get(i).len - 1;
				} else {
					cLen = groups.get(i).len + K - 1;
				}
			} else if (i == groups.size() - 1) {
				if (groups.get(i - 1).min > groups.get(i).min) {
					cLen = groups.get(i).len - 1;
				} else {
					cLen = groups.get(i).len + K - 1;
				}
			} else {
				if (groups.get(i - 1).min > groups.get(i).min) {
					if (groups.get(i + 1).min > groups.get(i).min) {
						cLen = Math.max(0, groups.get(i).len - K - 1);
					} else {
						cLen = groups.get(i).len - 1;
					}
				} else {
					if (groups.get(i + 1).min > groups.get(i).min) {
						cLen = groups.get(i).len - 1;

					} else {
						cLen = groups.get(i).len + K - 1;
					}
				}
			}
			ans = ans * count(cMin, cLen) % MOD;
		}
		printer.println(ans);
		printer.close();
	}

	long count(int min, int len) {
		int end = Math.min(len, K);
		long numG = MAX - min;
		long[] powG = new long[end + 1];
		long[] powGE = new long[end + 1];
		powG[0] = powGE[0] = 1;

		for (int i = 1; i <= end; i++) {
			powG[i] = powG[i - 1] * numG % MOD;
			powGE[i] = powGE[i - 1] * (numG + 1) % MOD;
		}

		if (len < K) {
			return powGE[len]; // if group size < K then condition must already be satisfied
		}

		long[] dp = new long[len + 2];
		dp[0] = dp[1] = 1;

		for (int i = 2; i <= end; i++) {
			dp[i] = powGE[i - 1];
		}

		for (int i = K; i <= len; i++) {
			dp[i + 1] = dp[i];
			dp[i + 1] = (dp[i + 1] + (MOD - dp[i - K]) * (powG[K - 1])) % MOD;
			dp[i + 1] = dp[i + 1] * numG % MOD;
			dp[i + 1] = (dp[i + 1] + dp[i]) % MOD;
		}
		return dp[len + 1];
	}

	class Group {
		int min;
		int len;

		Group(int min, int len) {
			this.min = min;
			this.len = len;
		}
	}

}
