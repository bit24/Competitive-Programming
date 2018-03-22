import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: clocks
*/

public class clocks {

	public static void main(String[] args) throws IOException {
		new clocks().execute();
	}

	int[] clockStates = new int[9];

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("clocks.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 9; i++) {
			if (!inputData.hasMoreTokens()) {
				inputData = new StringTokenizer(reader.readLine());
			}
			clockStates[i] = (Integer.parseInt(inputData.nextToken()) % 12)/3;
		}
		reader.close();
		computeOperations();
		explore(1, 0);
		
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("clocks.out")));

		printer.print(best.charAt(0));
		for(int i = 1; i < best.length(); i++){
			printer.print(" " + best.charAt(i));
		}
		printer.println();
		printer.close();
	}

	int[][] operations = new int[10][];

	void computeOperations() {
		String[] undeciphered = new String[] { "", "ABDE", "ABC", "BCEF", "ADG", "BDEFH", "CFI", "DEGH", "GHI",
				"EFHI" };
		for (int i = 1; i <= 9; i++) {
			operations[i] = new int[undeciphered[i].length()];
			for (int j = 0; j < undeciphered[i].length(); j++) {
				operations[i][j] = undeciphered[i].charAt(j) - 'A';
			}
		}
	}

	StringBuilder opRecord = new StringBuilder();
	int bestCount = Integer.MAX_VALUE;
	String best = "~~~~~~~~~~~";

	void explore(int opIndex, int opCount) {
		if (opIndex == 10) {
			for (int i = 0; i < 9; i++) {
				if (clockStates[i] != 0) {
					return;
				}
			}
			if (opCount < bestCount) {
				best = opRecord.toString();
			} else if (opCount == bestCount) {
				String strRecord = opRecord.toString();
				if (strRecord.compareTo(best) < 0) {
					best = strRecord;
				}
			}
		} else {
			// case numOpped = 0
			explore(opIndex + 1, opCount);

			// numOpped = 1..3
			for (int numOpped = 1; numOpped < 4; numOpped++) {
				for (int j : operations[opIndex]) {
					clockStates[j] += numOpped;
					if (clockStates[j] >= 4) {
						clockStates[j] -= 4;
					}
				}
				for (int i = 0; i < numOpped; i++) {
					opRecord.append(opIndex);
				}
				explore(opIndex + 1, opCount + numOpped);
				opRecord.delete(opRecord.length() - numOpped, opRecord.length());
				for (int j : operations[opIndex]) {
					clockStates[j] -= numOpped;
					if (clockStates[j] < 0) {
						clockStates[j] += 4;
					}
				}
			}
		}
	}

}
