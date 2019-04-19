import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PalindromicCowLineup {

	static int N;
	static int[] a;

	static final int MAXR = 300;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] cur = new int[2 * MAXR + 1];
		int[] fin = new int[2 * MAXR + 1];
		Arrays.fill(cur, Integer.MAX_VALUE / 4);
		Arrays.fill(fin, Integer.MAX_VALUE / 4);

		for (int l = N - 1; l >= 0; l--) {
			for (int d = -MAXR; d <= MAXR; d++) {
				int r = N - 1 - l + d;
				if (r >= N) {
					continue;
				}
				if (l == r || l - 1 == r) {
					cur[MAXR + d] = 0;
				} else if (l < r) {
					int cMin = Integer.MAX_VALUE / 4;
					if (a[l] == a[r]) {
						cMin = Math.min(cMin, fin[MAXR + d]);
					}
					if (l + 1 < N && d + 1 <= MAXR) {
						cMin = Math.min(cMin, 1 + fin[MAXR + d + 1]);
					}
					if (d - 1 >= -MAXR) {
						cMin = Math.min(cMin, 1 + cur[MAXR + d - 1]);
					}
					cur[MAXR + d] = cMin;
				}
			}

			int[] temp = fin;
			fin = cur;
			cur = temp;
			Arrays.fill(cur, Integer.MAX_VALUE / 4);
		}

		int ans = fin[MAXR + 0];

		if (ans > 300) {
			printer.println("many");
		} else {
			printer.println(ans);
		}
		printer.close();
	}
}
