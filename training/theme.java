
/*ID: eric.ca1
 LANG: JAVA
 TASK: theme
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class theme {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("theme.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("theme.out")));
		int numElements = Integer.parseInt(reader.readLine());
		int[] elements = new int[numElements];

		for (int i = 0; i < numElements;) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 20 && i < numElements; j++) {
				elements[i++] = Integer.parseInt(inputData.nextToken());
			}
		}
		reader.close();

		int maxLengthMinusOne = 0;

		for (int sPoint1 = 0; sPoint1 < numElements; sPoint1++) {
			for (int sPoint2 = sPoint1 + maxLengthMinusOne + 1; sPoint2 + maxLengthMinusOne < numElements; sPoint2++) {

				int difference = elements[sPoint1] - elements[sPoint2];
				for (int stringIndex = 0; sPoint1 + stringIndex < sPoint2 && sPoint2 + stringIndex < numElements
						&& elements[sPoint1 + stringIndex]
								- elements[sPoint2 + stringIndex] == difference; stringIndex++) {

					if (maxLengthMinusOne < stringIndex) {
						maxLengthMinusOne = stringIndex;
					}
				}
			}
			

		}

		int maxLength = maxLengthMinusOne + 1;

		if (maxLength < 5) {
			printer.println(0);
		} else {
			printer.println(maxLength);
		}
		printer.close();

	}

}
