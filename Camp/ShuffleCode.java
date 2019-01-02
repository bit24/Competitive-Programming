import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ShuffleCode {

	static long[] cnts;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int mode = Integer.parseInt(inputData.nextToken());
		int N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());

		cnts = new long[N + 1];
		cnts[0] = 1;
		cnts[1] = 1;

		for (int i = 2; i <= N; i++) {
			cnts[i] = 1L << (i - 2);
			cnts[i] += 2 * cnts[i - 2];
		}

		if (mode == 0) {
			long M = cnts[N];
			printer.println(M);

			while (Q-- > 0) {
				long cQ = Long.parseLong(reader.readLine());
				cQ %= M;
				if (cQ == 0) {
					cQ = M;
				}

				int[] ans = new int[N];
				for (int i = 0; i < N / 2; i++) {
					int nLen = N - 2 * (i + 1);

					if (cQ <= 1L << nLen) {
						ans[i] = 0;
						ans[N - 1 - i] = 0;

						cQ--;

						for (int j = 0; j < nLen; j++) {
							ans[N - 1 - i - 1 - j] = (cQ & (1L << j)) == 0 ? 0 : 1;
						}

						break;
					}
					cQ -= 1L << nLen;

					if (cQ <= cnts[nLen]) {
						ans[i] = 0;
						ans[N - 1 - i] = 1;
						continue;
					}
					cQ -= cnts[nLen];

					ans[i] = 1;
					ans[N - 1 - i] = 0;
				}

				for (int i = 0; i < N; i++) {
					printer.print(ans[i]);
				}
				printer.println();
			}
		} else {
			long M = cnts[N];

			while (Q-- > 0) {
				long cQ = 1;

				String line = reader.readLine();
				for (int i = 0; i < N / 2; i++) {
					int nLen = N - 2 * (i + 1);

					if (line.charAt(i) == '0' && line.charAt(N - 1 - i) == '0') {
						if (nLen != 0) {
							cQ += Long.parseLong(line.substring(i + 1, N - 1 - i), 2);
						}
						break;
					}
					if (line.charAt(i) == '1' && line.charAt(N - 1 - i) == '1') {
						StringBuilder str = new StringBuilder(line.substring(i + 1, N - 1 - i));
						str.reverse();
						if (nLen != 0) {
							cQ += Long.parseLong(str.toString(), 2) ^ ((1L << nLen) - 1);
						}
						break;
					}

					cQ += 1L << nLen;

					if (line.charAt(i) == '0' && line.charAt(N - 1 - i) == '1') {
						continue;
					}

					cQ += cnts[nLen];
				}

				cQ %= M;
				printer.println(cQ);
			}
		}

		printer.close();
	}
}
