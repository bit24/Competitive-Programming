import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_491E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		long inp = Long.parseLong(reader.readLine());
		int[] cnt = new int[10];
		while (inp > 0) {
			cnt[(int) (inp % 10)]++;
			inp /= 10;
		}

		long[][] choose = new long[40][40];

		choose[0][0] = 1;

		for (int i = 1; i < 40; i++) {
			choose[i][0] = 1;
			for (int j = 1; j < 40; j++) {
				choose[i][j] = choose[i - 1][j] + choose[i - 1][j - 1];
			}
		}

		long[][] dp = new long[11][20];
		dp[10][0] = 1;

		for (int i = 10; i >= 2; i--) {
			for (int pCnt = 0; pCnt < 20; pCnt++) {
				long cDP = dp[i][pCnt];
				if (cDP == 0) {
					continue;
				}

				if (cnt[i - 1] >= 1) {
					for (int cPick = 1; cPick <= cnt[i - 1]; cPick++) {
						dp[i - 1][pCnt + cPick] += cDP * choose[pCnt + cPick][cPick];
					}
				} else {
					dp[i - 1][pCnt] += cDP;
				}
			}
		}

		for (int pCnt = 0; pCnt < 20; pCnt++) {
			long cDP = dp[1][pCnt];
			if (cDP == 0) {
				continue;
			}

			if (cnt[0] >= 1) {
				for (int cPick = 1; cPick <= cnt[0]; cPick++) {
					dp[0][pCnt + cPick] += cDP * choose[pCnt + cPick - 1][cPick];
				}
			} else {
				dp[0][pCnt] += cDP;
			}
		}

		long ans = 0;

		for (int i = 0; i < 20; i++) {
			ans += dp[0][i];
		}
		printer.println(ans);
		printer.close();
	}
}
