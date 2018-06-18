import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_488B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nA = Integer.parseInt(inputData.nextToken());
		int nB = Integer.parseInt(inputData.nextToken());
		int[][] a = new int[nA][2];
		int[][] b = new int[nB][2];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nA; i++) {
			a[i][0] = Integer.parseInt(inputData.nextToken());
			a[i][1] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nB; i++) {
			b[i][0] = Integer.parseInt(inputData.nextToken());
			b[i][1] = Integer.parseInt(inputData.nextToken());
		}

		boolean single = true;
		int shared = -1;

		for (int i = 0; i < nA; i++) {
			for (int j = 0; j < nB; j++) {
				int mV = pos(a[i], b[j]);
				if (mV != -1) {
					if (shared == -1) {
						shared = mV;
					} else {
						if (mV != shared) {
							single = false;
						}
					}
				}
			}
		}

		if (single) {
			printer.println(shared);
			printer.close();
			return;
		}

		for (int i = 0; i < nA; i++) {
			single = true;
			shared = -1;

			for (int j = 0; j < nB; j++) {
				int mV = pos(a[i], b[j]);
				if (mV != -1) {
					if (shared == -1) {
						shared = mV;
					} else {
						if (mV != shared) {
							single = false;
						}
					}
				}
			}
			if (!single) {
				printer.println(-1);
				printer.close();
				return;
			}
		}

		for (int j = 0; j < nB; j++) {
			single = true;
			shared = -1;

			for (int i = 0; i < nA; i++) {
				int mV = pos(a[i], b[j]);
				if (mV != -1) {
					if (shared == -1) {
						shared = mV;
					} else {
						if (mV != shared) {
							single = false;
						}
					}
				}
			}
			if (!single) {
				printer.println(-1);
				printer.close();
				return;
			}
		}
		printer.println(0);
		printer.close();
		return;
	}

	static int pos(int[] a, int[] b) {
		int match = 0;
		int mV = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (a[i] == b[j]) {
					match++;
					mV = a[i];
				}
			}
		}
		if (match != 1) {
			return -1;
		}
		return mV;
	}
}
