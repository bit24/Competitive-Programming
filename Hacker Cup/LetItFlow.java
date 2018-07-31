import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LetItFlow {

	static final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("letitflow.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("letitflow.out")));
		int nT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= nT; cT++) {
			int nC = Integer.parseInt(reader.readLine());
			char[][] grid = new char[nC + 1][3];
			for (int j = 0; j < 3; j++) {
				String line = reader.readLine();
				for (int i = 1; i <= nC; i++) {
					grid[i][j] = line.charAt(i - 1);
				}
			}

			long[][] dp = new long[nC + 1][3];
			dp[0][0] = 1;

			for (int cC = 1; cC <= nC; cC++) {
				if (grid[cC][1] != '#') {
					if (grid[cC][0] != '#') {
						dp[cC][0] = dp[cC - 1][1];
						dp[cC][1] = dp[cC - 1][0];
					}
					if (grid[cC][2] != '#') {
						dp[cC][2] = dp[cC - 1][1];
						dp[cC][1] = (dp[cC][1] + dp[cC - 1][2]) % MOD;
					}
				}
			}
			printer.println("Case #" + cT + ": " + dp[nC][2]);
		}
		reader.close();
		printer.close();
	}
}