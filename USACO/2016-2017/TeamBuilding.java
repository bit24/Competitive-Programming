import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TeamBuilding {

	public static void main(String[] args) throws IOException {
		new TeamBuilding().execute();
	}

	final long MOD = 1_000_000_009;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("team.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("team.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numA = Integer.parseInt(inputData.nextToken());
		int numB = Integer.parseInt(inputData.nextToken());
		int numS = Integer.parseInt(inputData.nextToken());

		long[] aElements = new long[numA + 1];
		long[] bElements = new long[numB + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numA; i++) {
			aElements[i] = Long.parseLong(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numB; i++) {
			bElements[i] = Long.parseLong(inputData.nextToken());
		}
		reader.close();

		// in case of negative values
		Arrays.sort(aElements, 1, numA + 1);
		Arrays.sort(bElements, 1, numB + 1);

		long[][][] dp = new long[numS + 1][numA + 1][numB + 1];
		for (int cA = 1; cA <= numA; cA++) {
			for (int cB = 1; cB <= numB; cB++) {
				if (aElements[cA] > bElements[cB]) {
					dp[1][cA][cB] = 1;
				}
			}
		}

		for (int cS = 2; cS <= numS; cS++) {

			long[][] pSum = new long[numA + 1][numB + 1];

			for (int i = 1; i <= numA; i++) {
				for (int j = 1; j <= numB; j++) {
					pSum[i][j] = (dp[cS - 1][i][j] + pSum[i - 1][j] + pSum[i][j - 1] - pSum[i - 1][j - 1]) % MOD;
				}
			}

			for (int cA = 1; cA <= numA; cA++) {
				for (int cB = 1; cB <= numB; cB++) {

					long sum = pSum[cA - 1][cB - 1];

					if (aElements[cA] > bElements[cB]) {
						dp[cS][cA][cB] = sum;
					}
				}
			}
		}

		long sum = 0;
		for (int i = 0; i <= numA; i++) {
			for (int j = 0; j <= numB; j++) {
				sum += dp[numS][i][j];
			}
		}

		printer.println((sum + MOD) % MOD);
		printer.close();
	}
}
