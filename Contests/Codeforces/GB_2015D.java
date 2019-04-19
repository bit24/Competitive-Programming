import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GB_2015D {

	public static void main(String[] args) throws IOException {
		new GB_2015D().execute();
	}

	String data;

	long[][] dp;

	int[][] matchLength;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		reader.readLine();
		data = reader.readLine();
		reader.close();

		matchLength = new int[data.length()][data.length()];

		for (int left1 = data.length() - 1; left1 >= 0; left1--) {
			for (int left2 = data.length() - 1; left2 >= 0; left2--) {
				if (data.charAt(left1) == data.charAt(left2)) {
					if (left1 + 1 < data.length() && left2 + 1 < data.length()) {
						matchLength[left1][left2] = 1 + matchLength[left1 + 1][left2 + 1];
					}
				}
			}
		}

		dp = new long[data.length()][data.length()];
		sum = new long[data.length()];

		for (int length = 1; length <= data.length(); length++) {
			for (int right = length - 1; right < data.length(); right++) {
				int left = right - length + 1;
				findDP(left, right);
			}
		}
		long sum = 0;

		for (int left = 0; left < data.length(); left++) {
			sum += dp[left][data.length() - 1];
			sum %= 1_000_000_007L;
		}
		System.out.println(sum);
	}

	long[] sum;

	void findDP(int i, int j) {
		if (data.charAt(i) == '0') {
			return;
		}
		if (i == 0) {
			dp[i][j] = 1;
			return;
		}
		if (2 * i - j >= 0) {
			sum[i - 1] += dp[2 * i - j][i - 1];
		}

		long cSum = sum[i - 1];
		if (2 * i - j - 1 >= 0 && greater(i, j, 2 * i - j - 1, i - 1)) {
			cSum += dp[2 * i - j - 1][i - 1];
		}
		dp[i][j] = cSum % 1_000_000_007L;
	}

	boolean greater(int i, int j, int k, int l) {
		assert (j - i == l - k);

		int cML = matchLength[i][k];
		if (cML > j - i) {
			return false;
		}

		return data.charAt(i + cML) > data.charAt(k + cML);
	}

}
