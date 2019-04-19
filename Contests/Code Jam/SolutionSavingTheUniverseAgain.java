import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SolutionSavingTheUniverseAgain {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			long limit = Long.parseLong(inputData.nextToken());
			String seq = inputData.nextToken();
			int[] ops = new int[seq.length()];

			for (int i = 0; i < seq.length(); i++) {
				ops[i] = seq.charAt(i) == 'S' ? 1 : 0;
			}

			long lCost = cntTotal(ops);
			long cChange = 0;
			while (lCost > limit) {
				if (!swapLast(ops)) {
					break;
				}
				cChange++;
				lCost = cntTotal(ops);
			}

			if (lCost <= limit) {
				printer.println("Case #" + cT + ": " + cChange);
			} else {
				printer.println("Case #" + cT + ": IMPOSSIBLE");
			}
		}
		printer.close();
	}

	static boolean swapLast(int[] ops) {
		int lI = -1;
		for (int i = 0; i + 1 < ops.length; i++) {
			if (ops[i] == 0 && ops[i + 1] == 1) {
				lI = i;
			}
		}
		if (lI == -1) {
			return false;
		}
		ops[lI] = 1;
		ops[lI + 1] = 0;
		return true;
	}

	static long cntTotal(int[] ops) {
		long sum = 0;
		long cur = 1;
		for (int i = 0; i < ops.length; i++) {
			if (ops[i] == 1) {
				sum += cur;
			} else {
				cur *= 2;
			}
		}
		return sum;
	}
}
