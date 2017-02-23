import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_400B {

	public static void main(String[] args) throws IOException {
		new Div2_400B().execute();
	}

	int[] primes = new int[9592];
	boolean[] isPrime = new boolean[100_002];

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		// fPrimes();

		int n = Integer.parseInt(reader.readLine());

		if (n == 1) {
			System.out.println(1);
			System.out.println(1);
			return;
		}
		if (n == 2) {
			System.out.println("1  1");
			System.out.println(1);
			return;
		}

		fPrimes();

		printer.println(2);
		for (int cInt = 2; cInt <= n + 1; cInt++) {
			if (isPrime[cInt]) {
				printer.print(1 + " ");
			} else {
				printer.print(2 + " ");
			}
		}
		printer.println();
		printer.close();
	}

	void fPrimes() {
		int nP = 0;
		primes[nP++] = 2;
		isPrime[2] = true;

		pLoop: for (int cInt = 3; cInt <= 100_001; cInt += 2) {
			for (int pI = 0; primes[pI] * primes[pI] <= cInt; pI++) {
				if (cInt % primes[pI] == 0) {
					continue pLoop;
				}
			}
			primes[nP++] = cInt;
			isPrime[cInt] = true;
		}
	}
}