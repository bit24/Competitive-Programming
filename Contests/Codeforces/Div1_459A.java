import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_459A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String str = reader.readLine();
		int ans = 0;

		for (int i = 0; i < str.length(); i++) {
			int nO = 0;
			int nC = 0;
			loop2:
			for (int j = i; j < str.length(); j++) {
				if (str.charAt(j) == '(') {
					nO++;
				} else if (str.charAt(j) == '?') {
					if (nO > 0) {
						nO--;
						nC++;
					} else {
						nO++;
					}
				} else {
					if (nO > 0) {
						nO--;
					} else if (nC > 0) {
						nC--;
						nO++;
					} else {
						break loop2;
					}
				}
				if (nO == 0) {
					ans++;
				}
			}
		}
		printer.println(ans);
		printer.close();
	}
}
