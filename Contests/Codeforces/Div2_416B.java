import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_416B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numQ = Integer.parseInt(inputData.nextToken());

		int[] elements = new int[numE];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken()) - 1;
			int r = Integer.parseInt(inputData.nextToken()) - 1;
			int q = Integer.parseInt(inputData.nextToken()) - 1;
			int qI = elements[q];

			int less = 0;
			for (int j = l; j <= r; j++) {
				if (elements[j] < qI) {
					less++;
				}
			}

			if (less == q - l) {
				printer.println("Yes");
			} else {
				printer.println("No");
			}
		}
		printer.close();
	}

}
