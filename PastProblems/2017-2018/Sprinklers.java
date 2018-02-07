import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Sprinklers {

	static final long MOD = 1_000_000_007;
	static final long INV2 = 500_000_004;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("sprinklers.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("sprinklers.out")));

		int N = Integer.parseInt(reader.readLine());
		int[] yCord = new int[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			yCord[Integer.parseInt(inputData.nextToken())] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		int[] rMin = new int[N];
		int[] rMax = new int[N];

		rMin[0] = yCord[0];
		for (int cX = 1; cX < N; cX++) {
			rMin[cX] = Math.min(yCord[cX], rMin[cX - 1]);
		}

		rMax[N - 1] = yCord[N - 1];
		for (int cX = N - 2; cX >= 0; cX--) {
			rMax[cX] = Math.max(yCord[cX], rMax[cX + 1]);
		}

		int[] lMost = new int[N];
		Arrays.fill(lMost, N);

		int nY = N - 1;

		for (int cX = 0; cX < N; cX++) {
			while (yCord[cX] <= nY) {
				lMost[nY] = cX;
				nY--;
			}
		}

		long[] pre_lMost = new long[N];
		pre_lMost[0] = lMost[0];

		for (int i = 1; i < N; i++) {
			pre_lMost[i] = (pre_lMost[i - 1] + lMost[i]) % MOD;
		}

		long[] pre_iTLMost = new long[N];
		pre_iTLMost[0] = 0;
		for (int i = 1; i < N; i++) {
			pre_iTLMost[i] = (pre_iTLMost[i - 1] + (long) i * lMost[i]) % MOD;
		}

		long ans = 0;
		for (int cX = 0; cX < N; cX++) {
			int cRMax = rMax[cX];
			int cRMin = rMin[cX];

			long comp1 = (long) (cRMax - cRMin) * cRMax % MOD * cX % MOD;
			long comp2 = cRMax != 0
					? (long) -cRMax * (pre_lMost[cRMax - 1] - (cRMin != 0 ? pre_lMost[cRMin - 1] : 0)) % MOD
					: 0;
			long comp3 = (long) -cX * (cRMin + cRMax - 1) % MOD * (cRMax - cRMin) % MOD * INV2 % MOD;
			long comp4 = cRMax != 0 ? (pre_iTLMost[cRMax - 1] - (cRMin != 0 ? pre_iTLMost[cRMin - 1] : 0)) % MOD : 0;
			ans = (ans + comp1 + comp2 + comp3 + comp4 + 4 * MOD) % MOD;
		}
		printer.println(ans);
		printer.close();
	}
}
