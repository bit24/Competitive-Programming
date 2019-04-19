import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class VK16_3F {

	static int B;
	static int P;
	static int Q;

	static long[] iFact;
	static long[] iFact2;

	public static void main0(String[] args) {
		System.out.println(Integer.toUnsignedString(inv(7)));
	}

	static int[] numF;
	static int[] numFP;
	static int[] iNumF;

	static int[] denF;
	static int[] denFP;
	static int[] iDenF;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		B = Integer.parseInt(inputData.nextToken());
		P = Integer.parseInt(inputData.nextToken());
		P = Math.min(P, B);
		Q = Integer.parseInt(inputData.nextToken());

		numF = new int[P * 2 + 1];
		numF[0] = 1;
		numFP = new int[P * 2 + 1];

		iNumF = new int[P * 2 + 1];

		for (int i = 1; i <= P * 2; i++) {
			int cPow = Integer.numberOfTrailingZeros(B - i + 1);
			numF[i] = numF[i - 1] * ((B - i + 1) >> cPow);
			numFP[i] = numFP[i - 1] + cPow;
		}

		for (int i = 0; i <= P * 2; i++) {
			iNumF[i] = inv(numF[i]);
		}

		denF = new int[P * 2 + 1];
		denF[0] = 1;
		denFP = new int[P * 2 + 1];

		iDenF = new int[P * 2 + 1];

		for (int i = 1; i <= P * 2; i++) {
			int cPow = Integer.numberOfTrailingZeros(i);
			denF[i] = denF[i - 1] * (i >> cPow);
			denFP[i] = denFP[i - 1] + cPow;
		}

		for (int i = 0; i <= P * 2; i++) {
			iDenF[i] = inv(denF[i]);
		}

		int ans = 0;
		for (int n = 1; n <= Q; n++) {
			ans ^= n * calculate(n);
		}
		printer.println(Integer.toUnsignedString(ans));
		printer.close();
	}

	static int calculate(int n) {
		int cPow = 1;
		int sum = 0;
		int endI = Math.min(P, B - 1);
		for (int i = 0; i <= endI; i++) {
			sum += comb(B, i) * cPow;
			cPow *= n;
		}
		return sum;
	}

	static int comb(int n, int k) {
		int off = B - n;
		int cNumF = numF[k + off] * iNumF[off];
		int cNumP = numFP[k + off] - numFP[off];
		return (cNumF * iDenF[k]) << (cNumP - denFP[k]);
	}

	static int inv(int n) {
		int x = 1;
		x = x * (2 - x * n); /* Newton's method */
		x = x * (2 - x * n); /* 5 steps enough for 32 bits */
		x = x * (2 - x * n);
		x = x * (2 - x * n);
		x = x * (2 - x * n);
		return x;
	}
}
