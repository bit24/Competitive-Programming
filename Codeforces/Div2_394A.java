import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_394A {

	public static void main(String[] args) throws IOException {
		new Div2_394A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int a = Integer.parseInt(inputData.nextToken());
		int b = Integer.parseInt(inputData.nextToken());

		if (a == 0 && b == 0) {
			printer.println("NO");
		} else {
			if (Math.abs(a - b) <= 1) {
				printer.println("YES");
			} else {
				printer.println("NO");
			}
		}
		printer.close();
	}

}
