import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Seating {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("seating.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("seating.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		int numQ = Integer.parseInt(inputData.nextToken());
		lEmpty = new int[4 * numE];
		rEmpty = new int[4 * numE];
		mEmpty = new int[4 * numE];
		lazy = new int[4 * numE];
		buildTree(1, 1, numE);

		int numT = 0;
		while (numQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("A")) {
				int req = Integer.parseInt(inputData.nextToken());
				int ind = find(1, 1, numE, req);
				if (ind == -1) {
					numT++;
				} else {
					update(1, 1, numE, ind, ind + req - 1, FILL);
				}
			} else {
				int l = Integer.parseInt(inputData.nextToken());
				int r = Integer.parseInt(inputData.nextToken());
				update(1, 1, numE, l, r, CLEAR);
			}
		}
		reader.close();
		printer.println(numT);
		printer.close();
	}

	static final int NONE = 0;
	static final int FILL = 1;
	static final int CLEAR = 2;

	static int numE;
	static int[] lEmpty;
	static int[] rEmpty;
	static int[] mEmpty;
	static int[] lazy;

	static void buildTree(int nI, int rL, int rR) {
		if (rL != rR) {
			int mid = (rL + rR) / 2;
			buildTree(nI * 2, rL, mid);
			buildTree(nI * 2 + 1, mid + 1, rR);
		}
		lEmpty[nI] = rEmpty[nI] = mEmpty[nI] = rR - rL + 1;
	}

	static int find(int nI, int cL, int cR, int req) {
		pushLazy(nI, cL, cR);
		if (cL != cR) {
			int mid = (cL + cR) / 2;
			pushLazy(nI * 2, cL, mid);
			pushLazy(nI * 2 + 1, mid + 1, cR);

			if (mEmpty[nI * 2] >= req) {
				return find(nI * 2, cL, mid, req);
			}
			if (rEmpty[nI * 2] + lEmpty[nI * 2 + 1] >= req) {
				return mid - rEmpty[nI * 2] + 1;
			}
			if (mEmpty[nI * 2 + 1] >= req) {
				return find(nI * 2 + 1, mid + 1, cR, req);
			}
			return -1;
		}
		return req == 1 ? cL : -1;
	}

	static void update(int nI, int cL, int cR, int uL, int uR, int op) {
		pushLazy(nI, cL, cR);
		if (cL != cR) {
			int mid = (cL + cR) / 2;
			if (uL <= cL && cR <= uR) {
				lazy[nI] = op;
				return;
			}
			if (uL <= mid) {
				update(nI * 2, cL, mid, uL, Math.min(uR, mid), op);
			}
			if (mid + 1 <= uR) {
				update(nI * 2 + 1, mid + 1, cR, Math.max(uL, mid + 1), uR, op);
			}
			pushLazy(nI * 2, cL, mid);
			pushLazy(nI * 2 + 1, mid + 1, cR);
			lEmpty[nI] = lEmpty[nI * 2] + (lEmpty[nI * 2] == mid - cL + 1 ? lEmpty[nI * 2 + 1] : 0);
			rEmpty[nI] = rEmpty[nI * 2 + 1] + (rEmpty[nI * 2 + 1] == cR - mid ? rEmpty[nI * 2] : 0);
			mEmpty[nI] = Math.max(rEmpty[nI * 2] + lEmpty[nI * 2 + 1], Math.max(mEmpty[nI * 2], mEmpty[nI * 2 + 1]));
		} else {
			lEmpty[nI] = rEmpty[nI] = mEmpty[nI] = op == CLEAR ? 1 : 0;
		}
	}

	static void pushLazy(int nI, int cL, int cR) {
		if (lazy[nI] != NONE) {
			if (lazy[nI] == CLEAR) {
				lEmpty[nI] = rEmpty[nI] = mEmpty[nI] = cR - cL + 1;
			} else {
				lEmpty[nI] = rEmpty[nI] = mEmpty[nI] = 0;
			}
			if (cL != cR) {
				// internal node
				lazy[nI * 2] = lazy[nI];
				lazy[nI * 2 + 1] = lazy[nI];
			}
			lazy[nI] = NONE;
		}
	}
}
