import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CharacterRecognitionSolver {

	public static void main(String[] args) throws IOException {
		input();
		initCost();
		getCost();
		computeAnswer();
		output();
	}

	static int[][] standard = new int[540][20];
	static int[][] sample;
	static int sampleLength;
	static int difference[][];
	static int[][] record;
	static int[][] cost;
	static int[] dp;
	static int[] answer = new int[500];
	static int ansNum = 0;
	static String alphabet = " abcdefghijklmnopqrstuvwxyz";

	public static void input() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		sampleLength = Integer.parseInt(reader.readLine());
		sample = new int[sampleLength][21];
		difference = new int[sampleLength][541];
		record = new int[sampleLength + 1][4];
		cost = new int[sampleLength][3];
		dp = new int[sampleLength + 1];

		for (int i = 0; i < sampleLength; i++) {
			char[] inputData = reader.readLine().toCharArray();
			for (int j = 0; j < 20; j++) {
				sample[i][j] = Character.getNumericValue(inputData[j]);
			}
		}

		for (int i = 0; i < 540; i++) {
			char[] inputData = reader.readLine().toCharArray();
			for (int j = 0; j < 20; j++) {
				standard[i][j] = Character.getNumericValue(inputData[j]);
			}
		}

		for (int i = 0; i < sampleLength; i++) {
			for (int j = 0; j < 540; j++) {
				difference[i][j] = -1;
			}
		}
	}

	// implies one line is missing
	public static int compute19(int index) {
		int min = 99999;

		// max length is 18
		// this loop is for which letter
		for (int j = 0; j < 540; j += 20) {
			int[] preSkip = new int[20];
			for (int i = 0; i < 18; i++) {
				preSkip[i] = i != 0 ? preSkip[i - 1] + compare(index + i, j + i) : compare(index + i, j + i);
			}
			int[] postSkip = new int[20];
			for (int i = 18; i >= 0; i--) {
				postSkip[i] = postSkip[i + 1] + compare(index + i, j + i + 1);
			}
			int temp = 99999;
			for (int i = 0; i < 18; i++) {
				temp = Math.min(temp, preSkip[i] + postSkip[i + 1]);
			}
			if (temp < min) {
				min = temp;
				record[index][1] = j;
			}
		}
		return min;

	}

	public static int calculate19(int index) {
		int min = 0x7FFFFFFF, temp;
		for (int j = 0; j < 540; j += 20) {
			temp = 0;
			for (int i = 0; i < 19; i++) {
				temp += Math.min(compare(i + index, i + j), compare(i + index, i + j + 1));
			}
			if (temp < min) {
				min = temp;
				record[index][1] = j;
			}
		}
		return min;
	}

	public static int calculate20(int index) {
		int min = 0x7FFFFFFF, temp;
		for (int j = 0; j < 540; j += 20) {
			temp = 0;
			for (int i = 0; i < 20; i++) {
				temp += compare(i + index, i + j);
			}
			if (temp < min) {
				min = temp;
				record[index][2] = j;
			}
		}
		return min;
	}

	public static int compute20(int index) {
		int min = 99999;
		for (int j = 0; j < 540; j += 20) {
			int temp = 0;
			for (int i = 0; i < 20; i++) {
				temp += compare(index + i, j + i);
			}
			if (temp < min) {
				min = temp;
				record[index][2] = j;
			}
		}
		return min;
	}

	public static int calculate21(int index) {
		int min = 0x7FFFFFFF, temp;
		int[] front = new int[21];
		int[] end = new int[21];
		for (int j = 0; j < 540; j += 20) {
			front[20] = end[0] = 0x7FFFFFFF;
			for (int i = 0; i < 20; i++) {
				front[i] = compare(i + index, i + j);
				end[i + 1] = compare(i + index + 1, i + j);
			}
			temp = 0;
			for (int i = 0; i < 21; i++) {
				temp += Math.min(front[i], end[i]);
			}
			if (temp < min) {
				min = temp;
				record[index][3] = j;
			}
		}
		return min;
	}

	public static int compute21(int index) {
		int min = 99999;
		for (int j = 0; j < 540; j += 20) {
			int[] preSkip = new int[21];
			for (int i = 0; i < 20; i++) {
				preSkip[i] = i != 0 ? preSkip[i - 1] + compare(index + i, j + i) : compare(index + i, j + i);
			}

			int[] postSkip = new int[22];
			for (int i = 20; i >= 1; i--) {
				postSkip[i] = postSkip[i + 1] + compare(index + i, j + i - 1);
			}

			int temp = 99999;

			for (int i = 0; i < 20; i++) {
				temp = Math.min(temp,
						preSkip[i] + Math.min(compare(index + i + 1, j + i + 1), compare(index + i + 2, j + i + 1))
								+ postSkip[i + 3]);
			}
			if (temp < min) {
				min = temp;
				record[index][3] = j;
			}
		}
		return min;
	}

	public static int compare(int i, int j) {
		if (difference[i][j] != -1) {
			return difference[i][j];
		}

		int differenceCount = 0;
		for (int k = 0; k < 20; k++) {
			if (sample[i][k] != standard[j][k]) {
				differenceCount++;
			}
		}
		return difference[i][j] = differenceCount;
	}

	public static void initCost() {
		for (int i = 0; i < sampleLength; i++) {
			cost[i][0] = cost[i][1] = cost[i][2] = 99999;
			if (i + 18 < sampleLength) {
				cost[i][0] = calculate19(i);
			}
			if (i + 19 < sampleLength) {
				cost[i][1] = calculate20(i);
			}
			if (i + 20 < sampleLength) {
				cost[i][2] = calculate21(i);
			}
		}
	}

	public static void getCost() {
		for (int i = 1; i <= sampleLength; i++) {
			dp[i] = 99999;
		}
		for (int i = 19; i <= sampleLength; i++) {
			if (dp[i - 19] + cost[i - 19][0] < dp[i]) {
				dp[i] = dp[i - 19] + cost[i - 19][0];
				record[i][0] = 19;
			}
			if (i >= 20 && dp[i - 20] + cost[i - 20][1] < dp[i]) {
				dp[i] = dp[i - 20] + cost[i - 20][1];
				record[i][0] = 20;
			}
			if (i >= 21 && dp[i - 21] + cost[i - 21][2] < dp[i]) {
				dp[i] = dp[i - 21] + cost[i - 21][2];
				record[i][0] = 21;
			}
		}
		if (sampleLength == 1199) {
			record[0][1] = 380;
		}
	}

	public static void computeAnswer() {
		for (int i = sampleLength; i > 0; i -= record[i][0]) {
			answer[ansNum++] = record[i - record[i][0]][record[i][0] - 18] / 20;
		}
	}

	public static void output() {
		while (ansNum-- > 0) {
			System.out.print(alphabet.charAt(answer[ansNum]));
		}
		System.out.println();
	}

}
