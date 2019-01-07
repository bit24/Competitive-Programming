import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

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

		link = new int[T][3000];
		matI = new int[3000];
		pMat = new int[3000];
		fail = new int[3000];

		nxtS = new int[T][3000];

		addAll(a);

		int[][] dp = new int[tLen + 1][nNode];

		for (int[] i : dp) {
			Arrays.fill(i, Integer.MAX_VALUE / 2);
		}

		dp[0][0] = 0;

		for (int i = 1; i <= tLen; i++) {
			for (int pS = 0; pS < nNode; pS++) {
				for (int nC = 0; nC < T; nC++) {
					int newS = nxtS[nC][pS];

					if (matI[newS] == -1 && pMat[newS] == -1) {
						int newC = dp[i - 1][pS] + ((nC == text.charAt(i) - 'a') ? 0 : costs[i]);

						dp[i][newS] = Math.min(dp[i][newS], newC);
					}
				}
			}
		}

		int ans = Integer.MAX_VALUE / 2;
		for (int i = 0; i < nNode; i++) {
			ans = Math.min(ans, dp[tLen][i]);
		}
		if (ans >= Integer.MAX_VALUE / 2) {
			printer.println(-1);
		} else {
			printer.println(ans);
		}
		printer.close();
	}

	int[][] link;
	int[] matI;
	int[] pMat;
	int[] fail;

	int[][] nxtS;

	int nNode = 1;

	void add(String pat, int pI) {
		int node = 0;
		for (int i = 0; i < pat.length(); i++) {
			if (link[pat.charAt(i) - 'a'][node] == -1) {
				link[pat.charAt(i) - 'a'][node] = nNode++;
			}
			node = link[pat.charAt(i) - 'a'][node];
		}
		matI[node] = pI;
	}

	void addAll(String[] pats) {
		for (int[] a : link) {
			Arrays.fill(a, -1);
		}
		Arrays.fill(matI, -1);
		Arrays.fill(pMat, -1);
		Arrays.fill(fail, -1);

		for (int i = 0; i < pats.length; i++) {
			add(pats[i], i);
		}

		ArrayDeque<Integer> queue = new ArrayDeque<>();
		for (int cC = 0; cC < T; cC++) {
			int nLink = link[cC][0];
			if (nLink != -1) {
				fail[nLink] = 0;
				queue.add(nLink);
				nxtS[cC][0] = nLink;
			} else {
				nxtS[cC][0] = 0;
			}
		}

		while (!queue.isEmpty()) {
			int cur = queue.remove();
			for (int cC = 0; cC < T; cC++) {
				int nLink = link[cC][cur];
				if (nLink != -1) {
					queue.add(nLink);
					int suf = fail[cur];
					while (suf != -1 && link[cC][suf] == -1) {
						suf = fail[suf];
					}
					if (suf == -1) {
						fail[nLink] = 0;
					} else {
						fail[nLink] = link[cC][suf];
					}

					if (matI[fail[nLink]] != -1) {
						pMat[nLink] = fail[nLink];
					} else {
						pMat[nLink] = pMat[fail[nLink]];
					}

					nxtS[cC][cur] = nLink;
				} else {
					int suf = fail[cur];
					while (suf != -1 && link[cC][suf] == -1) {
						suf = fail[suf];
					}
					if (suf == -1) {
						nxtS[cC][cur] = 0;
					} else {
						nxtS[cC][cur] = link[cC][suf];
					}
				}
			}
		}
	}
}