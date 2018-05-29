import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Grazing {

	static int nR;
	static int nC;
	static int[][] grid;
	static int tSum = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nR = Integer.parseInt(inputData.nextToken());
		nC = Integer.parseInt(inputData.nextToken());

		grid = new int[nR][nC];
		for (int i = 0; i < nR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < nC; j++) {
				tSum += grid[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		// if bit at index i is unset, item i goes first
		// 0 = left first (left -> right)
		int[][] dp = new int[nR][1 << (nC - 1)];

		for (int bSet = 0; bSet < 1 << (nC - 1); bSet++) {
			int cSum = 0;
			for (int i = 0; i < nC - 1; i++) {
				if ((bSet & (1 << i)) != 0) {
					cSum += grid[0][i + 1];
				} else {
					cSum += grid[0][i];
				}
			}
			dp[0][bSet] = cSum;
		}

		for (int cR = 1; cR < nR; cR++) {
			for (int pSet = 0; pSet < 1 << (nC - 1); pSet++) {
				int pCnt = dp[cR - 1][pSet];

				for (int cSet = 0; cSet < (1 << (nC - 1)); cSet++) {
					int hSum = 0;
					for (int i = 0; i < nC - 1; i++) {
						if ((cSet & (1 << i)) != 0) {
							hSum += grid[cR][i + 1];
						} else {
							hSum += grid[cR][i];
						}
					}

					// sweep left to right and maximize value added with ups and downs
					// arrow = next
					int lU = 0;
					int lD = 0;

					for (int i = 0; i < nC; i++) {
						int nU = lU + grid[cR][i];
						int nD = lD + grid[cR - 1][i];
						if (i == 0 || !((pSet & (1 << (i - 1))) != 0 && ((cSet & (1 << (i - 1))) == 0))) {
							nU = Math.max(nU, lD + grid[cR][i]);
						}
						if (i == 0 || !((pSet & (1 << (i - 1))) == 0 && ((cSet & (1 << (i - 1))) != 0))) {
							nD = Math.max(nD, lU + grid[cR - 1][i]);
						}
						lU = nU;
						lD = nD;
					}

					dp[cR][cSet] = Math.max(dp[cR][cSet], pCnt + hSum + Math.max(lU, lD));
				}
			}
		}

		int max = 0;

		for (int i = 0; i < (1 << (nC - 1)); i++) {
			max = Math.max(max, dp[nR - 1][i]);
		}
		printer.println(max + tSum);
		printer.close();
	}
}
