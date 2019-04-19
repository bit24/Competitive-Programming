import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_427C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numP = Integer.parseInt(inputData.nextToken());
		int numQ = Integer.parseInt(inputData.nextToken());
		int mod = Integer.parseInt(inputData.nextToken()) + 1;

		int[][][] grid = new int[mod][101][101];

		int[][][] preS = new int[mod][101][101];

		for (int i = 0; i < numP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int x = Integer.parseInt(inputData.nextToken());
			int y = Integer.parseInt(inputData.nextToken());
			int s = Integer.parseInt(inputData.nextToken());

			for (int t = 0; t < mod; t++) {
				grid[t][y][x] += (s + t) % mod;
			}
		}

		for (int res = 0; res < mod; res++) {
			for (int y = 1; y <= 100; y++) {
				for (int x = 1; x <= 100; x++) {
					preS[res][y][x] = preS[res][y][x - 1] + grid[res][y][x];
				}
			}
		}

		while (numQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int t = Integer.parseInt(inputData.nextToken()) % mod;
			int left = Integer.parseInt(inputData.nextToken());
			int low = Integer.parseInt(inputData.nextToken());
			int right = Integer.parseInt(inputData.nextToken());
			int high = Integer.parseInt(inputData.nextToken());
			int sum = 0;

			for (int y = low; y <= high; y++) {
				sum += preS[t][y][right] - preS[t][y][left - 1];
			}
			printer.println(sum);
		}
		printer.close();
	}

}
