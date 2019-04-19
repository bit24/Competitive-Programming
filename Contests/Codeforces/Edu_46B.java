import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Edu_46B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nE = Integer.parseInt(inputData.nextToken()) + 2;
		int END = Integer.parseInt(inputData.nextToken());

		int[] e = new int[nE];
		e[0] = 0;
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i < nE - 1; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}
		e[nE - 1] = END;

		int[] sufS = new int[nE + 1];

		for (int i = nE - 2; i >= 0; i--) {
			sufS[i] = (i + 2 < nE ? sufS[i + 2] : 0) + e[i + 1] - e[i];
		}

		int max = 0;

		for (int i = 0; i + 1 < nE; i++) {
			if ((i & 1) == 0) {
				max += e[i + 1] - e[i];
			}
		}

		int sum0 = 0;

		for (int i = 0; i + 1 < nE; i++) {
			if ((i & 1) == 0) {

				if (e[i] != e[i + 1] - 1) {
					max = Math.max(max, sum0 + e[i + 1] - 1 - e[i] + sufS[i + 1]);
				}

				sum0 += e[i + 1] - e[i];
			} else {
				if (e[i] != e[i + 1] - 1) {
					max = Math.max(max, sum0 + e[i + 1] - 1 - e[i] + sufS[i + 2]);
				}
			}
		}
		printer.println(max);
		printer.close();
	}
}
