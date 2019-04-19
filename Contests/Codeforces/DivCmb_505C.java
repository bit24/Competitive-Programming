import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DivCmb_505C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String inp = reader.readLine();
		int oLen = inp.length();

		String process = inp + inp;

		int streak = 0;
		int max = 1;

		for (int i = 0; i < process.length(); i++) {
			if (i == 0 || process.charAt(i) != process.charAt(i - 1)) {
				streak++;
				if (streak > max) {
					max = streak;
				}
			} else {
				streak = 1;
			}
		}

		printer.println(Math.min(oLen, max));
		printer.close();
	}
}
