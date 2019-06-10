import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Sabotage {

	static int nE;
	static int[] elm;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("sabotage.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("sabotage.out")));

		nE = Integer.parseInt(reader.readLine());
		elm = new int[nE];
		for (int i = 0; i < nE; i++) {
			elm[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		double low = 0;
		double high = 10_000;

		while (high - low > 0.0001) {
			double mid = (high + low) / 2;
			if (lessThan(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		printer.printf("%.3f", (high + low) / 2);
		printer.println();
		printer.close();
	}

	static boolean lessThan(double guess) {
		double tSum = elm[0] + elm[nE - 1] - 2 * guess;
		double cSum = 0;
		double max = elm[1] - guess;
		for (int i = 1; i < nE - 1; i++) {
			double cE = elm[i] - guess;
			tSum += cE;
			cSum = cSum > 0 ? cSum + cE : cE;
			if (cSum > max) {
				max = cSum;
			}
		}
		double min = tSum - max;
		return min < 0;
	}

}
