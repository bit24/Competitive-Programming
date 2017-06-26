import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_420E {

	static final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numS = Integer.parseInt(inputData.nextToken());
		long dest = Long.parseLong(inputData.nextToken());

		long[] sEnd = new long[numS + 1];
		int[] sH = new int[numS + 1];
		for (int i = 1; i <= numS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			inputData.nextToken();
			sEnd[i] = Long.parseLong(inputData.nextToken());
			sH[i] = Integer.parseInt(inputData.nextToken());
			if (sH[i] < sH[i - 1]) {
				sEnd[i - 1]--;
			}
		}
		sEnd[numS] = dest;

		long[][] curMat = idMat();

		for (int i = 1; i <= numS; i++) {
			long[][] cHRep = hRep(sH[i]);
			long[][] cSegRep = expon(cHRep, sEnd[i] - sEnd[i - 1]);
			curMat = mult(cSegRep, curMat);
		}
		System.out.println(curMat[0][0]);
	}

	static long[][] hRep(int h) {
		long[][] mat = new long[16][16];

		mat[0][0] = 1;
		mat[0][1] = 1;

		for (int i = 1; i <= h; i++) {
			mat[i][i - 1] = 1;
			mat[i][i] = 1;
			if (i < 15) {
				mat[i][i + 1] = 1;
			}
		}
		return mat;
	}

	static long[][] expon(long[][] base, long pow) {
		long[][] cur = idMat();
		long[][] sqr = base;

		while (pow != 0) {
			if ((pow & 1) == 1) {
				cur = mult(cur, sqr);
			}
			pow >>= 1;
			sqr = mult(sqr, sqr);
		}
		return cur;
	}

	// composition of b into a
	static long[][] mult(long[][] a, long[][] b) {
		long[][] res = new long[16][16];

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				long sum = 0;
				for (int k = 0; k < 16; k++) {
					sum = (sum + a[i][k] * b[k][j]) % MOD;
				}
				res[i][j] = sum;
			}
		}
		return res;
	}

	static long[][] idMat() {
		long[][] matrix = new long[16][16];
		for (int i = 0; i < 16; i++) {
			matrix[i][i] = 1;
		}
		return matrix;
	}

}
