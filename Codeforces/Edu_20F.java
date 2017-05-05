import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Edu_20F {

	static long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());
		int[] elements = new int[numE];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int[] count = new int[100_001];
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
			count[elements[i]]++;
		}
		reader.close();

		long[] powers = new long[100_001];
		powers[0] = 1;
		for (int i = 1; i <= 100_000; i++) {
			powers[i] = (powers[i - 1] * 2) % MOD;
		}

		// stores the number of possible subsets whose elements' GCD is...
		long[] cntSub_GCD = new long[100_001];

		// lazy man's PIE
		for (int cGCD = 100_000; cGCD >= 1; cGCD--) {
			int cnt_div = 0;
			for (int mult = cGCD; mult <= 100_000; mult += cGCD) {
				cnt_div += count[mult];
			}

			// divSetCnt gives the number of sets such that it's GCD is divisible by cGCD
			long divSetCnt = (powers[cnt_div] - 1 + MOD) % MOD;

			long fCount = divSetCnt;
			// exclude all sets such that their GCD is greater than that of cGCD
			for (int eGCD = cGCD * 2; eGCD <= 100_000; eGCD += cGCD) {
				fCount = (fCount - cntSub_GCD[eGCD] + MOD) % MOD;
			}
			cntSub_GCD[cGCD] = fCount;
		}
		System.out.println(cntSub_GCD[1]);
	}
}
