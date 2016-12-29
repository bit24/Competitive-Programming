import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_385B {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numR = Integer.parseInt(inputData.nextToken());
		int numC = Integer.parseInt(inputData.nextToken());

		int left = Integer.MAX_VALUE;
		int right = 0;
		int bottom = 0;
		int top = Integer.MAX_VALUE;

		boolean[][] filled = new boolean[numR][numC];

		for (int i = 0; i < numR; i++) {
			String nextLine = reader.readLine();
			for (int j = 0; j < numC; j++) {
				filled[i][j] = nextLine.charAt(j) == 'X';

				if (filled[i][j]) {
					if (j < left) {
						left = j;
					}
					if (j > right) {
						right = j;
					}
					if (i > bottom) {
						bottom = i;
					}
					if (i < top) {
						top = i;
					}
				}
			}
		}

		for (int i = top; i <= bottom; i++) {
			for (int j = left; j <= right; j++) {
				if (!filled[i][j]) {
					System.out.println("NO");
					return;
				}
			}
		}
		System.out.println("YES");
	}

}
