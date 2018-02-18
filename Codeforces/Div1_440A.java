import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_440A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nQ = Integer.parseInt(reader.readLine());
		while (nQ-- > 0) {
			int i = Integer.parseInt(reader.readLine());
			if (i % 4 == 0) {
				printer.println(i / 4);
			} else if (i % 4 == 1) {
				if (i < 9) {
					printer.println(-1);
				} else {
					printer.println(1 + (i - 9) / 4);
				}
			} else if (i % 4 == 2) {
				if (i < 6) {
					printer.println(-1);
				} else {
					printer.println(1 + (i - 6) / 4);
				}
			} else {
				if (i < 15) {
					printer.println(-1);
				} else {
					printer.println(2 + (i - 15) / 4);
				}
			}
		}
		printer.close();
	}
}
