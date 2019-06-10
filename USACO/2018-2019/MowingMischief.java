import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MowingMischief {

	public static void main(String[] args) throws IOException {
		new MowingMischief().main();
	}

	int N;
	int L;

	Pt[] pts;

	ArrayList<Pt>[] levels;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("mowing.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("mowing.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken()) + 1;
		L = Integer.parseInt(inputData.nextToken());

		pts = new Pt[N];

		for (int i = 0; i < N - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			pts[i] = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		pts[N - 1] = new Pt(L, L);
		Arrays.sort(pts);

		levels = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			levels[i] = new ArrayList<>();
		}
		fLevels();

		for (Pt cPt : levels[0]) {
			cPt.minC = cPt.x * cPt.y;
		}
		
		best = new Pt[N];
		otk = new int[N];

		ArrayList<Pt> impt = new ArrayList<>();
		for (int cLI = 0; !levels[cLI + 1].isEmpty(); cLI++) {
			impt.clear();

			for (Pt cP : levels[cLI]) {
				cP.fDom = fFDom(cP, levels[cLI + 1]);
				cP.lDom = fLDom(cP, levels[cLI + 1]);
				if (cP.fDom != -1) {
					impt.add(cP);
				}
			}

			for (int iSt = 0; iSt < impt.size();) {
				int iEnd = iSt;
				while (iEnd + 1 < impt.size() && impt.get(iEnd + 1).fDom <= impt.get(iSt).lDom) {
					iEnd++;
				}
				cStInt(impt, levels[cLI + 1], iSt, iEnd, impt.get(iSt).fDom, impt.get(iSt).lDom);
				if (impt.get(iEnd).lDom >= impt.get(iSt).lDom + 1) {
					cEndInt(impt, levels[cLI + 1], iSt + 1, iEnd, impt.get(iSt).lDom + 1, impt.get(iEnd).lDom);
				}
				iSt = iEnd + 1;
			}
		}

		printer.println(pts[N - 1].minC);
		printer.close();
	}

	void fLevels() {
		pts[0].level = 0;
		levels[0].add(pts[0]);
		int maxL = 0;
		for (int i = 1; i < pts.length; i++) {
			Pt cPt = pts[i];

			int low = -1;
			int high = maxL;

			while (low != high) {
				int mid = (low + high + 1) >> 1;
				if (levels[mid].get(levels[mid].size() - 1).y < cPt.y) {
					low = mid;
				} else {
					high = mid - 1;
				}
			}

			cPt.level = low + 1;
			levels[low + 1].add(cPt);
			maxL = Math.max(maxL, low + 1);
		}
	}

	int fFDom(Pt cP, ArrayList<Pt> level) {
		int low = 0;
		int high = level.size() - 1;

		while (low != high) {
			int mid = (low + high) >> 1;
			if (level.get(mid).x > cP.x) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		if (level.get(low).x > cP.x && level.get(low).y > cP.y) {
			return low;
		}
		return -1;
	}

	int fLDom(Pt cP, ArrayList<Pt> level) {
		int low = 0;
		int high = level.size() - 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (level.get(mid).y > cP.y) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		if (level.get(low).x > cP.x && level.get(low).y > cP.y) {
			return low;
		}
		return -1;
	}

	long fCost(Pt a, Pt b) {
		return a.minC + (b.x - a.x) * (b.y - a.y);
	}

	int fOtk(ArrayList<Pt> level, Pt a, Pt b) { // a is better than b for everything to the right of return
		int low = 0;
		int high = level.size();

		while (low != high) {
			int mid = (low + high) >> 1;
			Pt cPt = level.get(mid);
			if (fCost(a, cPt) < fCost(b, cPt)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

	Pt[] best;
	int[] otk; // decreasing for st, increasing for end

	void cStInt(ArrayList<Pt> cL, ArrayList<Pt> nL, int cLS, int cLE, int nLS, int nLE) {
		int sz = 0;

		int cPtI = cLS;
		Pt cPt = cL.get(cPtI);
		for (int nPtI = nLS; nPtI <= nLE; nPtI++) {
			Pt nPt = nL.get(nPtI);

			while (cPtI <= cLE && cPt.fDom <= nPtI) { // cPt is now valid
				while (sz >= 2 && otk[sz - 2] <= fOtk(nL, best[sz - 1], cPt)) {
					sz--;
				}
				best[sz++] = cPt;
				if (sz >= 2) {
					otk[sz - 2] = fOtk(nL, best[sz - 2], cPt);
				}
				cPtI++;
				if (cPtI <= cLE) {
					cPt = cL.get(cPtI);
				}
				else {
					cPt = null;
				}
			}

			while (sz >= 2 && otk[sz - 2] <= nPtI) {
				sz--;
			}
			nPt.minC = Math.min(nPt.minC, fCost(best[sz - 1], nPt));
		}
	}

	void cEndInt(ArrayList<Pt> cL, ArrayList<Pt> nL, int cLS, int cLE, int nLS, int nLE) {
		int sz = 0;

		int cPtI = cLE;
		Pt cPt = cL.get(cPtI);
		for (int nPtI = nLE; nPtI >= nLS; nPtI--) {
			Pt nPt = nL.get(nPtI);

			while (cPtI >= cLS && cPt.lDom >= nPtI) { // cPt is now valid
				while (sz >= 2 && otk[sz - 2] >= fOtk(nL, cPt, best[sz - 1])) {
					sz--;
				}
				best[sz++] = cPt;
				if (sz >= 2) {
					otk[sz - 2] = fOtk(nL, cPt, best[sz - 2]);
				}
				cPtI--;
				if (cPtI >= cLS) {
					cPt = cL.get(cPtI);
				}
				else {
					cPt = null;
				}
			}

			while (sz >= 2 && otk[sz - 2] > nPtI) {
				sz--;
			}
			nPt.minC = Math.min(nPt.minC, fCost(best[sz - 1], nPt));
		}
	}

	class Pt implements Comparable<Pt> {
		long x;
		long y;
		int level;
		long minC = Long.MAX_VALUE / 4;
		int fDom;
		int lDom;

		Pt(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Pt o) {
			return x < o.x ? -1 : 1;
		}
	}
}
