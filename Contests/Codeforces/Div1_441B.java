import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_441B {

	static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		n = Integer.parseInt(reader.readLine());

		boolean[] sec = new boolean[n + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		printer.println(1);

		int lF = n;
		int nSB = 0;
		for (int i = 0; i < n; i++) {
			int cInd = Integer.parseInt(inputData.nextToken());
			sec[cInd] = true;
			if (cInd < lF) {
				nSB++;
			} else {
				lF--;
				while(sec[lF]) {
					nSB--;
					lF--;
				}
			}
			printer.println(nSB + 1);
		}

		printer.close();
	}
}
