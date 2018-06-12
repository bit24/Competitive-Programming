import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_485E {

	static final long MOD = 1_000_000_007;

	static int[] pDiv = new int[10_000_001];

	static int nV;

	static ArrayList<Integer>[] aList;
	static int[] val;

	static int nQ;

	static int[][] queries;

	static ArrayList<Integer>[] qPrimes = new ArrayList[10_000_001];
	static ArrayList<Integer>[] uPrimes = new ArrayList[10_000_001];

	static int[] h;

	static int[][] anc;

	static int nI = 1;
	static int[] first;
	static int[] last;

	static int[] BIT;

	static long[] ans;

	public static void main(String[] args) throws IOException {
		Arrays.fill(pDiv, -1);
		for (int i = 2; i <= 10_000_000; i++) {
			if (pDiv[i] == -1) {
				for (int m = i; m <= 10_000_000; m += i) {
					if (pDiv[m] == -1) {
						pDiv[m] = i;
					}
				}
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		nV = Integer.parseInt(reader.readLine());
		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		val = new int[nV];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			val[i] = Integer.parseInt(inputData.nextToken());
		}
		nQ = Integer.parseInt(reader.readLine());

		queries = new int[nQ][3];
		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			queries[i][0] = Integer.parseInt(inputData.nextToken()) - 1;
			queries[i][1] = Integer.parseInt(inputData.nextToken()) - 1;
			queries[i][2] = Integer.parseInt(inputData.nextToken());

			int cV = queries[i][2];

			while (cV != 1) {
				int cP = pDiv[cV];
				int pow = 1;
				while (pDiv[cV] == cP) {
					pow *= cP;
					cV /= cP;
					if (qPrimes[pow] == null) {
						qPrimes[pow] = new ArrayList<>();
					}
				}
				qPrimes[pow].add(i);
			}
		}

		for (int i = 0; i < nV; i++) {
			int cV = val[i];
			while (cV != 1) {
				int cP = pDiv[cV];
				int pow = 1;
				while (pDiv[cV] == cP) {
					pow *= cP;
					cV /= cP;
					if (qPrimes[pow] != null) {
						if (uPrimes[pow] == null) {
							uPrimes[pow] = new ArrayList<>();
						}
						uPrimes[pow].add(i);
					}
				}
			}
		}

		prep();

		ans = new long[nQ];
		Arrays.fill(ans, 1);

		for (int i = 2; i <= 10_000_000; i++) {
			if (pDiv[i] == i && qPrimes[i] != null) {

				long cPow = i;

				while (true) {
					if (cPow > 10_000_000 || qPrimes[(int) cPow] == null) {
						break;
					}

					if (uPrimes[(int) cPow] != null) {
						for (int tU : uPrimes[(int) cPow]) {
							updV(tU, 1);
						}
					}

					for (int cQ : qPrimes[(int) cPow]) {
						ans[cQ] = ans[cQ] * exp(i, pathQ(queries[cQ][0], queries[cQ][1])) % MOD;
					}

					cPow = cPow * i;
				}

				if (uPrimes[i] != null) {
					for (int tC : uPrimes[i]) {
						clearV(tC);
					}
				}
			}
		}

		for (int i = 0; i < nQ; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static long exp(long base, long pow) {
		long cur = 1;
		while (pow > 0) {
			if ((pow & 1) != 0) {
				cur = cur * base % MOD;
			}
			base = base * base % MOD;
			pow >>= 1;
		}
		return cur;
	}

	static void updV(int v, int val) {
		BIT_upd(first[v], val);
		BIT_upd(last[v], -val);
	}

	static void clearV(int v) {
		BIT_clear(first[v]);
		BIT_clear(last[v]);
	}

	static int pathQ(int a, int b) {
		int lca = lca(a, b);
		int sum = BIT_query(first[a]) + BIT_query(first[b]) - BIT_query(first[lca]);
		if (anc[lca][0] != lca) {
			sum -= BIT_query(first[anc[lca][0]]);
		}
		return sum;
	}

	static void BIT_clear(int i) {
		while (i <= 2 * nV) {
			BIT[i] = 0;
			i += (i & -i);
		}
	}

	static void BIT_upd(int i, int v) {
		while (i <= 2 * nV) {
			BIT[i] += v;
			i += (i & -i);
		}
	}

	static int BIT_query(int i) {
		int sum = 0;
		while (i > 0) {
			sum += BIT[i];
			i -= (i & -i);
		}
		return sum;
	}

	static void dfs(int cV, int pV) {
		first[cV] = nI++;
		for (int aV : aList[cV]) {
			if (aV != pV) {
				h[aV] = h[cV] + 1;
				anc[aV][0] = cV;
				dfs(aV, cV);
			}
		}
		last[cV] = nI++;
	}

	static void prep() {
		h = new int[nV];

		anc = new int[nV][17];
		anc[0][0] = 0; // so no out of bounds

		first = new int[nV];
		last = new int[nV];

		dfs(0, -1);

		for (int k = 1; k < 17; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}

		BIT = new int[2 * nV + 1];
	}

	static int lca(int a, int b) {
		if (h[a] > h[b]) {
			int t = a;
			a = b;
			b = t;
		}
		int d = h[b] - h[a];
		for (int i = 0; i < 17; i++) {
			if ((d & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}
		if (a == b) {
			return a;
		}

		for (int i = 16; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}
}