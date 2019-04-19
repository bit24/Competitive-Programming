import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_45G {

	static int[] num;
	static long[] ans = new long[200_001];

	static int[] lSt;
	static int[] nxt;
	static int[] ePt;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());

		num = new int[nV];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			num[i] = Integer.parseInt(inputData.nextToken());
		}

		lSt = new int[nV];
		Arrays.fill(lSt, -1);
		nxt = new int[nV * 2];
		ePt = new int[nV * 2];

		int nE = 0;
		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;

			nxt[nE] = lSt[a];
			lSt[a] = nE;
			ePt[nE++] = b;

			nxt[nE] = lSt[b];
			lSt[b] = nE;
			ePt[nE++] = a;
		}

		vCnt = new int[nV];
		rmvd = new boolean[nV];

		decompose(0);

		for (int i = 1; i <= 200_000; i++) {
			if (ans[i] != 0) {
				printer.println(i + " " + ans[i]);
			}
		}
		printer.close();
	}

	static int[] fCnt = new int[200_001];
	static int[] fList = new int[161];
	static int fS = 0;

	static int[] nCnt = new int[200_001];
	static int[] nList = new int[161];
	static int nS = 0;

	static int gcd(int a, int b) {
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	static int[] vCnt;

	static int gSize = 0;

	static boolean[] rmvd;

	// whenever something has no par use -1

	// prerequisite: initialize vCount and par
	// the root can be any arbitrary vertex within that "group"
	static void decompose(int root) {
		fCount(root, -1);
		gSize = vCnt[root];
		int centroid = fCentroid(root, -1);

		rmvd[centroid] = true;

		int cenN = num[centroid];
		ans[cenN]++;
		fCnt[cenN]++;
		fList[fS++] = cenN;

		int aI = lSt[centroid];
		while (aI != -1) {
			int aV = ePt[aI];
			if (!rmvd[aV]) {
				dfs(aV, centroid, gcd(cenN, num[aV]));

				for (int i = 0; i < nS; i++) {
					for (int j = 0; j < fS; j++) {
						ans[gcd(nList[i], fList[j])] += (long) nCnt[nList[i]] * fCnt[fList[j]];
					}
				}

				for (int i = 0; i < nS; i++) {
					int cI = nList[i];
					if (fCnt[cI] == 0) {
						fList[fS++] = cI;
					}
					fCnt[cI] += nCnt[cI];
					nCnt[cI] = 0;
				}
				nS = 0;
			}
			aI = nxt[aI];
		}

		for (int i = 0; i < fS; i++) {
			fCnt[fList[i]] = 0;
		}
		fS = 0;

		aI = lSt[centroid];
		while (aI != -1) {
			int aV = ePt[aI];
			if (!rmvd[aV]) {
				decompose(aV);
			}
			aI = nxt[aI];
		}
	}

	static void dfs(int cV, int pV, int cGCD) {
		nCnt[cGCD]++;
		if (nCnt[cGCD] == 1) {
			nList[nS++] = cGCD;
		}
		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && !rmvd[aV]) {
				dfs(aV, cV, gcd(cGCD, num[aV]));
			}
			aI = nxt[aI];
		}
	}

	// prerequisite: fDegree has to be run on the "group" and gSize has to be set to vCount of root
	// returns centroid
	static int fCentroid(int cV, int pV) {
		dLoop:
		while (true) {
			int aI = lSt[cV];
			while (aI != -1) {
				int aV = ePt[aI];
				if (!rmvd[aV] && aV != pV && vCnt[aV] > gSize / 2) {
					pV = cV;
					cV = aV;
					continue dLoop;
				}
				aI = nxt[aI];
			}
			return cV;
		}
	}

	static void fCount(int cV, int pV) {
		vCnt[cV] = 1;
		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && !rmvd[aV]) {
				fCount(aV, cV);
				vCnt[cV] += vCnt[aV];
			}
			aI = nxt[aI];
		}
	}
}
