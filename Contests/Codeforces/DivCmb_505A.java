import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DivCmb_505A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());
		int[] cnt = new int[26];
		String line = reader.readLine();
		for (int i = 0; i < len; i++) {
			cnt[line.charAt(i) - 'a']++;
		}

		int nSingle = 0;
		int nNSingle = 0;
		for (int i = 0; i < 26; i++) {
			if (cnt[i] == 1) {
				nSingle++;
			} else if (cnt[i] >= 2) {
				nNSingle++;
			}
		}
		
		if (nNSingle > 0) {
			printer.println("Yes");
			printer.close();
			return;
		}
		
		printer.println(nSingle <= 1 ? "Yes" : "No");
		printer.close();
	}
}
