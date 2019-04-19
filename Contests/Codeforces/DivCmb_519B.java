import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DivCmb_519B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		int[] a = new int[N];
		int[] xC = new int[N];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
			xC[i] = i == 0 ? a[i] : a[i] - a[i - 1];
		}

		ArrayList<Integer> ans = new ArrayList<Integer>();

		lLoop:
		for (int l = 1; l <= N; l++) {
			for (int j = 0; j < N; j++) {
				if (j >= l) {
					if (xC[j] != xC[j - l]) {
						continue lLoop;
					}
				}
			}
			ans.add(l);
		}

		printer.println(ans.size());
		for (int i : ans) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}
}
