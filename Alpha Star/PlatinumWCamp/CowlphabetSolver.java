import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CowlphabetSolver {

	int numU;
	int numL;
	int numP;

	public static void main(String[] args) throws IOException {
		new CowlphabetSolver().execute();
	}

	int[][] next;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numU = Integer.parseInt(inputData.nextToken());
		numL = Integer.parseInt(inputData.nextToken());
		numP = Integer.parseInt(inputData.nextToken());

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] nextList = new ArrayList[52];
		for (int i = 0; i < 52; i++) {
			nextList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numP; i++) {
			String nextLine = reader.readLine();
			char startChar = nextLine.charAt(0);
			int startNum = 0;
			if (Character.isLowerCase(startChar)) {
				startNum = startChar - 'a';
			} else {
				startNum = 26 + startChar - 'A';
			}

			char endChar = nextLine.charAt(1);
			int endNum = 0;
			if (Character.isLowerCase(endChar)) {
				endNum = endChar - 'a';
			} else {
				endNum = 26 + endChar - 'A';
			}
			nextList[startNum].add(endNum);
		}
		reader.close();

		next = new int[52][0];
		for (int i = 0; i < 52; i++) {
			next[i] = new int[nextList[i].size()];
			for (int j = 0; j < nextList[i].size(); j++) {
				next[i][j] = nextList[i].get(j);
			}
		}

		int[][][] dp = new int[numL + 1][numU + 1][52];
		for (int i = 0; i < 26; i++) {
			dp[1][0][i] = 1;
		}
		for (int i = 26; i < 52; i++) {
			dp[0][1][i] = 1;
		}

		for (int cL = 0; cL <= numL; cL++) {
			for (int cU = 0; cU <= numU; cU++) {
				for (int last = 0; last < 52; last++) {
					int currentNum = dp[cL][cU][last];
					for (int i = 0; i < next[last].length; i++) {
						int nextChar = next[last][i];
						if (nextChar < 26 && cL < numL) {
							dp[cL + 1][cU][nextChar] += currentNum;
							dp[cL + 1][cU][nextChar] %= 97654321;
						} else if (nextChar >= 26 && cU < numU) {
							dp[cL][cU + 1][nextChar] += currentNum;
							dp[cL][cU + 1][nextChar] %= 97654321;
						}
					}
				}
			}
		}

		int ans = 0;
		for (int i = 0; i < 52; i++) {
			ans += dp[numL][numU][i];
			ans %= 97654321;
		}

		System.out.println(ans);
	}

}
