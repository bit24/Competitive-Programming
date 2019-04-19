import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lyft_18FC {

	public static void main(String[] args) throws IOException {
		new Lyft_18FC().main();
	}

	int nP;
	int[] x;
	int[] y;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nP = Integer.parseInt(reader.readLine());
		x = new int[nP];
		y = new int[nP];

		// left, up, right, down
		int[] most = new int[4];

		for (int i = 0; i < nP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());

			if (i == 0 || x[i] < x[most[0]]) {
				most[0] = i;
			}
			if (i == 0 || y[i] > y[most[1]]) {
				most[1] = i;
			}
			if (i == 0 || x[i] > x[most[2]]) {
				most[2] = i;
			}
			if (i == 0 || y[i] < y[most[3]]) {
				most[3] = i;
			}
		}

		int ans = 0;

		for (int i = 0; i < nP; i++) {

			for (int j = 0; j < 4; j++) {
				int minX = x[i];
				int maxX = x[i];
				int minY = y[i];
				int maxY = y[i];
				
				int k = (j + 1) % 4;
				minX = Math.min(minX, Math.min(x[most[j]], x[most[k]]));
				maxX = Math.max(maxX, Math.max(x[most[j]], x[most[k]]));
				minY = Math.min(minY, Math.min(y[most[j]], y[most[k]]));
				maxY = Math.max(maxY, Math.max(y[most[j]], y[most[k]]));
				ans = Math.max(ans, 2 * (maxX - minX + maxY - minY));
			}
		}
		printer.print(ans + " ");

		int ans2 = 2 * (x[most[2]] - x[most[0]] + y[most[1]] - y[most[3]]);
		for (int i = 4; i <= nP; i++) {
			printer.print(ans2 + " ");
		}
		printer.println();
		printer.close();

	}
}
