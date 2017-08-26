import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: prime3
*/

public class prime3 {

	public static void main(String[] args) throws IOException {
		new prime3().execute();
	}

	void execute() throws IOException {
		input();
		computeLegal();
		fillPositions();
		search(1);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("prime3.out")));
		String[] solutionsInS = new String[solutions.size()];

		for (int cIndex = 0; cIndex < solutions.size(); cIndex++) {
			StringBuilder str = new StringBuilder();

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					str.append(solutions.get(cIndex)[i][j]);
				}
			}
			solutionsInS[cIndex] = str.toString();
		}
		Arrays.sort(solutionsInS);

		if (solutionsInS.length == 0) {
			printer.println("NONE");
			printer.close();
			return;
		}

		printer.println(solutionsInS[0].substring(0, 5));
		printer.println(solutionsInS[0].substring(5, 10));
		printer.println(solutionsInS[0].substring(10, 15));
		printer.println(solutionsInS[0].substring(15, 20));
		printer.println(solutionsInS[0].substring(20, 25));

		for (int i = 1; i < solutionsInS.length; i++) {
			printer.println();
			printer.println(solutionsInS[i].substring(0, 5));
			printer.println(solutionsInS[i].substring(5, 10));
			printer.println(solutionsInS[i].substring(10, 15));
			printer.println(solutionsInS[i].substring(15, 20));
			printer.println(solutionsInS[i].substring(20, 25));
		}
		printer.close();
	}

	boolean[] isPrime = new boolean[100_000];
	boolean[] isLegal = new boolean[100_000];

	int digitsSum(int input) {
		int digitsSum = 0;
		while (input > 0) {
			digitsSum += input % 10;
			input /= 10;
		}
		return digitsSum;
	}

	// uses a bad sieve
	// possible to upgrade to Euler's sieve which is linear time
	void computeLegal() {
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;
		for (int cNum = 2; cNum < 100_000; cNum++) {
			if (isPrime[cNum]) {
				if (cNum >= 10_000 && digitsSum(cNum) == requiredSum) {
					for (int numDigits = 1; numDigits <= 10_000; numDigits *= 10) {
						isLegal[cNum / numDigits] = true;
					}
				}
				for (int multiple = 2 * cNum; multiple < 100_000; multiple += cNum) {
					isPrime[multiple] = false;
				}
			}
		}
	}

	int requiredSum = 0;

	void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("prime3.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		requiredSum = Integer.parseInt(inputData.nextToken());
		cGrid[0][0] = Integer.parseInt(inputData.nextToken());
		reader.close();
	}

	ArrayList<int[][]> solutions = new ArrayList<int[][]>();

	int[][] cGrid = new int[5][5];

	// start with diagonal 1
	// continue with diagonal 2
	// fill first row
	// fill columns 2 and 4
	// fill last row
	// fill column 3
	// fill rows 2, 3, and 4

	int DIAGONAL_TB = 1;
	int DIAGONAL_BT = 2;
	int ROW = 3;
	int COLUMN = 4;

	int[] iIndices = new int[25];
	int[] jIndices = new int[25];
	boolean[][] filled;

	void fillPositions() {
		filled = new boolean[5][5];

		filled[0][0] = true;
		int count = 0;

		for (int temp = 0; temp < 5; temp++) {
			iIndices[count] = temp;
			jIndices[count++] = temp;
			filled[temp][temp] = true;
		}

		for (int temp = 4; temp >= 0; temp--) {
			if (!filled[temp][4 - temp]) {
				iIndices[count] = temp;
				jIndices[count++] = 4 - temp;
				filled[temp][4 - temp] = true;
			}
		}

		count = fillRow(0, count);
		count = fillColumn(1, count);
		count = fillColumn(3, count);
		count = fillRow(4, count);
		count = fillColumn(2, count);
		count = fillRow(1, count);
		count = fillRow(2, count);
		count = fillRow(3, count);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				filled[i][j] = false;
			}
		}
		filled[0][0] = true;
	}

	int fillRow(int rowNumber, int count) {
		for (int temp = 0; temp < 5; temp++) {
			if (!filled[rowNumber][temp]) {
				iIndices[count] = rowNumber;
				jIndices[count++] = temp;
				filled[rowNumber][temp] = true;
			}
		}
		return count;
	}

	int fillColumn(int columnNumber, int count) {
		for (int temp = 0; temp < 5; temp++) {
			if (!filled[temp][columnNumber]) {
				iIndices[count] = temp;
				jIndices[count++] = columnNumber;
				filled[temp][columnNumber] = true;
			}
		}
		return count;
	}

	void search(int index) {
		/*if(index == 12){
			System.out.println("Close");
		}*/
		
		int iIndex = iIndices[index];
		int jIndex = jIndices[index];

		posLoop: for (int posValue = 0; posValue < 10; posValue++) {
			if ((iIndex == 0 || jIndex == 0) && posValue == 0) {
				continue;
			}

			cGrid[iIndex][jIndex] = posValue;
			filled[iIndex][jIndex] = true;

			int cNum = 0;

			// DIAGONAL TB
			if (iIndex == jIndex) {
				for (int temp = 0; temp < 5 && filled[temp][temp]; temp++) {
					cNum *= 10;
					cNum += cGrid[temp][temp];
					if (!isLegal[cNum]) {
						continue posLoop;
					}
				}
				cNum = 0;

				// DIAGONAL BT
			} else if (iIndex + jIndex == 4) {
				for (int temp = 4; 0 <= temp && filled[temp][4 - temp]; temp--) {
					cNum *= 10;
					cNum += cGrid[temp][4 - temp];
					if (!isLegal[cNum]) {
						continue posLoop;
					}
				}
				cNum = 0;
			}
			// ROWS
			for (int temp = 0; temp < 5 && filled[iIndex][temp]; temp++) {
				cNum *= 10;
				cNum += cGrid[iIndex][temp];
				if (!isLegal[cNum]) {
					continue posLoop;
				}
			}
			cNum = 0;

			// COLUMNS
			for (int temp = 0; temp < 5 && filled[temp][jIndex]; temp++) {
				cNum *= 10;
				cNum += cGrid[temp][jIndex];
				if (!isLegal[cNum]) {
					continue posLoop;
				}
			}
			cNum = 0;

			// all OK

			if (index != 24) {
				search(index + 1);
			} else {
				cNum = 0;
				for (int temp = 0; temp < 5; temp++) {
					cNum *= 10;
					cNum += cGrid[iIndex][temp];
				}
				if (!isLegal[cNum]) {
					continue posLoop;
				}
				cNum = 0;

				for (int temp = 0; temp < 5; temp++) {
					cNum *= 10;
					cNum += cGrid[temp][jIndex];
				}
				if (!isLegal[cNum]) {
					continue posLoop;
				}

				int[][] copy = new int[5][5];
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						copy[i][j] = cGrid[i][j];
					}
				}
				solutions.add(copy);
			}

		}
		filled[iIndex][jIndex] = false;
	}
}
