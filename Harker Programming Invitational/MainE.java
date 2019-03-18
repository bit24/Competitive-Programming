import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class MainE {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		char[] a = new char[N];
		for (int i = 0; i < N; i++) {
			a[i] = reader.readLine().charAt(0);
		}

		StringBuilder seq = new StringBuilder();

		for (int i = 0; i < N; i++) {
			StringBuilder nSeq = new StringBuilder(seq);
			nSeq.append(a[i]);
			nSeq.append(seq);
			seq = nSeq;
		}

		String b = " " + seq.toString();
		String c = " " + reader.readLine();
		int bLen = b.length() - 1;
		int cLen = c.length() - 1;

		int[][] dp = new int[bLen + 1][cLen + 1];

		for (int[] i : dp) {
			Arrays.fill(i, Integer.MAX_VALUE / 4);
		}

		dp[0][0] = 0;

		for (int i = 0; i <= bLen; i++) {
			for (int j = 0; j <= cLen; j++) {
				if (i != 0 && j != 0) {
					if (b.charAt(i) == c.charAt(j)) {
						dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
					}
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + 1);
				}

				if (i != 0) {
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
				}
				if (j != 0) {
					dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
				}
			}
		}
		printer.println(dp[bLen][cLen]);
		printer.close();
	}
}
