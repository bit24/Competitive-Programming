import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_433D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nD = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int[] req = new int[nD];
		for (int i = 0; i < nD; i++) {
			req[i] = Integer.parseInt(inputData.nextToken()) / 100;
		}

		int[][] dp = new int[nD][36];
		for (int i = 0; i < nD; i++) {
			Arrays.fill(dp[i], Integer.MAX_VALUE);
		}

		dp[0][req[0] / 10] = req[0];
		for (int i = 0; i < nD - 1; i++) {
			for (int j = 0; j <= 35; j++) {
				if (dp[i][j] != Integer.MAX_VALUE) {
					if (j + req[i + 1] / 10 <= 35) {
						dp[i + 1][j + req[i + 1] / 10] = Math.min(dp[i + 1][j + req[i + 1] / 10],
								dp[i][j] + req[i + 1]);
					}
					int delta = Math.min(j, req[i + 1]);
					dp[i + 1][j - delta] = Math.min(dp[i + 1][j - delta], dp[i][j] + req[i + 1] - delta);
				}
			}
		}

		int ans = Integer.MAX_VALUE;
		for (int i = 0; i <= 35; i++) {
			ans = Math.min(ans, dp[nD - 1][i]);
		}
		printer.println(ans * 100);
		printer.close();
	}
}
