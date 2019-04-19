import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div1_459C {

	int k;
	int x;
	int n;
	int q;

	int[] jCost;

	public static void main(String[] args) throws IOException {
		new Div1_459C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		x = Integer.parseInt(inputData.nextToken());
		k = Integer.parseInt(inputData.nextToken());
		n = Integer.parseInt(inputData.nextToken());
		q = Integer.parseInt(inputData.nextToken());
		jCost = new int[k + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= k; i++) {
			jCost[i] = Integer.parseInt(inputData.nextToken());
		}

		TreeMap<Integer, Long> specs = new TreeMap<Integer, Long>();

		for (int i = 0; i < q; i++) {
			inputData = new StringTokenizer(reader.readLine());
			specs.put(Integer.parseInt(inputData.nextToken()), Long.parseLong(inputData.nextToken()));
		}

		if (!specs.containsKey(n)) {
			specs.put(n, 0L);
		}

		gBase();
		calcPows();

		long[] cSCosts = new long[matS];
		Arrays.fill(cSCosts, Long.MAX_VALUE);
		cSCosts[map[(1 << x) - 1]] = 0;

		int cI;

		for (cI = 1; cI <= n - x; cI++) {
			Integer ceil = specs.ceilingKey(cI);
			if (ceil == null || cI + k < ceil) {
				cSCosts = step(cSCosts, ceil - cI - k);
				cI = ceil - k;
			}
			long[] nSCosts = new long[matS];
			Arrays.fill(nSCosts, Long.MAX_VALUE);
			for (int cS = 0; cS < matS; cS++) {
				if (cSCosts[cS] != Long.MAX_VALUE) {
					int rState = iMap[cS];
					if ((rState & 1) == 1) {
						for (int i = 1; i <= k; i++) {
							if ((rState & (1 << i)) == 0) {
								int nMapped = map[(rState | (1 << i)) >> 1];
								Long add = specs.get(cI + i);
								long sum = cSCosts[cS] + jCost[i] + (add != null ? add : 0);
								if (nSCosts[nMapped] == Long.MAX_VALUE || sum < nSCosts[nMapped]) {
									nSCosts[nMapped] = sum;
								}
							}
						}
					} else {
						if (nSCosts[map[rState >> 1]] == Long.MAX_VALUE || cSCosts[cS] < nSCosts[map[rState >> 1]]) {
							nSCosts[map[rState >> 1]] = cSCosts[cS];
						}
					}
				}
			}
			cSCosts = nSCosts;
		}
		printer.println(cSCosts[map[(1 << x) - 1]]);
		printer.close();
	}

	class Spec implements Comparable<Spec> {
		int i;
		int c;

		Spec(int i, int c) {
			this.i = i;
			this.c = c;
		}

		public int compareTo(Spec o) {
			return Integer.compare(i, o.i);
		}
	}

	int matS;

	int[] map;
	int[] iMap;
	long[][] base;

	void gBase() {
		map = new int[1 << k];
		iMap = new int[70];
		Arrays.fill(map, -1);
		int validC = 0;
		for (int cState = 0; cState < (1 << k); cState++) {
			if (Integer.bitCount(cState) == x) {
				iMap[validC] = cState;
				map[cState] = validC++;
			}
		}

		matS = validC;
		base = new long[matS][matS];
		for (int i = 0; i < matS; i++) {
			Arrays.fill(base[i], Long.MAX_VALUE);
		}

		for (int cState = 0; cState < (1 << k); cState++) {
			int cMapped = map[cState];
			if (cMapped != -1) {
				if ((cState & 1) == 1) {
					for (int j = 1; j <= k; j++) {
						if ((cState & (1 << j)) == 0) {
							int nMapped = map[(cState | (1 << j)) >> 1];
							base[cMapped][nMapped] = jCost[j];
						}
					}
				} else {
					base[cMapped][map[cState >> 1]] = 0;
				}
			}
		}
	}

	long[][] mult(long[][] a, long[][] b) {
		long[][] ret = new long[matS][matS];
		for (int i = 0; i < matS; i++) {
			for (int j = 0; j < matS; j++) {
				long min = Long.MAX_VALUE;
				for (int k = 0; k < matS; k++) {
					if (a[i][k] != Long.MAX_VALUE && b[k][j] != Long.MAX_VALUE) {
						long sum = a[i][k] + b[k][j];
						if (min == Long.MAX_VALUE || sum < min) {
							min = sum;
						}
					}
				}
				ret[i][j] = min;
			}
		}
		return ret;
	}

	long[][][] pows;

	void calcPows() {
		pows = new long[27][][];
		pows[0] = base;
		for (int i = 1; i < 27; i++) {
			pows[i] = mult(pows[i - 1], pows[i - 1]);
		}
	}

	long[][] exp(int pow) {
		long[][] cur = new long[matS][matS];
		for (int i = 0; i < matS; i++) {
			Arrays.fill(cur[i], Long.MAX_VALUE);
			cur[i][i] = 0;
		}
		for (int i = 0; pow > 0; i++) {
			if ((pow & 1) == 1) {
				cur = mult(cur, pows[i]);
			}
			pow >>= 1;
		}
		return cur;
	}

	long[] step(long[] states, int stepC) {
		long[][] delta = exp(stepC);
		long[] ret = new long[matS];

		for (int i = 0; i < matS; i++) {
			long min = Long.MAX_VALUE;
			for (int j = 0; j < matS; j++) {
				if (states[j] != Long.MAX_VALUE && delta[j][i] != Long.MAX_VALUE) {
					long sum = states[j] + delta[j][i];
					if (min == Long.MAX_VALUE || sum < min) {
						min = sum;
					}
				}
			}
			ret[i] = min;
		}
		return ret;
	}

}
