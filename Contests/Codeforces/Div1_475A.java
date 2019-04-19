import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_475A {

	static final long MOD = 1_000_000_009;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		long a = Integer.parseInt(inputData.nextToken());
		long b = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());
		long[] aPow = new long[k + 1];
		long[] bPow = new long[k + 1];
		aPow[0] = 1;
		bPow[0] = 1;
		for (int i = 1; i <= k; i++) {
			aPow[i] = aPow[i - 1] * a % MOD;
			bPow[i] = bPow[i - 1] * b % MOD;
		}

		long aInv = inv(a);

		String inpLine = reader.readLine();
		int[] repVals = new int[k];
		for (int i = 0; i < k; i++) {
			repVals[i] = inpLine.charAt(i) == '+' ? 1 : -1;
		}

		long sum = 0;
		long curCoeff = pow(a, n);
		long ratio = aInv * b % MOD;

		for (int i = 0; i < k; i++) {
			sum = (sum + curCoeff * repVals[i] + MOD) % MOD;
			curCoeff = curCoeff * ratio % MOD;
		}

		long numBlks = (n + 1) / k;
		long aKInv = inv(aPow[k]);
		long blkRatio = aKInv * bPow[k] % MOD;

		long gSum;

		if (blkRatio != 1) {
			gSum = (pow(blkRatio, numBlks) - 1) * inv(blkRatio - 1) % MOD;
		} else {
			gSum = numBlks % MOD;
		}
		printer.println(gSum * sum % MOD);
		printer.close();
	}

	static long pow(long base, long exp) {
		base %= MOD;
		long cur = 1;
		while (exp > 0) {
			if ((exp & 1) == 1) {
				cur = cur * base % MOD;
			}
			exp >>= 1;
			base = base * base % MOD;
		}
		return cur;
	}

	static long inv(long number) {
		return pow(number, MOD - 2);
	}

}
