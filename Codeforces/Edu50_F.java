import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Edu50_F {

	static final int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59 };

	public static void main(String[] args) throws IOException {
		int[] mult = new int[61];

		bSetLoop:
		for (int bSet = 1; bSet < (1 << 17); bSet++) {
			int bCnt = Integer.bitCount(bSet);

			int prod = 1;
			for (int i = 0; i < 17; i++) {
				if ((bSet & (1 << i)) != 0) {
					prod *= primes[i];
					if (prod > 60) {
						continue bSetLoop;
					}
				}
			}

			if ((bCnt & 1) == 1) {
				mult[prod]++;
			} else {
				mult[prod]--;
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nQ = Integer.parseInt(reader.readLine());


		while (nQ-- > 0) {
			long qMax = Long.parseLong(reader.readLine());
			long ans = 0;
			for (int i = 2; i <= 60; i++) {
				ans += mult[i] * cnt(i, qMax);
			}
			printer.println(qMax - ans - 1);
		}
		printer.close();
	}

	static long cnt(int pow, long max) {
		long approx = (long) (Math.pow(max, 1.0 / pow));
		if (!greater(approx + 1, pow, max)) {
			return approx;
		} else if (!greater(approx, pow, max)) {
			return approx - 1;
		} else {
			return approx - 2;
		}
	}

	static boolean greater(long base, int pow, long bnd) {
		long stepA = bnd / base;
		long cur = 1;
		for (int i = 1; i <= pow - 1; i++) {
			cur *= base;
			if (cur > stepA) {
				return true;
			}
		}
		return false;
	}
}
