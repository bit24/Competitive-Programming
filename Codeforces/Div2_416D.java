import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_416D {

	static final int OK = 0;
	static final int NOK = 1;
	static final int FIN = 2;
	static final int BLK = 3;

	static final int[] rMod = new int[] { -1, 1, 0, 0 };
	static final int[] cMod = new int[] { 0, 0, -1, 1 };
	static final char[] std = new char[] { 'U', 'D', 'L', 'R' };

	public static void main(String[] args) throws IOException {
		new Div2_416D().execute();
	}

	int numR;
	int numC;

	BufferedReader reader;
	PrintWriter printer;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numR = Integer.parseInt(inputData.nextToken());
		numC = Integer.parseInt(inputData.nextToken());

		int[][] grid = new int[numR + 2][numC + 2];

		Coor fin = null;

		for (int i = 1; i <= numR; i++) {
			String nLine = reader.readLine();
			for (int j = 1; j <= numC; j++) {
				char nChar = nLine.charAt(j - 1);
				if (nChar == '.') {
					grid[i][j] = OK;
				} else if (nChar == '*') {
					grid[i][j] = NOK;
				} else {
					grid[i][j] = FIN;
					fin = new Coor(i, j);
				}
			}
		}

		ArrayDeque<Coor> sQueue = new ArrayDeque<Coor>();
		sQueue.add(fin);

		int[][] prev = new int[numR + 2][numC + 2];

		for (int i = 0; i <= numR + 1; i++) {
			Arrays.fill(prev[i], -1);
		}

		while (!sQueue.isEmpty()) {
			Coor cCoor = sQueue.remove();
			for (int i = 0; i < 4; i++) {
				int nR = cCoor.r + rMod[i];
				int nC = cCoor.c + cMod[i];

				if (grid[nR][nC] != NOK && 1 <= nR && nR <= numR && 1 <= nC && nC <= numC && prev[nR][nC] == -1) {
					prev[nR][nC] = i ^ 1;
					sQueue.add(new Coor(nR, nC));
				}
			}
		}

		char[] key = new char[] { 'n', 'n', 'n', 'n' };

		int cR = 1;
		int cC = 1;

		while (cR != fin.r || cC != fin.c) {
			int nxt = prev[cR][cC];

			while (key[nxt] == 'n') {
				printer.println(std[nxt]);
				printer.flush();
				inputData = new StringTokenizer(reader.readLine());
				int nR = Integer.parseInt(inputData.nextToken());
				int nC = Integer.parseInt(inputData.nextToken());

				if (nR == fin.r && nC == fin.c) {
					return;
				}

				if (nR != cR || nC != cC) {
					key[nxt] = std[nxt];
					key[nxt ^ 1] = std[nxt ^ 1];
					cR = nR;
					cC = nC;
					nxt = prev[cR][cC];
				} else {
					key[nxt] = std[nxt ^ 1];
					key[nxt ^ 1] = std[nxt];
				}
			}

			printer.println(key[nxt]);
			printer.flush();
			reader.readLine();

			cR += rMod[nxt];
			cC += cMod[nxt];
		}

	}

	class Coor {
		int r;
		int c;

		Coor(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

}
