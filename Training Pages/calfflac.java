import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
LANG: JAVA
TASK: calfflac
*/

public class calfflac {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("calfflac.in"));
		StringBuilder strBuilder = new StringBuilder();

		String nextLine;
		while ((nextLine = reader.readLine()) != null) {
			strBuilder.append(nextLine);
			strBuilder.append("\n");
		}
		reader.close();

		int numUsuable = 0;
		for (int i = 0; i < strBuilder.length(); i++) {
			char cChar = strBuilder.charAt(i);
			if ((65 <= cChar && cChar <= 90) || (97 <= cChar && cChar <= 122)) {
				numUsuable++;
			}
		}
		char[] uChar = new char[numUsuable];
		int[] reference = new int[numUsuable];

		int currentLength = 0;
		for (int i = 0; i < strBuilder.length(); i++) {
			char cChar = strBuilder.charAt(i);
			if ((65 <= cChar && cChar <= 90) || (97 <= cChar && cChar <= 122)) {
				reference[currentLength] = i;
				if (65 <= cChar && cChar <= 90) {
					uChar[currentLength++] = strBuilder.charAt(i);
				} else {
					uChar[currentLength++] = (char) (strBuilder.charAt(i) - 32);
				}
			}
		}

		int maxLength = 1;
		int mLeft = 0;
		int mRight = 0;

		// case length of palindrome is odd
		for (int center = 0; center < uChar.length; center++) {
			int left = center;
			int right = center;
			while (left - 1 >= 0 && right + 1 < uChar.length && uChar[left - 1] == uChar[right + 1]) {
				left--;
				right++;
			}
			int length = right - left + 1;
			if (maxLength < length) {
				maxLength = length;
				mLeft = left;
				mRight = right;
			}

			// case length of palindrome is even
			if (center + 1 < uChar.length && uChar[center] == uChar[center + 1]) {
				left = center;
				right = center + 1;
				while (left - 1 >= 0 && right + 1 < uChar.length && uChar[left - 1] == uChar[right + 1]) {
					left--;
					right++;
				}
				length = right - left + 1;
				if (maxLength < length) {
					maxLength = length;
					mLeft = left;
					mRight = right;
				}
			}
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("calfflac.out")));
		printer.println(maxLength);
		int pLeft = reference[mLeft];
		int pRight = reference[mRight];

		for (int i = pLeft; i <= pRight; i++) {
			printer.print(strBuilder.charAt(i));
		}
		printer.println();
		printer.close();

	}

}
