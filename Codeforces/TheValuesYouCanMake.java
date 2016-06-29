import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TheValuesYouCanMake {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numElements = Integer.parseInt(inputData.nextToken());
		int targetValue = Integer.parseInt(inputData.nextToken());

		int[] elementValues = new int[numElements + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numElements; i++) {
			elementValues[i] = Integer.parseInt(inputData.nextToken());
		}

		boolean[][] isPossible = new boolean[targetValue + 1][targetValue + 1];
		isPossible[0][0] = true;

		for (int i = 1; i <= numElements; i++) {
			for (int pileAValue = targetValue; pileAValue >= 0; pileAValue--) {
				for (int pileBValue = targetValue; pileBValue >= 0; pileBValue--) {

					if (!isPossible[pileAValue][pileBValue]) {
						if (pileAValue - elementValues[i] >= 0
								&& isPossible[pileAValue - elementValues[i]][pileBValue]) {
							isPossible[pileAValue][pileBValue] = true;
							continue;
						}
						if (pileBValue - elementValues[i] >= 0
								&& isPossible[pileAValue][pileBValue - elementValues[i]]) {
							isPossible[pileAValue][pileBValue] = true;
							continue;
						}
					}

				}
			}
		}

		int count = 0;
		for (int i = 0; i <= targetValue; i++) {
			if (isPossible[i][targetValue - i]) {
				count++;
			}
		}
		System.out.println(count);
		for (int i = 0; i <= targetValue; i++) {
			if (isPossible[i][targetValue - i]) {
				System.out.print(i + " ");
			}
		}
		System.out.println();

	}

}
