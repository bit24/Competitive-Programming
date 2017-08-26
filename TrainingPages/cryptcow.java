import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/*ID: eric.ca1
LANG: JAVA
TASK: cryptcow
*/

//Super Sketchy

public class cryptcow {

	public static void main(String[] args) throws IOException {
		new cryptcow().execute();
	}

	char[] pattern = "Begin the Escape execution at the Break of Dawn".toCharArray();
	char[] text;

	String patternS = "Begin the Escape execution at the Break of Dawn";
	String textS;

	boolean[] isImpossible = new boolean[1 << 22];
	boolean[] isPatternSub = new boolean[1 << 22];

	public void execute() throws IOException {
		for (int i = 0; i < pattern.length; i++) {
			for (int j = i; j < pattern.length; j++) {
				isPatternSub[hash(pattern, i, j)] = true;
			}
		}

		/*
		 * char[] testArray = "ution at the Bre".toCharArray(); System.out.println(isPatternSub[hash(testArray)]);
		 */

		BufferedReader reader = new BufferedReader(new FileReader("cryptcow.in"));
		textS = reader.readLine();
		text = textS.toCharArray();
		reader.close();

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cryptcow.out")));
		// CEW check
		int numSwaps = (text.length - pattern.length) / 3;
		if (countOccurences(text, 'C') != numSwaps || countOccurences(text, 'O') != numSwaps
				|| countOccurences(text, 'W') != numSwaps) {
			printer.println("0 0");
			printer.close();
			return;
		}

		// lower case letters check
		int[] numReq = new int[26];
		for (int i = 0; i < pattern.length; i++) {
			if (Character.isLowerCase(pattern[i])) {
				numReq[pattern[i] - 'a']++;
			}
		}

		int[] numCont = new int[26];
		for (int i = 0; i < text.length; i++) {
			if (Character.isLowerCase(text[i])) {
				numCont[text[i] - 'a']++;
			}
		}

		for (int i = 0; i < 26; i++) {
			if (numReq[i] != numCont[i]) {
				printer.println("0 0");
				printer.close();
				return;
			}
		}

		// upper case letters check
		if (countOccurences(text, 'B') != 2 || countOccurences(text, 'E') != 1 || countOccurences(text, 'D') != 1) {
			printer.println("0 0");
			printer.close();
			return;
		}

		if (!matchesPattern(text)) {
			printer.println("0 0");
			printer.close();
			return;
		}

		// Substring check

		int start = 0;
		for (int i = 0; i < text.length; i++) {
			if (text[i] == 'C' || text[i] == 'O' || text[i] == 'W') {
				if (!patternS.contains(textS.substring(start, i))) {
					printer.println("0 0");
					printer.close();
					return;
				}
				start = i + 1;
			}
		}

		printer.println("1 " + numSwaps);

		printer.close();

	}

	boolean matchesPattern(char[] cText) {
		if (isImpossible[hash(cText)]) {
			return false;
		}

		// PRUNING PHASE
		for (int i = 0; i < cText.length && cText[i] != 'C'; i++) {
			if (cText[i] != pattern[i]) {
				isImpossible[hash(cText)] = true;
				return false;
			}
		}

		for (int i = 0; i < cText.length && cText[cText.length - 1 - i] != 'W'; i++) {
			if (cText[cText.length - 1 - i] != pattern[pattern.length - 1 - i]) {
				isImpossible[hash(cText)] = true;
				return false;
			}
		}

		int lastChar = 0;
		for (int i = 0; i < cText.length; i++) {
			char cChar = cText[i];
			if (cChar == 'C' || cChar == 'O' || cChar == 'W') {
				if (lastChar != i && !isPatternSub[hash(cText, lastChar, i - 1)]) {
					isImpossible[hash(cText)] = true;
					return false;
				}
				lastChar = i + 1;
			}
		}

		// EXECUTION PHASE
		int swapsLeft = (cText.length - pattern.length) / 3;

		if (swapsLeft == 0) {
			assert (cText.length == pattern.length);
			for (int i = 0; i < cText.length; i++) {
				if (cText[i] != pattern[i]) {
					isImpossible[hash(cText)] = true;
					return false;
				}
			}
			return true;
		}

		int[] cIndices = new int[swapsLeft];
		int numC = 0;
		int[] oIndices = new int[swapsLeft];
		int numO = 0;
		int[] wIndices = new int[swapsLeft];
		int numW = 0;

		for (int i = 0; i < cText.length; i++) {
			char cChar = cText[i];
			if (cChar == 'C') {
				cIndices[numC++] = i;
			} else if (cChar == 'O') {
				oIndices[numO++] = i;
			} else if (cChar == 'W') {
				wIndices[numW++] = i;
			}
		}

		for (int cII = 0; cII < cIndices.length; cII++) {
			for (int oII = 0; oII < cIndices.length; oII++) {
				for (int wII = 0; wII < wIndices.length; wII++) {
					if (cIndices[cII] < oIndices[oII] && oIndices[oII] < wIndices[wII]) {
						if (matchesPattern(swapDelete(cText, cIndices[cII], oIndices[oII], wIndices[wII]))) {
							return true;
						}
					}
				}
			}
		}

		isImpossible[hash(cText)] = true;
		return false;
	}

	char[] swapDelete(char[] orig, int ind1, int ind2, int ind3) {
		char[] newArray = new char[orig.length - 3];
		int cLength = 0;
		for (int i = 0; i < ind1; i++) {
			newArray[cLength++] = orig[i];
		}
		for (int i = ind2 + 1; i < ind3; i++) {
			newArray[cLength++] = orig[i];
		}
		for (int i = ind1 + 1; i < ind2; i++) {
			newArray[cLength++] = orig[i];
		}
		for (int i = ind3 + 1; i < orig.length; i++) {
			newArray[cLength++] = orig[i];
		}
		return newArray;
	}

	int countOccurences(char[] text, char pattern) {
		int count = 0;
		for (char cChar : text) {
			if (cChar == pattern) {
				count++;
			}
		}
		return count;
	}

	// 22 bit hash
	public int hash(char[] data, int startInd, int endInd) {

		long hash = 2166136261L;
		for (int i = startInd; i <= endInd; i++) {
			hash ^= data[i];
			hash *= 16777619;
			hash %= Integer.MAX_VALUE;
		}

		// int hash = Arrays.hashCode(data);
		int finalHash = (int) ((hash >> 22) ^ (hash & ((1 << 22) - 1)));
		assert (finalHash < (1 << 22));
		// System.out.println(Integer.toBinaryString(finalHash));
		return finalHash;
	}

	public int hash(char[] data) {
		return hash(data, 0, data.length - 1);
	}

	// textEnd is exclusive

	class CAWrapper {
		char[] elements;

		public int hashCode() {
			return Arrays.hashCode(elements);
		}

		public boolean equals(Object obj) {
			return Arrays.equals(elements, ((CAWrapper) obj).elements);
		}

		CAWrapper(char[] elements) {
			this.elements = elements;
		}
	}

}
