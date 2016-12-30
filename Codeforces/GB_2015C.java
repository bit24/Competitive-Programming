import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class GB_2015C {

	public static void main(String[] args) throws IOException {
		new GB_2015C().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numR = Integer.parseInt(inputData.nextToken());
		int numC = Integer.parseInt(inputData.nextToken());

		boolean[][] ok = new boolean[numR + 2][numC + 2];

		for (int i = 1; i <= numR; i++) {
			String nextLine = reader.readLine();
			for (int j = 1; j <= numC; j++) {
				ok[i][j] = nextLine.charAt(j - 1) == '.';
			}
		}

		int[][] vPrefix = new int[numC + 2][numR + 2];
		int[][] hPrefix = new int[numR + 2][numC + 2];

		for (int i = 1; i <= numC; i++) {
			for (int j = 2; j <= numR; j++) {
				vPrefix[i][j] = vPrefix[i][j - 1];
				if (ok[j][i] && ok[j - 1][i]) {
					vPrefix[i][j]++;
				}
			}
		}

		for (int i = 1; i <= numR; i++) {
			for (int j = 2; j <= numC; j++) {
				hPrefix[i][j] = hPrefix[i][j - 1];
				if (ok[i][j] && ok[i][j - 1]) {
					hPrefix[i][j]++;
				}
			}
		}

		int numQ = Integer.parseInt(reader.readLine());

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int top = Integer.parseInt(inputData.nextToken());
			int left = Integer.parseInt(inputData.nextToken());
			int bottom = Integer.parseInt(inputData.nextToken());
			int right = Integer.parseInt(inputData.nextToken());

			int sum = 0;
			for (int j = left; j <= right; j++) {
				sum += vPrefix[j][bottom] - vPrefix[j][top];
			}
			for (int j = top; j <= bottom; j++) {
				sum += hPrefix[j][right] - hPrefix[j][left];
			}
			printer.println(sum);
		}
		printer.close();
	}

}
