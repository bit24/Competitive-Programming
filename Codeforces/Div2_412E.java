import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_412E {

	static int[] pOf2Cnt;
	static int[] lessCnt;
	static int[] pOf2CntM;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numE = Integer.parseInt(reader.readLine());

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		reader.close();

		long[] pOf2 = new long[41];
		pOf2[0] = 1;
		for (int i = 1; i <= 40; i++) {
			pOf2[i] = pOf2[i - 1] << 1;
		}

		pOf2Cnt = new int[41];
		lessCnt = new int[41];
		pOf2CntM = new int[41];

		for (int i = 0; i < numE; i++) {
			long nLong = Long.parseLong(inputData.nextToken());
			for (int j = 0; j <= 40; j++) {
				if (nLong < pOf2[j]) {
					lessCnt[j]++;
					break;
				} else if (nLong == pOf2[j]) {
					pOf2Cnt[j]++;
					break;
				}
			}
		}

		pOf2CntM[0] = pOf2Cnt[0];
		for (int i = 1; i <= 40; i++) {
			pOf2CntM[i] = Math.min(pOf2CntM[i - 1], pOf2Cnt[i]);
		}

		if (!possible(pOf2Cnt[0])) {
			printer.println(-1);
			printer.close();
			return;
		}

		int low = 0;
		int high = pOf2Cnt[0];

		// finds the min value for which it is still possible
		while (low < high) {
			int mid = (low + high) >> 1;

			if (!possible(mid)) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}

		for (int i = low; i <= pOf2Cnt[0]; i++) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}

	static boolean possible(int numCh) {
		int[] numEnd = new int[41];
		int[] numRem = Arrays.copyOf(lessCnt, 42);

		int numCmp = 0;
		for (int cPow = 40; cPow >= 0; cPow--) {
			int extra = Math.min(numCh - numCmp, pOf2CntM[cPow] - numCmp);
			numCmp += extra;
			numEnd[cPow] = extra;
			numRem[cPow] += pOf2Cnt[cPow] - numCmp;
		}

		int cRem = numRem[0];

		for (int cEnd = 0; cEnd <= 40; cEnd++) {
			cRem += numRem[cEnd + 1];
			int sub = Math.min(numEnd[cEnd], cRem);
			cRem -= sub;
		}
		return cRem == 0;
	}

}
