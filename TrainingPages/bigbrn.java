import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: bigbrn
*/

public class bigbrn {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("bigbrn.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bigbrn.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int length = Integer.parseInt(inputData.nextToken());
		int numMarked = Integer.parseInt(inputData.nextToken());

		int[][] maxSize = new int[length][length];

		boolean[][] isMarked = new boolean[length][length];
		
		for (int i = 0; i < numMarked; i++) {
			inputData = new StringTokenizer(reader.readLine());
			isMarked[Integer.parseInt(inputData.nextToken()) - 1][Integer.parseInt(inputData.nextToken()) - 1] = true;
		}
		reader.close();

		int ans = 0;
		for (int i = 0; i < length; i++) {
			if(!isMarked[i][0]){
				maxSize[i][0] = 1;
				ans = 1;
			}
			if(!isMarked[0][i]){
				maxSize[0][i] = 1;
				ans = 1;
			}
		}

		for (int i = 1; i < length; i++) {
			for (int j = 1; j < length; j++) {
				if (!isMarked[i][j]) {
					maxSize[i][j] = Math.min(maxSize[i - 1][j - 1], Math.min(maxSize[i - 1][j], maxSize[i][j - 1])) + 1;

					if (maxSize[i][j] > ans) {
						ans = maxSize[i][j];
					}
				}
			}
		}

		printer.println(ans);
		printer.close();
	}

}
