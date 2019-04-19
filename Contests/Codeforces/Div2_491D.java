import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_491D {

	static int nR;
	static boolean[][] taken;

	static int[][][] req = { { { 0, 0 }, { 0, 1 }, { 1, 0 } }, { { 0, 0 }, { 0, 1 }, { 1, 1 } },
			{ { 0, 1 }, { 1, 0 }, { 1, 1 } }, { { 0, 0 }, { 1, 0 }, { 1, 1 } } };

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String str1 = reader.readLine();
		String str2 = reader.readLine();
		nR = str1.length();

		taken = new boolean[2][nR + 4];
		for (int i = 1; i <= nR; i++) {
			taken[0][i] = str1.charAt(i - 1) == 'X';
			taken[1][i] = str2.charAt(i - 1) == 'X';
		}

		int[][] dp = new int[nR + 4][3];

		boolean[] sTaken = new boolean[2];

		for (int i = 1; i <= nR; i++) {
			// row i is finished is the one with set

			for (int cSet = 0; cSet <= 2; cSet++) {
				int cDP = dp[i][cSet];
				sTaken[0] = (cSet & 1) != 0;
				sTaken[1] = (cSet & 2) != 0;

				pLoop:
				for (int p = 0; p < 4; p++) {
					int[][] cReq = req[p];
					for (int[] cPos : cReq) {
						if (cPos[1] == 0 && sTaken[cPos[0]]) {
							continue pLoop;
						}

						if (taken[cPos[0]][i + cPos[1]]) {
							continue pLoop;
						}
					}

					if (p == 0) {
						dp[i + 1][1] = Math.max(dp[i + 1][1], cDP + 1);
					}
					if (p == 3) {
						dp[i + 1][2] = Math.max(dp[i + 1][2], cDP + 1);
					}
					if (p == 1 || p == 2) {
						dp[i + 2][0] = Math.max(dp[i + 2][0], cDP + 1);
					}
				}
				dp[i + 1][0] = Math.max(dp[i + 1][0], cDP);
			}
		}

		int ans = 0;
		for (int i = 1; i <= nR; i++) {
			for (int j = 0; j < 3; j++) {
				ans = Math.max(ans, dp[i][j]);
			}
		}
		ans = Math.max(ans, dp[nR + 1][0]);
		printer.println(ans);
		printer.close();
	}
}
