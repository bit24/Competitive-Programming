import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Lyft_18F {

	static int L;
	static int N;
	static int Q;
	static final int[] pow3 = { 0, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441 };

	static final int[][] req = { { 2, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { 1, 2 }, { 1, 0 } };

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		L = Integer.parseInt(inputData.nextToken());
		N = Integer.parseInt(inputData.nextToken());
		Q = Integer.parseInt(inputData.nextToken());

		int[] cnt = new int[1 << L];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			cnt[Integer.parseInt(inputData.nextToken())]++;
		}

		int[][] dp = new int[L][pow3[L]];

		mLoop:
		for (int cM = 0; cM < pow3[L]; cM++) {
			int tM = cM / 3;

			int conv = 0;
			for (int i = 1; i < L; i++) {
				int rem = tM % 3;
				if (rem == 2) {
					continue mLoop;
				}
				if (rem == 1) {
					conv |= 1 << i;
				}
				tM /= 3;
			}

			int rem = cM % 3;

			if (rem == 0 || rem == 2) {
				dp[0][cM] += cnt[conv];
			}

			if (rem == 1 || rem == 2) {
				dp[0][cM] += cnt[conv | 1];
			}
		}

		for (int i = 1; i < L; i++) {
			for (int cM = 0; cM < pow3[L]; cM++) {
				int tM = cM / pow3[i];
				int rem = tM % 3;

				if (rem == 0 || rem == 1) {
					dp[i][cM] = dp[i - 1][cM];
				} else {
					dp[i][cM] = dp[i - 1][cM - pow3[i]] + dp[i - 1][cM - 2 * pow3[i]];
				}
			}
		}

		int[] ans = new int[Q];

		int[] mapped = new int[L];
		for (int cQ = 0; cQ < Q; cQ++) {
			String str = reader.readLine();
			for (int i = 0; i < L; i++) {
				mapped[i] = map(str.charAt(L - 1 - i));
			}

			int sum = 0;
			mLoop:
			for (int fM = 0; fM < 1 << L; fM++) {
				if (cnt[fM] == 0) {
					continue;
				}
				int sM = 0;
				for (int i = L - 1; i >= 0; i--) {
					sM *= 3;
					int cR = req[mapped[i]][(fM >> i) & 1];
					if (cR == -1) {
						continue mLoop;
					}
					sM += cR;
				}
				sum += cnt[fM] * dp[L - 1][sM];
			}
			ans[cQ] = sum;
		}

		for (int i = 0; i < Q; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static int map(char inp) {
		if (inp == 'A') {
			return 0;
		}
		if (inp == 'O') {
			return 1;
		}
		if (inp == 'X') {
			return 2;
		}
		if (inp == 'a') {
			return 3;
		}
		if (inp == 'o') {
			return 4;
		}
		if (inp == 'x') {
			return 5;
		}
		return -1;
	}

}
