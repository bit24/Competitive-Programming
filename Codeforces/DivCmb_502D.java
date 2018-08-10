import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_502D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int len = Integer.parseInt(inputData.nextToken());
		int setS = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		int[] w = new int[len];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < len; i++) {
			w[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] cnt = new int[1 << len];
		for (int i = 0; i < setS; i++) {
			cnt[Integer.parseUnsignedInt(reader.readLine(), 2)]++;
		}

		int[][] pQ = new int[1 << len][101];

		for (int qStr = 0; qStr < (1 << len); qStr++) {
			for (int pStr = 0; pStr < (1 << len); pStr++) {
				int sum = 0;

				for (int i = 0; i < len; i++) {
					if ((qStr & (1 << i)) == (pStr & (1 << i))) {
						sum += w[len - 1 - i];
					}
				}
				if (sum <= 100) {
					pQ[qStr][sum] += cnt[pStr];
				}
			}
		}

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int qStr = Integer.parseUnsignedInt(inputData.nextToken(), 2);
			int K = Integer.parseInt(inputData.nextToken());
			int ans = 0;
			for (int i = 0; i <= K; i++) {
				ans += pQ[qStr][i];
			}
			printer.println(ans);
		}
		printer.close();
	}
}
