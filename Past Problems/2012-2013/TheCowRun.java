import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class TheCowRun {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowrun.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cowrun.out")));

		int nI = Integer.parseInt(reader.readLine());
		long[] pos = new long[nI];
		for (int i = 0; i < nI; i++) {
			pos[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		Arrays.sort(pos);

		long[][][] dp = new long[nI][nI][2];

		for (int i = 0; i < nI; i++) {
			for (int j = 0; j < nI; j++) {
				dp[i][j][0] = dp[i][j][1] = Long.MAX_VALUE / 2;
			}
		}

		for (int i = 0; i < nI; i++) {
			if (pos[i] >= 0) {
				dp[i][i][0] = dp[i][i][1] = pos[i] * nI;
				if (i >= 1) {
					dp[i - 1][i - 1][0] = dp[i - 1][i - 1][1] = -pos[i - 1] * nI;
				}
				break;
			}
		}
		if (pos[nI - 1] < 0) {
			dp[nI - 1][nI - 1][0] = dp[nI - 1][nI - 1][1] = -pos[nI - 1] * nI;
		}

		for (int len = 2; len <= nI; len++) {
			for (int left = 0; left + len - 1 < nI; left++) {
				int right = left + len - 1;
				int mult = nI - right + left;
				dp[left][right][0] = dp[left + 1][right][0] + (pos[left + 1] - pos[left]) * mult;
				dp[left][right][1] = dp[left][right - 1][1] + (pos[right] - pos[right - 1]) * mult;
				mult--;
				dp[left][right][0] = Math.min(Long.MAX_VALUE / 2,
						Math.min(dp[left][right][0], dp[left][right][1] + (pos[right] - pos[left]) * mult));
				dp[left][right][1] = Math.min(Long.MAX_VALUE / 2,
						Math.min(dp[left][right][1], dp[left][right][0] + (pos[right] - pos[left]) * mult));
			}
		}
		printer.println(Math.min(dp[0][nI - 1][0], dp[0][nI - 1][1]));
		printer.close();
	}
}
