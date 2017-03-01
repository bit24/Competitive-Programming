import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_402D {

	public static void main(String[] args) throws IOException {
		new Div2_402D().execute();
	}

	String original;
	String subseq;

	int[] rTime;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		original = reader.readLine();
		subseq = reader.readLine();

		rTime = new int[original.length()];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= original.length(); i++) {
			rTime[Integer.parseInt(inputData.nextToken()) - 1] = i;
		}
		System.out.println(binSearch());
	}

	int binSearch() {
		int low = 0;
		int high = original.length();
		while (low != high) {
			int mid = (low + high + 1) / 2;
			if (isPossible(mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	boolean isPossible(int cTime) {
		int next = 0;
		for (int i = 0; i < original.length(); i++) {
			if (rTime[i] > cTime && original.charAt(i) == subseq.charAt(next)) {
				next++;
				if (next == subseq.length()) {
					return true;
				}
			}
		}
		return false;
	}

}
