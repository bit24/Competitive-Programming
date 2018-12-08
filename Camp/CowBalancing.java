import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CowBalancing {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String str = reader.readLine();
		int N = str.length();

		int[] GMH = new int[N];
		GMH[0] = str.charAt(0) == 'G' ? 1 : -1;

		for (int i = 1; i < N; i++) {
			GMH[i] = GMH[i - 1] + (str.charAt(i) == 'G' ? 1 : -1);
		}

		if (GMH[N - 1] != 0) {
			printer.println("NO");
			printer.close();
			return;
		}

		boolean[] exists = new boolean[2 * N + 1];

		for (int i = 0; i < N; i++) {
			int n = (i + 1) % N;
			if (str.charAt(i) == 'H' && str.charAt(n) == 'G') {
				exists[N + GMH[i]] = true;
			}
		}

		for (int i = 0; i < N; i++) {
			int n = (i + 1) % N;
			if (str.charAt(i) == 'G' && str.charAt(n) == 'H') {
				if (GMH[i] - 1 + N >= 0 && exists[N + GMH[i] - 1]) {
					printer.println("YES");
					printer.close();
					return;
				}
			}
		}
		printer.println("NO");
		printer.close();
	}
}