import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PalindromicPartitions {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		while (nT-- > 0) {
			String text = reader.readLine();
			int nI = 0;

			int nC = 0;
			ArrayList<Character> lStr = new ArrayList<Character>();
			ArrayList<Character> rStr = new ArrayList<Character>();
			oLoop:
			while (true) {
				if (nI >= text.length() - 1 - nI) {
					if (!lStr.isEmpty() || nI == text.length() - 1 - nI) {
						nC++;
					}
					break;
				}
				lStr.add(text.charAt(nI));
				rStr.add(text.charAt(text.length() - 1 - nI));
				nI++;
				for (int i = 0; i < lStr.size(); i++) {
					if (lStr.get(i) != rStr.get(lStr.size() - 1 - i)) {
						continue oLoop;
					}
				}
				lStr.clear();
				rStr.clear();
				nC += 2;
			}
			printer.println(nC);
		}
		printer.close();
	}

}
