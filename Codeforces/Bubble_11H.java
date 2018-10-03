import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Bubble_11H {

	static final long MOD = 1_000_000_007;

	static int[] sCnts;
	static int[] cnts;

	static long[] fact = new long[200_001];
	static long[] iFact = new long[200_001];

	public static void main(String[] args) throws IOException {
		fact[0] = 1;
		for (int i = 1; i <= 200_000; i++) {
			fact[i] = fact[i - 1] * i % MOD;
		}

		iFact[200_000] = 750007460;
		for (int i = 200_000 - 1; i >= 0; i--) {
			iFact[i] = iFact[i + 1] * (i + 1) % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		String aStr = reader.readLine();
		String bStr = reader.readLine();

		sCnts = new int[] { Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine()),
				Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine()) };

		int[] aArr = new int[aStr.length()];
		for (int i = 0; i < aStr.length(); i++) {
			aArr[i] = aStr.charAt(i) == '1' ? 1 : 0;
		}
		int[] bArr = new int[bStr.length()];
		for (int i = 0; i < bStr.length(); i++) {
			bArr[i] = bStr.charAt(i) == '1' ? 1 : 0;
		}

		long sum = process(bArr);
		sum = (sum - process(aArr) + MOD) % MOD;

		for (int i = 0; i + 1 < aArr.length; i++) {
			sCnts[aArr[i] * 2 + aArr[i + 1]]--;
		}

		if (sCnts[0] == 0 && sCnts[1] == 0 && sCnts[2] == 0 && sCnts[3] == 0) {
			sum = (sum + 1) % MOD;
		}
		printer.println(sum);
		printer.close();
	}

	static long process(int[] uBound) {
		cnts = Arrays.copyOf(sCnts, 4);
		if (cnts[2] < cnts[1]) { // 1->0 has to be no less than 0->1
			return 0;
		}
		if (cnts[2] > cnts[1] + 1) { // 1->0 cannot be more than 1 more than 0->1
			return 0;
		}

		int nDigits = cnts[0] + cnts[1] + cnts[2] + cnts[3] + 1;
		if (nDigits == 1) {
			return 1;
		}

		if (nDigits > uBound.length) { // anything we make will be greater than uBound
			return 0;
		}

		if (nDigits < uBound.length) { // anything we make will be less than uBound
			return compute1();
		}

		long sum = 0;

		// case where it doesn't have the first one
		// then the sequence must start with 0 which is invalid

		// case where it misses a one after the first
		for (int i = 1; i < uBound.length; i++) {
			if (uBound[i] == 1) {
				// case: it becomes 0
				cnts[uBound[i - 1] * 2 + 0]--;
				sum = (sum + compute0()) % MOD;
				cnts[uBound[i - 1] * 2 + 0]++;
			}
			cnts[uBound[i - 1] * 2 + uBound[i]]--;
		}

		if (cnts[0] == 0 && cnts[1] == 0 && cnts[2] == 0 && cnts[3] == 0) {
			sum++;
		}
		return sum;
	}

	// first one is a 0
	static long compute0() {
		if (cnts[0] < 0 || cnts[1] < 0 || cnts[2] < 0 || cnts[3] < 0) {
			return 0;
		}

		if (cnts[1] < cnts[2]) { // 0->1 has to be no less than 1->0
			return 0;
		}
		if (cnts[1] > cnts[2] + 1) {
			return 0;
		}

		int num0 = 1 + cnts[2];
		int num1 = cnts[1];

		long ans = comb(cnts[0] + num0 - 1, num0 - 1) * comb(cnts[3] + num1 - 1, num1 - 1) % MOD;
		return ans;
	}

	static long compute1() {
		if (cnts[0] < 0 || cnts[1] < 0 || cnts[2] < 0 || cnts[3] < 0) {
			return 0;
		}

		if (cnts[2] < cnts[1]) { // 1-01 has to be no less than 0->1
			return 0;
		}
		if (cnts[2] > cnts[1] + 1) {
			return 0;
		}

		int num0 = cnts[2];
		int num1 = 1 + cnts[1];

		long ans = comb(cnts[0] + num0 - 1, num0 - 1) * comb(cnts[3] + num1 - 1, num1 - 1) % MOD;
		return ans;
	}

	static long comb(int a, int b) {
		if (b == -1) {
			if (a == -1) {
				return 1;
			}
			return 0;
		}
		return fact[a] * iFact[b] % MOD * iFact[a - b] % MOD;
	}
}
