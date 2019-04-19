import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class AIM_5H {

	static int[] pFact = new int[5_032_108];

	public static void main(String[] args) throws IOException {
		for (int i = 2; i < 5_032_108; i++) {
			if (pFact[i] == 0) {
				for (int m = i; m < 5_032_108; m += i) {
					if (pFact[m] == 0) {
						pFact[m] = i;
					}
				}
			}
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		int[] e = new int[nE + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
			int temp = e[i];

			while (temp != 1) {
				int cPFact = pFact[temp];
				temp /= cPFact;
				if (temp % cPFact == 0) {
					temp /= cPFact;
					e[i] /= cPFact * cPFact;
				}
			}
		}

		ArrayList<Integer>[] queries = new ArrayList[nE + 1];
		for (int i = 1; i <= nE; i++) {
			queries[i] = new ArrayList<>();
		}
		int[] qLeft = new int[nQ + 1];

		for (int i = 1; i <= nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int qL = Integer.parseInt(inputData.nextToken());
			int qR = Integer.parseInt(inputData.nextToken());
			qLeft[i] = qL;
			queries[qR].add(i);
		}

		int[][] map = new int[8][5_032_108];

		initTree();

		int[] p = new int[7];
		int[] sig = new int[15];

		int[] qAns = new int[nQ + 1];

		for (int cR = 1; cR <= nE; cR++) {
			int cNum = e[cR];
			int nP = 0;

			while (cNum != 1) {
				p[nP++] = pFact[cNum];
				cNum /= pFact[cNum];
			}

			Arrays.fill(sig, 0);

			for (int bSet = 0; bSet < (1 << nP); bSet++) {
				int bCnt = 0;
				int num = 1;

				for (int i = 0; i < nP; i++) {
					if ((bSet & (1 << i)) != 0) {
						bCnt++;
						num *= p[i];
					}
				}

				for (int oD = 0; oD <= 7; oD++) {
					int rMost = map[oD][num];
					if (rMost != 0) {
						int tDif = oD + nP - bCnt;
						sig[tDif] = Math.max(sig[tDif], rMost);
					}
				}
			}

			for (int i = 0; i < 15; i++) {
				if (sig[i] != 0 && (i == 0 || sig[i] > sig[i - 1])) {
					update(1, sig[i], i);
				}
				if (i + 1 < 15) {
					sig[i + 1] = Math.max(sig[i + 1], sig[i]);
				}
			}

			for (int cQ : queries[cR]) {
				int cL = qLeft[cQ];
				qAns[cQ] = query(cL, cR);
			}

			for (int bSet = 0; bSet < (1 << nP); bSet++) {
				int bCnt = 0;
				int num = 1;

				for (int i = 0; i < nP; i++) {
					if ((bSet & (1 << i)) != 0) {
						bCnt++;
						num *= p[i];
					}
				}

				map[nP - bCnt][num] = cR;
			}
		}
		for (int i = 1; i <= nQ; i++) {
			printer.println(qAns[i]);
		}
		printer.close();
	}

	static int nE;
	static int[] tree;
	static int[] lazy;

	static void initTree() {
		tree = new int[nE * 4];
		lazy = new int[nE * 4];
		Arrays.fill(tree, Integer.MAX_VALUE);
		Arrays.fill(lazy, Integer.MAX_VALUE);
	}

	static int query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, nE, qL, qR);
	}

	static int query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return Integer.MAX_VALUE;
		}

		if (lazy[nI] != Integer.MAX_VALUE) {
			tree[nI] = Math.min(tree[nI], lazy[nI]);
			if (cL != cR) {
				// internal node
				lazy[nI * 2] = Math.min(lazy[nI * 2], lazy[nI]);
				lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], lazy[nI]);
			}
			lazy[nI] = Integer.MAX_VALUE;
		}

		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int mid = (cL + cR) >> 1;

		int leftQ = query(nI * 2, cL, mid, qL, qR);
		int rightQ = query(nI * 2 + 1, mid + 1, cR, qL, qR);

		return Math.min(leftQ, rightQ);
	}

	static void update(int uL, int uR, int delta) {
		if (uL <= uR) {
			update(1, 1, nE, uL, uR, delta);
		}
	}

	static void update(int nI, int cL, int cR, int uL, int uR, int bnd) {
		// current node needs to have no lazy before returning because it will be used to compute tree[par]
		if (lazy[nI] != Integer.MAX_VALUE) {
			tree[nI] = Math.min(tree[nI], lazy[nI]);
			if (cL != cR) {
				// internal node
				lazy[nI * 2] = Math.min(lazy[nI * 2], lazy[nI]);
				lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], lazy[nI]);
			}
			lazy[nI] = Integer.MAX_VALUE;
		}
		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			tree[nI] = Math.min(tree[nI], bnd);
			if (cL != cR) {
				lazy[nI * 2] = Math.min(lazy[nI * 2], bnd);
				lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], bnd);
			}
			return;
		}

		int mid = (cL + cR) >> 1;

		update(nI * 2, cL, mid, uL, uR, bnd);
		update(nI * 2 + 1, mid + 1, cR, uL, uR, bnd);

		tree[nI] = Math.min(tree[nI * 2], tree[nI * 2 + 1]);
	}
}
