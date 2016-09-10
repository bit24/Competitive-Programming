import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*ID: eric.ca1
LANG: JAVA
TASK: hidden
*/

public class hidden {

	static int length;
	static char[] charSequence;

	public static void main(String[] args) throws IOException {
		input();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("hidden.out")));
		printer.println(findBest());
		printer.close();
	}

	public static void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("hidden.in"));
		length = Integer.parseInt(reader.readLine());
		charSequence = new char[length];

		int nIndex = 0;
		while (nIndex < length) {
			String nextLine = reader.readLine();
			for (int i = 0; i < nextLine.length(); i++) {
				charSequence[nIndex++] = nextLine.charAt(i);
			}
		}
		reader.close();
	}

	public static int findBest() {
		int[] bestDLength = new int[length];

		// no value of bestDLength should be greater than 2^cmpIndex

		ArrayList<Integer> evaluatingSet = new ArrayList<Integer>();

		char cBestChar = '~';
		for (int i = 0; i < length; i++) {
			if (charSequence[i] < cBestChar) {
				evaluatingSet.clear();
				cBestChar = charSequence[i];
				evaluatingSet.add(i);
			} else if (charSequence[i] == cBestChar) {
				evaluatingSet.add(i);
			}
		}

		for (int i : evaluatingSet) {
			bestDLength[i] = 1;
		}

		ArrayList<Integer> nSet = new ArrayList<Integer>();

		int guaranteedBest = 1;
		while (guaranteedBest < length) {
			int cBestLength = 0;
			cBestChar = '~';

			if (evaluatingSet.size() == 1) {
				return evaluatingSet.get(0);
			}

			for (int i : evaluatingSet) {
				// if i is in evaluatingSet, i is the best up to cmpIndex-1
				int oIndex = i + guaranteedBest;
				if (oIndex >= length) {
					oIndex -= length;
				}

				if (cBestLength < bestDLength[oIndex]
						|| (cBestLength == bestDLength[oIndex] && charSequence[oIndex] < cBestChar)) {

					nSet.clear();
					nSet.add(i);
					cBestLength = bestDLength[oIndex];
					cBestChar = charSequence[oIndex];

				} else if (cBestLength == bestDLength[oIndex] && charSequence[oIndex] == cBestChar) {
					nSet.add(i);
				}
			}
			if (cBestLength == 0) {
				cBestLength = 1;
			}

			// characters from o...cmpIndex (inclusive) are guaranteed to be best
			// next cBestLength characters are also guaranteed to be best
			for (int i : nSet) {
				bestDLength[i] = guaranteedBest + cBestLength;
			}

			ArrayList<Integer> swap = evaluatingSet;
			evaluatingSet = nSet;
			nSet = swap;
			swap.clear();

			guaranteedBest += cBestLength;
		}

		return evaluatingSet.get(0);
	}
}
