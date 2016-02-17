import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		ArrayList<Long> out = new ArrayList<Long>();

		while (reader.hasNext()) {
			int size = reader.nextInt();
			byte[][] value = new byte[size][size];
			int nSize = reader.nextInt();
			int modifier = nSize - 1;
			int boundary = size - nSize + 1;

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					value[i][j] = reader.nextByte();
				}
			}

			int[][] hCost = new int[size][boundary];

			for (int cRow = 0; cRow < size; cRow++) {
				byte[] rowValue = value[cRow];
				int[] rowHCost = hCost[cRow];
				int cSum = 0;
				for (int i = 0; i < nSize; i++) {
					cSum += rowValue[i];
				}
				rowHCost[0] = cSum;
				for (int cColumn = 1; cColumn < boundary; cColumn++) {
					cSum -= rowValue[cColumn - 1];
					cSum += rowValue[cColumn + modifier];
					rowHCost[cColumn] = cSum;
				}
			}

			long[][] squareCost = new long[boundary][boundary];

			long cSum = 0;

			for (int i = 0; i < nSize; i++) {
				cSum += hCost[i][0];
			}
			squareCost[0][0] = cSum;

			for (int cColumn = 1; cColumn < boundary; cColumn++) {
				cSum = 0;
				for(int cRow = 0; cRow < nSize; cRow++){
					cSum += hCost[cRow][cColumn];
				}
				squareCost[0][cColumn] = cSum;
			}
			
			for(int cColumn = 0; cColumn < boundary; cColumn++){
				cSum = squareCost[0][cColumn];
				for(int cRow = 1; cRow < boundary; cRow++){
					cSum -= hCost[cRow - 1][cColumn];
					cSum += hCost[cRow + modifier][cColumn];
					squareCost[cRow][cColumn] = cSum;
				}
			}
			
			long totalSum = 0;
			for(int cRow = 0; cRow < boundary; cRow++){
				long[] rowCost = squareCost[cRow];
				for(int cColumn = 0; cColumn < boundary; cColumn++){
					long item = rowCost[cColumn];
					out.add(item);
					totalSum += item;
				}
			}
			out.add(totalSum);
			out.add(Long.MIN_VALUE);
		}

		out.remove(out.size() - 1);

		for (long l : out) {
			if (l == Long.MIN_VALUE) {
				printer.println();
			} else {
				printer.println(l);
			}
		}
		printer.close();
		reader.close();

	}

}
