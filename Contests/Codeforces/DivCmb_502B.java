import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DivCmb_502B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());
		String a = reader.readLine();
		String b = reader.readLine();

		long t0 = 0;
		long t1 = 0;

		long cnt0 = 0;
		long cnt1 = 0;

		for (int i = 0; i < len; i++) {
			if (b.charAt(i) != '0') {
				if (a.charAt(i) == '0') {
					t0++;
				} else {
					t1++;
				}
			} else {
				if (a.charAt(i) == '0') {
					cnt0++;
				} else {
					cnt1++;
				}
			}
		}
		printer.println(cnt0 * cnt1 + cnt0 * t1 + cnt1 * t0);
		printer.close();
	}
}
