import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PartitioningTheFarm {

	public static void main(String[] args) throws IOException {
		new PartitioningTheFarm().execute();
	}

	int size;
	int numF;

	int[][] elements;
	int maxElement = 0;
	int elementsSum = 0;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("partition.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("partition.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		size = Integer.parseInt(inputData.nextToken());
		numF = Integer.parseInt(inputData.nextToken());

		elements = new int[size][size];
		for (int i = 0; i < size; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < size; j++) {
				elements[i][j] = Integer.parseInt(inputData.nextToken());
				elementsSum += elements[i][j];
				if (elements[i][j] > maxElement) {
					maxElement = elements[i][j];
				}
			}
		}
		reader.close();

		int finalAns = min_cPlacement();
		printer.println(finalAns);
		printer.close();
	}

	int min_cPlacement() {

		int min = Integer.MAX_VALUE;
		for (int bitSet = 0; bitSet < (1 << (size - 1)); bitSet++) {
			if (Integer.bitCount(bitSet) > numF) {
				continue;
			}
			boolean[] cPlacement = new boolean[size];

			for (int i = 0; i < size; i++) {
				if ((bitSet & (1 << i)) != 0) {
					cPlacement[i + 1] = true;
				}
			}
			int cost = binarySearch(cPlacement, numF - Integer.bitCount(bitSet));
			if (cost < min) {
				min = cost;
			}
		}
		return min;
	}

	boolean isPossible(int cost, boolean[] cPlaced, int numF) {
		int[] gridSum = new int[size];
		// gridSum[i] is non zero only if cPlaced[i + 1] is true

		for (int i = 0; i < size; i++) {
			boolean ok = true;

			for (int j = 0; j < size; j++) {
				int extraGridSum = elements[i][j];

				while (j + 1 < size && !cPlaced[j + 1]) {
					extraGridSum += elements[i][j + 1];
					j++;
				}
				if (extraGridSum + gridSum[j] > cost) {
					ok = false;
					break;
				}
			}

			if (!ok) {
				if (numF == 0) {
					return false;
				}
				numF--;
				Arrays.fill(gridSum, 0);
			}

			for (int j = 0; j < size; j++) {
				int extraGridSum = elements[i][j];

				while (j + 1 < size && !cPlaced[j + 1]) {
					extraGridSum += elements[i][j + 1];
					j++;
				}

				gridSum[j] += extraGridSum;
				if (gridSum[j] > cost) {
					return false;
				}
			}

			for (int j = 0; j + 1 < size; j++) {
				if (gridSum[j] != 0) {
					assert (cPlaced[j + 1]);
				}
			}
		}
		return true;
	}

	// binary search for min answer
	int binarySearch(boolean[] cPlacement, int numF) {
		int low = maxElement;
		int high = elementsSum;

		while (low != high) {
			int mid = (low + high) / 2;

			if (isPossible(mid, cPlacement, numF)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		assert (low == high);
		return low;
	}

}