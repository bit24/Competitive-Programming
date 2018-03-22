
public class RollHash {
	static long[] pre1;
	static long[] pre2;

	static long[] pow1;
	static long[] pow2;

	static void hash(int[] items) {
		pow1 = new long[items.length + 1];
		pow2 = new long[items.length + 1];
		pow1[0] = pow2[0] = 1;
		for (int i = 1; i < items.length; i++) {
			pow1[i] = pow1[i - 1] * 41 % 1_000_000_007;
			pow2[i] = pow2[i - 1] * 100_000_007;
		}

		pre1[0] = items[0] % 1_000_000_007;
		pre2[0] = items[0];
		for (int i = 1; i < items.length; i++) {
			pre1[i] = (pre1[i - 1] * 41 + items[i]) % 1_000_000_007;
			pre2[i] = pre1[i - 1] * 100_000_007 + items[i];
		}
	}
}
