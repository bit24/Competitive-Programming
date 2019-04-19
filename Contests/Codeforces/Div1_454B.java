import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_454B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int m = Integer.parseInt(inputData.nextToken());

		int[][] ans;
		if (n <= m) {
			ans = solve(n, m);
		} else {
			ans = solve(m, n);

			if (ans == null) {
				printer.println("NO");
				printer.close();
				return;
			}
			ans = flip(ans);
		}
		if (ans == null) {
			printer.println("NO");
			printer.close();
			return;
		} else {
			printer.println("YES");
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				printer.print(ans[i][j] + " ");
			}
			printer.println();
		}
		printer.close();
	}

	static int[][] solve(int n, int m) {
		assert (n <= m);
		if (n == 1) {
			if (m == 1) {
				return new int[][] { { 1 } };
			}
			if (m <= 3) {
				return null;
			}
			if (m == 4) {
				return new int[][] { { 2, 4, 1, 3 } };
			}
			int[] ans = new int[m];
			int k = (m + 1) / 2;
			for (int i = 0; i < k; i++) {
				ans[i] = 2 * i + 1;
			}
			for (int i = k; i < m; i++) {
				ans[i] = 2 + 2 * (i - k);
			}
			return new int[][] { ans };
		}
		if (n == 2) {
			if (m <= 3) {
				return null;
			}

			int[][] ans = new int[2][m];
			for (int i = 0; i < m; i++) {
				if ((i & 1) == 0) {
					ans[0][i] = i + 1;
					ans[1][i] = i + m + 3;
				} else {
					ans[0][i] = i + m + 3;
					ans[1][i] = i + 1;
				}
			}
			if ((m & 1) == 0) {
				ans[1][m - 2] = m + 1;
				ans[0][m - 1] = m + 2;
			} else {
				ans[0][m - 2] = m + 1;
				ans[1][m - 1] = m + 2;
			}
			return ans;
		}
		if (n == 3 && m == 3) {
			return new int[][] { { 6, 1, 8 }, { 7, 5, 3 }, { 2, 9, 4 } };
		}
		int[][] ans = new int[n][m];
		int nxt = 1;
		for (int sum = 0; sum <= n + m - 2; sum += 2) {
			for (int i = Math.max(0, sum - m + 1); i <= Math.min(sum, n - 1); i++) {
				int j = sum - i;
				ans[i][j] = nxt++;
			}
		}

		for (int sum = 1; sum <= n + m - 2; sum += 2) {
			for (int i = Math.max(0, sum - m + 1); i <= Math.min(sum, n - 1); i++) {
				int j = sum - i;
				ans[i][j] = nxt++;
			}
		}

		return ans;
	}

	static int[][] flip(int[][] inp) {
		int[][] ans = new int[inp[0].length][inp.length];

		int[][] iLoc = new int[inp.length][inp[0].length];
		int[][] jLoc = new int[inp.length][inp[0].length];

		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j < inp[0].length; j++) {
				int cur = inp[i][j] - 1;
				iLoc[i][j] = cur / inp[0].length;
				jLoc[i][j] = cur % inp[0].length;
			}
		}

		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j < inp[0].length; j++) {
				ans[jLoc[i][j]][iLoc[i][j]] = j * inp.length + i + 1;
			}
		}
		return ans;
	}

}
