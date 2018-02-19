import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_462A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nE = Integer.parseInt(reader.readLine());
		int[] e = new int[nE + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		for (int i = 1; i <= nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] p1 = new int[nE + 1];
		int[] p2 = new int[nE + 1];
		for (int i = 1; i <= nE; i++) {
			if (e[i] == 1) {
				p1[i] = p1[i - 1] + 1;
				p2[i] = p2[i - 1];
			} else {
				p1[i] = p1[i - 1];
				p2[i] = p2[i - 1] + 1;
			}
		}

		int[][] rDP = new int[nE + 1][nE + 1];
		int[][] dp = new int[nE + 1][nE + 1];

		for (int ind = 1; ind <= nE; ind++) {
			rDP[ind][ind] = 1;
			dp[ind][ind] = 1;
		}

		for (int len = 2; len <= nE; len++) {
			for (int lft = 1; lft + len - 1 <= nE; lft++) {
				int max = Math.max(rDP[lft + 1][lft + len - 1], rDP[lft][lft + len - 2]);
				if (e[lft] == 2) {
					max = Math.max(max, 1 + rDP[lft + 1][lft + len - 1]);
				} else {
					max = Math.max(max, p1[lft + len - 1] - p1[lft - 1]);
				}
				if (e[lft + len - 1] == 1) {
					max = Math.max(max, 1 + rDP[lft][lft + len - 2]);
				} else {
					max = Math.max(max, p2[lft + len - 1] - p2[lft - 1]);
				}
				rDP[lft][lft + len - 1] = max;
			}
		}

		for (int len = 2; len <= nE; len++) {
			for (int lft = 1; lft + len - 1 <= nE; lft++) {
				int max = Math.max(rDP[lft][lft + len - 1],
						Math.max(dp[lft + 1][lft + len - 1], dp[lft][lft + len - 2]));
				if (e[lft] == 1) {
					max = Math.max(max, 1 + dp[lft + 1][lft + len - 1]);
				} else {
					max = Math.max(max, p2[lft + len - 1] - p2[lft - 1]);
				}
				if (e[lft + len - 1] == 2) {
					max = Math.max(max, 1 + dp[lft][lft + len - 2]);
				} else {
					max = Math.max(max, p1[lft + len - 1] - p1[lft - 1]);
				}
				dp[lft][lft + len - 1] = max;
			}
		}
		printer.println(dp[1][nE]);
		printer.close();
	}

}
