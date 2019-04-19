import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class DivCmb_400F {

	int[] nxtV;
	boolean[] vRem;

	int numV;

	int gVCnt;
	int[] aVert;
	ArrayList<Integer>[] constituents;

	int nE;
	int[] nxtE;
	int[] ePt;
	int[] lSt;

	// centroid decomposition stuff
	boolean[] removed;
	int[] cnt;
	int gCnt;
	int[] color;

	public static void main(String[] args) throws IOException {
		new DivCmb_400F().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numV = Integer.parseInt(inputData.nextToken());
		int numD = Integer.parseInt(inputData.nextToken());

		Diag[] diags = new Diag[numD];
		for (int i = 0; i < numD; i++) {
			inputData = new StringTokenizer(reader.readLine());
			diags[i] = new Diag(Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()) - 1);
		}

		Arrays.sort(diags);

		nxtV = new int[numV];
		for (int i = 0; i < numV - 1; i++) {
			nxtV[i] = i + 1;
		}
		nxtV[numV - 1] = 0;
		
		vRem = new boolean[numV];

		aVert = new int[numV];
		Arrays.fill(aVert, -1);

		constituents = new ArrayList[numV];

		nxtE = new int[numV * 2];
		ePt = new int[numV * 2];
		lSt = new int[numV];
		Arrays.fill(lSt, -1);

		for (Diag cDiag : diags) {
			int cV = cDiag.st;
			int dEnd = (cDiag.st + cDiag.len) % numV;

			int cAVert = gVCnt++;
			ArrayList<Integer> cConstituents = constituents[cAVert] = new ArrayList<>();

			while (cV != dEnd) {
				vRem[cV] = true;
				cConstituents.add(cV);
				if (aVert[cV] != -1) {
					addEdge(cAVert, aVert[cV]);
				}
				cV = nxtV[cV];
			}
			vRem[cDiag.st] = false;
			cConstituents.add(dEnd);

			aVert[cDiag.st] = cAVert;
			nxtV[cDiag.st] = dEnd;
		}
		
		int cAVert = gVCnt++;
		ArrayList<Integer> cConstituents = constituents[cAVert] = new ArrayList<>();
		for(int i = 0; i < numV; i++) {
			if(!vRem[i]) {
				cConstituents.add(i);
				if(aVert[i] != -1) {
					addEdge(cAVert, aVert[i]);
				}
			}
		}

		for (int i = 0; i < gVCnt; i++) {
			Collections.sort(constituents[i]);
			Collections.reverse(constituents[i]);
		}

		removed = new boolean[gVCnt];
		cnt = new int[gVCnt];
		color = new int[gVCnt];

		proc(0, 1);

		Integer[] gVerts = new Integer[gVCnt];
		for (int i = 0; i < gVCnt; i++) {
			gVerts[i] = i;
		}

		Arrays.sort(gVerts, cImport);

		for (int i = 0; i < gVCnt; i++) {
			printer.print(color[gVerts[i]] + " ");
		}
		printer.println();
		printer.close();
	}

	Comparator<Integer> cImport = new Comparator<Integer>() {
		public int compare(Integer v1, Integer v2) {
			ArrayList<Integer> const1 = constituents[v1];
			ArrayList<Integer> const2 = constituents[v2];
			for (int i = 0; i < Math.min(const1.size(), const2.size()); i++) {
				int a1 = const1.get(i);
				int a2 = const2.get(i);
				if (a1 < a2) {
					return -1;
				}
				if (a1 > a2) {
					return 1;
				}
			}
			return Integer.compare(const1.size(), const2.size());
		}
	};

	void proc(int mV, int cColor) {
		gCnt = fCnt(mV, -1);
		int cent = fCent(mV, -1);
		color[cent] = cColor;

		removed[cent] = true;

		int aE = lSt[cent];
		while (aE != -1) {
			int aV = ePt[aE];
			if (!removed[aV]) {
				proc(aV, cColor + 1);
			}
			aE = nxtE[aE];
		}
	}

	int fCent(int cV, int pV) {
		int aE = lSt[cV];
		while (aE != -1) {
			int aV = ePt[aE];
			if (!removed[aV] && aV != pV && cnt[aV] > gCnt / 2) {
				return fCent(aV, cV);
			}
			aE = nxtE[aE];
		}
		return cV;
	}

	int fCnt(int cV, int pV) {
		int cCnt = 1;
		int aE = lSt[cV];
		while (aE != -1) {
			int aV = ePt[aE];
			if (!removed[aV] && aV != pV) {
				cCnt += fCnt(aV, cV);
			}
			aE = nxtE[aE];
		}
		return cnt[cV] = cCnt;
	}

	void addEdge(int u, int v) {
		ePt[nE] = v;
		nxtE[nE] = lSt[u];
		lSt[u] = nE++;

		ePt[nE] = u;
		nxtE[nE] = lSt[v];
		lSt[v] = nE++;
	}

	class Diag implements Comparable<Diag> {
		int st;
		int len;

		Diag(int a, int b) {
			int dist = (b - a + numV) % numV;
			if (dist <= numV / 2) {
				st = a;
				len = dist;
			} else {
				st = b;
				len = numV - dist;
			}
		}

		public int compareTo(Diag o) {
			return Integer.compare(len, o.len);
		}
	}
}
