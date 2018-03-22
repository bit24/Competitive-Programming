import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NodePairs {

	static int[][] chldn = new int[2][100_005];
	static int k;
	static int ans;

	public static void main(String[] args) throws IOException {
		Arrays.fill(chldn[0], -1);
		Arrays.fill(chldn[1], -1);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		k = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int lR = Integer.parseInt(inputData.nextToken());
			chldn[lR][a] = b;
		}

		dfs(0, 0, 0);
		printer.println(ans / 2);
		printer.close();
	}

	static void dfs(int v1, int v2, int dif) {
		if (dif == k) {
			ans++;
		}
		if (chldn[0][v1] != -1 && chldn[0][v2] != -1) {
			dfs(chldn[0][v1], chldn[0][v2], dif);
		}
		if (chldn[1][v1] != -1 && chldn[1][v2] != -1) {
			dfs(chldn[1][v1], chldn[1][v2], dif);
		}
		if (dif < k) {
			if (chldn[0][v1] != -1 && chldn[1][v2] != -1) {
				dfs(chldn[0][v1], chldn[1][v2], dif + 1);
			}
			if (chldn[1][v1] != -1 && chldn[0][v2] != -1) {
				dfs(chldn[1][v1], chldn[0][v2], dif + 1);
			}
		}
	}
}
