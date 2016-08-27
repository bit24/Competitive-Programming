import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*ID: eric.ca1
LANG: JAVA
TASK: charrec
*/

public class charrec {
	public static char[][][] dictionary;
	public static int numCharacters;

	public static int tLength;
	public static char[][] text;

	public static int[] minC;
	public static int[] cMatch;
	public static int[] previous;

	public static final int standard = 20;
	public static int INF = Integer.MAX_VALUE / 4;

	public static void main(String[] args) throws IOException {
		input();
		executeDP();
		printSolution();
	}

	public static void input() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("font.in"));
		numCharacters = Integer.parseInt(reader.readLine()) / standard;

		dictionary = new char[numCharacters][][];
		for (int charNum = 0; charNum < numCharacters; charNum++) {
			char[][] charDef = new char[standard][];
			for (int line = 0; line < standard; line++) {
				charDef[line] = reader.readLine().toCharArray();
			}
			dictionary[charNum] = charDef;
		}
		reader.close();

		reader = new BufferedReader(new FileReader("charrec.in"));
		tLength = Integer.parseInt(reader.readLine());

		text = new char[tLength][];

		for (int i = 0; i < tLength; i++) {
			text[i] = reader.readLine().toCharArray();
		}
		reader.close();
	}

	public static void executeDP() {
		minC = new int[tLength + 1];
		Arrays.fill(minC, INF);
		cMatch = new int[tLength + 1];
		previous = new int[tLength + 1];

		minC[0] = 0;

		for (int cLine = 0; cLine < tLength; cLine++) {
			pushTransitions(cLine);
		}
	}

	public static void pushTransitions(int cLine) {
		int cNumC = minC[cLine];
		if (cNumC >= INF) {
			return;
		}
		for (int charNum = 0; charNum < numCharacters; charNum++) {
			if (cLine + 18 < tLength) {

				int origValue = minC[cLine + 19];
				int newValue = cNumC + count19(cLine, charNum);

				if (newValue < origValue) {
					minC[cLine + 19] = newValue;
					cMatch[cLine + 19] = charNum;
					previous[cLine + 19] = cLine;
				}
			}
			if (cLine + 19 < tLength) {

				int origValue = minC[cLine + 20];
				int newValue = cNumC + count20(cLine, charNum);

				if (newValue < origValue) {
					minC[cLine + 20] = newValue;
					cMatch[cLine + 20] = charNum;
					previous[cLine + 20] = cLine;
				}
			}
			if (cLine + 20 < tLength) {

				int origValue = minC[cLine + 21];
				int newValue = cNumC + count21(cLine, charNum);

				if (newValue < origValue) {
					minC[cLine + 21] = newValue;
					cMatch[cLine + 21] = charNum;
					previous[cLine + 21] = cLine;
				}
			}
		}
	}

	public static int count19(int startL, int charNum) {
		final int lastLine = 18;
		int[] normCount = new int[lastLine + 1];
		int[] nextCount = new int[lastLine + 1];

		for (int i = 0; i <= lastLine; i++) {
			char[] textLine = text[startL + i];

			// i is always less than standard
			normCount[i] = count(textLine, dictionary[charNum][i]);

			// i + 1 is always less than standard
			nextCount[i] = count(textLine, dictionary[charNum][i + 1]);
		}

		for (int i = 1; i <= lastLine; i++) {
			normCount[i] += normCount[i - 1];
		}

		for (int i = lastLine - 1; i >= 0; i--) {
			nextCount[i] += nextCount[i + 1];
		}

		int min = INF;

		for (int missedAfter = 0; missedAfter <= lastLine; missedAfter++) {
			int cost = 0;
			cost += normCount[missedAfter];

			if (missedAfter + 1 <= lastLine) {
				cost += nextCount[missedAfter + 1];
			}
			if (cost < min) {
				min = cost;
			}
		}
		return min;
	}

	public static int count20(int startL, int charNum) {
		int total = 0;
		for (int i = 0; i < standard; i++) {
			char[] textLine = text[startL + i];
			char[] dictLine = dictionary[charNum][i];

			total += count(textLine, dictLine);
		}
		return total;
	}

	public static int count21(int startL, int charNum) {
		final int lastLine = 20;
		int[] dictNormC = new int[lastLine + 1];
		int[] dictPrevC = new int[lastLine + 1];

		for (int i = 0; i <= lastLine; i++) {
			char[] textLine = text[startL + i];

			// i can be >= standard
			if (i < standard) {
				dictNormC[i] = count(textLine, dictionary[charNum][i]);
			}
			// i - 1 can be less than 0
			// i - 1 is always less than standard
			if (i - 1 >= 0) {
				dictPrevC[i] = count(textLine, dictionary[charNum][i - 1]);
			}
		}

		int[] normSum = new int[lastLine + 1];
		int[] prevSum = new int[lastLine + 1];

		// last line doesn't have a normal count
		for (int i = 0; i < lastLine; i++) {
			normSum[i] = dictNormC[i];
			if (i - 1 >= 0) {
				normSum[i] += normSum[i - 1];
			}
		}

		// first line doesn't have a previous count
		for (int i = lastLine; i >= 1; i--) {
			prevSum[i] = dictPrevC[i];
			if (i + 1 <= lastLine) {
				prevSum[i] += prevSum[i + 1];
			}
		}

		int min = INF;
		for (int dLine = 0; dLine < lastLine; dLine++) {
			int cost = 0;
			if (dLine - 1 >= 0) {
				cost += normSum[dLine - 1];
			}
			cost += Math.min(dictNormC[dLine], dictPrevC[dLine + 1]);
			if (dLine + 2 <= lastLine) {
				cost += prevSum[dLine + 2];
			}
			if (cost < min) {
				min = cost;
			}
		}
		return min;
	}

	public static int count(char[] a, char[] b) {
		assert (a.length == b.length);
		int count = 0;
		for (int i = 0; i < standard; i++) {
			if (a[i] != b[i]) {
				count++;
			}
		}
		return count;
	}

	public static void printSolution() throws IOException {
		String characters = " abcdefghijklmnopqrstuvwxyz";
		int currentIndex = tLength;

		ArrayList<Character> output = new ArrayList<Character>();
		while (currentIndex > 0) {
			output.add(characters.charAt(cMatch[currentIndex]));
			currentIndex = previous[currentIndex];
		}
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("charrec.out")));
		Collections.reverse(output);
		for (char element : output) {
			printer.print(element);
		}

		printer.println();
		printer.close();
	}

}
