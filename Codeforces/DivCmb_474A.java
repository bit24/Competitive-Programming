import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DivCmb_474A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String line = reader.readLine();
		int i = 0;
		int aCnt = 0;
		int bCnt = 0;
		int cCnt = 0;

		for (; i < line.length(); i++) {
			if (line.charAt(i) == 'a') {
				aCnt++;
			} else {
				break;
			}
		}

		for (; i < line.length(); i++) {
			if (line.charAt(i) == 'b') {
				bCnt++;
			} else {
				break;
			}
		}
		for (; i < line.length(); i++) {
			if (line.charAt(i) == 'c') {
				cCnt++;
			} else {
				break;
			}
		}

		if (aCnt >= 1 && bCnt >= 1 && (cCnt == aCnt || cCnt == bCnt) && i == line.length()) {
			printer.println("YES");
		}
		else {
			printer.print("NO");
		}
		printer.close();
	}
}
