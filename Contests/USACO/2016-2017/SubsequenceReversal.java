import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SubsequenceReversal {

	public static void main(String[] args) throws IOException {
		new SubsequenceReversal().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("subrev.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("subrev.out")));
		size = Integer.parseInt(reader.readLine());
		arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		calculateAll();

		printer.println(calculated[0][size - 1][1][largest]);
		printer.close();
	}

	int size;
	final int largest = 50;
	int[] arr;
	int[][][][] calculated;

	void calculateAll() {
		calculated = new int[size][size][largest + 1][largest + 1];

		for (int rDif = 0; rDif < size; rDif++) {
			for (int left = 0; left + rDif < size; left++) {
				int right = left + rDif;

				for (int vDif = 0; vDif < largest; vDif++) {
					for (int sVal = 1; sVal + vDif <= largest; sVal++) {
						int lVal = sVal + vDif;
						calculate(left, right, sVal, lVal);
					}
				}
			}
		}
	}

	void calculate(int i, int j, int sVal, int lVal) {
		if (i == j) {
			int val = arr[i];
			if (sVal <= val && val <= lVal) {
				calculated[i][j][sVal][lVal] = 1;
			}
			return;
		}

		int iVal = arr[i];
		int jVal = arr[j];

		int max = 0;

		// case 1: swap (i, j)
		// note: after swapping, i moves right and j moves left to prevent further swapping of these indices

		if (sVal <= jVal && jVal <= iVal && iVal <= lVal) {
			int res = 2 + calculated[i + 1][j - 1][jVal][iVal];
			if (res > max) {
				max = res;
			}
		}
		if (sVal <= jVal && jVal <= lVal) {
			int res = 1 + calculated[i + 1][j - 1][jVal][lVal];
			if (res > max) {
				max = res;
			}
		}
		if (sVal <= iVal && iVal <= lVal) {
			int res = 1 + calculated[i + 1][j - 1][sVal][iVal];
			if (res > max) {
				max = res;
			}
		}

		// case 2: don't swap (i, j)

		if (sVal <= iVal && iVal <= lVal) {
			int res = 1 + calculated[i + 1][j][iVal][lVal];
			if (res > max) {
				max = res;
			}
		}
		if (sVal <= jVal && jVal <= lVal) {
			int res = 1 + calculated[i][j - 1][sVal][jVal];
			if (res > max) {
				max = res;
			}
		}
		int res = calculated[i + 1][j][sVal][lVal];
		if (res > max) {
			max = res;
		}
		res = calculated[i][j - 1][sVal][lVal];
		if (res > max) {
			max = res;
		}
		calculated[i][j][sVal][lVal] = max;
	}

}
