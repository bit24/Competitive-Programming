import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*ID: eric.ca1
 LANG: JAVA
 TASK: fracdec
 */
public class fracdec {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fracdec.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("fracdec.out")));
		String[] inputData = reader.readLine().split(" ");
		int numerator = Integer.parseInt(inputData[0]);
		int denominator = Integer.parseInt(inputData[1]);
		reader.close();

		ArrayList<Character> processedQuotient = generate(numerator,
				denominator, 100000);

		//ArrayList<Character> processedQuotient = removeRepeats(unProcessedQuotient);

		if (processedQuotient.get(0) == '.') {
			printer.print(0);
		}

		for (int numCharacter = 0; numCharacter < processedQuotient.size(); numCharacter++) {
			if (numCharacter != 0 && numCharacter % 76 == 0) {
				printer.println();
			}
			printer.print(processedQuotient.get(numCharacter));
		}

		printer.println();
		printer.close();
	}

	public static ArrayList<Character> generate(int numerator, int denominator,
			int length) {
		int currentCharacter = 0;
		int currentRemainder = 0;
		String sNumerator = String.valueOf(numerator);
		ArrayList<Integer> quotient = new ArrayList<Integer>();
		boolean hasDecimalPoint = false;

		ArrayList<Integer> remainder = new ArrayList<Integer>();

		int decimalPointIndex = -1;

		int repeatSequenceStart = -1;
		
		loop1:
		while (true) {
			int multiples = 0;
			while (currentRemainder - denominator >= 0) {
				multiples++;
				currentRemainder -= denominator;
			}

			if (currentRemainder == 0
					&& currentCharacter >= sNumerator.length()) {
				quotient.add(multiples);
				break;
			}
			
			if (hasDecimalPoint || multiples != 0) {
				quotient.add(multiples);
			}

			currentRemainder *= 10;
			if (currentCharacter < sNumerator.length()) {
				currentRemainder += Character.getNumericValue(sNumerator
						.charAt(currentCharacter));
			}
			
			for (int i = 0; i < remainder.size(); i++) {
				if (remainder.get(i) == currentRemainder) {
					repeatSequenceStart = i + 1;
					break loop1;
				}

			}
			
			if (hasDecimalPoint) {
				remainder.add(currentRemainder);
			}

			if (currentCharacter == sNumerator.length()) {
				hasDecimalPoint = true;
				decimalPointIndex = quotient.size();
			}
			currentCharacter++;
		}
		ArrayList<Character> withDecPoint = new ArrayList<Character>();
		for (int i = 0; i < quotient.size(); i++) {
			if (i == decimalPointIndex) {
				withDecPoint.add('.');
			}
			if(i == repeatSequenceStart){
				withDecPoint.add('(');
			}
			withDecPoint.add(Character.forDigit(quotient.get(i), 10));
		}
		if(repeatSequenceStart != -1){
			withDecPoint.add(')');
		}
		return withDecPoint;
	}

}
