import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_423C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int nI = Integer.parseInt(reader.readLine());

		String[] iStrs = new String[nI + 1];
		iStrs[0] = "a";
		int len = 0;

		int[] start = new int[2_000_001];

		for (int cI = 1; cI <= nI; cI++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			String cStr = iStrs[cI] = inputData.nextToken();
			int nO = Integer.parseInt(inputData.nextToken());
			while (nO-- > 0) {
				int cStart = Integer.parseInt(inputData.nextToken()) - 1;
				if (iStrs[start[cStart]].length() <= cStr.length()) {
					start[cStart] = cI;
				}
				if (cStart + cStr.length() > len) {
					len = cStart + cStr.length();
				}
			}
		}

		String cStr = "";
		int cStrI = 0;

		for (int i = 0; i < len; i++) {
			if (iStrs[start[i]].length() > cStr.length() - cStrI) {
				cStr = iStrs[start[i]];
				cStrI = 0;
			}
			printer.print(cStr.charAt(cStrI++));
		}
		printer.println();
		printer.close();
	}

}
