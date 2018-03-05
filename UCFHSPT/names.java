import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class names {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nQ = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nQ; cT++) {
			ArrayList<String> pos = new ArrayList<String>();
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			String a = inputData.nextToken();
			String b = inputData.nextToken();
			for (int i = 0; i < a.length() - 1; i++) {
				for (int j = 0; j < b.length() - 1; j++) {
					if (isVowel(a.charAt(i)) != isVowel(b.charAt(b.length() - j - 1))) {
						pos.add(a.substring(0, i + 1) + b.substring(b.length() - j - 1, b.length()));
					}
					if (isVowel(b.charAt(j)) != isVowel(a.charAt(a.length() - i - 1))) {
						pos.add(b.substring(0, j + 1) + a.substring(a.length() - i - 1, a.length()));
					}
				}
			}
			Collections.sort(pos);
			int cnt = 0;
			for (int i = 0; i < pos.size(); i++) {
				if (i == 0 || !pos.get(i).equals(pos.get(i - 1))) {
					cnt++;
				}
			}
			printer.println("Couple #" + cT + ": " + cnt + " possible names");
			for (int i = 0; i < pos.size(); i++) {
				if (i == 0 || !pos.get(i).equals(pos.get(i - 1))) {
					printer.println(pos.get(i));
				}
			}
			printer.println();
		}
		printer.close();
	}

	static boolean isVowel(char inp) {
		return inp == 'A' || inp == 'E' || inp == 'I' || inp == 'O' || inp == 'U';
	}
}
