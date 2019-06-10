import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FortMoo {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fortmoo.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fortmoo.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nR = Integer.parseInt(inputData.nextToken());
		int nC = Integer.parseInt(inputData.nextToken());

		boolean[][] ok = new boolean[nR + 1][nC + 1];
		int[][] pSum = new int[nR + 1][nC + 1];
		for (int i = 1; i <= nR; i++) {
			String nLine = " " + reader.readLine();
			for (int j = 1; j <= nC; j++) {
				ok[i][j] = nLine.charAt(j) == '.';
				pSum[i][j] = ok[i][j] ? pSum[i][j - 1] + 1 : pSum[i][j - 1];
			}
		}

		int mA = 0;
		for (int fL = 1; fL <= nC; fL++) {
			for (int fR = fL + 1; fR <= nC; fR++) {
				int lA = -1;
				int mH = 0;
				for (int cR = 1; cR <= nR; cR++) {
					if (!ok[cR][fL] || !ok[cR][fR]) {
						lA = -1;
					} else if (pSum[cR][fR] - pSum[cR][fL - 1] == fR - fL + 1) {
						if (lA != -1) {
							mH = Math.max(mH, cR - lA + 1);
						} else {
							lA = cR;
						}
					}
				}
				mA = Math.max(mA, mH * (fR - fL + 1));
			}
		}

		reader.close();
		printer.println(mA);
		printer.close();
	}

}
