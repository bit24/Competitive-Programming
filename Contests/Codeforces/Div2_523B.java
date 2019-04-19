import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_523B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int H = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());

		long nB = 0;
		long mH = 0;

		long[] cols = new long[N];
		for (int i = 0; i < N; i++) {
			long c = Integer.parseInt(inputData.nextToken());
			nB += c;
			mH = Math.max(mH, c);
			cols[i] = c;
		}

		Arrays.sort(cols);

		long lCovered = 0;
		for (int i = 0; i < N; i++) {
			if (cols[i] > lCovered) {
				lCovered++;
			}
		}

		printer.println(nB - (N + mH - lCovered));
		printer.close();
	}
}
