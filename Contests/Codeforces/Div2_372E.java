import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div2_372E {

	static long M;
	static long[] pow10 = new long[100_000];
	static long[] inv10 = new long[100_000];

	static int nE;
	static int[] ePt = new int[200_000];
	static long[] eVal = new long[200_000];
	static int[] nxt = new int[200_000];
	static int[] lSt = new int[100_000];

	static boolean[] removed = new boolean[100_000];

	static int gCnt;
	static int[] cnt = new int[100_000];

	static TreeMap<Long, Integer> sCnt = new TreeMap<Long, Integer>();

	static long ans;

	public static void main(String[] args) throws IOException {
		Arrays.fill(lSt, -1);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		M = Integer.parseInt(inputData.nextToken());

		pow10[0] = 1;
		for (int i = 1; i < 100_000; i++) {
			pow10[i] = pow10[i - 1] * 10 % M;
		}
		inv10[0] = 1;
		long cInv10 = inv(10, M);
		for (int i = 1; i < 100_000; i++) {
			inv10[i] = inv10[i - 1] * cInv10 % M;
			// assert (pow10[i] * inv10[i] % M == 1);
		}

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			long c = Integer.parseInt(inputData.nextToken()) % M;
			ePt[nE] = b;
			eVal[nE] = c;
			nxt[nE] = lSt[a];
			lSt[a] = nE++;

			ePt[nE] = a;
			eVal[nE] = c;
			nxt[nE] = lSt[b];
			lSt[b] = nE++;
		}

		proc(0);
		printer.println(ans);
		printer.close();
	}

	static long inv(long number, long mod) {
		long dend = number, dendA = 1;

		long dsor = mod, dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor, rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		// assert (dend == 1);
		return (dendA % mod + mod) % mod;
	}

	static void modStruct(int cV, int pV, long cNum, int cDist, int delta) {
		Integer pCnt = sCnt.get(cNum);

		if (pCnt == null) {
			sCnt.put(cNum, delta);
		} else {
			sCnt.put(cNum, pCnt + delta);
		}

		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV] && aV != pV) {
				modStruct(aV, cV, (eVal[eI] * pow10[cDist] + cNum) % M, cDist + 1, delta);
			}
			eI = nxt[eI];
		}
	}

	static void queryStruct(int cV, int pV, long cNum, int cDist) {
		long query = (M - cNum) * inv10[cDist] % M;
		Integer qCnt = sCnt.get(query);
		if (qCnt != null) {
			ans += qCnt;
		}

		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV] && aV != pV) {
				queryStruct(aV, cV, (cNum * 10 + eVal[eI]) % M, cDist + 1);
			}
			eI = nxt[eI];
		}
	}

	static void proc(int mV) {
		gCnt = cnt(mV, -1);
		int cent = fCent(mV, -1);

		modStruct(cent, -1, 0, 0, 1);

		ans += sCnt.get(0L) - 1;

		int eI = lSt[cent];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV]) {
				modStruct(aV, cent, eVal[eI], 1, -1);
				queryStruct(aV, cent, eVal[eI], 1);
				modStruct(aV, cent, eVal[eI], 1, 1);
			}
			eI = nxt[eI];
		}

		sCnt.clear();

		removed[cent] = true;

		eI = lSt[cent];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV]) {
				proc(aV);
			}
			eI = nxt[eI];
		}
	}

	static int cnt(int cV, int pV) {
		int cCnt = 1;
		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV] && aV != pV) {
				cCnt += cnt(aV, cV);
			}
			eI = nxt[eI];
		}
		return cnt[cV] = cCnt;
	}

	static int fCent(int cV, int pV) {
		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV] && aV != pV && cnt[aV] > gCnt / 2) {
				return fCent(aV, cV);
			}
			eI = nxt[eI];
		}
		return cV;
	}
}
