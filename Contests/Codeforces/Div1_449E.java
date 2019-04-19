import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_449E {

	static final int bSize = 316;
	static final int mE = 100_500;

	static int nE;
	static int nB;
	static int aSize;

	static int[] array;
	static int[] bArray;

	static int[][] bRoot;
	static int[][] bCnt;

	static int[] bSub;
	static int[] bMax;

	static int[] par;

	static int fRoot(int i) {
		while (par[i] != i) {
			i = par[i] = par[par[i]];
		}
		return i;
	}

	static void block(int bI) {
		bMax[bI] = 0;
		int bL = bSize * bI;
		int bR = bL + bSize - 1;

		for (int i = bL; i <= bR; i++) {
			int cE = array[i];
			if (bMax[bI] < cE) {
				bMax[bI] = cE;
			}
			if (bRoot[bI][cE] == -1) {
				bRoot[bI][cE] = i;
				par[i] = i;
				bArray[i] = cE;
			} else {
				par[i] = bRoot[bI][cE];
			}
			bCnt[bI][cE]++;
		}
	}

	static void deblock(int bI) {
		int bL = bSize * bI;
		int bR = bL + bSize - 1;

		for (int i = bL; i <= bR; i++) {
			array[i] = bArray[fRoot(i)];
		}
		for (int i = bL; i <= bR; i++) {
			bRoot[bI][array[i]] = -1;
			bCnt[bI][array[i]] = 0;
			par[i] = -1;
			array[i] -= bSub[bI];
		}
		bSub[bI] = 0;
	}

	static void merge(int bI, int mrgI, int mrgItI) {
		if (bRoot[bI][mrgI] == -1) {
			return;
		}
		if (bRoot[bI][mrgItI] == -1) {
			bRoot[bI][mrgItI] = bRoot[bI][mrgI];
			bArray[bRoot[bI][mrgItI]] = mrgItI;
		} else {
			par[bRoot[bI][mrgI]] = bRoot[bI][mrgItI];
		}
		bRoot[bI][mrgI] = -1;
		bCnt[bI][mrgItI] += bCnt[bI][mrgI];
		bCnt[bI][mrgI] = 0;
	}

	static void update(int bI, int uVal) {
		int cBSub = bSub[bI];
		int cBMax = bMax[bI];

		if (uVal * 2 <= cBMax - cBSub) {
			for (int cV = cBSub + 1; cV - cBSub <= uVal; cV++) {
				merge(bI, cV, cV + uVal);
			}
			bSub[bI] += uVal;
		} else {
			for (int cV = cBMax; cV - cBSub > uVal; cV--) {
				merge(bI, cV, cV - uVal);
			}
			bMax[bI] = Math.min(cBMax, uVal + cBSub);
		}
	}

	static void update(int lI, int rI, int uVal) {
		if (lI / bSize == rI / bSize) {
			deblock(lI / bSize);
			for (int i = lI; i <= rI; i++) {
				if (array[i] > uVal) {
					array[i] -= uVal;
				}
			}
			block(lI / bSize);
		} else {
			deblock(lI / bSize);
			int bR = (lI / bSize) * bSize + bSize - 1;
			for (int i = lI; i <= bR; i++) {
				if (array[i] > uVal) {
					array[i] -= uVal;
				}
			}
			block(lI / bSize);

			deblock(rI / bSize);
			for (int i = (rI / bSize) * bSize; i <= rI; i++) {
				if (array[i] > uVal) {
					array[i] -= uVal;
				}
			}
			block(rI / bSize);

			bR = rI / bSize - 1;
			for (int bI = lI / bSize + 1; bI <= bR; bI++) {
				update(bI, uVal);
			}
		}
	}

	static int cnt(int lI, int rI, int cVal) {
		int cnt = 0;
		if (lI / bSize == rI / bSize) {
			deblock(lI / bSize);
			for (int i = lI; i <= rI; i++) {
				if (array[i] == cVal) {
					cnt++;
				}
			}
			block(lI / bSize);
		} else {
			deblock(lI / bSize);
			int bR = (lI / bSize) * bSize + bSize - 1;
			for (int i = lI; i <= bR; i++) {
				if (array[i] == cVal) {
					cnt++;
				}
			}
			block(lI / bSize);

			deblock(rI / bSize);
			for (int i = (rI / bSize) * bSize; i <= rI; i++) {
				if (array[i] == cVal) {
					cnt++;
				}
			}
			block(rI / bSize);

			bR = rI / bSize - 1;
			for (int i = lI / bSize + 1; i <= bR; i++) {
				if (cVal + bSub[i] <= mE) {
					cnt += bCnt[i][cVal + bSub[i]];
				}
			}
		}
		return cnt;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		nB = (nE - 1) / bSize + 1;
		aSize = nB * bSize;
		int nO = Integer.parseInt(inputData.nextToken());
		array = new int[aSize];
		bArray = new int[aSize];
		par = new int[aSize];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			array[i] = Integer.parseInt(inputData.nextToken());
			par[i] = i;
		}

		bRoot = new int[nB][mE + 1];
		bCnt = new int[nB][mE + 1];

		for (int i = 0; i < nB; i++) {
			for (int j = 0; j <= mE; j++) {
				bRoot[i][j] = -1;
			}
		}

		bSub = new int[nB];
		bMax = new int[nB];

		for (int i = 0; i < nB; i++) {
			block(i);
		}

		while (nO-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int op = Integer.parseInt(inputData.nextToken());
			int l = Integer.parseInt(inputData.nextToken()) - 1;
			int r = Integer.parseInt(inputData.nextToken()) - 1;
			int v = Integer.parseInt(inputData.nextToken());
			if (op == 1) {
				update(l, r, v);
			} else {
				printer.println(cnt(l, r, v));
			}
		}
		printer.close();
	}

}
