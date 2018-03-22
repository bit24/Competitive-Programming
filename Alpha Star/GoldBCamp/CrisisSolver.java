import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CrisisSolver {

	static int numCowStacks;
	static int numHayStacks;
	static int numMovements;
	static int[] cX;
	static int[] cY;
	static int[] hX;
	static int[] hY;

	static int[][] numOverlapping;

	static int[][][] maxValue;
	static char[][][] solution;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numCowStacks = Integer.parseInt(inputData.nextToken());
		numHayStacks = Integer.parseInt(inputData.nextToken());
		numMovements = Integer.parseInt(inputData.nextToken());
		cX = new int[numCowStacks];
		cY = new int[numCowStacks];
		hX = new int[numHayStacks];
		hY = new int[numHayStacks];

		for (int i = 0; i < numCowStacks; i++) {
			inputData = new StringTokenizer(reader.readLine());
			cX[i] = Integer.parseInt(inputData.nextToken());
			cY[i] = Integer.parseInt(inputData.nextToken());
		}
		for (int i = 0; i < numHayStacks; i++) {
			inputData = new StringTokenizer(reader.readLine());
			hX[i] = Integer.parseInt(inputData.nextToken());
			hY[i] = Integer.parseInt(inputData.nextToken());
		}

		numOverlapping = new int[65][65];
		precomputeOverlap();

		maxValue = new int[numMovements + 2][65][65];
		solution = new char[numMovements + 2][65][65];

		for (int i = 0; i <= numMovements; i++) {
			for (int j = 0; j < 65; j++) {
				for (int k = 0; k < 65; k++) {
					maxValue[i][j][k] = 0;
					// Z will always be lower than any whistle combination
					solution[i][j][k] = 'Z';
				}
			}
		}

		for (int movementsLeft = 1; movementsLeft <= numMovements; movementsLeft++) {
			for (int cX = 1; cX <= 61; cX++) {
				for (int cY = 1; cY <= 61; cY++) {
					int cMaxValue = maxValue[movementsLeft - 1][cX + 1][cY] + numOverlapping[cX + 1][cY];
					char best = 'E';
					int sum = maxValue[movementsLeft - 1][cX][cY + 1] + numOverlapping[cX][cY + 1];
					if (cMaxValue < sum) {
						cMaxValue = sum;
						best = 'N';
					}
					sum = maxValue[movementsLeft - 1][cX][cY - 1] + numOverlapping[cX][cY - 1];
					if (cMaxValue < sum) {
						cMaxValue = sum;
						best = 'S';
					}
					sum = maxValue[movementsLeft - 1][cX - 1][cY] + numOverlapping[cX - 1][cY];
					if (cMaxValue < sum) {
						cMaxValue = sum;
						best = 'W';
					}
					maxValue[movementsLeft][cX][cY] = cMaxValue;
					solution[movementsLeft][cX][cY] = best;
				}
			}
		}

		System.out.println(maxValue[numMovements][31][31]);

		int cX = 31;
		int cY = 31;
		for (int cNumMovements = numMovements; cNumMovements >= 1; cNumMovements--) {
			System.out.print(solution[cNumMovements][cX][cY]	);
			if (solution[cNumMovements][cX][cY] == 'E')
				cX++;
			else if (solution[cNumMovements][cX][cY] == 'N')
				cY++;
			else if (solution[cNumMovements][cX][cY] == 'S')
				cY--;
			else if (solution[cNumMovements][cX][cY] == 'W')
				cX--;
		}
	}
	
	public static void precomputeOverlap() {
		for (int cowStackNumber = 0; cowStackNumber < numCowStacks; cowStackNumber++) {
			for (int hayStackNumber = 0; hayStackNumber < numHayStacks; hayStackNumber++) {
				if (Math.abs(hX[hayStackNumber] - cX[cowStackNumber]) <= 30
						&& Math.abs(hY[hayStackNumber] - cY[cowStackNumber]) <= 30) {
					numOverlapping[31 + hX[hayStackNumber] - cX[cowStackNumber]][31 + hY[hayStackNumber] - cY[cowStackNumber]
		]++;
				}
			}
		}
	}

}
