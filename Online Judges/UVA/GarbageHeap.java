import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		ArrayList<Long> out = new ArrayList<Long>();
		int numProblems = reader.nextInt();

		for (int i = 0; i < numProblems; i++) {
			int A = reader.nextInt();
			int B = reader.nextInt();
			int C = reader.nextInt();
			long[][][] value = new long[A][B][C];
			long best = Long.MIN_VALUE;

			for (int a = 0; a < A; a++) {
				for (int b = 0; b < B; b++) {
					for (int c = 0; c < C; c++) {
						value[a][b][c] = reader.nextLong();
						best = Long.max(best, value[a][b][c]);
					}
				}
			}

			for (int sMatrix = 0; sMatrix < A; sMatrix++) {
				long[][] cMatrix = new long[B][C];
				for (int eMatrix = sMatrix; eMatrix < A; eMatrix++) {
					for (int b = 0; b < B; b++) {
						for (int c = 0; c < C; c++) {
							cMatrix[b][c] += value[eMatrix][b][c];
						}
					}

					for (int sColumn = 0; sColumn < B; sColumn++) {
						long[] cColumn = new long[C];
						for (int eColumn = sColumn; eColumn < B; eColumn++) {
							for (int c = 0; c < C; c++) {
								cColumn[c] += cMatrix[eColumn][c];
							}

							long cValue = 0;
							for (int c = 0; c < C; c++) {
								if (cValue < 0) {
									cValue = cColumn[c];
								} else {
									cValue += cColumn[c];
								}
								best = Long.max(best, cValue);
							}
						}
					}

				}
			}
			out.add(best);
			best = Long.MIN_VALUE;

		}

		reader.close();

		System.out.println(out.get(0));
		for (int i = 1; i < out.size(); i++) {
			printer.println();
			printer.println(out.get(i));
		}
		printer.close();

	}

}
