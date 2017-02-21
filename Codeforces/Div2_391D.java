import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_391D {

	public static void main(String[] args) throws IOException {
		new Div2_391D().execute();
	}

	int numE;
	boolean[] elements;

	int MOD = 1_000_000_007;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		numE = Integer.parseInt(reader.readLine());
		elements = new boolean[numE + 1];

		String inputLine = reader.readLine();
		for (int i = 1; i <= numE; i++) {
			elements[i] = inputLine.charAt(i - 1) == '1';
		}
		reader.close();

		int[][] dp = new int[numE + 1][1 << 20];

		for (int i = 0; i <= numE; i++) {
			dp[i][0] = 1;
		}

		for (int cI = 0; cI <= numE; cI++) {
			for (int cS = 0; cS < (1 << 20); cS++) {
				if (dp[cI][cS] == 0) {
					continue;
				}
				int cNum = 0;
				for (int nI = cI + 1; nI <= numE; nI++) {
					cNum <<= 1;
					if (elements[nI]) {
						cNum |= 1;
					}
					if (cNum == 0) {
						// invalid cut, as all cuts must form positive integers
						continue;
					}
					if (cNum > 20) {
						// cuts over 20 will never satisfy final requirement
						break;
					}
					// bitsets are zero-indexed
					dp[nI][cS | (1 << (cNum - 1))] = (dp[nI][cS | (1 << (cNum - 1))] + dp[cI][cS]) % MOD;
				}
			}
		}

		int total = 0;
		for (int cI = 1; cI <= numE; cI++) {
			for (int cM = 1; cM < (1 << 20); cM <<= 1, cM |= 1) {
				if (dp[cI][cM] != 0) {
					total = (total + dp[cI][cM]) % MOD;
				}
			}
		}
		System.out.println(total);
	}
}