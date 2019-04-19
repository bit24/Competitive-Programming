import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CookieClicker {

	static int N;
	static long K;
	static long pA;
	static long pB;

	static long[] cA;
	static long[] cB;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Long.parseLong(inputData.nextToken());
		pA = Long.parseLong(inputData.nextToken());
		pB = Long.parseLong(inputData.nextToken());

		cA = new long[N + 1];
		cB = new long[N + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= N; i++) {
			cA[i] = Long.parseLong(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= N; i++) {
			cB[i] = Long.parseLong(inputData.nextToken());
		}

		long[] cPrim = new long[N + 1];
		long[] nPrim = new long[N + 1]; // minimize

		long[] cSec = new long[N + 1];
		long[] nSec = new long[N + 1]; // maximize

		Arrays.fill(cPrim, Long.MAX_VALUE);
		Arrays.fill(nPrim, Long.MAX_VALUE);
		cPrim[0] = 0;

		long ans = Long.MAX_VALUE / 2;

		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= N; j++) {
				long cRate = pA * i + pB * j + 1;
				if (i + 1 <= N) {
					long eTime = (cA[i + 1] - cSec[j] + cRate - 1) / cRate;
					eTime = Math.max(eTime, 0);
					long nTime = cPrim[j] + eTime;
					long nRem = cSec[j] + eTime * cRate - cA[i + 1];

					if (nPrim[j] > nTime) {
						nPrim[j] = nTime;
						nSec[j] = nRem;
					} else if (nPrim[j] == nTime && nRem > nSec[j]) {
						nSec[j] = nRem;
					}
				}
				if (j + 1 <= N) {
					long eTime = (cB[j + 1] - cSec[j] + cRate - 1) / cRate;
					eTime = Math.max(eTime, 0);
					long nTime = cPrim[j] + eTime;
					long nRem = cSec[j] + eTime * cRate - cB[j + 1];

					if (cPrim[j + 1] > nTime) {
						cPrim[j + 1] = nTime;
						cSec[j + 1] = nRem;
					} else if (cPrim[j + 1] == nTime && nRem > cSec[j + 1]) {
						cSec[j + 1] = nRem;
					}
				}

				long time = cPrim[j] + Math.max(0, (K - cSec[j] + cRate - 1) / cRate);
				ans = Math.min(ans, time);
			}

			long[] temp = cPrim;
			cPrim = nPrim;
			nPrim = temp;
			Arrays.fill(nPrim, Long.MAX_VALUE / 2);
			temp = cSec;
			cSec = nSec;
			nSec = temp;
			Arrays.fill(nSec, 0);
		}
		printer.println(ans);
		printer.close();
	}
}