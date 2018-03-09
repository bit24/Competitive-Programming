import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_358E {

	static long[] x;
	static long[] y;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());

		x = new long[nV];
		y = new long[nV];

		for (int i = 0; i < nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
		}

		int p1 = 0;
		int p2 = 1;
		int p3 = 2;
		long maxArea = area(p1, p2, p3);

		boolean improving = true;

		while (improving) {
			improving = false;
			for (int i = 0; i < nV; i++) {
				long cArea = area(i, p2, p3);
				if (cArea > maxArea) {
					maxArea = cArea;
					p1 = i;
					improving = true;
				}
				cArea = area(p1, i, p3);
				if (cArea > maxArea) {
					maxArea = cArea;
					p2 = i;
					improving = true;
				}
				cArea = area(p1, p2, i);
				if (cArea > maxArea) {
					maxArea = cArea;
					p3 = i;
					improving = true;
				}
			}
		}

		printer.print(x[p1] + x[p2] - x[p3] + " ");
		printer.println(y[p1] + y[p2] - y[p3]);

		printer.print(x[p2] + x[p3] - x[p1] + " ");
		printer.println(y[p2] + y[p3] - y[p1] + " ");

		printer.print(x[p3] + x[p1] - x[p2] + " ");
		printer.println(y[p3] + y[p1] - y[p2] + " ");
		printer.close();
	}

	static long area(int p1, int p2, int p3) {
		return Math.abs((x[p2] - x[p1]) * (y[p3] - y[p1]) - (y[p2] - y[p1]) * (x[p3] - x[p1]));
	}
}
