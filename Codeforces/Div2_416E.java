import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_416E {

	static final int LEFT = 0;
	static final int RIGHT = 1;

	int numR;
	int numC;

	int[][] type;

	public static void main(String[] args) throws IOException {
		new Div2_416E().execute();
	}

	int[] cCnt;
	int[][][] sides;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numR = Integer.parseInt(inputData.nextToken());
		numC = Integer.parseInt(inputData.nextToken());

		int numQ = Integer.parseInt(inputData.nextToken());

		type = new int[numR + 1][numC + 1];

		for (int i = 1; i <= numR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 1; j <= numC; j++) {
				type[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		sides = new int[4 * numC][2][];
		cCnt = new int[4 * numC];

		build(1, 1, numC);

		while (numQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			printer.println(query(1, 1, numC, l, r).cCnt);
		}
		printer.close();
	}

	int unused = 1;

	void build(int nI, int cL, int cR) {
		if (cL == cR) {
			int[] curInf = sides[nI][LEFT] = sides[nI][RIGHT] = new int[numR + 1];

			curInf[1] = unused++;
			int cnt = 1;

			for (int i = 2; i <= numR; i++) {
				if (type[i - 1][cL] == type[i][cL]) {
					curInf[i] = curInf[i - 1];
				} else {
					curInf[i] = unused++;
					cnt++;
				}
			}
			cCnt[nI] = cnt;
		} else {
			int mid = (cL + cR) >> 1;
			build(nI << 1, cL, mid);
			build((nI << 1) + 1, mid + 1, cR);

			int[][] lSides = sides[nI << 1];
			int[][] rSides = sides[(nI << 1) + 1];

			int nC = cCnt[nI << 1] + cCnt[(nI << 1) + 1];

			int[][] nSides = sides[nI] = new int[][] { copy(lSides[LEFT]), copy(rSides[RIGHT]) };

			int[] lRight = copy(lSides[RIGHT]);
			int[] rLeft = copy(rSides[LEFT]);

			for (int i = 1; i <= numR; i++) {
				int pComp = lRight[i];
				int cComp = rLeft[i];

				if (type[i][mid] == type[i][mid + 1] && pComp != cComp) {
					nC--;
					for (int j = 1; j <= numR; j++) {
						if (rLeft[j] == cComp) {
							rLeft[j] = pComp;
						}
						if (lRight[j] == cComp) {
							lRight[j] = pComp;
						}
						if (nSides[RIGHT][j] == cComp) {
							nSides[RIGHT][j] = pComp;
						}
						if (nSides[LEFT][j] == cComp) {
							nSides[LEFT][j] = pComp;
						}
					}
				}
			}
			cCnt[nI] = nC;
		}
	}

	Result query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return null;
		}

		if (qL <= cL && cR <= qR) {
			return new Result(sides[nI], cCnt[nI]);
		}

		int mid = (cL + cR) / 2;

		Result lRes = query(nI << 1, cL, mid, qL, qR);
		Result rRes = query((nI << 1) + 1, mid + 1, cR, qL, qR);

		if (lRes == null) {
			return rRes;
		}
		if (rRes == null) {
			return lRes;
		}

		Result fRes = new Result(new int[][] { copy(lRes.sides[LEFT]), copy(rRes.sides[RIGHT]) },
				lRes.cCnt + rRes.cCnt);

		int[] lRight = copy(lRes.sides[RIGHT]);
		int[] rLeft = copy(rRes.sides[LEFT]);

		for (int i = 1; i <= numR; i++) {
			int pComp = lRight[i];
			int cComp = rLeft[i];
			if (type[i][mid] == type[i][mid + 1] && pComp != cComp) {
				fRes.cCnt--;
				for (int j = 1; j <= numR; j++) {
					if (rLeft[j] == cComp) {
						rLeft[j] = pComp;
					}
					if (lRight[j] == cComp) {
						lRight[j] = pComp;
					}
					if (fRes.sides[RIGHT][j] == cComp) {
						fRes.sides[RIGHT][j] = pComp;
					}
					if (fRes.sides[LEFT][j] == cComp) {
						fRes.sides[LEFT][j] = pComp;
					}
				}
			}
		}
		return fRes;
	}

	int[] copy(int[] orig) {
		int[] copy = new int[orig.length];
		System.arraycopy(orig, 0, copy, 0, orig.length);
		return copy;
	}

	class Result {
		int[][] sides;
		int cCnt = 0;

		Result(int[][] a, int b) {
			sides = a;
			cCnt = b;
		}
	}

}
