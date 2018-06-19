import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_489C {

	static final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long st = Long.parseLong(inputData.nextToken());
		if(st == 0) {
			printer.println(0);
			printer.close();
			return;
		}
		st %= MOD;
		long years = Long.parseLong(inputData.nextToken());
		long[][] res = exp(years);
		long ans = (res[0][0] * st % MOD * 2 % MOD + res[0][1] * (-1 + MOD) % MOD) % MOD;
		printer.println(ans);
		printer.close();
	}

	static long[][] exp(long pow) {
		long[][] cBase = base;
		long[][] res = { { 1, 0 }, { 0, 1 } };

		while (pow != 0) {
			if ((pow & 1) != 0) {
				res = mult(res, cBase);
			}
			cBase = mult(cBase, cBase);
			pow >>= 1;
		}
		return res;
	}

	static long[][] base = { { 2, 1 }, { 0, 1 } };

	static long[][] mult(long[][] a, long[][] b) {
		long[][] res = new long[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				res[i][j] = (a[i][0] * b[0][j] % MOD + a[i][1] * b[1][j] % MOD) % MOD;
			}
		}
		return res;
	}

}
