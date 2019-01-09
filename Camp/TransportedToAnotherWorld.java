import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TransportedToAnotherWorld {

	static int nR;
	static int nC;
	static int nQ;

	static boolean[][] on;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nC = Integer.parseInt(inputData.nextToken());
		nR = Integer.parseInt(inputData.nextToken());
		nQ = Integer.parseInt(inputData.nextToken());

		on = new boolean[nR][nC + 1];

		for (int j = 1; j <= nC; j++) {
			String line = reader.readLine();
			for (int i = 0; i < nR; i++) {
				on[i][j] = line.charAt(i) == '.';
			}
		}

		tree = new int[4 * nC][][];
		temp = new int[4 * nR][4 * nR];

		bAll(1, 1, nC);

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("T")) {
				int cI = Integer.parseInt(inputData.nextToken());
				int rI = Integer.parseInt(inputData.nextToken()) - 1;
				on[rI][cI] = !on[rI][cI];
				update(1, 1, nC, cI);
			} else {
				int cI1 = Integer.parseInt(inputData.nextToken());
				int rI1 = Integer.parseInt(inputData.nextToken()) - 1;

				int cI2 = Integer.parseInt(inputData.nextToken());
				int rI2 = Integer.parseInt(inputData.nextToken()) - 1;

				if (cI1 > cI2) {
					int t = cI1;
					cI1 = cI2;
					cI2 = t;

					t = rI1;
					rI1 = rI2;
					rI2 = t;
				}

				int[][] cBlock = query(1, 1, nC, cI1, cI2);

				int[][] lBlock = query(1, 1, nC, 1, cI1);
				int[][] rBlock = query(1, 1, nC, cI2, nC);

				int ans = computeAns(lBlock, cBlock, rBlock, rI1, rI2);
				if (ans >= Integer.MAX_VALUE / 4) {
					printer.println(-1);
				} else {
					printer.println(ans);
				}
			}
		}
		printer.close();
	}

	static int computeAns(int[][] l, int[][] c, int[][] r, int rI1, int rI2) {
		for (int[] a : temp) {
			Arrays.fill(a, Integer.MAX_VALUE / 4);
		}

		for (int i = 0; i < temp.length; i++) {
			temp[i][i] = 0;
		}

		for (int i = 0; i < 2 * nR; i++) {
			for (int j = 0; j < 2 * nR; j++) {
				temp[i][j] = Math.min(temp[i][j], l[i][j]);
				temp[i + nR][j + nR] = Math.min(temp[i + nR][j + nR], c[i][j]);
				temp[i + 2 * nR][j + 2 * nR] = Math.min(temp[i + 2 * nR][j + 2 * nR], r[i][j]);
			}
		}

		for (int i = 0; i < 4 * nR; i++) {
			for (int s = 0; s < 4 * nR; s++) {
				for (int e = 0; e < 4 * nR; e++) {
					int nCost = temp[s][i] + temp[i][e];
					if (nCost < temp[s][e]) {
						temp[s][e] = nCost;
					}
				}
			}
		}
		return temp[rI1 + nR][rI2 + 2 * nR];
	}

	static int[][][] tree;

	static int[][] temp;

	static void update(int nI, int cL, int cR, int uI) {
		if (cL == cR) {
			tree[nI] = bRow(uI);
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI);
			}
			tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	static int[][] query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return null;
		}
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int mid = (cL + cR) >> 1;
		return merge(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	static void bAll(int nI, int cL, int cR) {
		if (cL == cR) {
			tree[nI] = bRow(cL);
		} else {
			int mid = (cL + cR) >> 1;
			bAll(nI * 2, cL, mid);
			bAll(nI * 2 + 1, mid + 1, cR);
			tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	static int[][] bRow(int cI) {
		int[][] res = new int[2 * nR][2 * nR];

		for (int[] i : res) {
			Arrays.fill(i, Integer.MAX_VALUE / 4);
		}

		for (int i = 0; i < nR; i++) {
			if (on[i][cI]) {
				res[i][i] = 0;
				res[i + nR][i] = 0;
				res[i][i + nR] = 0;
				res[i + nR][i + nR] = 0;

				for (int pI = i - 1; pI >= 0 && on[pI][cI]; pI--) {
					res[pI][i] = i - pI;
					res[pI + nR][i] = i - pI;
					res[pI][i + nR] = i - pI;
					res[pI + nR][i + nR] = i - pI;

					res[i][pI] = i - pI;
					res[i + nR][pI] = i - pI;
					res[i][pI + nR] = i - pI;
					res[i + nR][pI + nR] = i - pI;
				}
			}
		}
		return res;
	}

	static int[][] merge(int[][] a, int[][] b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		for (int[] i : temp) {
			Arrays.fill(i, Integer.MAX_VALUE / 4);
		}

		for (int i = 0; i < temp.length; i++) {
			temp[i][i] = 0;
		}

		for (int i = 0; i < 2 * nR; i++) {
			for (int j = 0; j < 2 * nR; j++) {
				temp[i][j] = a[i][j];
				temp[i + 2 * nR][j + 2 * nR] = b[i][j];
			}
		}

		for (int i = nR; i < 2 * nR; i++) {
			temp[i][i + nR] = 1;
			temp[i + nR][i] = 1;
		}

		for (int i = 0; i < 4 * nR; i++) {
			for (int s = 0; s < 4 * nR; s++) {
				for (int e = 0; e < 4 * nR; e++) {
					int nCost = temp[s][i] + temp[i][e];
					if (nCost < temp[s][e]) {
						temp[s][e] = nCost;
					}
				}
			}
		}

		int[][] res = new int[2 * nR][2 * nR];

		for (int i = 0; i < 2 * nR; i++) {
			for (int j = 0; j < 2 * nR; j++) {
				int tI = i < nR ? i : i + 2 * nR;
				int tJ = j < nR ? j : j + 2 * nR;
				res[i][j] = temp[tI][tJ];
			}
		}
		return res;
	}

}
