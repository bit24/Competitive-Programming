import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_497B {

	static int[] nDiv = new int[100_001];

	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= 100_000; i++) {
			for (int j = i; j <= 100_000; j += i) {
				nDiv[j]++;
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {
					if (((masks[i] & 0b001) + (masks[j] & 0b010) + (masks[k] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[i] & 0b001) + (masks[k] & 0b010) + (masks[j] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[j] & 0b001) + (masks[i] & 0b010) + (masks[k] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[j] & 0b001) + (masks[k] & 0b010) + (masks[i] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[k] & 0b001) + (masks[i] & 0b010) + (masks[j] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[k] & 0b001) + (masks[j] & 0b010) + (masks[i] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					}
				}
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		while (nT-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int[] lens = { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()) };
			printer.println(process(lens));
		}
		printer.close();
	}

	static final int[] masks = { 0b111, 0b110, 0b101, 0b011, 0b100, 0b010, 0b001, 0b000 };

	static long process(int[] lens) {
		int[] cnts = new int[8];
		for (int i = 0; i < 8; i++) {
			int cMask = masks[i];
			int cGCD = 0;
			for (int j = 0; j < 3; j++) {
				if ((cMask & (1 << j)) != 0) {
					cGCD = gcd(lens[j], cGCD);
				}
			}
			int cCnt = nDiv[cGCD];
			for (int j = 0; j < i; j++) {
				int sMask = masks[j];
				if ((sMask | cMask) == sMask) {
					cCnt -= cnts[j];
				}
			}
			cnts[i] = cCnt;
		}

		long total = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = i; j < 8; j++) {
				for (int k = j; k < 8; k++) {
					if (suf[i][j][k]) {
						if (i == j) {
							if (i == k) {
								total += (cnts[i] + 2) * (cnts[i] + 1) * cnts[i] / 6;
							} else {
								total += (cnts[i] * (cnts[i] - 1) / 2 + cnts[i]) * cnts[k];
							}
						} else if (i == k) {
							total += (cnts[i] * (cnts[i] - 1) / 2 + cnts[i]) * cnts[j];
						} else {
							if (j == k) {
								total += (cnts[j] * (cnts[j] - 1) / 2 + cnts[j]) * cnts[i];
							} else {
								total += cnts[i] * cnts[j] * cnts[k];
							}
						}
					}
				}
			}
		}
		return total;
	}

	static boolean[][][] suf = new boolean[8][8][8];

	static void prepSuf() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {
					if (((masks[i] & 0b001) + (masks[j] & 0b010) + (masks[k] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[i] & 0b001) + (masks[k] & 0b010) + (masks[j] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[j] & 0b001) + (masks[i] & 0b010) + (masks[k] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[j] & 0b001) + (masks[k] & 0b010) + (masks[i] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[k] & 0b001) + (masks[i] & 0b010) + (masks[j] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					} else if (((masks[k] & 0b001) + (masks[j] & 0b010) + (masks[i] & 0b100)) == 0b111) {
						suf[i][j][k] = true;
					}
				}
			}
		}
	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
