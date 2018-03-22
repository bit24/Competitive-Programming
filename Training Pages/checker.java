import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*ID: eric.ca1
LANG: JAVA
TASK: checker
*/

public class checker {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		new checker().execute();
		System.out.println(System.currentTimeMillis() - startTime);
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("checker.in"));
		gridSize = Integer.parseInt(reader.readLine());
		reader.close();
		cPositions = new int[gridSize];

		cUsed = new boolean[gridSize];
		dLRUsed = new boolean[(gridSize << 1) - 1];
		dRLUsed = new boolean[(gridSize << 1) - 1];

		place(0);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("checker.out")));
		// Collections.sort(solutions);
		for (int i = 0; i < 3; i++) {
			printer.println(solutions.get(i));
		}
		printer.println(solutions.size());
		printer.close();
	}

	int gridSize;
	int[] cPositions;

	boolean[] cUsed;
	boolean[] dLRUsed;
	boolean[] dRLUsed;

	ArrayList<String> solutions = new ArrayList<String>();

	void place(int rowInd) {
		if (rowInd == gridSize) {
			// great!
			StringBuilder currentStr = new StringBuilder();
			currentStr.append(cPositions[0] + 1);
			for (int i = 1; i < gridSize; i++) {
				currentStr.append(" ");
				currentStr.append(cPositions[i] + 1);
			}
			solutions.add(currentStr.toString());
			return;
		}

		for (int columnInd = 0; columnInd < gridSize; columnInd++) {
			if (cUsed[columnInd]) {
				continue;
			}
			int dLRInd = (gridSize - 1) + (rowInd - columnInd);
			if (dLRUsed[dLRInd]) {
				continue;
			}
			int dRLInd = (gridSize - 1 + gridSize - 1) - (rowInd + columnInd);
			if (dRLUsed[dRLInd]) {
				continue;
			}
			// next
			cUsed[columnInd] = dLRUsed[dLRInd] = dRLUsed[dRLInd] = true;
			cPositions[rowInd] = columnInd;
			place(rowInd + 1);
			cUsed[columnInd] = dLRUsed[dLRInd] = dRLUsed[dRLInd] = false;
		}
	}
}
