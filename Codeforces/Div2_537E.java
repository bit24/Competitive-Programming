import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_537E {

	static final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		new Div2_537E().main();
	}

	public void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		nV = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}
		preprocess();
		BIT = new int[nV + 2];
		marked = new int[nV];

		long[] pDP = new long[301];
		long[] cDP = new long[301];

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int sCnt = Integer.parseInt(inputData.nextToken());
			int maxG = Integer.parseInt(inputData.nextToken());
			qRoot = Integer.parseInt(inputData.nextToken()) - 1;

			VertexDist[] spec = new VertexDist[sCnt];
			for (int i = 0; i < sCnt; i++) {
				spec[i] = new VertexDist(Integer.parseInt(inputData.nextToken()) - 1);
			}
			Arrays.sort(spec);

			for (int i = 0; i < sCnt; i++) {
				mark(spec[i].v, 1);
			}

			Arrays.fill(pDP, 0);
			Arrays.fill(cDP, 0);
			pDP[0] = 1;

			for (int i = 1; i <= sCnt; i++) {
				int nAncM = cntMarked(spec[i - 1].v, qRoot) - 1;

				for (int j = 1; j <= maxG; j++) {
					cDP[j] = (pDP[j - 1] + pDP[j] * (MOD + j - nAncM)) % MOD;
				}
				long[] temp = pDP;
				pDP = cDP;
				cDP = temp;
				Arrays.fill(cDP, 0);
			}

			long ans = 0;
			for (int i = 0; i <= maxG; i++) {
				ans = (ans + pDP[i]) % MOD;
			}
			printer.println(ans);

			for (int i = 0; i < sCnt; i++) {
				mark(spec[i].v, -1);
			}
		}
		printer.close();
	}

	int[] marked;

	void mark(int cV, int delta) {
		update(start[cV], delta);
		update(end[cV] + 1, -delta);
		marked[cV] += delta;
	}

	int cntMarked(int a, int b) { // using subtree updates + prefix queries, we can get O(log N) queries, better
									// than heavy light's O(log^2N)
		int lca = fLCA(a, b);
		return query(start[a]) + query(start[b]) - 2 * query(start[lca]) + marked[lca];
	}

	int[] BIT;

	void update(int ind, int delta) {
		ind++;
		while (ind < BIT.length) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	int query(int ind) {
		ind++;
		int sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}

	int nV;
	ArrayList<Integer>[] aList;

	int root;

	int[][] anc;
	int[] depth;

	int[] start;
	int[] end;
	int cT = 0;

	// good for depth of up to 1_048_576 = 2^20

	void preprocess() {
		anc = new int[nV][21];
		anc[root][0] = root;
		depth = new int[nV];
		start = new int[nV];
		end = new int[nV];
		fParent(root, -1);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	void fParent(int cV, int pV) {
		start[cV] = cT++;
		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}
			anc[aV][0] = cV;
			depth[aV] = depth[cV] + 1;
			fParent(aV, cV);
		}
		end[cV] = cT - 1;
	}

	int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int dif = depth[b] - depth[a];

		for (int i = 0; i <= 20; i++) {
			if ((dif & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}

		if (a == b) {
			return a;
		}

		for (int i = 20; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}

	int fDist(int a, int b) {
		int lca = fLCA(a, b);
		return depth[a] + depth[b] - 2 * depth[lca];
	}

	int qRoot;

	class VertexDist implements Comparable<VertexDist> {
		int v;
		int d;

		VertexDist(int v) {
			this.v = v;
			this.d = fDist(qRoot, v);
		}

		public int compareTo(VertexDist o) {
			return Integer.compare(d, o.d);
		}
	}
}
