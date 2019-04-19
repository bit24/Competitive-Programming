import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_502F {

	static final int SQRT = 17_321;
	static boolean[] isPrime = new boolean[SQRT];

	static int nP;
	static int[] primes = new int[SQRT];
	static int[] nAppear = new int[SQRT];

	static boolean[] cSieve = new boolean[SQRT];

	static int N;
	static int A;
	static int B;
	static int C;
	static int D;

	static int ans;
	
	static int pCnt;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		A = Integer.parseInt(inputData.nextToken());
		B = Integer.parseInt(inputData.nextToken());
		C = Integer.parseInt(inputData.nextToken());
		D = Integer.parseInt(inputData.nextToken());

		Arrays.fill(isPrime, true);
		for (int i = 2; i < SQRT; i++) {
			if (isPrime[i]) {
				int m;
				for (m = i * i; m < SQRT; m += i) {
					isPrime[m] = false;
				}
				primes[nP] = i;
				nAppear[nP++] = m - SQRT;
				
				process(i);
			}
		}

		for (int nxt = SQRT; nxt <= N; nxt += SQRT) {
			Arrays.fill(cSieve, true);

			for (int i = 0; i < nP; i++) {
				int cP = primes[i];
				int m;
				for (m = nAppear[i]; m < SQRT; m += cP) {
					cSieve[m] = false;
				}
				nAppear[i] = m - SQRT;
			}

			for (int i = nxt; i <= Math.min(N, nxt + SQRT - 1); i++) {
				if (cSieve[i - nxt]) {
					process(i);
				}
			}
		}

		printer.println(Integer.toUnsignedString(ans));
		printer.close();
	}

	static void process(int i) {
		long cur = i;
		int cnt = 0;

		while (cur <= N) {
			cnt += N / cur;
			cur *= i;
		}

		int eval = A;
		eval *= i;
		eval += B;
		eval *= i;
		eval += C;
		eval *= i;
		eval += D;

		ans += cnt * eval;
	}
}
