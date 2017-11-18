import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FencedIn {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fencedin.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fencedin.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int lV = Integer.parseInt(inputData.nextToken());
		int lH = Integer.parseInt(inputData.nextToken());
		int nV = Integer.parseInt(inputData.nextToken());
		int nH = Integer.parseInt(inputData.nextToken());

		int[] x = new int[nV];
		int[] y = new int[nH];
		for (int i = 0; i < nV; i++) {
			x[i] = Integer.parseInt(reader.readLine());
		}
		for (int i = 0; i < nH; i++) {
			y[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		Arrays.sort(x);
		Arrays.sort(y);

		int[] v = new int[nV + 1];
		int[] h = new int[nH + 1];

		int last = 0;
		for (int i = 0; i < nV; i++) {
			v[i] = x[i] - last;
			last = x[i];
		}
		v[nV] = lV - last;
		last = 0;
		for (int i = 0; i < nH; i++) {
			h[i] = y[i] - last;
			last = y[i];
		}
		h[nH] = lH - last;
		reader.close();

		Arrays.sort(v);
		Arrays.sort(h);

		int cV = 0;
		int cH = 0;

		long cost = 0;
		while (cV <= nV || cH <= nH) {
			if (cH > nH || (cV <= nV && v[cV] <= h[cH])) {
				if (cV == 0 || cH == 0) {
					cost += (long) nH * v[cV];
				} else {
					cost += (long) (nH + 1 - cH) * v[cV];
				}
				cV++;
			} else {
				if (cV == 0 || cH == 0) {
					cost += (long) nV * h[cH];
				} else {
					cost += (long) (nV + 1 - cV) * h[cH];
				}
				cH++;
			}
		}
		printer.println(cost);
		printer.close();
	}
}
