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
		search(1);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("prime3.out")));
		String[] solutionsInS = new String[solutions.size()];

		for (int i = 0; i < solutions.size(); i++) {
			StringBuilder str = new StringBuilder();
			for (int cDigit : solutions.get(i)) {
				str.append(cDigit);
			}
			solutionsInS[i] = str.toString();
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
	boolean[] rLegal = new boolean[100_000];

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
						rLegal[cNum % numDigits] = true;
					}
					rLegal[cNum] = true;
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
		cGrid[0] = Integer.parseInt(inputData.nextToken());
		reader.close();
	}

	ArrayList<int[]> solutions = new ArrayList<int[]>();
	// ArrayList<int[][]> solutions1 = new ArrayList<int[][]>();

	int[] cGrid = new int[25];
	/*
	 * int[][] cGrid1 = new int[5][5];
	 * 
	 * void search(int i, int j) { posLoop: for (int posValue = 0; posValue < 10; posValue++) { cGrid1[i][j] = posValue;
	 * int cNum = 0; for (int newJ = 0; newJ <= j; newJ++) { cNum *= 10; cNum += cGrid1[i][newJ]; } if (!isLegal[cNum])
	 * { continue posLoop; }
	 * 
	 * cNum = 0; for (int newI = 0; newI <= i; newI++) { cNum *= 10; cNum += cGrid1[newI][j]; } if (!isLegal[cNum]) {
	 * continue posLoop; }
	 * 
	 * if (i == 4 && j == 0) { cNum = cGrid1[4][0] * 10_000 + cGrid1[3][1] * 1_000 + cGrid1[2][2] * 100 + cGrid1[1][3] *
	 * 10 + cGrid1[0][4]; if (!isLegal[cNum]) { continue posLoop; } }
	 * 
	 * if (i == j) { cNum = 0; for (int newIJ = 0; newIJ <= i; newIJ++) { cNum *= 10; cNum += cGrid1[newIJ][newIJ]; } if
	 * (!isLegal[cNum]) { continue posLoop; } }
	 * 
	 * if (i == 4 && j == 4) { int[][] copy = new int[5][5]; for (int tempI = 0; tempI < 5; tempI++) { for (int tempJ =
	 * 0; tempJ < 5; tempJ++) { copy[tempI][tempJ] = cGrid1[tempI][tempJ]; } } solutions1.add(copy); } else { if (j ==
	 * 4) { search(i + 1, 0); } else { search(i, j + 1); } } } }
	 */

	void search(int index) {
		posLoop: for (int posValue = 0; posValue < 10; posValue++) {
			cGrid[index] = posValue;
			int cNum = 0;
			for (int i = (index / 5) * 5; i <= index; i++) {
				cNum *= 10;
				cNum += cGrid[i];
			}
			if (!isLegal[cNum]) {
				continue posLoop;
			}

			cNum = 0;
			for (int i = index % 5; i <= index; i += 5) {
				cNum *= 10;
				cNum += cGrid[i];
			}
			if (!isLegal[cNum]) {
				continue posLoop;
			}

			if (index % 4 == 0 && index != 0 && index != 24) {
				cNum = 0;
				for (int i = index; i >= 4; i -= 4) {
					cNum *= 10;
					cNum += cGrid[i];
				}
				if (!rLegal[cNum]) {
					continue posLoop;
				}
			}

			if (index % 6 == 0) {
				cNum = 0;
				for (int i = 0; i <= index; i += 6) {
					cNum *= 10;
					cNum += cGrid[i];
				}
				if (!isLegal[cNum]) {
					continue posLoop;
				}
			}

			if (index == 24) {
				solutions.add(Arrays.copyOf(cGrid, 25));
			} else {
				search(index + 1);
			}
		}
	}
	
	
}
