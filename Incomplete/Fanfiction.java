import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Fanfiction {

	public static void main(String[] args) throws IOException {
		new Fanfiction().main();
	}

	final long BASE1 = 88919;
	final long BASE2 = 103333;

	final long MOD = 1_000_000_123;

	long[] POW1;
	long[] POW2;

	int nA;
	int T;

	void main() throws IOException {
		POW1 = new long[3001];
		POW2 = new long[3001];

		POW1[0] = 1;
		POW2[0] = 1;

		for (int i = 1; i <= 3000; i++) {
			POW1[i] = POW1[i - 1] * BASE1 % MOD;
			POW2[i] = POW2[i - 1] * BASE2 % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nA = Integer.parseInt(inputData.nextToken());
		T = Integer.parseInt(inputData.nextToken());

		String[] a = new String[nA];
		for (int i = 0; i < nA; i++) {
			a[i] = reader.readLine();
		}

		String text = " " + reader.readLine();

		int tLen = text.length() - 1;
		int[] costs = new int[tLen + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= tLen; i++) {
			costs[i] = Integer.parseInt(inputData.nextToken());
		}

		// input end

		TreeSet<Hash> hashes = new TreeSet<>();

		Hash eHash = new Hash(0, 0);
		eHash.str = "";
		hashes.add(eHash);

		Hash[][] preH = new Hash[nA][];

		for (int i = 0; i < nA; i++) {
			String cS = a[i];
			preH[i] = new Hash[cS.length() + 1];

			Hash cH = eHash;
			preH[i][0] = cH;

			for (int j = 0; j < cS.length(); j++) {
				cH = cH.addN(cS.charAt(j));
				cH.str = cS;
				cH.len = j + 1;

				hashes.add(cH);
				preH[i][j + 1] = cH;
			}
		}

		TreeMap<Hash, Integer> hInd = new TreeMap<>();
		int nH = 0;
		for (Hash cH : hashes) {
			hInd.put(cH, nH++);
		}

		long[] hArr = new long[nH];
		int nArrI = 0;
		for (Hash cH : hashes) {
			hArr[nArrI++] = cH.h1;
		}

		int[][] nxtH = new int[nH][T];

		for (Hash cH : hashes) {
			int cHI = hInd.get(cH);

			for (int i = 0; i < T; i++) {
				Hash newH = cH.addN((char) ('a' + i));

				int sI = 0;
				while (!contains(hArr, newH.h1)) {
					if (sI == cH.len) {
						newH.remove((char) ('a' + i), 0);
					} else {
						newH.remove(cH.str.charAt(sI), cH.len - sI);
					}
					sI++;
				}
				nxtH[cHI][i] = hInd.get(newH);
			}
		}

		boolean[] banned = new boolean[nH];

		for (int i = 0; i < nA; i++) {
			banned[hInd.get(preH[i][a[i].length()])] = true;
		}

		ArrayList<Hash> hAL = new ArrayList<>(hashes);
		Collections.sort(hAL, LENCOMP);

		oLoop:
		for (Hash cH : hAL) {
			int cHI = hInd.get(cH);
			if (banned[cHI]) {
				continue;
			}

			Hash newH = new Hash(cH.h1, cH.h2);

			for (int i = 0; i < cH.len; i++) {
				newH.remove(cH.str.charAt(i), cH.len - 1 - i);
				Integer newHI = hInd.get(newH);
				if (newHI != null) {
					if (banned[newHI]) {
						banned[cHI] = true;
					}
					continue oLoop;
				}
			}
		}

		int[][] dp = new int[tLen + 1][nH];

		for (int[] i : dp) {
			Arrays.fill(i, Integer.MAX_VALUE / 2);
		}

		dp[0][0] = 0;

		for (int i = 1; i <= tLen; i++) {
			for (int pH = 0; pH < nH; pH++) {
				for (int nC = 0; nC < T; nC++) {
					int newHI = nxtH[pH][nC];

					if (!banned[newHI]) {
						int newC = dp[i - 1][pH] + ((nC == text.charAt(i) - 'a') ? 0 : costs[i]);

						dp[i][newHI] = Math.min(dp[i][newHI], newC);
					}
				}
			}
		}

		int ans = Integer.MAX_VALUE / 2;
		for (int i = 0; i < nH; i++) {
			ans = Math.min(ans, dp[tLen][i]);
		}
		if (ans >= Integer.MAX_VALUE / 2) {
			printer.println(-1);
		} else {
			printer.println(ans);
		}
		printer.close();
	}

	boolean contains(long[] a, long n) {
		int low = 0;
		int high = a.length - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			if (a[mid] == n) {
				return true;
			}
			if (a[mid] < n) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return low == high && a[low] == n;
	}

	class Hash implements Comparable<Hash> {
		long h1;
		long h2;
		String str;
		int len;

		Hash(long h1, long h2) {
			this.h1 = h1;
			// this.h2 = h2;
		}

		public int compareTo(Hash o) {
			if (h1 != o.h1) {
				return h1 < o.h1 ? -1 : 1;
			}
			return 0;
			// return (h2 < o.h2) ? -1 : ((h2 == o.h2) ? 0 : 1);
		}

		Hash addN(char c) {
			return new Hash((h1 * BASE1 + c) % MOD, /*(h2 * BASE2 + c) % MOD*/ 0);
		}

		void remove(char c, int back) {
			h1 = (h1 + (MOD - POW1[back]) * c + MOD) % MOD;
			// h2 = (h2 + (MOD - POW2[back]) * c + MOD) % MOD;
		}
	}

	Comparator<Hash> LENCOMP = new Comparator<Hash>() {
		public int compare(Hash o1, Hash o2) {
			if (o1.len != o2.len) {
				return o1.len < o2.len ? -1 : 1;
			}
			return o1.compareTo(o2);
		}
	};
}