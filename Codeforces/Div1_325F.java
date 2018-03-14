import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Div1_325F {

	static final long MOD = 1_000_000_007;

	static int len;
	static int rLen;

	static int[] low;
	static int[] high;

	static TreeMap<String, Integer> sSI = new TreeMap<>();
	static int nSS = 0;

	static int[][] nxt;
	static boolean[] fin;

	static final int NOBOUND = 0;
	static final int LOWBOUND = 1;
	static final int HIGHBOUND = 2;

	// bound, index, state
	static long[][][] dp;

	static long[][] cnt;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String aStr = reader.readLine();
		String lowS = reader.readLine();
		String highS = reader.readLine();
		len = lowS.length();
		rLen = len / 2;

		low = new int[len];
		high = new int[len];
		for (int i = 0; i < len; i++) {
			low[i] = lowS.charAt(i) - '0';
			high[i] = highS.charAt(i) - '0';
		}

		TreeSet<String> sS = new TreeSet<>();
		for (int sLen = 1; sLen <= rLen; sLen++) {
			for (int l = 0; l + sLen - 1 < aStr.length(); l++) {
				sS.add(aStr.substring(l, l + sLen));
			}
		}
		sS.add("");
		nSS = sS.size();

		fin = new boolean[nSS];

		int nI = 0;
		for (String cString : sS) {
			if (cString.length() == rLen) {
				fin[nI] = true;
			}
			sSI.put(cString, nI++);
		}
		nxt = new int[10][nSS];

		for (Entry<String, Integer> cE : sSI.entrySet()) {
			for (int nC = 0; nC < 10; nC++) {
				String nString = cE.getKey() + nC;
				while (!sSI.containsKey(nString)) {
					nString = nString.substring(1);
				}
				nxt[nC][cE.getValue()] = sSI.get(nString);
			}
		}

		dp = new long[3][len + 1][nSS];
		cnt = new long[3][len + 1];

		for (int cI = len; cI >= 0; cI--) {
			if (cI == len) {
				cnt[NOBOUND][cI] = 1;
				cnt[LOWBOUND][cI] = 1;
				cnt[HIGHBOUND][cI] = 1;
			} else {
				long cSum = 0;
				for (int nC = 0; nC < 10; nC++) {
					cSum = (cSum + cnt[NOBOUND][cI + 1]) % MOD;
				}
				cnt[NOBOUND][cI] = cSum;

				cSum = cnt[LOWBOUND][cI + 1];
				for (int nC = low[cI] + 1; nC < 10; nC++) {
					cSum = (cSum + cnt[NOBOUND][cI + 1]) % MOD;
				}
				cnt[LOWBOUND][cI] = cSum;

				cSum = cnt[HIGHBOUND][cI + 1];
				for (int nC = 0; nC < high[cI]; nC++) {
					cSum = (cSum + cnt[NOBOUND][cI + 1]) % MOD;
				}
				cnt[HIGHBOUND][cI] = cSum;
			}

			for (int cSS = 0; cSS < nSS; cSS++) {
				if (fin[cSS]) {
					dp[NOBOUND][cI][cSS] = cnt[NOBOUND][cI];
					dp[LOWBOUND][cI][cSS] = cnt[LOWBOUND][cI];
					dp[HIGHBOUND][cI][cSS] = cnt[HIGHBOUND][cI];
					continue;
				}
				if (cI == len) {
					continue;
				}

				// process NOBOUND
				long sum = 0;
				for (int nC = 0; nC < 10; nC++) {
					sum = (sum + dp[NOBOUND][cI + 1][nxt[nC][cSS]]) % MOD;
				}
				dp[NOBOUND][cI][cSS] = sum;

				// process LOWBOUND
				sum = dp[LOWBOUND][cI + 1][nxt[low[cI]][cSS]];
				for (int nC = low[cI] + 1; nC < 10; nC++) {
					sum = (sum + dp[NOBOUND][cI + 1][nxt[nC][cSS]]) % MOD;
				}
				dp[LOWBOUND][cI][cSS] = sum;

				// process HIGHBOUND
				sum = dp[HIGHBOUND][cI + 1][nxt[high[cI]][cSS]];
				for (int nC = 0; nC < high[cI]; nC++) {
					sum = (sum + dp[NOBOUND][cI + 1][nxt[nC][cSS]]) % MOD;
				}
				dp[HIGHBOUND][cI][cSS] = sum;
			}
		}

		int sI = 0;
		int cSS = 0;
		boolean finA = false;
		for (; sI < len && low[sI] == high[sI]; sI++) {
			if (fin[cSS]) {
				finA = true;
			}
			cSS = nxt[low[sI]][cSS];
		}
		if (finA || fin[cSS]) {
			if(sI == len) {
				printer.println(1);
				printer.close();
				return;
			}
			long fAns = (cnt[LOWBOUND][sI + 1] + cnt[HIGHBOUND][sI + 1]) % MOD;
			for (int nC = low[sI] + 1; nC < high[sI]; nC++) {
				fAns = (fAns + cnt[NOBOUND][sI + 1]) % MOD;
			}
			printer.println(fAns);
			printer.close();
		}
		
		if(sI == len) {
			printer.println(0);
			printer.close();
			return;
		}

		long fAns = (dp[LOWBOUND][sI + 1][nxt[low[sI]][cSS]] + dp[HIGHBOUND][sI + 1][nxt[high[sI]][cSS]]) % MOD;
		for (int nC = low[sI] + 1; nC < high[sI]; nC++) {
			fAns = (fAns + dp[NOBOUND][sI + 1][nxt[nC][cSS]]) % MOD;
		}
		printer.println(fAns);
		printer.close();
	}
}
