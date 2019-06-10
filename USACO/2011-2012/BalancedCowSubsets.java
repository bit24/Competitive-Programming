import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class BalancedCowSubsets {

	static int nI;

	static long[] val;

	static int nS;
	static TreeSet<Long>[] sComb;

	static long ans = 0;

	static boolean[] counted;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("subsets.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("subsets.out")));
		nI = Integer.parseInt(reader.readLine());
		val = new long[nI];
		for (int i = 0; i < nI; i++) {
			val[i] = Long.parseLong(reader.readLine());
		}
		reader.close();

		nS = (nI + 1) / 2;

		sComb = new TreeSet[(1 << nS)];

		for (int i = 0; i < (1 << nS); i++) {
			sComb[i] = new TreeSet<Long>();
		}

		sComb[0].add(0L);

		stDfs(nI / 2, 0, 0);

		counted = new boolean[1 << nI];
		fDfs(nI / 2 - 1, 0, 0);
		printer.println(ans - 1);
		printer.close();
	}

	static void stDfs(int cI, long sum, int bSet) {
		if (cI == nI) {
			sComb[bSet].add(Math.abs(sum));
			return;
		}

		bSet <<= 1;
		stDfs(cI + 1, sum + val[cI], bSet | 1);
		stDfs(cI + 1, sum - val[cI], bSet | 1);
		stDfs(cI + 1, sum, bSet);
	}

	static void fDfs(int cI, long sum, int bSet) {
		if (cI == -1) {
			bSet <<= nS;
			for (int i = 0; i < (1 << nS); i++) {
				if (!counted[bSet | i] && sComb[i].contains(Math.abs(sum))) {
					ans++;
					counted[bSet | i] = true;
				}
			}
			return;
		}

		bSet <<= 1;
		fDfs(cI - 1, sum + val[cI], bSet | 1);
		fDfs(cI - 1, sum - val[cI], bSet | 1);
		fDfs(cI - 1, sum, bSet);
	}

}
