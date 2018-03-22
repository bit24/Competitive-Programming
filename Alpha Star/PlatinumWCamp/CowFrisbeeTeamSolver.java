import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CowFrisbeeTeamSolver {

	static int modReq;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numElements = Integer.parseInt(inputData.nextToken());
		modReq = Integer.parseInt(inputData.nextToken());

		int[] elements = new int[numElements + 1];
		for (int i = 1; i <= numElements; i++) {
			elements[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		int[][] dp = new int[numElements + 1][modReq];

		dp[0][0] = 1;
		for (int i = 1; i <= numElements; i++) {
			for (int j = 0; j < modReq; j++) {
				int previousMod = adjust(j - elements[i]);
				dp[i][j] = dp[i - 1][previousMod] + dp[i - 1][j];
				dp[i][j] %= 100_000_000;
			}
		}
		System.out.println(dp[numElements][0] - 1);
	}

	static int adjust(int value) {
		while (value < 0) {
			value += modReq;
		}
		return value;
	}

}
