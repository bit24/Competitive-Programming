import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Div2_451B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());
		int a = Integer.parseInt(reader.readLine());
		int b = Integer.parseInt(reader.readLine());
		int[] last = new int[n + 1];
		Arrays.fill(last, -1);
		last[0] = 2;
		for (int i = 0; i < n; i++) {
			if (last[i] != -1) {
				if (i + a <= n) {
					last[i + a] = 0;
				}
				if (i + b <= n) {
					last[i + b] = 1;
				}
			}
		}
		if (last[n] == -1) {
			printer.println("NO");
		} else {
			printer.println("YES");
			int aCnt = 0;
			int bCnt = 0;
			for (int i = n; i > 0;) {
				if (last[i] == 0) {
					aCnt++;
					i -= a;
				} else {
					bCnt++;
					i -= b;
				}
			}
			printer.println(aCnt + " " + bCnt);
		}
		printer.close();
	}

}
