import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BalancedCowBreeds {

	static boolean OPEN = true;
	static boolean CLOSE = false;

	static int nE;
	static boolean[] elem;

	static int[] nO;

	static int[][] nW;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("bbreeds.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bbreeds.out")));
		String inputLine = reader.readLine();
		reader.close();

		nE = inputLine.length();
		elem = new boolean[nE];
		for (int i = 0; i < inputLine.length(); i++) {
			elem[i] = inputLine.charAt(i) == '(';
		}

		nO = new int[nE + 1];

		nO[0] = elem[0] ? 1 : 0;
		for (int i = 1; i < nE; i++) {
			nO[i] = nO[i - 1];
			if (elem[i]) {
				nO[i]++;
			} else {
				nO[i]--;
			}
		}

		nW = new int[nE + 1][nE + 1];

		nW[0][0] = 1;
		nW[0][1] = 1;

		for (int i = 0; i < nE; i++) {
			for (int j = 0; j < nE; j++) {
				if (nW[i][j] == 0) {
					continue;
				}
				compute(i, j);
			}
		}
		printer.println(nW[nE - 1][0] % 2012);
		printer.close();
	}

	public static void compute(int cI, int t1O) {
		int type2Opened = nO[cI] - t1O;

		int cNW = nW[cI][t1O] % 2012;

		if (cI + 1 >= nE) {
			return;
		}

		if (elem[cI + 1]) {
			nW[cI + 1][t1O + 1] += cNW;
			nW[cI + 1][t1O] += cNW;
		} else {
			if (t1O > 0) {
				nW[cI + 1][t1O - 1] += cNW;
			}

			if (type2Opened > 0) {
				nW[cI + 1][t1O] += cNW;
			}
		}
	}

}