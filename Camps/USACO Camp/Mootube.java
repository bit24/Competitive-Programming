import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Mootube {

	static final long MOD = 1_000_000_123;
	static final long BASE = 100_787;
	static long[] POW;

	static int pF;
	static int pR;
	static int pC;
	static int[][][] pat;

	static long pH;

	static int tF;
	static int tR;
	static int tC;
	static int[][][] txt;

	static long[][][] h1D;
	static long[][][] h2D;

	static int ans = 0;

	public static void main(String[] args) throws IOException {
		POW = new long[500_000];
		POW[0] = 1;
		for (int i = 1; i < 500_000; i++) {
			POW[i] = POW[i - 1] * BASE % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		pF = Integer.parseInt(inputData.nextToken());
		pR = Integer.parseInt(inputData.nextToken());
		pC = Integer.parseInt(inputData.nextToken());
		pat = new int[pF][pR][pC];

		for (int i = 0; i < pF; i++) {
			for (int j = 0; j < pR; j++) {
				inputData = new StringTokenizer(reader.readLine());
				for (int k = 0; k < pC; k++) {
					pat[i][j][k] = Integer.parseInt(inputData.nextToken());
				}
			}
			reader.readLine();
		}

		inputData = new StringTokenizer(reader.readLine());
		tF = Integer.parseInt(inputData.nextToken());
		tR = Integer.parseInt(inputData.nextToken());
		tC = Integer.parseInt(inputData.nextToken());
		txt = new int[tF][tR][tC];

		for (int i = 0; i < tF; i++) {
			for (int j = 0; j < tR; j++) {
				inputData = new StringTokenizer(reader.readLine());
				for (int k = 0; k < tC; k++) {
					txt[i][j][k] = Integer.parseInt(inputData.nextToken());
				}
			}
			if (i != tF - 1) {
				reader.readLine();
			}
		}

		if (pF > tF || pR > tR || pC > tC) {
			printer.println(0);
			printer.close();
			return;
		}

		for (int i = 0; i < pF; i++) {
			for (int j = 0; j < pR; j++) {
				for (int k = 0; k < pC; k++) {
					pH = (pH * BASE + pat[i][j][k]) % MOD;
				}
			}
		}

		comp1D();
		comp2D();
		cnt3D();

		printer.println(ans);
		printer.close();
	}

	static void comp1D() {
		h1D = new long[tF][tR][tC - pC + 1];
		for (int i = 0; i < tF; i++) {
			for (int j = 0; j < tR; j++) {
				long cH = 0;
				for (int k = 0; k < pC; k++) {
					cH = (cH * BASE + txt[i][j][k]) % MOD;
				}
				h1D[i][j][0] = cH;

				for (int k = 1; k + pC - 1 < tC; k++) {
					cH = (cH - POW[pC - 1] * txt[i][j][k - 1] % MOD + MOD) % MOD;
					cH = (cH * BASE) % MOD;
					cH = (cH + txt[i][j][k + pC - 1]) % MOD;
					h1D[i][j][k] = cH;
				}
			}
		}

	}

	static void comp2D() {
		h2D = new long[tF][tR - pR + 1][tC - pC + 1];

		for (int i = 0; i < tF; i++) {
			for (int k = 0; k + pC - 1 < tC; k++) {
				long cH = 0;
				for (int j = 0; j < pR; j++) {
					cH = (cH * POW[pC] + h1D[i][j][k]) % MOD;
				}
				h2D[i][0][k] = cH;

				for (int j = 1; j + pR - 1 < tR; j++) {
					cH = (cH - POW[pC * (pR - 1)] * h1D[i][j - 1][k] % MOD + MOD) % MOD;
					cH = (cH * POW[pC]) % MOD;
					cH = (cH + h1D[i][j + pR - 1][k]) % MOD;
					h2D[i][j][k] = cH;
				}
			}
		}
	}

	static void cnt3D() {
		for (int j = 0; j + pR - 1 < tR; j++) {
			for (int k = 0; k + pC - 1 < tC; k++) {
				long cH = 0;
				for (int i = 0; i < pF; i++) {
					cH = (cH * POW[pC * pR] + h2D[i][j][k]) % MOD;
				}
				if (cH == pH) {
					ans++;
				}

				for (int i = 1; i + pF - 1 < tF; i++) {
					cH = (cH - POW[pC * pR * (pF - 1)] * h2D[i - 1][j][k] % MOD + MOD) % MOD;
					cH = (cH * POW[pC * pR]) % MOD;
					cH = (cH + h2D[i + pF - 1][j][k]) % MOD;
					if (cH == pH) {
						ans++;
					}
				}
			}
		}
	}
}