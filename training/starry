import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*ID: eric.ca1
LANG: JAVA
TASK: starry
*/

public class starry {

	// default
	int[] rowMods = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };
	int[] columnMods = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };

	// not done
	int numRows;
	int numColumns;
	boolean[][] data;
	ArrayList<boolean[][]> patterns;
	boolean[][] everVisited;

	int[][] ID;

	public static void main(String[] args) throws IOException {
		new starry().execute();
	}

	public void execute() throws IOException {

		init();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("starry.out")));

		for (int currentRow = 0; currentRow < numRows; currentRow++) {
			for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
				if (everVisited[currentRow][currentColumn] || !data[currentRow][currentColumn]) {
					continue;
				}
				boolean[][] cutOut = cut(currentRow, currentColumn);
				int ID = findID(cutOut);
				if (ID == -1) {
					ID = patterns.size();
					patterns.add(cutOut);
				}
				label(currentRow, currentColumn, ID);
			}
		}

		for (int currentRow = 0; currentRow < numRows; currentRow++) {
			for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
				if (ID[currentRow][currentColumn] == -1) {
					printer.print(0);
				} else {
					printer.print((char)(ID[currentRow][currentColumn] + 'a'));
				}
			}
			printer.println();
		}
		printer.close();
	}

	public void label(int currentRow, int currentColumn, int label) {
		ID[currentRow][currentColumn] = label;

		for (int direction = 0; direction < 8; direction++) {
			int newRow = currentRow + rowMods[direction];
			int newColumn = currentColumn + columnMods[direction];
			if(!isLegal(newRow, newColumn)){
				continue;
			}
			
			if (data[newRow][newColumn] && ID[newRow][newColumn] == -1) {
				label(newRow, newColumn, label);
			}
		}

	}

	public boolean equals(boolean[][] a, boolean[][] b) {
		if (a.length != b.length) {
			return false;
		}
		if (a[0].length != b[0].length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean[][] rotate(boolean[][] input) {
		int inputNumRows = input.length;
		int inputNumColumns = input[0].length;
		boolean[][] answer = new boolean[inputNumColumns][inputNumRows];

		for (int i = 0; i < inputNumColumns; i++) {
			for (int j = 0; j < inputNumRows; j++) {
				answer[i][j] = input[j][i];
			}

			for (int j = 0; j < inputNumRows / 2; j++) {
				boolean temp = answer[i][j];
				answer[i][j] = answer[i][inputNumRows - j - 1];
				answer[i][inputNumRows - j - 1] = temp;
			}
		}
		return answer;
	}

	public boolean[][] mirror(boolean[][] input) {
		int inputNumRows = input.length;
		int inputNumColumns = input[0].length;
		boolean[][] answer = new boolean[inputNumRows][inputNumColumns];

		for (int i = 0; i < inputNumRows; i++) {
			answer[i][inputNumColumns / 2] = input[i][inputNumColumns / 2];

			for (int j = 0; j < inputNumColumns / 2; j++) {

				answer[i][j] = input[i][inputNumColumns - j - 1];
				answer[i][inputNumColumns - j - 1] = input[i][j];
			}
		}
		return answer;
	}

	public int findID(boolean[][] data) {
		for (int i = 0; i < patterns.size(); i++) {
			boolean[][] pattern = patterns.get(i);
			for (int j = 0; j < 3; j++) {
				if (equals(pattern, data)) {
					return i;
				}
				data = rotate(data);
			}
			if (equals(pattern, data)) {
				return i;
			}
			// 3 rotations, 4 checks

			data = mirror(data);
			for (int j = 0; j < 3; j++) {
				if (equals(pattern, data)) {
					return i;
				}
				data = rotate(data);
			}
			if (equals(pattern, data)) {
				return i;
			}
		}
		return -1;
	}

	public boolean isLegal(int currentRow, int currentColumn) {
		return !(currentRow < 0 || currentRow >= numRows || currentColumn < 0 || currentColumn >= numColumns);
	}

	public boolean[][] cut(int currentRow, int currentColumn) {
		Query answers = new Query();
		searchNQuery(currentRow, currentColumn, answers);
		// Query complete

		int rOffset = answers.topMost;
		int cOffset = answers.leftMost;
		int nRows = answers.bottomMost - answers.topMost + 1;
		int nColumns = answers.rightMost - answers.leftMost + 1;
		boolean[][] description = new boolean[nRows][nColumns];
		copy(currentRow, currentColumn, rOffset, cOffset, description);
		return description;
	}

	public void copy(int currentRow, int currentColumn, int rOffset, int cOffset, boolean[][] blank) {
		blank[currentRow - rOffset][currentColumn - cOffset] = true;

		for (int direction = 0; direction < 8; direction++) {
			int newRow = currentRow + rowMods[direction];
			int newColumn = currentColumn + columnMods[direction];
			if (!isLegal(newRow, newColumn)) {
				continue;
			}

			if (data[newRow][newColumn] && !blank[newRow - rOffset][newColumn - cOffset]) {
				copy(newRow, newColumn, rOffset, cOffset, blank);
			}
		}
	}

	public void searchNQuery(int currentRow, int currentColumn, Query searchQueries) {
		everVisited[currentRow][currentColumn] = true;
		searchQueries.leftMost = Math.min(searchQueries.leftMost, currentColumn);
		searchQueries.rightMost = Math.max(searchQueries.rightMost, currentColumn);
		searchQueries.topMost = Math.min(searchQueries.topMost, currentRow);
		searchQueries.bottomMost = Math.max(searchQueries.bottomMost, currentRow);

		for (int direction = 0; direction < 8; direction++) {
			int newRow = currentRow + rowMods[direction];
			int newColumn = currentColumn + columnMods[direction];
			if (!isLegal(newRow, newColumn)) {
				continue;
			}

			if (data[newRow][newColumn] && !everVisited[newRow][newColumn]) {
				searchNQuery(newRow, newColumn, searchQueries);
			}
		}
	}

	class Query {
		int leftMost = Integer.MAX_VALUE;
		int rightMost = Integer.MIN_VALUE;
		int topMost = Integer.MAX_VALUE;
		int bottomMost = Integer.MIN_VALUE;
	}

	public void init() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("starry.in"));
		numColumns = Integer.parseInt(reader.readLine());
		numRows = Integer.parseInt(reader.readLine());

		data = new boolean[numRows][];
		for (int currentRow = 0; currentRow < numRows; currentRow++) {
			boolean[] currentData = new boolean[numColumns];
			String inputData = reader.readLine();

			for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
				currentData[currentColumn] = inputData.charAt(currentColumn) == '1';
			}
			data[currentRow] = currentData;
		}
		reader.close();
		// data done, numRows done, numColumns done

		patterns = new ArrayList<boolean[][]>();
		// patterns initialized

		everVisited = new boolean[numRows][numColumns];
		// everVisited initialized

		ID = new int[numRows][numColumns];
		for (int currentRow = 0; currentRow < numRows; currentRow++) {
			for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
				ID[currentRow][currentColumn] = -1;
			}
		}
		// ID initialized
	}
}
