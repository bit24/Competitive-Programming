import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_395A {

	public static void main(String[] args) throws IOException {
		new Div2_395A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int cInt = Integer.parseInt(inputData.nextToken());
		int aInt = Integer.parseInt(inputData.nextToken());
		int length = Integer.parseInt(inputData.nextToken());
		int count = 0;
		for (int i = 1; i <= length; i++) {
			if (i % aInt == 0 && i % cInt == 0) {
				count++;
			}
		}
		printer.println(count);
		printer.close();
	}

}
