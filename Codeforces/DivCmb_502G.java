import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DivCmb_502G implements Runnable {

	public static void main(String[] args) throws IOException {
		new Thread(null, new DivCmb_502G(), "yay", 1 << 26).start();
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			nV = Integer.parseInt(inputData.nextToken());
			int nQ = Integer.parseInt(inputData.nextToken());
			root = 1;

			chldn = new ArrayList[nV + 1];
			for (int i = 1; i <= nV; i++) {
				chldn[i] = new ArrayList<>();
			}
			par = new int[nV + 1];
			par[root] = -1;

			inputData = new StringTokenizer(reader.readLine());
			for (int i = 2; i <= nV; i++) {
				int p = Integer.parseInt(inputData.nextToken());
				par[i] = p;
				chldn[p].add(i);
			}

			init();

			for (int cQ = 1; cQ <= nQ; cQ++) {
				inputData = new StringTokenizer(reader.readLine());
				int t = Integer.parseInt(inputData.nextToken());
				int cV = Integer.parseInt(inputData.nextToken());
				if (t == 1) {
					mark(cV);
				} else if (t == 2) {
					st_Clear(cV);
				} else {
					Data cData = rP_Query(cV);
					if (cData.def > 0) {
						printer.println("black");
					} else {
						printer.println("white");
					}
				}
			}
			printer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Data {
		int gap;
		int def;

		Data(int gap, int def) {
			this.gap = gap;
			this.def = def;
		}

		public String toString() {
			return "(gap: " + gap + ", def: " + def + ")";
		}

	}

	Data merge(Data d1, Data d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		if (d1.def - 1 <= d2.gap) {
			return new Data(d1.gap + d2.gap - (d1.def - 1), d2.def);
		} else {
			return new Data(d1.gap, d2.def + d1.def - 1 - d2.gap);
		}
	}

	int nV;

	int root;

	ArrayList<Integer>[] chldn;
	int[] par;

	int[] heavyC;
	int[] chainS;

	LPSegTree tree;
	int[] tInd;
	int[] stEnd;

	// returns vCount
	int initDFS(int cV) {
		int cDeg = 1;
		int mADeg = 0;
		heavyC[cV] = -1;

		for (int aV : chldn[cV]) {
			int aDeg = initDFS(aV);
			if (aDeg > mADeg) {
				mADeg = aDeg;
				heavyC[cV] = aV;
			}
			cDeg += aDeg;
		}
		return cDeg;
	}

	int nTInd = 1;

	void cODFS(int cV) {
		tInd[cV] = nTInd++;
		int cHeavyC = heavyC[cV];

		if (cHeavyC != -1) {
			chainS[cHeavyC] = chainS[cV];
			cODFS(cHeavyC);
		}

		for (int aV : chldn[cV]) {
			if (aV != cHeavyC) {
				chainS[aV] = aV;
				cODFS(aV);
			}
		}
		stEnd[cV] = nTInd - 1;
	}

	void init() {
		heavyC = new int[nV + 1];
		chainS = new int[nV + 1];

		tree = new LPSegTree(nV);
		tInd = new int[nV + 1];
		stEnd = new int[nV + 1];

		initDFS(root);

		chainS[root] = root;
		cODFS(root);
	}

	Data rP_Query(int cV) {
		Data tData = null;
		while (cV != -1) {
			tData = merge(tree.query(tInd[chainS[cV]], tInd[cV]), tData);
			cV = par[chainS[cV]];
		}
		return tData;
	}

	void st_Clear(int cV) {
		tree.clear(tInd[cV], stEnd[cV]);
		if (cV != root) {
			Data pData = rP_Query(par[cV]);
			tree.update(tInd[cV], new Data(pData.def == 0 ? 0 : pData.def - 1, 0));
		}
	}

	void mark(int cV) {
		Data pVal = tree.query(tInd[cV], tInd[cV]);
		tree.update(tInd[cV], new Data(pVal.gap, pVal.def + 1));
	}

	class LPSegTree {
		int nE;
		Data[] tree;
		boolean[] lazy;

		LPSegTree(int nE) {
			this.nE = nE;
			tree = new Data[nE * 4];
			lazy = new boolean[nE * 4];
			init(1, 1, nE);
		}

		void init(int nI, int cL, int cR) {
			if (cL == cR) {
				tree[nI] = new Data(0, 0);
			} else {
				int mid = (cL + cR) >> 1;
				init(nI * 2, cL, mid);
				init(nI * 2 + 1, mid + 1, cR);
				tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
			}
		}

		Data query(int qL, int qR) {
			return query(1, 1, nE, qL, qR);
		}

		Data query(int nI, int cL, int cR, int qL, int qR) {
			if (cR < qL || qR < cL) {
				return null;
			}

			pLazy(nI, cL, cR);

			if (qL <= cL && cR <= qR) {
				return tree[nI];
			}

			int mid = (cL + cR) >> 1;

			Data leftQ = query(nI * 2, cL, mid, qL, qR);
			Data rightQ = query(nI * 2 + 1, mid + 1, cR, qL, qR);

			return merge(leftQ, rightQ);
		}

		void update(int uI, Data nData) {
			update(1, 1, nE, uI, nData);
		}

		void update(int nI, int cL, int cR, int uI, Data nData) {
			pLazy(nI, cL, cR);

			if (cL == cR) {
				tree[nI] = nData;
			} else {
				int mid = (cL + cR) >> 1;
				if (uI <= mid) {
					update(nI * 2, cL, mid, uI, nData);
					pLazy(nI * 2 + 1, mid + 1, cR);
				} else {
					update(nI * 2 + 1, mid + 1, cR, uI, nData);
					pLazy(nI * 2, cL, mid);
				}
				tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
			}
		}

		void clear(int uL, int uR) {
			clear(1, 1, nE, uL, uR);
		}

		void clear(int nI, int cL, int cR, int uL, int uR) {
			// current node needs to have no lazy before returning because it will be used to compute tree[par]
			pLazy(nI, cL, cR);

			if (cR < uL || uR < cL) {
				return;
			}

			if (uL <= cL && cR <= uR) {
				tree[nI] = new Data(cR - cL, 0);
				if (cL != cR) {
					lazy[nI * 2] = true;
					lazy[nI * 2 + 1] = true;
				}
				return;
			}

			int mid = (cL + cR) >> 1;

			clear(nI * 2, cL, mid, uL, uR);
			clear(nI * 2 + 1, mid + 1, cR, uL, uR);
			tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
		}

		void pLazy(int nI, int cL, int cR) {
			if (lazy[nI]) {
				tree[nI] = new Data(cR - cL, 0);

				if (cL != cR) {
					lazy[nI * 2] = true;
					lazy[nI * 2 + 1] = true;
				}
				lazy[nI] = false;
			}
		}
	}
}