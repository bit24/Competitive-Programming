import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BinarySudokuSolver {

	static final int EVEN = 0;
	static final int ODD = 1;

	static int[] grid = new int[10];

	static int[][] cCost = new int[1 << 9][(1 << 3)];
	static int[][] nCost = new int[1 << 9][(1 << 3)];

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 1; i <= 9; i++) {
			grid[i] = Integer.parseInt(reader.readLine(), 2);
		}
		reader.close();

		for (int i = 0; i < nCost.length; i++) {
			for (int j = 0; j < nCost[i].length; j++) {
				cCost[i][j] = Integer.MAX_VALUE / 2;
				nCost[i][j] = Integer.MAX_VALUE / 2;
			}
		}

		cCost[0][0] = 0;

		for (int cRow = 0; cRow < 9; cRow++) {

			for (int cState = 0; cState < (1 << 9); cState++) {
				for (int cGState = 0; cGState < (1 << 3); cGState++) {
					push(cRow, cState, cGState);
				}
			}

			int[][] temp = cCost;
			cCost = nCost;
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp[i].length; j++) {
					temp[i][j] = Integer.MAX_VALUE / 2;
				}
			}
			nCost = temp;
		}

		System.out.println(cCost[0][0]);
	}

	static void push(int row, int cRPolarity, int cGPolarity) {
		int cost = cCost[cRPolarity][cGPolarity];
		if (cost >= Integer.MAX_VALUE / 2) {
			return;
		}

		for (int newRow = 0; newRow < (1 << 9); newRow++) {
			if ((Integer.bitCount(newRow) & 1) == 1) {
				continue;
			}

			int nGState = cGPolarity;

			if ((Integer.bitCount((newRow & 0b111_000_000)) & 1) == 1) {
				nGState ^= 0b100;
			}
			if ((Integer.bitCount((newRow & 0b000_111_000)) & 1) == 1) {
				nGState ^= 0b010;
			}
			if ((Integer.bitCount((newRow & 0b000_000_111)) & 1) == 1) {
				nGState ^= 0b001;
			}

			if ((row % 3) == 2 && nGState != 0) {
				continue;
			}

			int value = cost + Integer.bitCount((newRow ^ grid[row + 1]));

			int nRPolarity = newRow ^ cRPolarity;
			if (value < nCost[nRPolarity][nGState]) {
				nCost[nRPolarity][nGState] = value;
			}
		}
	}
}
