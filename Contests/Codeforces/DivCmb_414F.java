import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_414F {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());
		long[][] init = new long[nE + 1][10];

		inputData = new StringTokenizer(reader.readLine());

		for (int i = 1; i <= nE; i++) {
			conv(init[i], Integer.parseInt(inputData.nextToken()));
		}

		init(init);

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			String t = inputData.nextToken();
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());

			if (t.equals("1")) {
				int a = Integer.parseInt(inputData.nextToken());
				int b = Integer.parseInt(inputData.nextToken());
				if(a != b) {
					update(l, r, a, b);
				}
			} else {
				printer.println(query(l, r));
			}
		}
		printer.close();
	}

	static void conv(long[] res, int a) {
		int cV = 1;
		while (a > 0) {
			res[a % 10] += cV;
			a /= 10;
			cV *= 10;
		}
	}

	static int nE;
	static long[][] tree;
	static int[][] lazy;

	static void init(long[][] init) {
		tree = new long[init.length * 4][10];
		lazy = new int[init.length * 4][10];
		buildTree(init, 1, 1, nE);
	}

	static void buildTree(long[][] init, int nI, int rL, int rR) {
		if (rL == rR) {
			// leaf node
			for (int i = 0; i < 10; i++) {
				tree[nI][i] = init[rL][i];
				lazy[nI][i] = i;
			}
		} else {
			// internal node
			int mid = (rL + rR) / 2;
			buildTree(init, nI * 2, rL, mid);
			buildTree(init, nI * 2 + 1, mid + 1, rR);
			for (int i = 0; i < 10; i++) {
				tree[nI][i] = tree[nI * 2][i] + tree[nI * 2 + 1][i];
				lazy[nI][i] = i;
			}
		}
	}

	static long query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, nE, qL, qR);
	}

	static long query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}
		pushLazy(nI, cL, cR);

		if (qL <= cL && cR <= qR) {
			long sum = 0;
			for (int i = 1; i < 10; i++) {
				sum += tree[nI][i] * i;
			}
			return sum;
		}

		int mid = (cL + cR) >> 1;

		long leftQ = query(nI * 2, cL, mid, qL, qR);
		long rightQ = query(nI * 2 + 1, mid + 1, cR, qL, qR);

		return leftQ + rightQ;
	}

	static void update(int uL, int uR, int a, int b) {
		if (uL <= uR) {
			update(1, 1, nE, uL, uR, a, b);
		}
	}

	static void update(int nI, int cL, int cR, int uL, int uR, int a, int b) {
		// current node needs to have no lazy before returning because it will be used to compute tree[par]
		pushLazy(nI, cL, cR);
		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			tree[nI][b] += tree[nI][a];
			tree[nI][a] = 0;

			if (cL != cR) {
				int[] chLazy = lazy[nI * 2];
				for (int i = 0; i < 10; i++) {
					if (chLazy[i] == a) {
						chLazy[i] = b;
					}
				}
				chLazy = lazy[nI * 2 + 1];
				for (int i = 0; i < 10; i++) {
					if (chLazy[i] == a) {
						chLazy[i] = b;
					}
				}
			}

			return;
		}

		int mid = (cL + cR) >> 1;

		update(nI * 2, cL, mid, uL, uR, a, b);
		update(nI * 2 + 1, mid + 1, cR, uL, uR, a, b);

		for (int i = 0; i < 10; i++) {
			tree[nI][i] = tree[nI * 2][i] + tree[nI * 2 + 1][i];
		}
	}

	static long[] aux = new long[10];

	static void pushLazy(int nI, int cL, int cR) {
		long[] cTree = tree[nI];
		int[] cLazy = lazy[nI];

		for (int i = 0; i < 10; i++) {
			aux[i] = cTree[i];
			cTree[i] = 0;
		}
		for (int i = 0; i < 10; i++) {
			cTree[cLazy[i]] += aux[i];
		}

		if (cL != cR) {
			int[] chLazy = lazy[nI * 2];
			for (int i = 0; i < 10; i++) {
				chLazy[i] = cLazy[chLazy[i]];
			}
			chLazy = lazy[nI * 2 + 1];
			for (int i = 0; i < 10; i++) {
				chLazy[i] = cLazy[chLazy[i]];
			}
		}

		for (int i = 0; i < 10; i++) {
			cLazy[i] = i;
		}
	}
}
