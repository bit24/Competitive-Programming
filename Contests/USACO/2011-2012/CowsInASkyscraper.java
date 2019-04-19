import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CowsInASkyscraper {

	static int limit;
	static int[] cost;
	static int[] ind;

	static PrintWriter printer;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("skyscraper.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nI = Integer.parseInt(inputData.nextToken());
		limit = Integer.parseInt(inputData.nextToken());
		cost = new int[nI];
		ind = new int[nI];
		for (int i = 0; i < nI; i++) {
			cost[nI - 1 - i] = Integer.parseInt(reader.readLine());
			ind[nI - 1 - i] = i + 1;
		}
		reader.close();
		printer = new PrintWriter(new BufferedWriter(new FileWriter("skyscraper.out")));
		nextTrip(nI, true);
		printer.close();
	}

	static void nextTrip(int nC, boolean pNT) {
		if (nC == 0) {
			return;
		}

		int[] sum = new int[1 << nC];
		for (int i = 0; i < (1 << nC); i++) {
			for (int j = 0; j < nC; j++) {
				if ((i & (1 << j)) != 0) {
					sum[i] += cost[j];
				}
			}
		}

		int[] mPreT = new int[1 << nC];
		// mPreTr stores the maximum cost of the a subset that can be transported in less than cT trips

		int allS = (1 << nC) - 1;
		int cTCnt = 1;
		while (true) {
			if (cTCnt > 1) {
				for (int i = 0; i < (1 << nC); i++) {
					for (int j = 0; j < nC; j++) {
						if ((i & (1 << j)) == 0) {
							mPreT[i | (1 << j)] = Math.max(mPreT[i | (1 << j)], mPreT[i]);
						}
					}
				}
			}

			if (sum[allS] - mPreT[allS] <= limit) {
				if (pNT) {
					printer.println(cTCnt);
				}

				for (int pPreT = 0; pPreT < (1 << nC); pPreT++) {
					if (sum[pPreT] == mPreT[pPreT] && sum[allS] - mPreT[pPreT] <= limit) {
						int preT = pPreT;
						int cT = allS & (~preT);
						printer.print(Integer.bitCount(cT));
						// ordering purposes
						for (int i = nC - 1; i >= 0; i--) {
							if ((cT & (1 << i)) != 0) {
								printer.print(" " + ind[i]);
							}
						}
						printer.println();
						// reconstruction of other trips
						int nF = 0;
						for (int i = 0; i < nC; i++) {
							if ((preT & (1 << i)) != 0) {
								cost[nF] = cost[i];
								ind[nF] = ind[i];
								nF++;
							}
						}
						nextTrip(nF, false);
						return;
					}
				}
			}
			for (int i = 0; i < (1 << nC); i++) {
				mPreT[i] = sum[i] - mPreT[i] <= limit ? sum[i] : 0;
			}
			cTCnt++;
		}
	}

}
