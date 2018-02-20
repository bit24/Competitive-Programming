import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_434A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String orig = reader.readLine();

		StringBuffer output = new StringBuffer();

		for (int i = 0; i < orig.length(); i++) {
			char cur = orig.charAt(i);
			if (isVowel(cur)) {
				output.append(cur);
			} else {
				if (output.length() >= 2) {
					char l2 = output.charAt(output.length() - 2);
					char l1 = output.charAt(output.length() - 1);
					if (!isVowel(l2) && !isVowel(l1) && !(l1 == l2 && l2 == cur)) {
						output.append(' ');
					}
				}
				output.append(cur);
			}
		}
		printer.println(output.toString());
		printer.close();
	}

	static boolean isVowel(char cur) {
		return cur == 'a' || cur == 'e' || cur == 'i' || cur == 'o' || cur == 'u' || cur == ' ';
	}

}
