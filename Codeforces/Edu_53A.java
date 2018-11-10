import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Edu_53A {

	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());
		String s = reader.readLine();

		for (int l = 0; l < N; l++) {
			int[] cnt = new int[26];
			int max = 0;
			for (int r = l; r < N; r++) {
				max = Math.max(max, ++cnt[s.charAt(r) - 'a']);
				if (r - l + 1 >= 2 * max) {
					printer.println("YES");
					printer.println(s.substring(l, r + 1));
					printer.close();
					return;
				}
			}
		}
		printer.println("NO");
		printer.close();
	}
}
