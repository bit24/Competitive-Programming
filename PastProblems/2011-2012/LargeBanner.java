import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class LargeBanner {

	public static void main(String[] args) throws IOException {
		int[][] pFact = new int[100_001][6];
		int[] pFCnt = new int[100_001];

		for (int i = 2; i <= 100_000; i++) {
			if (pFCnt[i] == 0) {
				for (int mult = i; mult <= 100_000; mult += i) {
					pFact[mult][pFCnt[mult]++] = i;
				}
			}
		}

		BufferedReader reader = new BufferedReader(new FileReader("banner.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("banner.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long widB = Integer.parseInt(inputData.nextToken());
		long lenB = Integer.parseInt(inputData.nextToken());
		long minHyp = Integer.parseInt(inputData.nextToken());
		long maxHyp = Integer.parseInt(inputData.nextToken());
		long mod = Integer.parseInt(inputData.nextToken());
		reader.close();

		long fAns = 0;
		for (int cWid = 1; cWid <= Math.min(widB, maxHyp); cWid++) {

			long eSum = 0;
			for (int eSet = 1; eSet < (1 << pFCnt[cWid]); eSet++) {
				int prod = 1;
				for (int j = 0; j < pFCnt[cWid]; j++) {
					if ((eSet & (1 << j)) != 0) {
						prod *= pFact[cWid][j];
					}
				}

				// (prod * m)^2 + cWid^2 >= minHyp^2
				long minM = Math.max((long) Math.ceil(Math.sqrt((minHyp - cWid) * (minHyp + cWid)) / prod), 1);
				long maxM = Math.min((long) (Math.sqrt((maxHyp - cWid) * (maxHyp + cWid)) / prod), lenB / prod);

				if (minM <= maxM) {
					long pSum = (long) (lenB - minM * prod + 1 + lenB - maxM * prod + 1) * (maxM - minM + 1) / 2 % mod;
					if ((Integer.bitCount(eSet) & 1) == 1) {
						eSum += pSum;
					} else {
						eSum -= pSum;
					}
					eSum %= mod;
				}
			}

			long numW = widB - cWid + 1;

			long minL = Math.max((long) Math.ceil(Math.sqrt((minHyp - cWid) * (minHyp + cWid))), 1);
			long maxL = Math.min((long) Math.sqrt((maxHyp - cWid) * (maxHyp + cWid)), lenB);
			long minR = lenB - maxL + 1;
			long maxR = lenB - minL + 1;
			long numL = 0;
			if (minR <= maxR) {
				numL = ((long) (minR + maxR) * (maxR - minR + 1) / 2 - eSum) % mod;
			}

			fAns = (fAns + numW * numL) % mod;
		}
		fAns *= 2;
		if (minHyp == 1) {
			fAns += (widB + 1) * lenB + (lenB + 1) * widB;
		}
		fAns %= mod;
		printer.println(fAns);
		printer.close();
	}

}
