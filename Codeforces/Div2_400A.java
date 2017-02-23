import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_400A {

	public static void main(String[] args) throws IOException {
		new Div2_400A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		String[] data = new String[] { inputData.nextToken(), inputData.nextToken() };
		printer.println(data[0] + " " + data[1]);

		int n = Integer.parseInt(reader.readLine());
		for (int i = 0; i < n; i++) {
			inputData = new StringTokenizer(reader.readLine());
			if (data[0].equals(inputData.nextToken())) {
				data[0] = inputData.nextToken();
			} else {
				data[1] = inputData.nextToken();
			}

			printer.println(data[0] + " " + data[1]);
		}
		printer.close();
	}
}
