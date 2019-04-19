import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_519A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int sum = 0;
		int max = 0;
		for (int i = 0; i < N; i++) {
			int c = Integer.parseInt(inputData.nextToken());
			max = Math.max(max, c);
			sum += c;
		}

		printer.println(Math.max(max, 2 * sum / N + 1));
		printer.close();
	}
}
