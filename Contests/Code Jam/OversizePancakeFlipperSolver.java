import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class OversizePancakeFlipperSolver {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("pancake.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("pancake.out")));

		int nT = Integer.parseInt(reader.readLine());

		tLoop:
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			String text = inputData.nextToken();
			int nE = text.length();

			boolean[] valid = new boolean[nE];
			for (int i = 0; i < nE; i++) {
				valid[i] = text.charAt(i) == '+';
			}

			int k = Integer.parseInt(inputData.nextToken());

			int cnt = 0;
			for (int i = 0; i + k - 1 < nE; i++) {
				if (!valid[i]) {
					cnt++;
					for (int j = i; j < i + k; j++) {
						valid[j] = !valid[j];
					}
				}
			}
			for (int i = 0; i < nE; i++) {
				if (!valid[i]) {
					printer.println("Case #" + cT + ": IMPOSSIBLE");
					continue tLoop;
				}
			}
			printer.println("Case #" + cT + ": " + cnt);
		}
		reader.close();
		printer.close();
	}

}
