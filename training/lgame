import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: lgame
*/

public class lgame {

	static char[] alphabet;
	static int alphabetSize;
	static Trie dict = new lgame().new Trie();

	static int[] value = new int[] { 2, 5, 4, 4, 1, 6, 5, 5, 1, 7, 6, 3, 5, 2, 3, 5, 7, 2, 1, 2, 4, 6, 6, 7, 5, 7 };

	static int maxValue = 0;

	static LinkedHashSet<String> solutionSet = new LinkedHashSet<String>();

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("lgame.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("lgame.out")));

		alphabet = reader.readLine().toCharArray();
		Arrays.sort(alphabet);
		alphabetSize = alphabet.length;
		reader.close();

		BufferedReader dictReader = new BufferedReader(new FileReader("lgame.dict"));

		String inputLine = dictReader.readLine();
		while (!inputLine.equals(".")) {
			dict.add(inputLine);
			inputLine = dictReader.readLine();
		}
		dictReader.readLine();
		dictReader.close();

		enumerate("", "", (1 << (alphabetSize + 1)) - 1);

		String[] items = solutionSet.toArray(new String[0]);

		solutionSet.clear();

		for (String currentLine : items) {
			StringTokenizer tokenizer = new StringTokenizer(currentLine);
			String[] words = new String[tokenizer.countTokens()];

			for (int cWordIndex = 0; cWordIndex < words.length; cWordIndex++) {
				words[cWordIndex] = tokenizer.nextToken();
			}

			Arrays.sort(words);
			StringBuilder builder = new StringBuilder();
			for (String currentWord : words) {
				builder.append(currentWord);
				builder.append(" ");
			}
			builder.deleteCharAt(builder.length() - 1);
			solutionSet.add(builder.toString());
		}
		
		items = solutionSet.toArray(new String[0]);
		Arrays.sort(items);
		
		printer.println(maxValue);
		
		for(String str : items){
			printer.println(str);
		}
		
		printer.close();
	}

	public static void enumerate(String finishedString, String unfinishedString, int unused) {
		if (maxValue < computeValue(finishedString)) {
			maxValue = computeValue(finishedString);
			solutionSet.clear();
			solutionSet.add(finishedString);
		} else if (maxValue == computeValue(finishedString)) {
			solutionSet.add(finishedString);
		}

		for (int charID = 0; charID < alphabetSize; charID++) {

			if ((unused & (1 << charID)) != 0) {

				String newPhrase = unfinishedString + alphabet[charID];

				if (dict.containsPrefix(newPhrase)) {

					if (dict.containsWord(newPhrase)) {
						enumerate(finishedString + " " + newPhrase, "", unused & ~(1 << charID));
					}

					enumerate(finishedString, newPhrase, unused & ~(1 << charID));

				}
			}
		}

	}

	public static int computeValue(String str) {
		int total = 0;
		for (char i : str.toCharArray()) {
			if (i == ' ') {
				continue;
			}
			total += value[i - 97];
		}
		return total;
	}

	class Trie {

		Node[] root = new Node[26];

		public void add(String element) {

			int[] indices = toKey(element);

			Node currentNode = root[indices[0]];

			if (currentNode == null) {
				root[indices[0]] = currentNode = new Node();
			}

			for (int ii = 1; ii < indices.length; ii++) {
				if (currentNode.child[indices[ii]] == null) {
					currentNode.child[indices[ii]] = new Node();
				}
				currentNode = currentNode.child[indices[ii]];
				if (ii == indices.length - 1) {
					currentNode.finishesWord = true;
				}
			}
		}

		public int[] toKey(String input) {
			input.toLowerCase();
			char[] charInput = input.toCharArray();
			int inputLength = charInput.length;

			int[] key = new int[inputLength];

			for (int i = 0; i < inputLength; i++) {
				key[i] = charInput[i] - 97;
			}
			return key;
		}

		public boolean containsPrefix(String element) {
			int[] indices = toKey(element);
			Node currentNode = root[indices[0]];

			if (currentNode == null) {
				return false;
			}

			for (int ii = 1; ii < indices.length; ii++) {
				if (currentNode.child[indices[ii]] == null) {
					return false;
				}
				currentNode = currentNode.child[indices[ii]];
			}
			return true;
		}

		public boolean containsWord(String element) {
			int[] indices = toKey(element);
			Node currentNode = root[indices[0]];

			if (currentNode == null) {
				return false;
			}

			for (int ii = 1; ii < indices.length; ii++) {
				if (currentNode.child[indices[ii]] == null) {
					return false;
				}
				currentNode = currentNode.child[indices[ii]];
			}
			return currentNode.finishesWord;
		}

		class Node {
			Node[] child = new Node[26];
			boolean finishesWord;
		}

	}

}
