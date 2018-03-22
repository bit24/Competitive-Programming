import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class WCS9_WeightedUniformStrings {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String elements = reader.readLine();

		long[] bestCount = new long[26];
		int streak = 1;
		if (elements.length() >= 1) {
			bestCount[elements.charAt(0) - 'a'] = 1;
		}

		for (int i = 1; i < elements.length(); i++) {
			if (elements.charAt(i - 1) == elements.charAt(i)) {
				streak++;
			} else {
				streak = 1;
			}
			if (bestCount[elements.charAt(i) - 'a'] < streak) {
				bestCount[elements.charAt(i) - 'a'] = streak;
			}
		}

		int numQ = Integer.parseInt(reader.readLine());
		for (int i = 0; i < numQ; i++) {
			long value = Long.parseLong(reader.readLine());
			boolean possible = false;
			for (int j = 0; j < 26; j++) {
				if (value % (j + 1L) == 0L && (value / (j + 1L)) <= bestCount[j]) {
					possible = true;
					break;
				}
			}
			if (possible) {
				printer.println("Yes");
			} else {
				printer.println("No");
			}
		}
		reader.close();
		printer.close();
	}

}
