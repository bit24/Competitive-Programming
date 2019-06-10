import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

class NewBarns {

	static int[] ePt = new int[1_000_000];
	static int[] nxt = new int[1_000_000];
	static int[] lSt = new int[500_000];
	static int nE = 0;

	static boolean[] removed = new boolean[500_000];

	static int[] cLevel = new int[500_000];
	static int[] cPar = new int[500_000];

	static int[][] dist = new int[500_000][17];

	static int cCnt;
	static int[] cnt = new int[500_000];

	static int[][] fDist = new int[500_000][2];
	static int[][] fBranch = new int[500_000][2];

	static boolean[] active = new boolean[500_000];

	public static void main(String[] args) throws IOException {
		Arrays.fill(lSt, -1);
		Arrays.fill(cPar, -1);
		for (int i = 0; i < 500_000; i++) {
			fBranch[i][0] = -1;
			fBranch[i][1] = -2;
		}
		BufferedReader reader = new BufferedReader(new FileReader("newbarn.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("newbarn.out")));
		int nQ = Integer.parseInt(reader.readLine());
		boolean[] query = new boolean[nQ];
		int[] action = new int[nQ];

		int nV = 0;
		for (int i = 0; i < nQ; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("Q")) {
				query[i] = true;
			}
			action[i] = Integer.parseInt(inputData.nextToken()) - 1;
			if (!query[i]) { // build command
				if (action[i] != -2) {
					ePt[nE] = nV;
					nxt[nE] = lSt[action[i]];
					lSt[action[i]] = nE++;

					ePt[nE] = action[i];
					nxt[nE] = lSt[nV];
					lSt[nV] = nE++;
				}
				action[i] = nV++;
			}
		}
		reader.close();

		for (int i = 0; i < nV; i++) {
			if (!removed[i]) {
				prep(i, 0, -1);
			}
		}

		for (int i = 0; i < nQ; i++) {
			if (query[i]) {
				printer.println(query(action[i]));
			} else {
				activate(action[i]);
			}
		}
		printer.close();
	}

	static int query(int cV) {
		int cC = cPar[cV];
		int sC = cV;
		int max = fDist[cV][0]; // mark

		while (cC != -1) {
			if (active[cC]) {
				if (fBranch[cC][0] != sC) {
					max = Math.max(max, dist[cV][cLevel[cC]] + fDist[cC][0]);
				} else {
					max = Math.max(max, dist[cV][cLevel[cC]] + fDist[cC][1]);
				}
			}
			sC = cC;
			cC = cPar[cC];
		}
		return max;
	}

	static void activate(int cV) {
		active[cV] = true;
		int cC = cPar[cV];
		int sC = cV;
		while (cC != -1) {
			int cDist = dist[cV][cLevel[cC]];
			if (cDist > fDist[cC][0]) {
				if (sC != fBranch[cC][0]) {
					fDist[cC][1] = fDist[cC][0];
					fBranch[cC][1] = fBranch[cC][0];
				}
				fDist[cC][0] = cDist;
				fBranch[cC][0] = sC;
			} else if (cDist > fDist[cC][1] && fBranch[cC][0] != sC) {
				fDist[cC][1] = cDist;
				fBranch[cC][1] = sC;
			}
			sC = cC;
			cC = cPar[cC];
		}
	}

	static void prep(int mV, int cCLevel, int cCPar) {
		cCnt = cnt(mV, -1);
		int cent = fCent(mV, -1);
		cLevel[cent] = cCLevel;
		cPar[cent] = cCPar;

		cDist(cent, -1, cCLevel);

		removed[cent] = true;

		int eI = lSt[cent];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV]) {
				prep(aV, cCLevel + 1, cent);
			}
			eI = nxt[eI];
		}
	}

	static void cDist(int cV, int pV, int cCLevel) {
		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV] && aV != pV) {
				dist[aV][cCLevel] = dist[cV][cCLevel] + 1;
				cDist(aV, cV, cCLevel);
			}
			eI = nxt[eI];
		}
	}

	static int fCent(int cV, int pV) {
		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (!removed[aV] && aV != pV && cnt[aV] > cCnt / 2) {
				return fCent(aV, cV);
			}
			eI = nxt[eI];
		}
		return cV;
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
		cnt[cV] = cCnt;
		return cCnt;
	}
}