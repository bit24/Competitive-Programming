import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class MainG {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int[] a = new int[1000002];
		int N = Integer.parseInt(reader.readLine());

		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("P")) {
				int b = Integer.parseInt(inputData.nextToken());
				int c = Integer.parseInt(inputData.nextToken()) + 1;
				a[b]++;
				a[c]--;
			} else {
				int b = Integer.parseInt(inputData.nextToken());
				int c = Integer.parseInt(inputData.nextToken()) + 1;
				a[b]--;
				a[c]++;
			}
		}

		long[] cnt = new long[1000002];

		for (int i = 1; i <= 1000000; i++) {
			cnt[i] = cnt[i - 1] + a[i];
		}

		long[] pSum = new long[1000002];

		for (int i = 1; i <= 1000000; i++) {
			pSum[i] = pSum[i - 1] + cnt[i];
		}

		int R = Integer.parseInt(reader.readLine());
		for (int i = 0; i < R; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int b = Integer.parseInt(inputData.nextToken());
			int c = Integer.parseInt(inputData.nextToken());
			printer.println(pSum[c] - pSum[b - 1]);
		}
		printer.close();
	}
}
