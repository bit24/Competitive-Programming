import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/*ID: eric.ca1
LANG: JAVA
TASK: twofive
*/

public class twofive {

	public static void main(String[] args) throws IOException {
		input();

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("twofive.out")));
		if (getNumber) {
			printer.println(getNumber());
		} else {
			printer.println(getString());
		}
		printer.close();
	}

	static boolean getNumber;
	static int gridNumber;
	static String grid;

	static int[][][][][] count;
	static int UNKNOWN = -1;

	public static void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("twofive.in"));
		getNumber = reader.readLine().charAt(0) == 'W';

		if (getNumber) {
			grid = reader.readLine();
		} else {
			gridNumber = Integer.parseInt(reader.readLine());
		}
		reader.close();

		count = new int[5 + 1][5 + 1][5 + 1][5 + 1][5 + 1];
	}

	// given that we always place letters in order of A...Z
	public static int countWays(int[][] fixed, int[] size, boolean[] used) {

		if (count[size[0]][size[1]][size[2]][size[3]][size[4]] != UNKNOWN) {
			return count[size[0]][size[1]][size[2]][size[3]][size[4]];
		}

		int cChar = 0;
		while (used[cChar]) {
			cChar++;
		}

		int sum = 0;

		for (int cRow = 0; cRow < 5; cRow++) {
			if (size[cRow] < 5 && (cRow == 0 || size[cRow - 1] > size[cRow])) {
				if (fixed[cRow].length == 0 || fixed[cRow].length < size[cRow] || fixed[cRow][size[cRow] - 1] < cChar) {
					if (cRow == 0 || fixed[cRow - 1].length <= size[cRow] || fixed[cRow - 1][size[cRow]] < cChar) {
						size[cRow]++;
						used[cChar] = true;
						sum += countWays(fixed, size, used);
						size[cRow]--;
						used[cChar] = false;
					}
				}
			}
		}

		return count[size[0]][size[1]][size[2]][size[3]][size[4]] = sum;
	}

	public static int countWays(StringBuilder str) {
		int[][] fixed = new int[5][];
		boolean[] used = new boolean[25];

		for (int i = 0; i < 25; i += 5) {
			int length = Math.max(0, Math.min(5, str.length() - i));
			fixed[i / 5] = new int[length];

			for (int j = 0; j < length; j++) {
				fixed[i / 5][j] = str.charAt(i + j) - 'A';
				used[str.charAt(i + j) - 'A'] = true;
			}
		}

		for (int i = 0; i < fixed.length; i++) {
			for (int j = 0; j < fixed[i].length; j++) {
				if (j > 0 && fixed[i][j - 1] >= fixed[i][j]) {
					return 0;
				}
				if (i > 0 && fixed[i - 1][j] >= fixed[i][j]) {
					return 0;
				}
			}
		}

		for (int[][][][] a : count) {
			for (int[][][] b : a) {
				for (int[][] c : b) {
					for (int[] d : c) {
						Arrays.fill(d, UNKNOWN);
					}
				}
			}
		}
		count[5][5][5][5][5] = 1;

		return countWays(fixed,
				new int[] { fixed[0].length, fixed[1].length, fixed[2].length, fixed[3].length, fixed[4].length },
				used);
	}

	public static String getString() {
		int toCount = gridNumber;

		boolean[] used = new boolean[25];

		StringBuilder cString = new StringBuilder();

		for (int cLength = 0; cLength < 25; cLength++) {
			for (int i = 0; i < 25; i++) {
				if (used[i]) {
					continue;
				}
				cString.append((char) (i + 'A'));
				if (countWays(cString) < toCount) {
					toCount -= countWays(cString);
					cString.deleteCharAt(cString.length() - 1);
				} else {
					used[i] = true;
					break;
				}
			}
		}
		return cString.toString();
	}

	public static int getNumber() {
		int currentCount = 1;

		StringBuilder cString = new StringBuilder();

		boolean[] used = new boolean[25];

		for (int cLength = 0; cLength < 25; cLength++) {
			for (int i = 'A'; i < grid.charAt(cLength); i++) {
				if (used[i - 'A']) {
					continue;
				}
				cString.append((char) i);
				currentCount += countWays(cString);
				cString.deleteCharAt(cString.length() - 1);
			}
			cString.append(grid.charAt(cLength));
			used[grid.charAt(cLength) - 'A'] = true;
		}
		return currentCount;
	}

}
