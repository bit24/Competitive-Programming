import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class BullInAChinaShop {

	public static void main(String[] args) throws IOException {
		new BullInAChinaShop().execute();
	}

	static final long[] HB = new long[] { 382737283, 382878283 };
	static final long[] HM = new long[] { 975919579, 975979579 };

	// MNC is doubled because we are going to shift items right to canonicalize it
	static final int MNC = 505 * 2;
	static final int MNR = 505;
	static final int MPOW = MNC * MNR;

	static int aNR;
	static int aNC;

	static long[][] POW = new long[2][MPOW];

	// rotation unaware structures:
	TreeMap<char[][], Integer> pMap = new TreeMap<>(CCHAR);
	int[] pCnt;

	// rotation aware structures
	ArrayList<Integer> pRUI = new ArrayList<>();
	ArrayList<Hashed> pHash = new ArrayList<>();
	ArrayList<int[][]> pSSums = new ArrayList<>();
	ArrayList<int[]> pLNE = new ArrayList<>();

	TreeMap<Hashed, Integer> pHashQ = new TreeMap<Hashed, Integer>();

	void execute() throws IOException {
		for (int i = 0; i < 2; i++) {
			POW[i][0] = 1;
			for (int j = 1; j < MPOW; j++) {
				POW[i][j] = POW[i][j - 1] * HB[i] % HM[i];
			}
		}

		BufferedReader reader = new BufferedReader(new FileReader("bcs.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bcs.out")));
		int nP = Integer.parseInt(reader.readLine());
		char[][] all = processPiece(reader);
		aNR = all.length;
		aNC = all[0].length;

		Hashed aHash = new Hashed(all);
		int[][] aSSums = cSSums(all);
		int[] aLNE = fLNE(aSSums);

		for (int i = 0; i < nP; i++) {
			char[][] nxt = processPiece(reader);
			Integer cnt = pMap.get(nxt);
			pMap.put(nxt, cnt == null ? 1 : cnt + 1);
		}

		pCnt = new int[pMap.size()];

		int cInd = 0;
		for (Entry<char[][], Integer> cE : pMap.entrySet()) {
			char[][] cPat = cE.getKey();
			pCnt[cInd] = cE.getValue();

			for (int nF = 0; nF < 2; nF++) {
				for (int nR = 0; nR < 4; nR++) {
					if (cPat.length <= aNR && cPat[0].length <= aNC) {
						pRUI.add(cInd);

						Hashed cHash = new Hashed(cPat);
						pHash.add(cHash);

						int[][] cSSums = cSSums(cPat);
						pSSums.add(cSSums);

						int[] cLNE = fLNE(cSSums);
						pLNE.add(cLNE);

						// shift the final hash such that the last element will be at [aNR][aNC]
						// this is so that we will never have to translate our test hash left or up
						pHashQ.put(cHash.trans(aNR - cLNE[0], aNC - cLNE[1]), cInd);
					}
					cPat = rotate(cPat);
				}
				cPat = hFlip(cPat);
			}
			cInd++;
		}

		TreeSet<int[]> sols = new TreeSet<int[]>(CINT);

		for (int p1 = 0; p1 < pHash.size(); p1++) {
			int[] cLNE1 = pLNE.get(p1);
			int[] ofst1 = new int[] { aLNE[0] - cLNE1[0], aLNE[1] - cLNE1[1] };
			if (ofst1[0] < 0 || ofst1[1] < 0) {
				continue; // impossible because the pattern will have stuff poking out to the left or top
			}

			Hashed minuend1 = pHash.get(p1).trans(ofst1[0], ofst1[1]);

			int[] sLNE1 = fLNE(aSSums, new int[][][] { pSSums.get(p1) }, new int[][] { ofst1 });
			for (int p2 = 0; p2 < pHash.size(); p2++) {
				int[] cLNE2 = pLNE.get(p2);
				int[] ofst2 = new int[] { sLNE1[0] - cLNE2[0], sLNE1[1] - cLNE2[1] };
				if (ofst2[0] < 0 || ofst2[1] < 0) {
					continue; // same reason
				}

				Hashed minuend2 = pHash.get(p2).trans(ofst2[0], ofst2[1]);

				int[] sLNE2 = fLNE(aSSums, new int[][][] { pSSums.get(p1), pSSums.get(p2) },
						new int[][] { ofst1, ofst2 });
				int[] canOfst = new int[] { aNR - sLNE2[0], aNC - sLNE2[1] }; // canonicalization offset
				if (canOfst[0] < 0 || canOfst[1] < 0) {
					continue; // same reason
				}

				Hashed fHash = aHash.sub(minuend1).sub(minuend2).trans(canOfst[0], canOfst[1]);

				Integer p3 = pHashQ.get(fHash);
				if (p3 != null) {
					int[] sArr = new int[] { pRUI.get(p1), pRUI.get(p2), p3 };
					Arrays.sort(sArr);
					sols.add(sArr);
				}
			}
		}

		int ans = 0;
		for (int[] cSol : sols) {
			int c0 = pCnt[cSol[0]];
			int c1 = pCnt[cSol[1]];
			int c2 = pCnt[cSol[2]];

			if (cSol[0] == cSol[2]) {
				ans += c0 * (c0 - 1) * (c0 - 2) / 6;
			} else if (cSol[0] == cSol[1]) {
				ans += c0 * (c0 - 1) / 2 * c2;
			} else if (cSol[1] == cSol[2]) {
				ans += c0 * (c1) * (c1 - 1) / 2;
			} else {
				ans += c0 * c1 * c2;
			}
		}
		printer.println(ans);
		printer.close();
	}

	char[][] processPiece(BufferedReader reader) throws IOException {
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int iR = Integer.parseInt(inputData.nextToken());
		// int iC = Integer.parseInt(inputData.nextToken());
		char[][] inp = new char[iR][];
		for (int i = 0; i < iR; i++) {
			inp[i] = reader.readLine().toCharArray();
		}

		int uM = Integer.MAX_VALUE;
		int bM = 0;
		int lM = Integer.MAX_VALUE;
		int rM = 0;

		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j < inp[0].length; j++) {
				if (inp[i][j] != '.') {
					uM = Math.min(uM, i);
					bM = Math.max(bM, i);
					lM = Math.min(lM, j);
					rM = Math.max(rM, j);
				}
			}
		}

		char[][] trmd = new char[bM - uM + 1][rM - lM + 1];

		for (int i = uM; i <= bM; i++) {
			for (int j = lM; j <= rM; j++) {
				trmd[i - uM][j - lM] = inp[i][j];
			}
		}

		char[][] min = null;
		for (int nF = 0; nF < 2; nF++) {
			for (int nR = 0; nR < 4; nR++) {
				if (min == null || CCHAR.compare(trmd, min) == -1) {
					min = trmd;
				}
				trmd = rotate(trmd);
			}
			trmd = hFlip(trmd);
		}
		return min;
	}

	char[][] hFlip(char[][] inp) {
		char[][] res = new char[inp.length][inp[0].length];
		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j <= inp[0].length / 2; j++) {
				res[i][j] = inp[i][inp[0].length - 1 - j];
				res[i][inp[0].length - 1 - j] = inp[i][j];
			}
		}
		return res;
	}

	char[][] rotate(char[][] inp) {
		char[][] res = new char[inp[0].length][inp.length];
		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j < inp[0].length; j++) {
				res[inp[0].length - 1 - j][i] = inp[i][j];
			}
		}
		return res;
	}

	static final Comparator<int[]> CINT = new Comparator<int[]>() {
		public int compare(int[] a, int[] b) {
			if (a.length != b.length) {
				return a.length < b.length ? -1 : 1;
			}

			for (int i = 0; i < a.length; i++) {
				if (a[i] != b[i]) {
					return a[i] < b[i] ? -1 : 1;
				}
			}
			return 0;
		}
	};

	static final Comparator<char[][]> CCHAR = new Comparator<char[][]>() {
		public int compare(char[][] a, char[][] b) {
			if (a.length != b.length) {
				return a.length < b.length ? -1 : 1;
			}
			if (a[0].length != b[0].length) {
				return a[0].length < b[0].length ? -1 : 1;
			}

			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[0].length; j++) {
					if (a[i][j] != b[i][j]) {
						return a[i][j] < b[i][j] ? -1 : 1;
					}
				}
			}
			return 0;
		}
	};

	class Hashed implements Comparable<Hashed> {
		long[] hashes;

		Hashed(char[][] pat) {
			hashes = new long[] { 0, 0 };
			for (int i = 0; i < pat.length; i++) {
				for (int j = 0; j < pat[0].length; j++) {
					if (pat[i][j] != '.') {
						hashes[0] = (hashes[0] + POW[0][i * MNC + j] * (pat[i][j] - 'a' + 26)) % HM[0];
						hashes[1] = (hashes[1] + POW[1][i * MNC + j] * (pat[i][j] - 'a' + 26)) % HM[1];
					}
				}
			}
		}

		Hashed(long[] hashes) {
			this.hashes = hashes;
		}

		Hashed trans(int down, int right) {
			return new Hashed(new long[] { hashes[0] * POW[0][down * MNC + right] % HM[0],
					hashes[1] * POW[1][down * MNC + right] % HM[1] });
		}

		public int compareTo(Hashed o) {
			return hashes[0] < o.hashes[0] ? -1 : hashes[0] == o.hashes[0] ? Long.compare(hashes[1], o.hashes[1]) : 1;
		}

		Hashed sub(Hashed minuend) {
			return new Hashed(new long[] { (hashes[0] - minuend.hashes[0] + HM[0]) % HM[0],
					(hashes[1] - minuend.hashes[1] + HM[1]) % HM[1] });
		}
	}

	int[][] cSSums(char[][] inp) {
		int[][] sSums = new int[inp.length][inp[0].length];
		int lst = 0;
		for (int i = inp.length - 1; i >= 0; i--) {
			for (int j = inp[0].length - 1; j >= 0; j--) {
				if (inp[i][j] != '.') {
					lst++;
				}
				sSums[i][j] = lst;
			}
		}
		return sSums;
	}

	int[] fLNE(int[][] base) {
		return fLNE(base, new int[0][][], new int[0][]);
	}

	// returns last non-empty value
	int[] fLNE(int[][] base, int[][][] rmv, int[][] rOff) {
		int bNR = base.length;
		int bNC = base[0].length;

		int low = 0;
		int high = bNR * bNC - 1;
		while (low < high) {
			int mid = (low + high + 1) / 2;
			int cR = mid / bNC;
			int cC = mid % bNC;

			int cCnt = base[cR][cC];
			for (int i = 0; i < rmv.length; i++) {
				int[][] cRmv = rmv[i];
				int rNR = cRmv.length;
				int rNC = cRmv[0].length;
				int aOfR = cR - rOff[i][0];
				int aOfC = cC - rOff[i][1];

				if (aOfC < 0) {
					aOfC = 0;
				} else if (rNC <= aOfC) {
					aOfC = 0;
					aOfR++;
				}

				if (aOfR < 0) {
					cCnt -= cRmv[0][0];
				} else if (aOfR < rNR) {
					cCnt -= cRmv[aOfR][aOfC];
				}
			}
			if (cCnt > 0) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}

		return new int[] { low / bNC, low % bNC };
	}

}
