import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SeahorseShoes {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= nT; cT++) {
			int nE = Integer.parseInt(reader.readLine());
			int[] e = new int[nE + 1];

			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int i = 1; i <= nE; i++) {
				e[i] = Integer.parseInt(inputData.nextToken());
			}
			e[0] = -2;
			Arrays.sort(e);

			int[] dp = new int[nE + 1];

			for (int i = 1; i <= nE; i++) {
				dp[i] = dp[i - 1] + 1;
				if (e[i] - e[i - 1] <= 2) {
					dp[i] = Math.min(dp[i], dp[i - 2] + 1);
				}
			}
			printer.println("Litter #" + cT + ": " + dp[nE]);
		}
		printer.close();
	}
}
