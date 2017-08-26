import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
LANG: JAVA
TASK: betsy
*/

public class betsy {

	public static void main(String[] args) throws IOException {
		new betsy().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("betsy.in"));
		gridLength = Integer.parseInt(reader.readLine());
		reader.close();
		// assert (2 <= gridLength && gridLength <= 7);

		grid = new boolean[gridLength + 2][gridLength + 2];
		for (int i = 0; i < gridLength + 2; i++) {
			grid[0][i] = true;
			grid[gridLength + 1][i] = true;

			grid[i][0] = true;
			grid[i][gridLength + 1] = true;
		}
		gridTotalSize = gridLength * gridLength;

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("betsy.out")));
		printer.println(explore(1, 1, 0));
		printer.close();
	}

	int gridLength;
	boolean[][] grid;

	int gridTotalSize;

	int numAdjForcing(int i, int j) {
		int count = 0;
		if (forcing(i - 1, j)) {
			count++;
		}
		if (forcing(i + 1, j)) {
			count++;
		}
		if (forcing(i, j - 1)) {
			count++;
		}
		if (forcing(i, j + 1)) {
			count++;
		}
		return count;
	}

	boolean forcing(int i, int j) {
		if (i == gridLength && j == 1) {
			return false;
		}

		if (!grid[i][j]) {
			int sum = 0;
			if (grid[i - 1][j]) {
				sum++;
			}
			if (grid[i + 1][j]) {
				sum++;
			}
			if (grid[i][j - 1]) {
				sum++;
			}
			if (grid[i][j + 1]) {
				sum++;
			}
			if (sum == 3) {
				return true;
			}
		}
		return false;
	}

	int explore(int i, int j, int numFilled) {
		if (prune(i, j)) {
			grid[i][j] = false;
			return 0;
		}

		grid[i][j] = true;
		numFilled++;

		if (i == gridLength && j == 1) {
			if (numFilled == gridTotalSize) {
				grid[i][j] = false;
				return 1;
			} else {
				// assert (!verify(gridTotalSize - numFilled));
				grid[i][j] = false;
				return 0;
			}
		}

		int numForced = numAdjForcing(i, j);
		int count = 0;
		if (numForced == 0) {
			// right
			if (!grid[i][j + 1]) {
				count += explore(i, j + 1, numFilled);
			}
			// down
			if (!grid[i + 1][j]) {
				count += explore(i + 1, j, numFilled);
			}
			// left
			if (!grid[i][j - 1]) {
				count += explore(i, j - 1, numFilled);
			}
			// up
			if (!grid[i - 1][j]) {
				count += explore(i - 1, j, numFilled);
			}
		} else if (numForced == 1) {
			// right
			if (forcing(i, j + 1)) {
				count = explore(i, j + 1, numFilled);
			}
			// down
			else if (forcing(i + 1, j)) {
				count = explore(i + 1, j, numFilled);
			}
			// left
			else if (forcing(i, j - 1)) {
				count = explore(i, j - 1, numFilled);
			}
			// up
			else if (forcing(i - 1, j)) {
				count = explore(i - 1, j, numFilled);
			}
		}
		grid[i][j] = false;
		return count;
	}

	boolean[][] counted;
	int numCounted;
	int required;

	boolean prune(int i, int j) {
		if (grid[i - 1][j] && grid[i + 1][j] && (!grid[i][j - 1]) && (!grid[i][j + 1])) {
			return true;
		}
		if ((!grid[i - 1][j]) && (!grid[i + 1][j]) && grid[i][j - 1] && grid[i][j + 1]) {
			return true;
		}
		return false;
	}
}
