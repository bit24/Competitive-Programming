import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_425B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String gStr = reader.readLine();

		boolean[] isGood = new boolean[26];

		for (int i = 0; i < gStr.length(); i++) {
			isGood[gStr.charAt(i) - 'a'] = true;
		}

		String pattern = reader.readLine();

		int numQ = Integer.parseInt(reader.readLine());

		boolean hFill = false;

		for (int i = 0; i < pattern.length(); i++) {
			if (pattern.charAt(i) == '*') {
				hFill = true;
				break;
			}
		}

		qLoop:
		while (numQ-- > 0) {
			String query = reader.readLine();

			int fLen = query.length() - (hFill ? pattern.length() - 1 : pattern.length());

			if (fLen < 0) {
				printer.println("NO");
				continue qLoop;
			}

			int pI = 0;
			int qI = 0;
			while (pI < pattern.length() && qI < query.length() && pattern.charAt(pI) != '*') {
				if (pattern.charAt(pI) == '?') {
					if (!isGood[query.charAt(qI) - 'a']) {
						printer.println("NO");
						continue qLoop;
					}
				} else {
					if (pattern.charAt(pI) != query.charAt(qI)) {
						printer.println("NO");
						continue qLoop;
					}
				}
				pI++;
				qI++;
			}

			if (hFill) {
				for (int fI = 0; fI < fLen; fI++) {
					if (isGood[query.charAt(qI) - 'a']) {
						printer.println("NO");
						continue qLoop;
					}
					qI++;
				}
				pI++;
			}

			while (pI < pattern.length() && qI < query.length()) {
				if (pattern.charAt(pI) == '?') {
					if (!isGood[query.charAt(qI) - 'a']) {
						printer.println("NO");
						continue qLoop;
					}
				} else {
					if (pattern.charAt(pI) != query.charAt(qI)) {
						printer.println("NO");
						continue qLoop;
					}
				}
				pI++;
				qI++;
			}

			if (qI == query.length() && pI == pattern.length()) {
				printer.println("YES");
			} else {
				printer.println("NO");
			}
		}
		printer.close();
	}

}
