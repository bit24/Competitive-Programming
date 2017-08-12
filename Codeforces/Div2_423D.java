import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_423D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numL = Integer.parseInt(inputData.nextToken());
		
		int cLen = (numV - 1) / numL;
		int numR = numV - 1 - numL * cLen;

		if (numR == 0) {
			printer.println(cLen * 2);
		} else if (numR == 1) {
			printer.println(cLen * 2 + 1);
		} else {
			printer.println(cLen * 2 + 2);
		}

		int lUsed = 1;
		for (int i = 1; i <= numL - numR; i++) {
			if (cLen >= 1) {
				printer.println(1 + " " + ++lUsed);
			}
			for (int j = 2; j <= cLen; j++) {
				printer.println(lUsed + " " + ++lUsed);
			}
		}

		for (int i = 1; i <= numR; i++) {
			printer.println(1 + " " + ++lUsed);
			for (int j = 2; j <= cLen + 1; j++) {
				printer.println(lUsed + " " + ++lUsed);
			}
		}

		printer.close();
	}

}
