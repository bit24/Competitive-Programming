import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div2_421E {

	static int[] numPF = new int[1_000_001];
	static int[][] pF = new int[1_000_001][7];
	static int[][] pFC = new int[1_000_001][7];

	static void sieve() {
		for (int cInt = 2; cInt <= 1_000_000; cInt++) {
			if (numPF[cInt] == 0) {

				for (int mult = cInt; mult <= 1_000_000; mult += cInt) {
					int pfI = numPF[mult]++;
					pF[mult][pfI] = cInt;
					pFC[mult][pfI] = 1;
				}

				for (long pow = (long) cInt * cInt; pow <= 1_000_000; pow *= cInt) {
					int powI = (int) pow;
					for (int mult = powI; mult <= 1_000_000; mult += powI) {
						pFC[mult][numPF[mult] - 1]++;
					}
				}
			}
		}
	}

	static int sNumPF;
	static int[] sPF;
	static int[] sPFC;

	public static long compute(int[] nF, int[] mF, int[] sF) {
		long n = (long) nF[0] * nF[1] * nF[2];
		long m = (long) mF[0] * mF[1] * mF[2];

		TreeMap<Integer, Integer> nPFMap = new TreeMap<Integer, Integer>();
		TreeMap<Integer, Integer> sPFMap = new TreeMap<Integer, Integer>();

		for (int i = 0; i < 3; i++) {
			int cF = nF[i];
			for (int j = 0; j < numPF[cF]; j++) {
				int cPrime = pF[cF][j];
				Integer cCnt = nPFMap.get(cPrime);
				if (cCnt == null) {
					nPFMap.put(cPrime, pFC[cF][j]);
				} else {
					nPFMap.put(cPrime, cCnt + pFC[cF][j]);
				}
			}
			cF = sF[i];
			for (int j = 0; j < numPF[cF]; j++) {
				int cPrime = pF[cF][j];
				Integer cCnt = sPFMap.get(cPrime);
				if (cCnt == null) {
					sPFMap.put(cPrime, pFC[cF][j]);
				} else {
					sPFMap.put(cPrime, cCnt + pFC[cF][j]);
				}
			}
		}

		if (sPFMap.containsKey(2)) {
			sPFMap.put(2, sPFMap.get(2) + 1);
		} else {
			sPFMap.put(2, 1);
		}

		// from here on, everything that refers to s is actually 2s

		int nNumPF = 0;
		sNumPF = 0;

		int[] nPF = new int[15];
		sPF = new int[15];
		int[] nPFC = new int[15];
		sPFC = new int[15];

		for (Entry<Integer, Integer> cEntry : nPFMap.entrySet()) {
			nPF[nNumPF] = cEntry.getKey();
			nPFC[nNumPF++] = cEntry.getValue();
		}
		for (Entry<Integer, Integer> cEntry : sPFMap.entrySet()) {
			sPF[sNumPF] = cEntry.getKey();
			sPFC[sNumPF++] = cEntry.getValue();
		}

		int nInd = 0;
		int sInd = 0;

		int nFbdn = 0;
		long[] fbdn = new long[15];

		while (nInd < nNumPF && sInd < sNumPF) {
			if (nPF[nInd] < sPF[sInd]) {
				fbdn[nFbdn++] = nPF[nInd];
				nInd++;
			} else if (nPF[nInd] == sPF[sInd]) {
				if (nPFC[nInd] > sPFC[sInd]) {
					fbdn[nFbdn++] = pow(sPF[sInd], sPFC[sInd] + 1);
				}
				nInd++;
				sInd++;
			} else {
				sInd++;
			}
		}
		
		while (nInd < nNumPF){
			fbdn[nFbdn++] = nPF[nInd];
			nInd++;
		}

		// PIE to count number of integers which are not divisible by any of fbdn
		// can be optimized more

		long cnt = m;
		for (int bSet = 1; bSet < (1 << nFbdn); bSet++) {
			long prod = 1;

			int copy = bSet;
			int cInd = 0;
			int pol = 0;
			while (copy > 0) {
				if ((copy & 1) == 1) {
					prod *= fbdn[cInd];
					pol ^= 1;
				}
				cInd++;
				copy >>= 1;
			}

			if (pol == 1) {
				cnt -= m / prod;
			} else {
				cnt += m / prod;
			}
		}
		
		long phase1 = cnt;

		long phase2 = cntLess(1, 0, n);

		return phase1 + phase2;
	}
	
	static long pow(long base, int exp) {
		long cur = 1;
		while (exp-- > 0) {
			cur *= base;
		}
		return cur;
	}

	static long cntLess(long cProd, int cPI, long bound) {
		if (cProd > bound) {
			return 0;
		}

		if (cPI == sNumPF) {
			return 1;
		}

		long nProd = cProd;
		long sum = 0;
		for (int numU = 0; numU <= sPFC[cPI]; numU++) {
			sum += cntLess(nProd, cPI + 1, bound);
			nProd *= sPF[cPI];
		}
		return sum;
	}

	public static void main(String[] args) throws IOException {
		sieve();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numT = Integer.parseInt(reader.readLine());

		while (numT-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int[] n = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()) };
			inputData = new StringTokenizer(reader.readLine());
			int[] m = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()) };
			inputData = new StringTokenizer(reader.readLine());
			int[] s = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()) };
			printer.println(compute(n, m, s));
		}
		printer.close();
	}

}
