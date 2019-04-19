import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_395B {

	public static void main(String[] args) throws IOException {
		new Div2_395B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numE = Integer.parseInt(reader.readLine());
		long[] elements = new long[numE];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			elements[i] = Long.parseLong(inputData.nextToken());
		}

		for (int i = 0; i < numE / 2; i++) {
			if ((i & 1) == 1) {
				printer.print(elements[i] + " ");
			} else {
				printer.print(elements[numE - 1 - i] + " ");
			}
		}
		for (int i = numE / 2; i < numE; i++) {
			if ((i & 1) != ((numE - 1) & 1)) {
				printer.print(elements[i] + " ");
			} else {
				printer.print(elements[numE - 1 - i] + " ");
			}
		}
		printer.println();
		printer.close();
	}

}
