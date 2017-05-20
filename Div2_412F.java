import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_412F {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numV = Integer.parseInt(reader.readLine());

		long[][] aMat = new long[numV][numV];
		long[] mCost = new long[numV];

		long oMin = Long.MAX_VALUE / 8;
		for (int i = 0; i < numV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());

			for (int j = i + 1; j < numV; j++) {
				long nLong = Long.parseLong(inputData.nextToken());
				aMat[i][j] = nLong;
				aMat[j][i] = nLong;
				if (nLong < oMin) {
					oMin = nLong;
				}
			}
		}
		reader.close();

		for (int i = 0; i < numV; i++) {
			for (int j = 0; j < numV; j++) {
				if (i != j) {
					aMat[i][j] -= oMin;
				}
			}
		}

		for (int i = 0; i < numV; i++) {
			long mEdge = Long.MAX_VALUE / 8;
			for (int j = 0; j < numV; j++) {
				if (i != j && aMat[i][j] < mEdge) {
					mEdge = aMat[i][j];
				}
			}
			mCost[i] = mEdge << 1;
		}

		boolean[] fin = new boolean[numV];
		for (int i = 0; i < numV; i++) {
			int cV = -1;

			for (int j = 0; j < numV; j++) {
				if (!fin[j] && (cV == -1 || mCost[j] < mCost[cV])) {
					cV = j;
				}
			}

			for (int adj = 0; adj < numV; adj++) {
				if (adj != cV && mCost[cV] + aMat[cV][adj] < mCost[adj]) {
					mCost[adj] = mCost[cV] + aMat[cV][adj];
				}
			}
			fin[cV] = true;
		}

		for (int i = 0; i < numV; i++) {
			printer.println(mCost[i] + (numV - 1) * oMin);
		}
		printer.close();
	}
}
