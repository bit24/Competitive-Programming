import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_419C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numR = Integer.parseInt(inputData.nextToken());
		int numC = Integer.parseInt(inputData.nextToken());

		int[][] tar = new int[numR + 1][numC + 1];
		for (int i = 1; i <= numR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 1; j <= numC; j++) {
				tar[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		int[] rowA = new int[numR + 1];
		int[] colA = new int[numC + 1];

		int[] rowAns = null;
		int[] colAns = null;

		int mCost = Integer.MAX_VALUE;
		rALoop:
		for (int fRA = 0; fRA <= tar[1][1]; fRA++) {
			int fCA = tar[1][1] - fRA;
			rowA[1] = fRA;
			colA[1] = fCA;

			int cost = tar[1][1];

			for (int i = 2; i <= numR; i++) {
				if (tar[i][1] < fCA) {
					continue rALoop;
				}
				rowA[i] = tar[i][1] - fCA;
				cost += rowA[i];
			}

			for (int i = 2; i <= numC; i++) {
				if (tar[1][i] < fRA) {
					continue rALoop;
				}
				colA[i] = tar[1][i] - fRA;
				cost += colA[i];
			}

			for (int i = 1; i <= numR; i++) {
				for (int j = 1; j <= numC; j++) {
					if (tar[i][j] != rowA[i] + colA[j]) {
						continue rALoop;
					}
				}
			}

			if (cost < mCost) {
				mCost = cost;
				rowAns = Arrays.copyOf(rowA, numR + 1);
				colAns = Arrays.copyOf(colA, numC + 1);
			}
		}

		if (mCost == Integer.MAX_VALUE) {
			printer.println(-1);
		} else {
			printer.println(mCost);
			for (int i = 1; i <= numR; i++) {
				for (int j = 0; j < rowAns[i]; j++) {
					printer.println("row " + i);
				}
			}
			
			for (int i = 1; i <= numC; i++) {
				for (int j = 0; j < colAns[i]; j++) {
					printer.println("col " + i);
				}
			}
		}
		printer.close();
	}

}
