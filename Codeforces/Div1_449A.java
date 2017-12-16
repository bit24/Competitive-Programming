import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_449A {

	static String f0 = "What are you doing at the end of the world? Are you busy? Will you save us?";
	static String a0 = "What are you doing while sending \"";
	static String a1 = "\"? Are you busy? Will you send \"";
	static String a2 = "\"?";

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		long[] len = new long[100_005];
		len[0] = f0.length();

		for (int i = 1; i <= 53; i++) {
			len[i] = 2 * len[i - 1] + a0.length() + a1.length() + a2.length();
		}
		for (int i = 54; i < 100_005; i++) {
			len[i] = Long.MAX_VALUE;
		}

		int nQ = Integer.parseInt(reader.readLine());
		qLoop:
		while (nQ-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int n = Integer.parseInt(inputData.nextToken());
			long k = Long.parseLong(inputData.nextToken()) - 1;
			if (k >= len[n]) {
				printer.print(".");
				continue;
			}

			while (n > 0) {
				if (k < a0.length()) {
					printer.print(a0.charAt((int) k));
					continue qLoop;
				}
				k -= a0.length();
				if (k < len[n - 1]) {
					n--;
					continue;
				}
				k -= len[n - 1];
				if (k < a1.length()) {
					printer.print(a1.charAt((int) k));
					continue qLoop;
				}
				k -= a1.length();
				if (k < len[n - 1]) {
					n--;
					continue;
				}
				k -= len[n - 1];
				printer.print(a2.charAt((int) k));
				continue qLoop;
			}

			if (n == 0) {
				printer.print(f0.charAt((int) k));
			}
		}
		printer.println();
		printer.close();
	}

}
