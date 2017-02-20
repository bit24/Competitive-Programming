import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div2_390C {

	public static void main(String[] args) throws IOException {
		new Div2_390C().execute();
	}

	char[] seperators = new char[] { '.', ',', '!', '?', ' ' };

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numT = Integer.parseInt(reader.readLine());

		caseLoop: while (numT-- > 0) {
			int numU = Integer.parseInt(reader.readLine());
			TreeMap<String, Integer> id = new TreeMap<String, Integer>();
			String[] names = new String[numU + 1];

			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int i = 1; i <= numU; i++) {
				String inputLine = inputData.nextToken();
				id.put(inputLine, i);
				names[i] = inputLine;
			}

			int numM = Integer.parseInt(reader.readLine());

			boolean[][] possible = new boolean[numM + 1][numU + 1];

			for (int i = 1; i <= numU; i++) {
				possible[0][i] = true;
			}

			String[] text = new String[numM + 1];
			for (int mI = 1; mI <= numM; mI++) {
				int pCount = 0;
				for (int i = 1; i <= numU; i++) {
					if (possible[mI - 1][i]) {
						pCount++;
					}
				}
				if (mI == 1) {
					pCount = numU + 1;
				}

				String inputLine = reader.readLine();

				if (inputLine.charAt(0) != '?') {
					int split = inputLine.indexOf(':');
					int cID = id.get(inputLine.substring(0, split));
					text[mI] = inputLine.substring(split + 1);

					if (pCount >= 2 || (pCount == 1 && !possible[mI - 1][cID])) {
						possible[mI][cID] = true;
					}
				} else {
					text[mI] = inputLine.substring(2);

					boolean[] banned = new boolean[numU + 1];
					StringBuilder cString = new StringBuilder();

					for (char cC : text[mI].toCharArray()) {
						if (isSeperator(cC)) {
							if (cString.length() > 0 && id.containsKey(cString.toString())) {
								banned[id.get(cString.toString())] = true;
							}
							cString.delete(0, cString.length());
						} else {
							cString.append(cC);
						}
					}
					if (cString.length() > 0 && id.containsKey(cString.toString())) {
						banned[id.get(cString.toString())] = true;
					}

					for (int cID = 1; cID <= numU; cID++) {
						if (!banned[cID] && (pCount >= 2 || (pCount == 1 && !possible[mI - 1][cID]))) {
							possible[mI][cID] = true;
						}
					}
				}
			}

			int lUsed = -1;
			for (int mI = numM; mI >= 1; mI--) {
				for (int i = 1; i <= numU; i++) {
					if (i != lUsed && possible[mI][i]) {
						text[mI] = names[i] + ':' + text[mI];
						lUsed = i;
						break;
					}
				}
				if (lUsed == -1) {
					printer.println("Impossible");
					continue caseLoop;
				}
			}
			for (int mI = 1; mI <= numM; mI++) {
				printer.println(text[mI]);
			}
		}
		printer.close();
	}

	boolean isSeperator(char c) {
		for (char seperator : seperators) {
			if (c == seperator) {
				return true;
			}
		}
		return false;
	}

}
