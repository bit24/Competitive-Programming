import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_519D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int M = Integer.parseInt(inputData.nextToken());
		int N = Integer.parseInt(inputData.nextToken());

		int[][] a = new int[N][M];
		int[][] loc = new int[N][M];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < M; j++) {
				a[i][j] = Integer.parseInt(inputData.nextToken()) - 1;
				loc[i][a[i][j]] = j;
			}
		}

		int[] pos = new int[N];

		long ans = 0;

		for (int i = 0; i < M;) {
			int cNum = a[0][i];

			for (int j = 0; j < N; j++) {
				pos[j] = loc[j][cNum];
			}

			int len = 2;
			lLoop:
			while (i + len - 1 < M) {
				cNum = a[0][i + len - 1];
				for (int j = 0; j < N; j++) {
					if (pos[j] + len - 1 >= M || cNum != a[j][pos[j] + len - 1]) {
						break lLoop;
					}
				}
				len++;
			}
			len--;

			ans += len * (len + 1L) / 2;
			i += len;
		}
		printer.println(ans);
		printer.close();
	}
}
