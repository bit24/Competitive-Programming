import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_422D {

	public static void main(String[] args) throws IOException {
		long[] mCost = new long[5_000_001];
		Arrays.fill(mCost, Long.MAX_VALUE);
		mCost[1] = 0;

		for (int cur = 1; cur <= 5_000_000; cur++) {
			long nCost = mCost[cur] + cur;
			for (int prod = cur * 2; prod <= 5_000_000; prod += cur) {
				if (nCost < mCost[prod]) {
					mCost[prod] = nCost;
				}
				nCost += prod;
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long t = Long.parseLong(inputData.nextToken());
		int l = Integer.parseInt(inputData.nextToken());
		int r = Integer.parseInt(inputData.nextToken());

		final long MOD = 1_000_000_007;

		long prod = 1;
		long sum = 0;

		for (int i = l; i <= r; i++) {
			sum = (sum + prod * (mCost[i] % MOD)) % MOD;
			prod = prod * t % MOD;
		}
		System.out.println(sum);
	}
}
