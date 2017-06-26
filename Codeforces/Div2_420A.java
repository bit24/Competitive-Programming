import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Div2_420A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int len = Integer.parseInt(reader.readLine());

		int[][] grid = new int[len + 1][len + 1];

		for (int i = 1; i <= len; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int j = 1; j <= len; j++) {
				int nxt = Integer.parseInt(inputData.nextToken());
				grid[i][j] = nxt;
			}
		}

		for (int i = 1; i <= len; i++) {
			HashSet<Integer> rSet = new HashSet<Integer>();
			for (int j = 1; j <= len; j++) {
				rSet.add(grid[i][j]);
			}

			jLoop:
			for (int j = 1; j <= len; j++) {
				int cur = grid[i][j];
				if (cur == 1) {
					continue;
				}
				for (int oR = 1; oR <= len; oR++) {
					if (rSet.contains(cur - grid[oR][j])) {
						continue jLoop;
					}
				}
				System.out.println("No");
				return;
			}
		}
		System.out.println("Yes");
	}

}
