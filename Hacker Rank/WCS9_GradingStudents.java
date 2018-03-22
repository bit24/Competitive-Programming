import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class WCS9_GradingStudents {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numE = Integer.parseInt(reader.readLine());
		for (int i = 0; i < numE; i++) {
			int input = Integer.parseInt(reader.readLine());
			if (input < 38) {
				printer.println(input);
			} else {
				if (input % 5 < 3) {
					printer.println(input);
				} else {
					printer.println(input + (5 - (input % 5)));
				}
			}
		}
		reader.close();
		printer.close();
	}

}
