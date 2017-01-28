import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class EDU_17C {

	public static void main(String[] args) throws IOException {
		new EDU_17C().execute();
	}

	char[] master;
	char[] edition;
	int mSize;
	int eSize;

	int[] preInd;
	int[] sufInd;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		master = (" " + reader.readLine()).toCharArray();
		edition = (" " + reader.readLine()).toCharArray();
		mSize = master.length - 1;
		eSize = edition.length - 1;

		reader.close();

		preInd = new int[eSize + 2];
		Arrays.fill(preInd, Integer.MAX_VALUE - 1);
		preInd[0] = 0;

		for (int i = 1; i <= eSize; i++) {
			char cChar = edition[i];
			for (int j = preInd[i - 1] + 1; j <= mSize; j++) {
				if (master[j] == cChar) {
					preInd[i] = j;
					break;
				}
			}
		}

		sufInd = new int[eSize + 2];
		Arrays.fill(sufInd, Integer.MIN_VALUE + 1);
		sufInd[eSize + 1] = mSize + 1;

		for (int i = eSize; i >= 1; i--) {
			char cChar = edition[i];
			for (int j = sufInd[i + 1] - 1; j >= 1; j--) {
				if (master[j] == cChar) {
					sufInd[i] = j;
					break;
				}
			}
		}

		int ans = binSearch();
		if (ans == eSize) {
			printer.println('-');
		} else {
			StringBuilder str = new StringBuilder();
			for (int i = 1; i <= ansL; i++) {
				str.append(edition[i]);
			}
			for (int i = ansR; i <= eSize; i++) {
				str.append(edition[i]);
			}
			printer.println(str);
		}
		printer.close();
	}

	int ansL = -1;
	int ansR = -1;

	boolean isPossible(int length) {
		for (int i = 1; i + length - 1 <= eSize; i++) {
			if (preInd[i - 1] < sufInd[i + length]) {
				ansL = i - 1;
				ansR = i + length;
				return true;
			}
		}
		return false;
	}

	int binSearch() {
		int low = 0;
		int high = eSize;

		while (low != high) {
			int mid = (low + high) / 2;
			if (isPossible(mid)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

}
