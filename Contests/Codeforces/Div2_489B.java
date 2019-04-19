import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_489B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long l = Integer.parseInt(inputData.nextToken());
		long r = Integer.parseInt(inputData.nextToken());
		long x = Integer.parseInt(inputData.nextToken());
		long y = Integer.parseInt(inputData.nextToken());
		if (y % x != 0) {
			printer.println(0);
			printer.close();
			return;
		}

		long v = y / x;

		long[] pPow = new long[15];
		int nP = 0;

		for (long c = 2; c * c < v; c++) {
			if (v % c == 0) {
				pPow[nP] = 1;
				while (v % c == 0) {
					v /= c;
					pPow[nP] *= c;
				}
				nP++;
			}
		}

		if (v != 1) {
			pPow[nP++] = v;
		}

		long ans = 0;
		for (int bSet = 0; bSet < (1 << nP); bSet++) {
			long aProd = x;
			long bProd = x;
			for (int i = 0; i < nP; i++) {
				if ((bSet & (1 << i)) != 0) {
					aProd *= pPow[i];
				} else {
					bProd *= pPow[i];
				}
			}
			if (l <= aProd && aProd <= r && l <= bProd && bProd <= r) {
				ans++;
			}
		}
		printer.println(ans);

		/*
		long ans2 = 0;
		for (long i = l; i <= r; i++) {
			for (long j = l; j <= r; j++) {
				if (gcd(i, j) == x && i * j / gcd(i, j) == y) {
					ans2++;
				}
			}
		}
		printer.println(ans2);*/

		printer.close();
	}

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
