import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_419B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numS = Integer.parseInt(inputData.nextToken());
		int numR = Integer.parseInt(inputData.nextToken());
		int numQ = Integer.parseInt(inputData.nextToken());

		int[] dSum = new int[200_002];
		for (int i = 0; i < numS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			dSum[Integer.parseInt(inputData.nextToken())]++;
			dSum[Integer.parseInt(inputData.nextToken()) + 1]--;
		}

		int[] pSum = new int[200_002];

		int cItem = 0;
		for (int i = 1; i <= 200_000; i++) {
			cItem += dSum[i];
			pSum[i] = pSum[i - 1] + (cItem >= numR ? 1 : 0);
		}

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			printer.println(
					-pSum[Integer.parseInt(inputData.nextToken()) - 1] + pSum[Integer.parseInt(inputData.nextToken())]);
		}
		printer.close();
	}

}
