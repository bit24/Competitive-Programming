import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class disc {
	static int N;
	static int[] arr = new int[10];
	static int max;
	static boolean[] used = new boolean[10];

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int t = Integer.parseInt(reader.readLine());
		for (int i = 0; i < t; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			max = 0;
			N = Integer.parseInt(inputData.nextToken());
			Arrays.fill(arr, 0);
			Arrays.fill(used, false);

			for (int j = 0; j < N; j++) {
				arr[j] = Integer.parseInt(inputData.nextToken());
			}
			permute(0, -1, 0);
			printer.println("Wave #" + (i+1) + ": " + max);
		}
		printer.close();
	}

	static void permute(int cI, int last, int sum) {
		if (cI == N) {
			if(sum > max) {
				max = sum;
			}
			return;
		}
		for (int i = 0; i < N; i++) {
			if (!used[i]) {
				used[i] = true;
				int v = last == -1 ? 0 : arr[i] - last;
				permute(cI + 1, arr[i], sum + (v < 0 ? -v : v));
				used[i] = false;
			}
		}
	}
}
