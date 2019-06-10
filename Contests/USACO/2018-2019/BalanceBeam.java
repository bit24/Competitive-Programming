import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BalanceBeam {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("balance.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("balance.out")));
		int N = Integer.parseInt(reader.readLine());
		long[] a = new long[N + 2];
		for (int i = 1; i <= N; i++) {
			a[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		int[] stack = new int[N + 2];
		int sS = 0;
		stack[sS++] = 0;

		for (int i = 1; i <= N + 1; i++) {
			while (sS > 1) {
				long sup = a[stack[sS - 2]] * (i - stack[sS - 2])
						+ (a[i] - a[stack[sS - 2]]) * (stack[sS - 1] - stack[sS - 2]);

				if (i - stack[sS - 2] > 0) {
					if (sup > a[stack[sS - 1]] * (i - stack[sS - 2])) {
						sS--;
					} else {
						break;
					}
				} else {
					if (sup < a[stack[sS - 1]] * (i - stack[sS - 2])) {
						sS--;
					} else {
						break;
					}
				}
			}
			stack[sS++] = i;
		}

		int cSI = 0;
		for (int i = 1; i <= N; i++) {
			if (cSI + 2 < sS && stack[cSI + 1] <= i) {
				cSI++;
			}

			// System.out.println(stack[cSI] + " " + a[stack[cSI]]);
			// System.out.println(i + " " + a[i]);
			// System.out.println(stack[cSI + 1] + " " + a[stack[cSI + 1]]);
			// System.out.println();

			long sup = (a[stack[cSI]] * (stack[cSI + 1] - stack[cSI])
					+ (a[stack[cSI + 1]] - a[stack[cSI]]) * (i - stack[cSI])) * 100_000L
					/ (stack[cSI + 1] - stack[cSI]);
			printer.println(sup);
		}
		printer.close();
	}

}
