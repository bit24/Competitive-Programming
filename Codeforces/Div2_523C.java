import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_523C {

	static final long MOD = 1_000_000_007;

	static int[] pF = new int[1_000_001];

	static int nF = 0;
	static int[] facts = new int[10];
	static int[] mult = new int[10];

	public static void main(String[] args) throws IOException {
		for (int i = 2; i <= 1_000_000; i++) {
			if (pF[i] == 0) {
				for (int m = i; m <= 1_000_000; m += i) {
					pF[m] = i;
				}
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int N = Integer.parseInt(reader.readLine());

		int[] a = new int[N];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		long[] cnt = new long[1_000_001];
		cnt[0] = 1;

		int[] inc = new int[2000];
		long[] del = new long[2000];

		for (int i = 0; i < N; i++) {
			int cNum = a[i];
			
			nF = 0;

			int temp = cNum;
			while (temp > 1) {
				int cF = pF[temp];
				if (nF != 0 && cF == facts[nF - 1]) {
					mult[nF - 1]++;
				} else {
					facts[nF] = cF;
					mult[nF++] = 1;
				}
				temp /= cF;
			}

			lS = 0;
			recurse(0, 1);

			int iI = 0;

			for (int j = 0; j < lS; j++) {
				inc[iI] = list[j];
				del[iI++] = cnt[list[j] - 1];
			}

			for (int j = 0; j < iI; j++) {
				cnt[inc[j]] = (cnt[inc[j]] + del[j]) % MOD;
			}
		}

		long sum = 0;
		for (int i = 1; i <= 1_000_000; i++) {
			sum = (sum + cnt[i]) % MOD;
		}
		printer.println(sum);
		printer.close();
	}

	static int lS = 0;
	static int[] list = new int[2000];

	static void recurse(int cI, int cN) {
		if (cI == nF) {
			list[lS++] = cN;
			return;
		}

		int pow = 1;
		for (int i = 0; i <= mult[cI]; i++) {
			recurse(cI + 1, cN * pow);
			pow *= facts[cI];
		}
	}
}
