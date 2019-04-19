import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Raisins {

	static int[][] cnt;

	static int[][][][] cost;

	static int[][] sum;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nR = Integer.parseInt(inputData.nextToken());
		int nC = Integer.parseInt(inputData.nextToken());
		cnt = new int[nR + 1][nC + 1];
		for (int i = 1; i <= nR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 1; j <= nC; j++) {
				cnt[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		sum = new int[nR + 1][nC + 1];

		for (int i = 1; i <= nR; i++) {
			for (int j = 1; j <= nC; j++) {
				sum[i][j] = cnt[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
			}
		}

		cost = new int[nR + 1][nR + 1][nC + 1][nC + 1];
		for (int[][][] a : cost) {
			for (int[][] b : a) {
				for (int[] c : b) {
					Arrays.fill(c, Integer.MAX_VALUE);
				}
			}
		}

		printer.println(calc(1, nR, 1, nC));
		printer.close();
	}

	static int calc(int tR, int bR, int lC, int rC) {
		if (cost[tR][bR][lC][rC] != Integer.MAX_VALUE) {
			return cost[tR][bR][lC][rC];
		}

		if (tR == bR && lC == rC) {
			return cost[tR][bR][lC][rC] = 0;
		}

		int mCost = Integer.MAX_VALUE;
		for (int sR = tR; sR < bR; sR++) {
			mCost = Math.min(mCost, calc(tR, sR, lC, rC) + calc(sR + 1, bR, lC, rC));
		}

		for (int sC = lC; sC < rC; sC++) {
			mCost = Math.min(mCost, calc(tR, bR, lC, sC) + calc(tR, bR, sC + 1, rC));
		}
		return cost[tR][bR][lC][rC] = mCost + sum[bR][rC] - sum[bR][lC - 1] - sum[tR - 1][rC] + sum[tR - 1][lC - 1];
	}

}
