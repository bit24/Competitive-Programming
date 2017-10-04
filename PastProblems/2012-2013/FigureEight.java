import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FigureEight {

	static int size;

	static boolean[][] ok;
	static int[][] prefixBAD;

	static int[][][] tHeight;
	static int[][][] bHeight;

	static int[][][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("eight.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("eight.out")));
		size = Integer.parseInt(reader.readLine());
		ok = new boolean[size + 2][size + 2];

		for (int i = 1; i <= size; i++) {
			String nextLine = reader.readLine();
			for (int j = 1; j <= size; j++) {
				ok[i][j] = nextLine.charAt(j - 1) == '.';
			}
		}
		reader.close();

		computePrefix();
		tHeight();
		bHeight();
		computeDP();

		long ans = 0;

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				for (int k = j + 2; k <= size; k++) {
					long value = (long) tHeight[i][j][k] * (k - j - 1) * dp[i][j][k];
					if (ans < value) {
						ans = value;
					}
				}
			}
		}
		if (ans == 0) {
			printer.println(-1);
		} else {
			printer.println(ans);
		}
		printer.close();
	}

	static void computePrefix() {
		prefixBAD = new int[size + 2][size + 2];
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				prefixBAD[i][j] = (prefixBAD[i][j - 1] + (ok[i][j] ? 0 : 1));
			}
		}
	}

	static void tHeight() {
		tHeight = new int[size + 2][size + 2][size + 2];

		for (int left = 1; left <= size; left++) {
			for (int right = left + 2; right <= size; right++) {

				int best = -1;

				for (int row = 1; row <= size; row++) {
					if (best == -1 && (prefixBAD[row][right] - prefixBAD[row][left - 1]) == 0) {
						best = row;
					}
					if ((!ok[row][left]) || (!ok[row][right])) {
						best = -1;
					}

					if ((prefixBAD[row][right] - prefixBAD[row][left - 1]) == 0 && best != -1 && row - best >= 2) {
						tHeight[row][left][right] = row - best - 1;
					} else {
						tHeight[row][left][right] = 0;
					}
				}
			}
		}
	}

	static void bHeight() {
		bHeight = new int[size + 2][size + 2][size + 2];
		for (int left = 1; left <= size; left++) {
			for (int right = left + 2; right <= size; right++) {

				int best = -1;

				for (int row = size; row >= 1; row--) {
					if (best == -1 && (prefixBAD[row][right] - prefixBAD[row][left - 1]) == 0) {
						best = row;
					}
					if ((!ok[row][left]) || (!ok[row][right])) {
						best = -1;
					}

					if ((prefixBAD[row][right] - prefixBAD[row][left - 1]) == 0 && best != -1 && best - row >= 2) {
						bHeight[row][left][right] = best - row - 1;
					} else {
						bHeight[row][left][right] = 0;
					}
				}
			}
		}
	}

	static void computeDP() {
		dp = bHeight;

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				for (int k = j + 2; k <= size; k++) {
					dp[i][j][k] = (k - j - 1) * bHeight[i][j][k];
				}
			}
		}

		for (int row = 1; row <= size; row++) {
			for (int length = size; length >= 1; length--) {
				for (int left = 1; left + length - 1 <= size; left++) {
					int right = left + length - 1;
					dp[row][left][right] = max(dp[row][left][right],
							max(dp[row][left - 1][right], dp[row][left][right + 1]));
				}
			}
		}
	}

	static int max(int a, int b) {
		return a > b ? a : b;
	}
}