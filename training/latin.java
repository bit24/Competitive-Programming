import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
LANG: JAVA
TASK: latin
*/

public class latin {

	public static void main(String[] args) throws IOException {
		new latin().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("latin.in"));
		size = Integer.parseInt(reader.readLine());
		reader.close();

		rUsed = new boolean[size][size];
		cUsed = new boolean[size][size];

		for (int i = 0; i < size; i++) {
			rUsed[0][i] = true;
			cUsed[0][i] = true;
			rUsed[i][i] = true;
			cUsed[i][i] = true;
		}

		long sum1 = search(1, 1);
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("latin.out")));
		printer.println(sum1 * factorial(size - 1));
		printer.close();
	}

	long factorial(long n) {
		if (n == 1) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	int size;
	boolean[][] rUsed;
	boolean[][] cUsed;

	long search(int i, int j) {
		long sum = 0;
		boolean computed = false;
		long savedValue = 0;
		for (int posValue = 0; posValue < size; posValue++) {
			if (rUsed[i][posValue] || cUsed[j][posValue]) {
				continue;
			}

			// really difficult to observe optimization that only works for row 1
			if (i == 1 && posValue > j && computed) {
				sum += savedValue;
			} else {
				rUsed[i][posValue] = true;
				cUsed[j][posValue] = true;
				if (j == size - 1) {
					if (i == size - 1) {
						sum++;
					} else {
						savedValue = search(i + 1, 1);
						sum += savedValue;
					}
				} else {
					savedValue = search(i, j + 1);
					sum += savedValue;
				}

				rUsed[i][posValue] = false;
				cUsed[j][posValue] = false;
				computed = posValue > j;
			}
		}
		return sum;
	}
}
