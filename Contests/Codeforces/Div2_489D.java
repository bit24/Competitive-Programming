import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_489D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		long k = Integer.parseInt(inputData.nextToken());
		long[] a = new long[n + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= n; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		int lN1 = 0;
		int[] pN1 = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			pN1[i] = lN1;
			if (a[i] != 1) {
				lN1 = i;
			}
		}

		long[] pSum = new long[n + 1];
		for (int i = 1; i <= n; i++) {
			pSum[i] = pSum[i - 1] + a[i];
		}

		long ans = 0;
		for (int r = 1; r <= n; r++) {
			long cProd = a[r];
			long cSum = a[r];
			int cN1 = r;

			for (int i = 0; i < 70; i++) {
				int cPN1 = pN1[cN1];
				if ((cProd - k * cSum) % k == 0) {
					long x = (cProd - k * cSum) / k;
					if (x >= 0) {
						if (cN1 - x > cPN1) {
							ans++;
						}
					}
				}

				if (cPN1 == 0) {
					break;
				}

				if (a[cPN1] > Long.MAX_VALUE / cProd) {
					break;
				}

				cProd *= a[cPN1];
				cSum += pSum[cN1 - 1] - pSum[cPN1 - 1];
				cN1 = cPN1;

				if (cProd > 2_000_000_000_000_000_000L || cProd < 0) {
					break;
				}
			}
		}
		printer.println(ans);
		printer.close();
	}
}
