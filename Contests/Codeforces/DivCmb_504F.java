import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_504F {

	int nV;
	int nOE;
	int nUE;

	int[] dsPar;
	int[] dsRank;

	int fRoot(int cV) {
		return dsPar[cV] == cV ? cV : (dsPar[cV] = fRoot(dsPar[cV]));
	}

	public static void main(String[] args) throws IOException {
		new DivCmb_504F().execute();
	}

	void execute() throws IOException {
		// long sTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nOE = Integer.parseInt(inputData.nextToken());
		nUE = Integer.parseInt(inputData.nextToken());

		dsPar = new int[nV];
		dsRank = new int[nV];
		for (int i = 0; i < nV; i++) {
			dsPar[i] = i;
		}

		lSt = new int[nV];
		Arrays.fill(lSt, -1);
		eInd = new int[2 * (nOE + nUE)];
		nxt = new int[2 * (nOE + nUE)];
		crit = new boolean[2 * (nOE + nUE)];

		for (int i = 1; i <= nOE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			connect(a, b, true);

			int aR = fRoot(a);
			int bR = fRoot(b);
			if (aR != bR) {
				if (dsRank[aR] < dsRank[bR]) {
					dsPar[aR] = bR;
				} else if (dsRank[aR] == dsRank[bR]) {
					dsPar[aR] = bR;
					dsRank[bR]++;
				} else {
					dsPar[bR] = aR;
				}
			}
		}

		int[][] updates = new int[500_000][3];
		int nU = 0;

		for (int i = 1; i <= nUE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			int aR = fRoot(a);
			int bR = fRoot(b);
			if (aR != bR) {
				connect(a, b, false);

				if (Math.random() < 0.5) {
					dsPar[aR] = bR;
				} else {
					dsPar[bR] = aR;
				}
			} else {
				updates[nU][0] = a;
				updates[nU][1] = b;
				updates[nU][2] = c;
				nU++;
			}
		}

		init();

		for (int i = 0; i < nU; i++) {
			update(updates[i][0], updates[i][1], updates[i][2]);
		}

		output();

		printer.println(ans);

		printer.close();
	}

	int nE = 0;

	void connect(int a, int b, boolean c) {
		nxt[nE] = lSt[a];
		eInd[nE] = b;
		crit[nE] = c;
		lSt[a] = nE++;

		nxt[nE] = lSt[b];
		eInd[nE] = a;
		crit[nE] = c;
		lSt[b] = nE++;
	}

	long ans = 0;

	int[] lSt;
	int[] eInd;
	int[] nxt;
	boolean[] crit;

	int[] par;
	int[] depth;
	int[] heavyC;
	int[] chainS;

	int[] tInd;

	boolean[] critV;

	int[] tDef;

	// returns vCount
	int initDFS(int cV) {
		int cDeg = 1;
		int mADeg = 0;
		heavyC[cV] = -1;

		int aI = lSt[cV];
		while (aI != -1) {
			int aV = eInd[aI];
			boolean cCV = crit[aI];
			if (aV != par[cV]) {
				par[aV] = cV;
				depth[aV] = depth[cV] + 1;
				critV[aV] = cCV;

				int aDeg = initDFS(aV);
				if (aDeg > mADeg) {
					mADeg = aDeg;
					heavyC[cV] = aV;
				}
				cDeg += aDeg;
			}
			aI = nxt[aI];
		}
		return cDeg;
	}

	void init() {
		par = new int[nV];
		Arrays.fill(par, -1);
		depth = new int[nV];
		heavyC = new int[nV];
		chainS = new int[nV];

		tInd = new int[nV];

		critV = new boolean[nV];
		tDef = new int[nV];

		for (int i = 0; i < nV; i++) {
			if (par[i] == -1) {
				par[i] = -1;
				initDFS(i);
			}
		}

		int nTInd = 0;
		for (int cV = 0; cV < nV; cV++) {
			if (par[cV] == -1 || heavyC[par[cV]] != cV) {
				for (int cMem = cV; cMem != -1; cMem = heavyC[cMem]) {
					chainS[cMem] = cV;
					tInd[cMem] = nTInd++;
				}
			}
		}

		for (int cV = 0; cV < nV; cV++) {
			if (critV[cV]) {
				tDef[tInd[cV]] = Integer.MAX_VALUE;
			}
		}

		initTable(nV);
	}

	void update(int v1, int v2, int bound) {
		// if they're tied, v2 will still update
		while (chainS[v1] != chainS[v2]) {
			if (depth[chainS[v1]] > depth[chainS[v2]]) {
				updateTable(tInd[chainS[v1]], tInd[v1], bound);
				v1 = par[chainS[v1]];
			} else {
				updateTable(tInd[chainS[v2]], tInd[v2], bound);
				v2 = par[chainS[v2]];
			}
		}

		if (tInd[v1] < tInd[v2]) {
			updateTable(tInd[v1] + 1, tInd[v2], bound);
		} else {
			updateTable(tInd[v2] + 1, tInd[v1], bound);
		}
	}

	int[][] table = new int[19][500_000];

	void initTable(int nE) {
		for (int i = 1; i < 19; i++) {
			Arrays.fill(table[i], Integer.MAX_VALUE);
		}

		Arrays.fill(table[0], 0);

		for (int i = 1; i < tDef.length; i++) {
			table[0][i] = tDef[i];
		}
	}

	void updateTable(int l, int r, int bnd) {
		if (l <= r) {
			int len = r - l + 1;
			int size = 31 - Integer.numberOfLeadingZeros(len);
			int cover = 1 << size;
			table[size][l] = Math.min(table[size][l], bnd);
			table[size][r - cover + 1] = Math.min(table[size][r - cover + 1], bnd);
		}
	}

	void output() {
		for (int size = 18; size > 0; size--) {
			for (int i = 0; i < 500_000; i++) {
				table[size - 1][i] = Math.min(table[size - 1][i], table[size][i]);
				int subLen = 1 << (size - 1);
				if (i + subLen < 500_000) {
					table[size - 1][i + subLen] = Math.min(table[size - 1][i + subLen], table[size][i]);
				}
			}
		}

		for (int i = 0; i < 500_000; i++) {
			if (ans != -1) {
				if (table[0][i] == Integer.MAX_VALUE) {
					ans = -1;
				} else {
					ans += table[0][i];
				}
			}
		}
	}
}