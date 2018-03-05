import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class two {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());

		for (int t = 1; t <= n; t++) {
			int test = Integer.parseInt(reader.readLine());
			long min = Long.MAX_VALUE;
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					long prod = (long) (1 << i) * ((1 << j) - 1L);
					if (prod > test && subbable(prod) && prod < min) {
						min = prod;
					}
				}
			}
			printer.println("The next hyper-even after " + test + " is " + min);
		}
		printer.close();
	}

	static boolean subbable(long num) {
		for (long i = 100_000_000_000L; i >= 10; i /= 10) {
			if (Long.bitCount(num / i) == 1 && Long.bitCount(num % i) == 1) {
				return true;
			}
		}
		return false;
	}
}
