
public class HashSuffixArray {
	static final long MOD = 1_000_000_123;

	static final long B1 = 1_343_317;
	static final long B2 = 3_943_673;

	static long[] P1 = new long[200_000];
	static long[] P2 = new long[200_000];

	void main() {
		P1[0] = 1;
		P2[0] = 1;

		for (int i = 1; i < 200_000; i++) {
			P1[i] = P1[i - 1] * B1 % MOD;
			P2[i] = P2[i - 1] * B2 % MOD;
		}

		for (int i = 1; i <= N; i++) {
			h1[i] = (h1[i - 1] * B1 + patt.charAt(i)) % MOD;
			h2[i] = (h2[i - 1] * B2 + patt.charAt(i)) % MOD;
		}
	}

	String patt;
	int N;

	long[] h1;
	long[] h2;

	class Suffix implements Comparable<Suffix> {
		int i;

		Suffix(int i) {
			this.i = i;
		}

		public int compareTo(Suffix o) {
			int lcp = fLCP(i, o.i);
			return patt.charAt(i + lcp) - patt.charAt(o.i + lcp);
		}
	}

	int fLCP(int i, int j) {
		int low = 0;
		int high = N - Math.max(i, j) + 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if ((h1[i + mid - 1] - h1[j + mid - 1] + (h1[j - 1] - h1[i - 1]) * P1[mid]) % MOD == 0
					&& (h2[i + mid - 1] - h2[j + mid - 1] + (h2[j - 1] - h2[i - 1]) * P2[mid]) % MOD == 0) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}
}