import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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

		grid = new boolean[gridLength][gridLength];
		gridTotalSize = gridLength * gridLength;
		// explore(0, 0, 0);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("betsy.out")));
		printer.println(explore(0, 0, 0, 0));
		printer.close();
	}

	long computeFinalAltProfile(int gridLength) {
		long profile = 1 * 8 * 8;

		return profile;
	}

	int gridLength;
	int[][] neighborCount;
	boolean[][] grid;

	int gridTotalSize;

	/*
	 * long computeFinalProfile(int gridLength) { long profile = 0; for (int i = 0; i < gridLength - 1; i++) { profile
	 * *= 4; profile += 2; } for (int i = 0; i < gridLength - 1; i++) { profile *= 4; profile += 3; } profile *= 8;
	 * profile += gridLength - 1; profile *= 8; return profile; }
	 */

	HashMap<Long, Integer> numWays = new HashMap<Long, Integer>();

	int[] adjI = new int[] { 0, 1, 0, -1 };
	int[] adjJ = new int[] { -1, 0, 1, 0 };
	/*
	 * boolean bubbled(int i, int j, long grid) { for (int dirI = 0; dirI < 4; dirI++) { int newI = adjI[dirI] + i; int
	 * newJ = adjJ[dirI] + j; if (0 <= newI && newI < gridLength && 0 <= newJ && newJ < gridLength) { if (query(grid,
	 * index(newI, newJ))) { // one of the spaces must be blocked to not be bubbled if (!(query(grid, index(i, newJ)) ||
	 * query(grid, index(newI, j)))) { return true; } } } } return false; }
	 */

	int numFree(int i, int j) {
		int count = 0;
		// right
		if (j + 1 < grid.length && !grid[i][j + 1]) {
			count++;
		}
		// down
		if (i + 1 < grid.length && !grid[i + 1][j]) {
			count++;
		}
		// left
		if (j - 1 >= 0 && !grid[i][j - 1]) {
			count++;
		}
		// up
		if (i - 1 >= 0 && !grid[i - 1][j]) {
			count++;
		}
		return count;
	}

	int numForcing(int i, int j) {
		assert (grid[i][j] == true);
		int numForcing = 0;
		// right
		if (j + 1 < grid.length && !grid[i][j + 1] && numFree(i, j + 1) == 0) {
			numForcing++;
		}
		// down
		if (i + 1 < grid.length && !grid[i + 1][j] && numFree(i + 1, j) == 0) {
			numForcing++;
		}
		// left
		if (j - 1 >= 0 && !grid[i][j - 1] && numFree(i, j - 1) == 0) {
			numForcing++;
		}
		// up
		if (i - 1 >= 0 && !grid[i - 1][j] && numFree(i - 1, j) == 0) {
			numForcing++;
		}
		return numForcing;
	}
	/*
	 * int explore(int i, int j, long grid) { // System.out.println(i + " " + j); grid = setBit(grid, index(i, j));
	 * 
	 * if (i == gridLength - 1 && j == 0) { if (Long.bitCount(grid) == gridLength * gridLength) { return 1; } else {
	 * return 0; } }
	 * 
	 * if (bubbled(i, j, grid)) { return 0; }
	 * 
	 * // int numFilled = Long.bitCount(grid); long profile = 8 * (grid * 8 + i) + j; if (numWays.containsKey(profile))
	 * { return numWays.get(profile); }
	 * 
	 * int sum = 0;
	 * 
	 * // right if (j + 1 < gridLength && !query(grid, index(i, j + 1))) { sum += explore(i, j + 1, grid); } // down if
	 * (i + 1 < gridLength && !query(grid, index(i + 1, j))) { sum += explore(i + 1, j, grid); } // left if (j - 1 >= 0
	 * && !query(grid, index(i, j - 1))) { sum += explore(i, j - 1, grid); } // up if (i - 1 >= 0 && !query(grid,
	 * index(i - 1, j))) { sum += explore(i - 1, j, grid); }
	 * 
	 * numWays.put(profile, numWays.containsKey(profile) ? numWays.get(profile) + sum : sum); return sum; }
	 */

	long setBit(long bitSet, int index) {
		return bitSet | (1L << index);
	}

	/*
	 * boolean query(long bitSet, int index) { return (bitSet & (1L << index)) != 0; }
	 */
	int index(int i, int j) {
		return gridLength * i + j;
	}

	int explore(int i, int j, int numFilled, long gridHash) {
		if (prune(i, j)) {
			grid[i][j] = false;
			return 0;
		}

		gridHash = setBit(gridHash, index(i, j));
		grid[i][j] = true;
		numFilled++;

		if (i == grid.length - 1 && j == 0) {
			if (numFilled == gridTotalSize) {
				grid[i][j] = false;
				return 1;
			} else {
				// assert (!verify(gridTotalSize - numFilled));
				grid[i][j] = false;
				return 0;
			}
		}

		int numForced = numForcing(i, j);
		int sum = 0;

		if (numForced > 1) {
			grid[i][j] = false;
			return 0;
		}
		if (prune(i, j)) {
			grid[i][j] = false;
			return 0;
		}
		if (!verify(gridTotalSize - numFilled)) {
			grid[i][j] = false;
			return 0;
		}

		long completeHash = 8 * (gridHash * 8 + i) + j;
		if (numWays.containsKey(completeHash)) {
			grid[i][j] = false;
			return numWays.get(completeHash);
		}

		if (numForced == 1) {
			if (j + 1 < grid.length && !grid[i][j + 1] && numFree(i, j + 1) == 0) {
				sum += explore(i, j + 1, numFilled, gridHash);
			}
			// down
			if (i + 1 < grid.length && !grid[i + 1][j] && numFree(i + 1, j) == 0) {
				sum += explore(i + 1, j, numFilled, gridHash);
			}
			// left
			if (j - 1 >= 0 && !grid[i][j - 1] && numFree(i, j - 1) == 0) {
				sum += explore(i, j - 1, numFilled, gridHash);
			}
			// up
			if (i - 1 >= 0 && !grid[i - 1][j] && numFree(i - 1, j) == 0) {
				sum += explore(i - 1, j, numFilled, gridHash);
			}

		} else {
			// right
			if (j + 1 < grid.length && !grid[i][j + 1]) {
				sum += explore(i, j + 1, numFilled, gridHash);
			}
			// down
			if (i + 1 < grid.length && !grid[i + 1][j]) {
				sum += explore(i + 1, j, numFilled, gridHash);
			}
			// left
			if (j - 1 >= 0 && !grid[i][j - 1]) {
				sum += explore(i, j - 1, numFilled, gridHash);
			}
			// up
			if (i - 1 >= 0 && !grid[i - 1][j]) {
				sum += explore(i - 1, j, numFilled, gridHash);
			}
		}

		grid[i][j] = false;
		numWays.put(completeHash, sum);
		return sum;
	}

	boolean[][] counted;
	int numCounted;
	int required;

	boolean prune(int i, int j) {
		if (i - 1 >= 0 && i + 1 < gridLength && j - 1 >= 0 && j + 1 < gridLength) {
			if (grid[i - 1][j] && grid[i + 1][j] && (!grid[i][j - 1]) & (!grid[i][j + 1])) {
				return true;
			}
			if ((!grid[i - 1][j]) && (!grid[i + 1][j]) && grid[i][j - 1] & grid[i][j + 1]) {
				return true;
			}
		}
		return false;
	}

	boolean verify(int required) {
		counted = new boolean[grid.length][grid.length];
		numCounted = 0;
		this.required = required;
		if (grid[gridLength - 1][0]) {
			return required == 0;
		}

		ff(gridLength - 1, 0);
		return numCounted >= required;
	}

	void ff(int i, int j) {
		if (numCounted == required) {
			return;
		}
		counted[i][j] = true;
		numCounted++;
		if (i + 1 < gridLength && !grid[i + 1][j] && !counted[i + 1][j]) {
			ff(i + 1, j);
		}
		if (j + 1 < gridLength && !grid[i][j + 1] && !counted[i][j + 1]) {
			ff(i, j + 1);
		}
		if (i - 1 >= 0 && !grid[i - 1][j] && !counted[i - 1][j]) {
			ff(i - 1, j);
		}
		if (j - 1 >= 0 && !grid[i][j - 1] && !counted[i][j - 1]) {
			ff(i, j - 1);
		}
	}

	// up, right, down, left
	int[] movementI = new int[] { -1, 0, 1, 0 };
	int[] movementJ = new int[] { 0, 1, 0, -1 };
}
