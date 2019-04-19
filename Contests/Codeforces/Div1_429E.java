import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_429E {

	static int nV;

	static int[] vals = new int[50_000];

	static int[] block = new int[50_000];
	static int[] p256 = new int[50_000];

	static int[] cnt = new int[256 * 8 * 2];

	static int[][] maxLower = new int[50_000][256];
	static int[][] maxUpper = new int[50_000][256];

	static int[] ePt = new int[100_000];
	static int[] nxt = new int[100_000];
	static int[] lSt = new int[50_000];

	static int[] par = new int[50_000];
	static int[] ht = new int[50_000];

	static int[] stack = new int[50_000];
	static int sI;

	public static void main(String[] args) throws IOException {
		Arrays.fill(block, -1);
		Arrays.fill(p256, -1);
		Arrays.fill(lSt, -1);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			vals[i] = Integer.parseInt(inputData.nextToken());
		}

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

		prep(0, -1);

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int e = Integer.parseInt(inputData.nextToken()) - 1;
			int s = Integer.parseInt(inputData.nextToken()) - 1;
			printer.println(query(s, e));
		}
		printer.close();
	}

	static int query(int cV, int eV) {
		int max = 0;
		int cDist = 0;
		int cP256 = p256[cV];

		while (cP256 != -1 && ht[cP256] >= ht[eV]) {
			int cMaxUpper = maxUpper[cV][cDist >> 8];
			int cMaxLower = maxLower[cV][cMaxUpper ^ (cDist >> 8)];
			int cur = (cMaxUpper << 8) + cMaxLower;
			max = Math.max(max, cur);
			cV = cP256;
			cP256 = p256[cV];
			cDist += 256;
		}
		while (cV != -1 && cV != eV) {
			int cXor = vals[cV] ^ cDist;
			if (cXor > max) {
				max = cXor;
			}
			cDist++;
			cV = par[cV];
		}
		if (cV == eV) {
			max = Math.max(max, vals[cV] ^ cDist);
		}
		return max;
	}

	static void prep(int cV, int pV) {
		par[cV] = pV;
		stack[sI++] = cV;

		for (int cI = 1; cI <= 256 && cI <= sI; cI++) {
			int cVal = vals[stack[sI - cI]];
			maxLower[cV][cVal >> 8] = Math.max(maxLower[cV][cVal >> 8], (cVal & 255) ^ (cI - 1));
		}

		mTrie(vals[cV] >> 8, 1);
		if (sI > 256) {
			p256[cV] = stack[sI - 257];
			mTrie(vals[stack[sI - 257]] >> 8, -1);
		}

		for (int i = 0; i < 256; i++) {
			maxUpper[cV][i] = qTrie(i);
		}

		int cHt = ht[cV];
		int eI = lSt[cV];
		while (eI != -1) {
			int aV = ePt[eI];
			if (aV != pV) {
				ht[aV] = cHt + 1;
				prep(aV, cV);
			}
			eI = nxt[eI];
		}

		mTrie(vals[cV] >> 8, -1);
		if (sI > 256) {
			mTrie(vals[stack[sI - 257]] >> 8, 1);
		}

		sI--;
	}

	static int qTrie(int query) {
		int cNode = 1;
		int ans = 0;
		for (int i = 7; i >= 0; i--) {
			int cB = (query >> i) & 1;
			if (cnt[2 * cNode + (cB ^ 1)] > 0) {
				ans |= (1 << i);
				cNode = 2 * cNode + (cB ^ 1);
			} else {
				cNode = 2 * cNode + cB;
			}
		}
		return ans;
	}

	static void mTrie(int ind, int dlt) {
		int cNode = 1;
		for (int i = 7; i >= 0; i--) {
			cnt[cNode] += dlt;
			cNode = 2 * cNode + ((ind >> i) & 1);
		}
		cnt[cNode] += dlt;
	}
}
