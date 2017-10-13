import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BuildingASkiCourse {

	int numRows;
	int numColumns;

	boolean[][] isSmooth;

	public static void main(String[] args) throws IOException {
		new BuildingASkiCourse().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("skicourse.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("skicourse.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numRows = Integer.parseInt(inputData.nextToken());
		numColumns = Integer.parseInt(inputData.nextToken());

		isSmooth = new boolean[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			String inputLine = reader.readLine();
			for (int j = 0; j < numColumns; j++) {
				isSmooth[i][j] = inputLine.charAt(j) == 'S';
			}
		}
		reader.close();
		printer.println(binarySearch());
		printer.close();
	}

	public int binarySearch() {
		int low = 1;
		int high = Math.min(numRows, numColumns) + 1;

		while (high - low > 1) {
			int middle = (low + high) / 2;

			if (check(middle)) {
				low = middle;
			} else {
				high = middle;
			}
		}
		return low;
	}

	public boolean check(int size) {
		isWildCard = new boolean[numRows][numColumns];

		while (true) {
			int[][] maxSize = computeMaxSizes(false, true);
			int[][] wildCardMaxSize = computeMaxSizes(true, true);
			Pair coordinates = findCoordinates(maxSize, wildCardMaxSize, size);

			if (coordinates == null) {
				maxSize = computeMaxSizes(false, false);
				wildCardMaxSize = computeMaxSizes(true, false);
				coordinates = findCoordinates(maxSize, wildCardMaxSize, size);
			}
			if (coordinates == null) {
				return allWildCard();
			}
			fill(coordinates.i, coordinates.j, size);
		}
	}

	public int[][] computeMaxSizes(boolean onlyWildCards, boolean computeSmooth) {
		int[][] answer = new int[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (isWildCard[i][j]) {
					computeState(answer, i, j);
				} else if (!onlyWildCards) {
					if (computeSmooth && isSmooth[i][j]) {
						computeState(answer, i, j);
					} else if (!computeSmooth && !isSmooth[i][j]) {
						computeState(answer, i, j);
					}
				}
			}
		}
		return answer;
	}

	public void computeState(int[][] data, int i, int j) {
		if (i > 0 && j > 0) {
			data[i][j] = Math.min(data[i - 1][j], Math.min(data[i][j - 1], data[i - 1][j - 1]));
		}
		data[i][j]++;
	}

	public Pair findCoordinates(int[][] maxSquare, int[][] wildCardMaxSquare, int testSize) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				int size = maxSquare[i][j];
				int wildCardSize = wildCardMaxSquare[i][j];
				if (size >= testSize && testSize > wildCardSize) {
					return new Pair(i - testSize + 1, j - testSize + 1);
				}
			}
		}
		return null;
	}

	class Pair {
		int i;
		int j;

		Pair(int a, int b) {
			i = a;
			j = b;
		}
	}

	boolean[][] isWildCard;

	public void fill(int i, int j, int size) {
		for (int i1 = 0; i1 < size; i1++) {
			for (int j1 = 0; j1 < size; j1++) {
				isWildCard[i + i1][j + j1] = true;
			}
		}
	}

	public boolean allWildCard() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (!isWildCard[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

}