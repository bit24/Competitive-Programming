import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Edu_48G {

	static int N;
	static long X;
	static long Y;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		X = Long.parseLong(inputData.nextToken());
		Y = Long.parseLong(inputData.nextToken());

		if (Y % X != 0) {
			printer.println(0);
			printer.close();
			return;
		}

		long[] fact = new long[20];
		int nF = 0;

		long rem = Y / X;
		if (rem % 2 == 0) {
			rem /= 2;
			fact[nF++] = 2;
			while (rem % 2 == 0) {
				rem /= 2;
			}
		}

		for (int i = 3; i <= 1_000_000; i += 2) {
			if (rem % i == 0) {
				rem /= i;
				fact[nF++] = i;
				while (rem % i == 0) {
					rem /= i;
				}
			}
		}

		// yRem may or may not be prime

		ArrayList<Long> A = new ArrayList<>();
		ArrayList<Long> B = new ArrayList<>();

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			long c = Long.parseLong(inputData.nextToken());
			long cGCD = gcd(c, rem);
			if (cGCD != 1 && cGCD != rem) {
				fact[nF++] = cGCD;
				rem /= cGCD;
				fact[nF++] = rem;
				rem = 1;
			}
			if (c % X == 0) {
				A.add(c);
			}
			if (Y % c == 0) {
				B.add(c);
			}
		}

		if (rem != 1) {
			fact[nF++] = rem;
		}

		int nSets = 1 << nF;
		long[] aSet = new long[nSets];
		long[] bSet = new long[nSets];

		for (long c : A) {
			long cR = c / X;
			int cSet = 0;

			for (int i = 0; i < nF; i++) {
				if (cR % fact[i] == 0) {
					cSet |= (1 << i);
				}
			}
			aSet[cSet]++;
		}

		for (long c : B) {
			long cR = Y / c;
			int cSet = 0;

			for (int i = 0; i < nF; i++) {
				if (cR % fact[i] == 0) {
					cSet |= (1 << i);
				}
			}
			bSet[cSet]++;
		}

		if (nF == 0) {
			printer.println(A.size() * B.size());
			printer.close();
			return;
		}

		long[][] aSetS = new long[nF][nSets];

		for (int cSet = 0; cSet < nSets; cSet++) {
			if ((cSet & 1) != 0) {
				aSetS[0][cSet] = aSet[cSet] + aSet[cSet ^ 1];
			} else {
				aSetS[0][cSet] = aSet[cSet];
			}
		}

		for (int i = 1; i < nF; i++) {
			for (int cSet = 0; cSet < nSets; cSet++) {
				if ((cSet & (1 << i)) != 0) {
					aSetS[i][cSet] = aSetS[i - 1][cSet] + aSetS[i - 1][cSet ^ (1 << i)];
				} else {
					aSetS[i][cSet] = aSetS[i - 1][cSet];
				}
			}
		}
		int mask = nSets - 1;

		long ans = 0;
		for (int cSet = 0; cSet < nSets; cSet++) {
			int comp = mask ^ cSet;
			ans += bSet[cSet] * aSetS[nF - 1][comp];
		}

		printer.println(ans);
		printer.close();
	}

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
