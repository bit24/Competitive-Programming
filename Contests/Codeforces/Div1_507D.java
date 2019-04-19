import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_507D {

	static int nV;

	static int sqrtN;

	static int[] lSt;
	static int[] ePt;
	static int[] nxt;

	static int k;

	static int[] ans;

	static int[] computed;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());

		lSt = new int[nV];
		Arrays.fill(lSt, -1);
		ePt = new int[nV * 2];
		nxt = new int[nV * 2];

		int nE = 0;

		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			nxt[nE] = lSt[a];
			ePt[nE] = b;
			lSt[a] = nE++;

			nxt[nE] = lSt[b];
			ePt[nE] = a;
			lSt[b] = nE++;
		}

		sqrtN = (int) Math.sqrt(nV);

		computed = new int[nV + 1];
		Arrays.fill(computed, -1);

		ans = new int[nV + 2];
		for (int i = 1; i <= sqrtN; i++) {
			k = i;
			process();
			ans[i] = nC;
		}

		solveAll(1, sqrtN, 1, nV);

		for (int i = nV; i >= 1; i--) {
			if (ans[i] == 0) {
				ans[i] = ans[i + 1];
			}
		}

		for (int i = 1; i <= nV; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static void solveAll(int l, int r, int sL, int sR) {
		int mid = (l + r) >> 1;
		int res = search(mid, sL, sR);

		if (l <= mid - 1) {
			solveAll(l, mid - 1, res, sR);
		}

		if (res > sqrtN) {
			ans[res] = mid;

			if (mid + 1 <= r) {
				solveAll(mid + 1, r, sL, res);
			}
		}
	}

	static int search(int v, int low, int high) {
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			k = mid;
			process();
			if (nC >= v) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	static void process() {
		if (computed[k] != -1) {
			nC = computed[k];
			return;
		}

		if (k == 1) {
			nC = nV;
		} else {
			dfs(0, -1);
		}
		computed[k] = nC;
	}

	static int nC;
	static int nI;

	static void dfs(int cV, int pV) {
		int tC = 0;
		int mI = 0;

		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			aI = nxt[aI];
			if (aV == pV) {
				continue;
			}

			dfs(aV, cV);
			tC += nC;
			if (mI != -1) {
				if (mI + nI + 1 >= k) {
					mI = -1;
				} else if (nI > mI) {
					mI = nI;
				}
			}
		}

		if (mI == -1) {
			tC++;
		}
		nC = tC;
		nI = mI + 1;
	}
}
