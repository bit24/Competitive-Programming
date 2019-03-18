import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class MainF {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] d = new int[N];

		for (int i = 0; i < N; i++) {
			d[i] = Integer.parseInt(st.nextToken());
		}

		if (N == 1) {
			printer.println(Math.max(0, d[0]));
			printer.close();
			return;
		}

		int[] dp = new int[N];
		dp[0] = Math.max(0, d[0]);
		dp[1] = Math.max(Math.max(0, d[1]), dp[0]);
		for (int i = 2; i < N; i++) {
			dp[i] = Math.max(dp[i - 1], d[i] + dp[i - 2]);
		}

		printer.println(dp[N - 1]);
		printer.close();
	}
}
