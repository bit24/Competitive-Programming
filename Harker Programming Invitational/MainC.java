import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class MainC {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int M = Integer.parseInt(inputData.nextToken());
		int[] c = new int[N];
		int[] v = new int[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			v[i] = Integer.parseInt(inputData.nextToken());
			c[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] dp = new int[M + 1];

		for (int j = 0; j < N; j++) {
			for (int i = M; i >= 0; i--) {
				if (i + c[j] <= M) {
					dp[i + c[j]] = Math.max(dp[i + c[j]], dp[i] + v[j]);
				}
			}
		}
		printer.println(dp[M]);
		printer.close();
	}
}
