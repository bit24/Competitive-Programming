import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CookieClicker {

	static int N;
	static int T;
	static long pA;
	static long pB;

	static long[] cA;
	static long[] cB;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		T = Integer.parseInt(inputData.nextToken());
		pA = Integer.parseInt(inputData.nextToken());
		pB = Integer.parseInt(inputData.nextToken());

		cA = new long[N];
		cB = new long[N];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			cA[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			cB[i] = Integer.parseInt(inputData.nextToken());
		}

		long[][] prim = new long[N + 1][N + 1]; // minimize
		long[][] sec = new long[N + 1][N + 1]; // maximize

		for (long[] a : prim) {
			Arrays.fill(a, Long.MAX_VALUE / 2);
		}

		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= N; j++) {
				long cRate = pA * i + pB * j + 1;
				if (i + 1 <= N) {
					long nRate = cRate + pA;
					long eTime = (cA[i + 1] - sec[i][j] + cRate - 1) / cRate;
					long nRem = sec[i][j] + eTime * cRate - cA[i + 1];

				}
			}
		}

	}
}
