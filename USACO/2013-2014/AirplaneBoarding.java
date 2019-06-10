import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class AirplaneBoarding {

	static Random rng = new Random();

	static int mH = 14;

	static int mask = 1 << 14;

	static int getRH() {
		return Integer.numberOfTrailingZeros(rng.nextInt() | mask);
	}

	static int[][] nxt;
	static int[][] lazy;

	static int[] posR;
	static int[] timeR;

	static void pushLazy(int cN, int cH) {
		if (cH == 0) {
			return;
		}
		int cLazy = lazy[cN][cH];
		if (cLazy == 0) {
			return;
		}
		lazy[cN][cH - 1] += cLazy;
		for (int sN = nxt[cN][cH - 1]; sN != nxt[cN][cH]; sN = nxt[sN][cH - 1]) {
			posR[sN] -= cLazy;
			lazy[sN][cH - 1] += cLazy;
		}
		lazy[cN][cH] = 0;
	}

	static boolean canreplace(int n1, int n2) {
		return timeR[n1] - posR[n1] >= timeR[n2] - posR[n2];
	}

	public static void main(String[] args) throws IOException {
		long sTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("boarding.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("boarding.out")));
		int nP = Integer.parseInt(reader.readLine());
		int[] dest = new int[nP];
		int[] timeS = new int[nP];
		for (int i = 0; i < nP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			dest[i] = Integer.parseInt(inputData.nextToken());
			timeS[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		nxt = new int[nP + 5][mH + 1];
		lazy = new int[nP + 5][mH + 1];

		posR = new int[nP + 5];
		timeR = new int[nP + 5];

		for (int i = 0; i < nP + 5; i++) {
			Arrays.fill(nxt[i], -1);
		}

		int ans = 0;
		posR[0] = 0;
		timeR[0] = 0;
		int iN = 1;
		for (int cP = nP - 1; cP >= 0; cP--, iN++) {
			posR[iN] = dest[cP];
			int nH = getRH();

			int cN = 0;
			for (int cH = mH; cH >= 0; cH--) {
				while (nxt[cN][cH] != -1 && posR[nxt[cN][cH]] < posR[iN]) {
					cN = nxt[cN][cH];
				}
				pushLazy(cN, cH);
			}

			int eTime = timeR[cN] - posR[cN] + dest[cP] + timeS[cP];
			timeR[iN] = eTime;
			if (eTime > ans) {
				ans = eTime;
			}

			cN = 0;
			posR[0]--;
			for (int cH = mH; cH >= 0; cH--) {
				while (nxt[cN][cH] != -1 && posR[nxt[cN][cH]] < posR[iN]) {
					lazy[cN][cH]++;
					cN = nxt[cN][cH];
					posR[cN]--;
				}
				pushLazy(cN, cH);

				while (nxt[cN][cH] != -1 && canreplace(iN, nxt[cN][cH])) {
					pushLazy(nxt[cN][cH], cH);
					nxt[cN][cH] = nxt[nxt[cN][cH]][cH];
				}

				if (nH >= cH) {
					nxt[iN][cH] = nxt[cN][cH];
					nxt[cN][cH] = iN;
				}
			}
			posR[iN]--;
		}
		printer.println(ans);
		printer.close();
		System.out.println(System.currentTimeMillis() - sTime);
	}

}
