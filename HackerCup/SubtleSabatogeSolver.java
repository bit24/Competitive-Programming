import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SubtleSabatogeSolver {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("subtle_sabatoge.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("subtle_sabatoge.out")));

		int numT = Integer.parseInt(reader.readLine());
		for (int i = 1; i <= numT; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int n = Integer.parseInt(inputData.nextToken());
			int m = Integer.parseInt(inputData.nextToken());
			int k = Integer.parseInt(inputData.nextToken());
			if (m < n) {
				int temp = m;
				m = n;
				n = temp;
			}
			assert (n <= m);

			int ans = -1;
			if (m >= 2 * k + 3 && n >= k + 1) {
				ans = (n + k - 1) / k;
			}

			if (k == 1) {
				if (3 <= n && 5 <= m) {
					if (ans == -1 || ans > 5) {
						ans = 5;
					}
				}
			} else {
				if (2 * k + 1 <= n && 2 * k + 3 <= m) {
					if (ans == -1 || ans > 4) {
						ans = 4;
					}
				}
			}

			printer.println("Case #" + i + ": " + ans);
		}
		reader.close();
		printer.close();
	}
}
