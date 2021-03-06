import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Numbers {

	public static void main(String[] args) throws IOException {
		new Numbers().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("262144.in"));

		int numElements = Integer.parseInt(reader.readLine());

		int[] elements = new int[numElements];
		for (int i = 0; i < numElements; i++) {
			elements[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		// inclusive
		int[][] right = new int[numElements][65];
		int[][] left = new int[numElements][65];

		for (int i = 0; i < numElements; i++) {
			for (int j = 0; j < 65; j++) {
				right[i][j] = -1;
				left[i][j] = -1;
			}
		}

		for (int i = 0; i < numElements; i++) {
			right[i][elements[i]] = i;
			left[i][elements[i]] = i;
		}

		for (int value = 2; value < 65; value++) {

			// merge index & index + 1
			for (int index = 0; index + 1 < numElements; index++) {
				if (left[index][value - 1] != -1 && right[index + 1][value - 1] != -1) {
					// mergeable
					left[right[index + 1][value - 1]][value] = left[index][value - 1];
					right[left[index][value - 1]][value] = right[index + 1][value - 1];
				}
			}
		}

		int max = 0;
		for (int i = 0; i < numElements; i++) {
			for (int j = max + 1; j < 65; j++) {
				if (left[i][j] != -1 || right[i][j] != -1) {
					max = j;
				}
			}
		}
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("262144.out")));
		printer.println(max);
		printer.close();
	}
}
