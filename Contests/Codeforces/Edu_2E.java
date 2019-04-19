import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_2E {

	static int[] color;

	static int[] ePt;
	static int[] nxt;
	static int[] lSt;

	static int[] size;

	static int[] cnt;
	static int mCnt = 0;
	static long sum = 0;
	
	static long[] ans;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());
		color = new int[nV];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			color[i] = Integer.parseInt(inputData.nextToken()) - 1;
		}

		ePt = new int[2 * nV];
		nxt = new int[2 * nV];
		lSt = new int[nV];
		Arrays.fill(lSt, -1);
		int nE = 0;

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			ePt[nE] = b;
			nxt[nE] = lSt[a];
			lSt[a] = nE++;

			ePt[nE] = a;
			nxt[nE] = lSt[b];
			lSt[b] = nE++;
		}

		size = new int[nV];
		cntSize(0, -1);

		cnt = new int[nV];
		
		ans = new long[nV];
		cntSub(0, -1, true);
		
		for(int i = 0; i < nV; i++) {
			printer.print(ans[i] + " ");
		}
		printer.println();
		printer.close();
	}

	static int cntSize(int cV, int pV) {
		int cSize = 1;
		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV) {
				cSize += cntSize(aV, cV);
			}
			aI = nxt[aI];
		}
		return size[cV] = cSize;
	}

	static void modCnt(int cV, int pV, int delta) {
		cnt[color[cV]] += delta;
		if (delta > 0) {
			if (cnt[color[cV]] == mCnt) {
				sum += color[cV] + 1;
			} else if (cnt[color[cV]] > mCnt) {
				mCnt = cnt[color[cV]];
				sum = color[cV] + 1;
			}
		}
		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV) {
				modCnt(aV, cV, delta);
			}
			aI = nxt[aI];
		}
	}

	static void cntSub(int cV, int pV, boolean keep) {
		int hC = -1;
		int hS = 0;

		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV) {
				if (size[aV] > hS) {
					hC = aV;
					hS = size[aV];
				}
			}
			aI = nxt[aI];
		}

		aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && aV != hC) {
				cntSub(aV, cV, false);
			}
			aI = nxt[aI];
		}

		if (hC != -1) {
			cntSub(hC, cV, true);
		}

		aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (aV != pV && aV != hC) {
				modCnt(aV, cV, 1);
			}
			aI = nxt[aI];
		}
		cnt[color[cV]]++;
		if (cnt[color[cV]] == mCnt) {
			sum += color[cV] + 1;
		} else if (cnt[color[cV]] > mCnt) {
			mCnt = cnt[color[cV]];
			sum = color[cV] + 1;
		}
		
		ans[cV] = sum;

		if (!keep) {
			modCnt(cV, pV, -1);
			mCnt = 0;
			sum = 0;
		}
	}
}
