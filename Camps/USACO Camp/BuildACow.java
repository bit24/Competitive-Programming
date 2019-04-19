import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BuildACow {

	public static void main(String[] args) throws IOException {
		new BuildACow().main();
	}

	int N;
	long T;

	long[] w;
	long[] a;

	int[] rMap;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		T = Long.parseLong(inputData.nextToken());

		w = new long[N];
		a = new long[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			w[i] = Integer.parseInt(inputData.nextToken());
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		ci = new CostIndex[N];
		rMap = new int[N];

		// case 1: right first
		long[] rA = a;
		long[] rW = Arrays.copyOf(w, N);
		for (int i = 0; i < N; i++) {
			rW[i] *= 2;
		}
		long[] rCost = fAns(rA, rW);

		long[] lA = flip1(a);
		lA[0] = Long.MAX_VALUE / 8;
		long[] lW = flip0(w);
		long[] lCost = fAns(lA, lW);

		int ans = 0;
		int lT = N;
		while (lCost[lT] > T) {
			lT--;
		}

		int rT = 0;
		while (lT >= 0) {
			while (lCost[lT] + rCost[rT + 1] <= T) {
				rT++;
			}
			ans = Math.max(ans, lT + rT);
			lT--;
		}

		// case 2: left first
		lA = flip1(a);
		for (int i = 0; i < N; i++) {
			lW[i] *= 2;
		}
		lCost = fAns(lA, lW);

		rA[0] = Long.MAX_VALUE / 8;
		rW = w;
		rCost = fAns(rA, rW);

		lT = N;
		while (lCost[lT] > T) {
			lT--;
		}

		rT = 0;
		while (lT >= 0) {
			while (lCost[lT] + rCost[rT + 1] <= T) {
				rT++;
			}
			ans = Math.max(ans, lT + rT);
			lT--;
		}

		printer.println(ans);
		printer.close();
	}

	long[] flip0(long[] o) { // used for flipping w
		long[] n = Arrays.copyOf(o, o.length);
		for (int i = 0; i < o.length; i++) {
			int sI = o.length - 1 - i;
			n[i] = o[sI];
		}
		return n;
	}

	long[] flip1(long[] o) { // used for flipping a
		long[] n = Arrays.copyOf(o, o.length);
		n[0] = o[0];
		for (int i = 1; i < o.length; i++) {
			int sI = o.length - i;
			n[i] = o[sI];
		}
		return n;
	}

	long[] fAns(long[] a, long[] w) {
		long[] ans = new long[N + 1];

		long[] pW = new long[N];
		for (int i = 1; i < N; i++) {
			pW[i] = pW[i - 1] + w[i - 1];
		}

		for (int i = 0; i < N; i++) {
			ci[i] = new CostIndex(a[i], i);
		}
		Arrays.sort(ci);

		for (int i = 0; i < N; i++) {
			rMap[ci[i].i] = i;
		}

		roots = new Node[N + 1];

		roots[0] = add(build(0, N - 1), 0, N - 1, rMap[0]);
		for (int i = 1; i < N; i++) {
			roots[i] = add(roots[i - 1], 0, N - 1, rMap[i]);
		}

		fAns(ans, a, pW, 0, N, 0, N - 1);
		return ans;
	}

	void fAns(long[] ans, long[] a, long[] pW, int qL, int qR, int iL, int iR) {
		if (iL == iR) {
			for (int cQ = qL; cQ <= qR; cQ++) {
				ans[cQ] = pW[iL] + query(iL, cQ);
			}
			return;
		}

		int cQ = (qL + qR) >> 1;
		long mAns = Long.MAX_VALUE / 8;
		int bI = iL;

		for (int cI = Math.max(iL, cQ - 1); cI <= iR; cI++) {
			long cAns = pW[cI] + query(cI, cQ);
			if (cAns < mAns) {
				mAns = cAns;
				bI = cI;
			}
		}

		ans[cQ] = mAns;

		if (mAns > T) {
			if (cQ < qR) {
				for (int i = cQ + 1; i <= qR; i++) {
					ans[i] = T + 1;
				}
				qR = cQ;
			}
		}

		if (qL < cQ) {
			fAns(ans, a, pW, qL, cQ - 1, iL, bI);
		}
		if (cQ < qR) {
			fAns(ans, a, pW, cQ + 1, qR, bI, iR);
		}
	}

	CostIndex[] ci;
	Node[] roots;

	long query(int r, int nR) {
		if (r + 1 < nR) {
			return Long.MAX_VALUE / 8;
		}

		return query(roots[r], 0, N - 1, nR);
	}

	long query(Node cN, int cL, int cR, int nR) {
		int mid = (cL + cR) >> 1;

		if (cL == cR) {
			if (nR == 0) {
				return 0;
			}
			if (nR == 1) {
				return cN.sumC;
			}
			throw new RuntimeException();
		}

		if (cN.l.cnt >= nR) {
			return query(cN.l, cL, mid, nR);
		} else {
			nR -= cN.l.cnt;
			return cN.l.sumC + query(cN.r, mid + 1, cR, nR);
		}
	}

	Node build(int cL, int cR) {
		if (cL == cR) {
			return new Node();
		} else {
			int mid = (cL + cR) >> 1;
			Node cNode = new Node();
			cNode.l = build(cL, mid);
			cNode.r = build(mid + 1, cR);
			return cNode;
		}
	}

	Node add(Node oNode, int cL, int cR, int uI) {
		Node nNode = new Node();

		if (cL == cR) {
			nNode.sumC = ci[cL].c;
			nNode.cnt = 1;
			return nNode;
		}

		int mid = (cL + cR) >> 1;

		if (uI <= mid) {
			nNode.l = add(oNode.l, cL, mid, uI);
			nNode.r = oNode.r;
		} else {
			nNode.l = oNode.l;
			nNode.r = add(oNode.r, mid + 1, cR, uI);
		}
		nNode.sumC = nNode.l.sumC + nNode.r.sumC;
		nNode.cnt = nNode.l.cnt + nNode.r.cnt;
		return nNode;
	}

	class Node {
		long sumC;
		int cnt;
		Node l;
		Node r;
	}

	class CostIndex implements Comparable<CostIndex> {
		long c;
		int i;

		CostIndex(long c, int i) {
			this.c = c;
			this.i = i;
		}

		public int compareTo(CostIndex o) {
			if (c != o.c) {
				return c < o.c ? -1 : 1;
			}
			return Integer.compare(i, o.i);
		}
	}
}