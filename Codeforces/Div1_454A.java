import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_454A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int nO = Integer.parseInt(reader.readLine());
		boolean[] pos = new boolean[26];
		Arrays.fill(pos, true);
		int nP = 26;

		int ext = 0;

		for (int i = 0; i < nO - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			String op = inputData.nextToken();

			if (op.equals(".")) {
				String word = inputData.nextToken();
				for (int j = 0; j < word.length(); j++) {
					int cChar = word.charAt(j) - 'a';
					if (pos[cChar]) {
						pos[cChar] = false;
						nP--;
					}
				}
			} else if (op.equals("!")) {
				if (nP != 1) {
					String word = inputData.nextToken();
					boolean[] used = new boolean[26];
					for (int j = 0; j < word.length(); j++) {
						used[word.charAt(j) - 'a'] = true;
					}
					for (int j = 0; j < 26; j++) {
						if (!used[j]) {
							if (pos[j]) {
								pos[j] = false;
								nP--;
							}
						}
					}
				} else {
					ext++;
				}
			} else {
				if (nP != 1) {
					int cChar = inputData.nextToken().charAt(0) - 'a';
					if(pos[cChar]) {
						pos[cChar] = false;
						nP--;
					}
				}
				else {
					ext++;
				}
			}
		}
		printer.println(ext);
		printer.close();
	}
}
