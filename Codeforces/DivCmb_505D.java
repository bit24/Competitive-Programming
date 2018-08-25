import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

public class DivCmb_505D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nE = Integer.parseInt(reader.readLine());
		int[] e = new int[nE];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		BitSet[] connect = new BitSet[nE];

		for (int i = 0; i < nE; i++) {
			connect[i] = new BitSet(700);
			for (int j = 0; j < nE; j++) {
				if (gcd(e[i], e[j]) != 1) {
					connect[i].set(j);
				}
			}
		}

		BitSet[] pLeft = new BitSet[nE];
		BitSet[] pRight = new BitSet[nE];

		for (int i = 0; i < nE; i++) {
			pLeft[i] = new BitSet(700);
			pRight[i] = new BitSet(700);
			pLeft[i].set(i);
			pRight[i].set(i);
		}

		BitSet[][] pRoot = new BitSet[nE][nE];

		for (int i = 0; i < nE; i++) {
			for (int j = i; j < nE; j++) {
				pRoot[i][j] = new BitSet(700);
			}
			pRoot[i][i].set(i);
		}

		for (int size = 2; size <= nE; size++) {
			for (int i = 0; i + size - 1 <= nE; i++) {
				if (i >= 1) {
					if (pRoot[i][i + size - 2].intersects(connect[i - 1])) {
						pRight[i + size - 2].set(i - 1);
					}
				}
				if (i + size - 1 < nE) {
					if (pRoot[i][i + size - 2].intersects(connect[i + size - 1])) {
						pLeft[i].set(i + size - 1);
					}
				}
			}

			for (int i = 0; i + size <= nE; i++) {
				pRoot[i][i + size - 1].or(pLeft[i]);
				pRoot[i][i + size - 1].and(pRight[i + size - 1]);
			}
		}

		if (pRoot[0][nE - 1].isEmpty()) {
			printer.println("No");
		} else {
			printer.println("Yes");
		}
		printer.close();
	}

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
