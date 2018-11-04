import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DivCmb_519C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String str = reader.readLine();
		int N = str.length();

		int[] ans = new int[N];

		boolean rev = false;
		for (int cI = N - 1; cI >= 0; cI--) {
			if (rev) {
				if (str.charAt(cI) == 'a') {
					rev = true;
				} else {
					rev = false;
					ans[cI] = 1;
				}
			} else {
				if (str.charAt(cI) == 'b') {
					rev = false;
				} else {
					rev = true;
					ans[cI] = 1;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			printer.print(ans[i] + " ");
		}
		printer.println();
		printer.close();
	}
}
