import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_462B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long p = Long.parseLong(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());

		long[] ans = new long[80];

		int cI;
		for (cI = 0; p > 0; cI++) {
			if ((cI & 1) == 0) {
				ans[cI] = p % k;
				p /= k;
			} else {
				if (p % k != 0) {
					ans[cI] = k - p % k;
					p /= k;
					p += 1;
				} else {
					p /= k;
				}
			}
		}

		printer.println(cI);
		for (int i = 0; i < cI; i++) {
			printer.print(ans[i] + " ");
		}
		printer.println();
		printer.close();
	}

}
