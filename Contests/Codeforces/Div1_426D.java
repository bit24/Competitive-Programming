import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_426D {

	int[] start;
	int[] ePt;
	int[] nxt;
	int[] color;
	int[] len;

	final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		new Div1_426D().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());

		start = new int[nV];
		Arrays.fill(start, -1);

		ePt = new int[nV * 2];
		nxt = new int[nV * 2];

		color = new int[nV * 2];
		len = new int[nV * 2];

		int nE = 0;
		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int u = Integer.parseInt(inputData.nextToken()) - 1;
			int v = Integer.parseInt(inputData.nextToken()) - 1;
			int cLen = Integer.parseInt(inputData.nextToken());
			int cCol = Integer.parseInt(inputData.nextToken());

			nxt[nE] = start[u];
			start[u] = nE;
			ePt[nE] = v;
			color[nE] = cCol;
			len[nE] = cLen;
			nE++;

			nxt[nE] = start[v];
			start[v] = nE;
			ePt[nE] = u;
			color[nE] = cCol;
			len[nE] = cLen;
			nE++;
		}

		OFST = nV + 5;
		OMAX = OFST + OFST;
		vCount = new int[nV];
		removed = new boolean[nV];

		prod = new long[OMAX + 1];
		Arrays.fill(prod, 1);
		cnt = new int[OMAX + 1];
		decompose(0);
		printer.println(fAns);
		printer.close();
	}

	long inv(long base) {
		return pow(base, MOD - 2);
	}

	long pow(long base, long exp) {
		base %= MOD;
		long cur = 1;
		while (exp > 0) {
			if ((exp & 1) == 1) {
				cur = cur * base % MOD;
			}
			base = base * base % MOD;
			exp >>= 1;
		}
		return cur;
	}

	long fAns = 1;

	int OFST;
	int OMAX;

	int[] vCount;

	int gSize = 0;

	boolean[] removed;

	static final int[] TRANS0 = new int[] { 0, 0 };
	static final int[] TRANS1 = new int[] { 1, -2 };
	static final int[] TRANS2 = new int[] { -2, 1 };

	void cPaths(int cV, int pV, int cSum, long cProd, int[] wt) {
		update(OFST + cSum, cProd);

		int aI = start[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && !removed[aV]) {
				cPaths(aV, cV, cSum + wt[color[aI]], cProd * len[aI] % MOD, wt);
			}
			aI = nxt[aI];
		}
	}

	long compAns(int cV, int pV, int cSum, long cProd, int[] wt) {
		Ret qRes = query(OFST - cSum + 1, OMAX);
		long ans = qRes.prod * pow(cProd, qRes.cnt) % MOD;

		int aI = start[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && !removed[aV]) {
				ans = ans * compAns(aV, cV, cSum + wt[color[aI]], cProd * len[aI] % MOD, wt) % MOD;
			}
			aI = nxt[aI];
		}
		return ans;
	}

	void decompose(int root) {
		fCount(root, -1);
		gSize = vCount[root];
		int centroid = fCentroid(root, -1);

		removed[centroid] = true;

		long aCnt = 1;

		update(OFST, 1);
		int aI = start[centroid];

		while (aI != -1) {
			int aV = ePt[aI];
			if (!removed[aV]) {
				aCnt = aCnt * compAns(aV, centroid, 1, len[aI], TRANS0) % MOD;
				cPaths(aV, centroid, 0, len[aI], TRANS0);
			}
			aI = nxt[aI];
		}

		clear(OFST - 2 * gSize, OFST + 2 * gSize);

		long sCnt = 1;

		update(OFST, 1);
		aI = start[centroid];

		while (aI != -1) {
			int aV = ePt[aI];
			if (!removed[aV]) {
				sCnt = sCnt * compAns(aV, centroid, TRANS1[color[aI]], len[aI], TRANS1) % MOD;
				cPaths(aV, centroid, TRANS1[color[aI]], len[aI], TRANS1);
			}
			aI = nxt[aI];
		}

		clear(OFST - 2 * gSize, OFST + 2 * gSize);

		update(OFST, 1);
		aI = start[centroid];

		while (aI != -1) {
			int aV = ePt[aI];
			if (!removed[aV]) {
				sCnt = sCnt * compAns(aV, centroid, TRANS2[color[aI]], len[aI], TRANS2) % MOD;
				cPaths(aV, centroid, TRANS2[color[aI]], len[aI], TRANS2);
			}
			aI = nxt[aI];
		}

		clear(OFST - 2 * gSize, OFST + 2 * gSize);

		fAns = fAns * aCnt % MOD * inv(sCnt) % MOD;

		aI = start[centroid];
		while (aI != -1) {
			int aV = ePt[aI];
			if (!removed[aV]) {
				decompose(aV);
			}
			aI = nxt[aI];
		}
	}

	int fCentroid(int cV, int pV) {
		dLoop:
		while (true) {
			int aI = start[cV];
			while (aI != -1) {
				int aV = ePt[aI];
				if (aV != pV && !removed[aV] && vCount[aV] > gSize / 2) {
					pV = cV;
					cV = aV;
					continue dLoop;
				}
				aI = nxt[aI];
			}
			return cV;
		}
	}

	void fCount(int cV, int pV) {
		vCount[cV] = 1;
		int aI = start[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && !removed[aV]) {
				fCount(aV, cV);
				vCount[cV] += vCount[aV];
			}
			aI = nxt[aI];
		}
	}

	long[] prod;
	int[] cnt;

	void update(int ind, long uV) {
		while (ind <= OMAX) {
			prod[ind] = prod[ind] * uV % MOD;
			cnt[ind] += 1;
			ind += (ind & -ind);
		}
	}

	Ret query(int qL, int qR) {
		Ret rRes = query(qR);
		Ret lRes = query(qL - 1);
		return new Ret(rRes.cnt - lRes.cnt, rRes.prod * inv(lRes.prod) % MOD);
	}

	Ret query(int qI) {
		long cProd = 1;
		int cCnt = 0;
		while (qI > 0) {
			cProd = cProd * prod[qI] % MOD;
			cCnt += cnt[qI];
			qI -= (qI & -qI);
		}
		return new Ret(cCnt, cProd);
	}

	void clear(int cL, int cR) {
		cL = cL > 1 ? cL : 1;
		cR = cR < OMAX ? cR : OMAX;
		for (int i = cL; i <= cR; i++) {
			int nI = i;
			while (nI <= OMAX) {
				prod[nI] = 1;
				cnt[nI] = 0;
				nI += (nI & -nI);
			}
		}
	}

	class Ret {
		int cnt;
		long prod;

		Ret(int cnt, long prod) {
			this.cnt = cnt;
			this.prod = prod;
		}
	}

	Ret merge(Ret a, Ret b) {
		return new Ret(a.cnt + b.cnt, a.prod * b.prod % MOD);
	}
}