import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_424B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		String t1 = reader.readLine();
		String t2 = reader.readLine();
		int[] map = new int[26];

		for (int i = 0; i < 26; i++) {
			map[t1.charAt(i) - 'a'] = t2.charAt(i) - 'a';
		}

		String t3 = reader.readLine();

		for (int i = 0; i < t3.length(); i++) {
			char cChar = t3.charAt(i);
			if (!Character.isAlphabetic(cChar)) {
				printer.print(t3.charAt(i));
			} else if (Character.isLowerCase(cChar)) {
				printer.print((char) ('a' + map[cChar - 'a']));
			} else {
				printer.print((char) ('A' + map[cChar - 'A']));
			}
		}
		printer.println();
		printer.close();
	}

}
