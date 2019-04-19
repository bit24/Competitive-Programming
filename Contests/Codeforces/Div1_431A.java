import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_431A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int k = Integer.parseInt(reader.readLine());

		if (k == 0) {
			printer.println('a');
			printer.close();
		}

		for (int i = 0; k > 0; i++) {
			int n = 0;
			while ((n + 1) * n / 2 <= k) {
				n++;
			}
			k -= n * (n - 1) / 2;
			for (int j = 0; j < n; j++) {
				printer.print((char) ('a' + i));
			}
		}
		printer.println();
		printer.close();
	}

}
