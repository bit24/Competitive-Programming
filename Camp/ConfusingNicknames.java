import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ConfusingNicknames {

	long BASE1 = 88919;
	long BASE2 = 110501;

	long[] POW1;
	long[] POW2;

	final long MOD = 1_000_000_123;

	int nA;
	int nB;

	char[] a;
	char[] b;

	public static void main(String[] args) throws IOException {
		new ConfusingNicknames().main();
	}

	void main() throws IOException {
		POW1 = new long[100_001];
		POW2 = new long[100_001];
		POW1[0] = 1;
		POW2[0] = 1;

		for (int i = 1; i <= 100_000; i++) {
			POW1[i] = POW1[i - 1] * BASE1 % MOD;
			POW2[i] = POW2[i - 1] * BASE2 % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nA = Integer.parseInt(inputData.nextToken());
		nB = Integer.parseInt(inputData.nextToken());

		String aStr = reader.readLine();
		String bStr = reader.readLine();

		a = aStr.toCharArray();
		b = bStr.toCharArray();

		printer.println(binSearch());
		printer.close();
	}

	int binSearch() {
		int low = 0;
		int high = Math.min(nA, nB);
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (match(mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	boolean match(int len) {
		if (len == 0) {
			return true;
		}
		Hash[] hashes = new Hash[nA - len + 1];

		Hash cHash = new Hash(0, 0);
		for (int i = 0; i < len; i++) {
			cHash = cHash.add(a[i]);
		}

		hashes[0] = cHash;
		for (int i = 1; i + len - 1 < nA; i++) {
			hashes[i] = hashes[i - 1].shift(a[i - 1], a[i + len - 1], len);
		}
		Arrays.sort(hashes);

		cHash = new Hash(0, 0);
		for (int i = 0; i < len; i++) {
			cHash = cHash.add(b[i]);
		}

		if (Arrays.binarySearch(hashes, cHash) >= 0) {
			return true;
		}

		for (int i = 1; i + len - 1 < nB; i++) {
			cHash = cHash.shift(b[i - 1], b[i + len - 1], len);
			if (Arrays.binarySearch(hashes, cHash) >= 0) {
				return true;
			}
		}
		return false;
	}

	class Hash implements Comparable<Hash> {
		long h1;
		long h2;

		Hash(long h1, long h2) {
			this.h1 = h1;
			this.h2 = h2;
		}

		Hash add(char nxt) {
			return new Hash((h1 * BASE1 + nxt) % MOD, (h2 * BASE2 + nxt) % MOD);
		}

		Hash shift(char o, char n, int l) {
			return new Hash(((h1 - POW1[l - 1] * o % MOD + MOD) % MOD * BASE1 + n) % MOD,
					((h2 - POW2[l - 1] * o % MOD + MOD) % MOD * BASE2 + n) % MOD);
		}

		public int compareTo(Hash o) {
			if (h1 != o.h1) {
				return h1 < o.h1 ? -1 : 1;
			}
			return Long.compare(h2, o.h2);
		}
	}
}